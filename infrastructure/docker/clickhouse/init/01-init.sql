-- ============================================================
-- Personal Wealth — ClickHouse Initialization Script
-- Executed on first container start.
-- Creates databases for OLAP services (ADR-013).
-- ============================================================
-- ClickHouse is the OLAP engine for read-heavy aggregation workloads.
-- Data arrives via Kafka consumers in analytics-service — never written
-- directly by OLTP services (transaction-service, account-service, etc.)
-- ============================================================

-- analytics-service database
-- Stores: monthly spend aggregations, category breakdowns, cashflow projections
-- Engine: MergeTree (dev), ReplicatedMergeTree (prod)
-- Access: analytics-service Kafka consumer (writes), analytics-service REST API (reads)
CREATE DATABASE IF NOT EXISTS pw_analytics;

-- financial-coach-service database
-- Stores: FinancialSnapshot materialized views, coaching advice history
-- Access: financial-coach-service (reads only — writes come from analytics-service consumer)
CREATE DATABASE IF NOT EXISTS pw_coach_analytics;

-- ============================================================
-- pw_analytics: Core analytical tables
-- ============================================================

-- Raw transaction events (append-only, partitioned by month)
-- This is the landing table — Kafka consumer in analytics-service writes here.
-- Time complexity: O(1) insert via batch append (MergeTree background merges)
CREATE TABLE IF NOT EXISTS pw_analytics.transaction_events (
    user_id         UUID,
    transaction_id  UUID,
    saga_id         UUID,
    amount_paise    Int64,           -- INR paise (1 INR = 100 paise) — never Float
    currency        LowCardinality(String),  -- 'INR', 'USD' etc.
    category        LowCardinality(String),  -- 'FOOD', 'TRANSPORT', 'SALARY' etc.
    txn_type        LowCardinality(String),  -- 'DEBIT', 'CREDIT'
    txn_status      LowCardinality(String),  -- 'POSTED', 'REVERSED', 'PENDING'
    source          LowCardinality(String),  -- 'SMS', 'EMAIL', 'MANUAL'
    merchant        String,
    txn_time        DateTime,
    txn_month       Date,            -- Materialised partition key (first day of month)
    ingested_at     DateTime DEFAULT now(),
    kafka_offset    Int64            -- Idempotency: track last consumed offset
) ENGINE = MergeTree()
PARTITION BY toYYYYMM(txn_month)
ORDER BY (user_id, txn_time, transaction_id)
SETTINGS index_granularity = 8192;

-- Monthly spend summary per user per category (Materialized View)
-- Pre-aggregated on insert — O(1) dashboard reads regardless of row count
CREATE MATERIALIZED VIEW IF NOT EXISTS pw_analytics.monthly_spend_by_category
ENGINE = SummingMergeTree()
PARTITION BY toYYYYMM(txn_month)
ORDER BY (user_id, txn_month, category)
AS
SELECT
    user_id,
    txn_month,
    category,
    countIf(txn_type = 'DEBIT')             AS debit_count,
    sumIf(amount_paise, txn_type = 'DEBIT') AS total_debit_paise,
    countIf(txn_type = 'CREDIT')            AS credit_count,
    sumIf(amount_paise, txn_type = 'CREDIT') AS total_credit_paise
FROM pw_analytics.transaction_events
WHERE txn_status = 'POSTED'
GROUP BY user_id, txn_month, category;

-- Daily cashflow (net flow per day for trend charts)
CREATE MATERIALIZED VIEW IF NOT EXISTS pw_analytics.daily_cashflow
ENGINE = SummingMergeTree()
PARTITION BY toYYYYMM(txn_date)
ORDER BY (user_id, txn_date)
AS
SELECT
    user_id,
    toDate(txn_time)                          AS txn_date,
    sumIf(amount_paise, txn_type = 'DEBIT')  AS total_debit_paise,
    sumIf(amount_paise, txn_type = 'CREDIT') AS total_credit_paise,
    (total_credit_paise - total_debit_paise)  AS net_cashflow_paise
FROM pw_analytics.transaction_events
WHERE txn_status = 'POSTED'
GROUP BY user_id, toDate(txn_time) AS txn_date;

-- ============================================================
-- pw_coach_analytics: Financial coach read models
-- ============================================================

-- Latest financial snapshot per user (coach reads this for advice generation)
-- Written by analytics-service consumer after each monthly aggregation
CREATE TABLE IF NOT EXISTS pw_coach_analytics.financial_snapshots (
    user_id              UUID,
    snapshot_month       Date,
    salary_paise         Int64,
    total_expense_paise  Int64,
    total_income_paise   Int64,
    savings_paise        Int64,        -- income - expense
    savings_rate_bps     Int32,        -- basis points (savings / income * 10000)
    top_category         LowCardinality(String),
    goal_progress_pct    Float32,      -- average goal completion percentage
    active_loans         Int16,
    snapshot_at          DateTime DEFAULT now()
) ENGINE = ReplacingMergeTree(snapshot_at)
PARTITION BY toYYYYMM(snapshot_month)
ORDER BY (user_id, snapshot_month);

-- Coaching advice history (append-only log — for coach to see past advice)
CREATE TABLE IF NOT EXISTS pw_coach_analytics.coach_advice_history (
    advice_id        UUID,
    user_id          UUID,
    advice_type      LowCardinality(String),   -- 'BUDGET_ALERT', 'GOAL_PROJECTION', 'SAVING_TIP'
    advice_text      String,
    triggered_by     LowCardinality(String),   -- 'BUDGET_THRESHOLD', 'GOAL_DEADLINE', 'MANUAL'
    data_snapshot    String,                   -- JSON of financial data used to generate advice
    created_at       DateTime DEFAULT now()
) ENGINE = MergeTree()
PARTITION BY toYYYYMM(created_at)
ORDER BY (user_id, created_at, advice_id);

-- ============================================================
SELECT 'ClickHouse initialization complete' AS status,
       'pw_analytics + pw_coach_analytics created' AS databases;
