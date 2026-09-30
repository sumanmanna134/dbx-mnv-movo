-- ============================================================
-- transaction-sore — Transaction Service V1 Migration
-- Declarative Range Partitioning by Month (ADR-012)
-- Transactional Outbox for Kafka Reliability (ADR-005)
-- Distributed Saga Compensation Log (ADR-011)
-- ============================================================

-- 1. Partitioned Transactions Table (Hyper-partitioned by monthly intervals)
CREATE TABLE IF NOT EXISTS transactions (
    id                  UUID NOT NULL,
    saga_id             UUID NOT NULL,
    user_id             UUID NOT NULL,
    amount              NUMERIC(15, 2) NOT NULL,
    currency            VARCHAR(3) NOT NULL,
    type                VARCHAR(10) NOT NULL,
    category            VARCHAR(50) NOT NULL,
    merchant            VARCHAR(255),
    source              VARCHAR(20) NOT NULL,
    status              VARCHAR(30) NOT NULL,
    fingerprint         VARCHAR(64) NOT NULL,
    card_id             UUID,
    account_id          UUID,
    notes               TEXT,
    confirmed_by_user   BOOLEAN NOT NULL DEFAULT FALSE,
    confidence_score    NUMERIC(3, 2),
    txn_time            TIMESTAMPTZ NOT NULL,
    created_at          TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    PRIMARY KEY (id, txn_time)
) PARTITION BY RANGE (txn_time);

-- 2. Pre-created Monthly Partitions (2026 - 2027)
CREATE TABLE IF NOT EXISTS transactions_2026_01 PARTITION OF transactions
    FOR VALUES FROM ('2026-01-01 00:00:00+00') TO ('2026-02-01 00:00:00+00');
CREATE TABLE IF NOT EXISTS transactions_2026_02 PARTITION OF transactions
    FOR VALUES FROM ('2026-02-01 00:00:00+00') TO ('2026-03-01 00:00:00+00');
CREATE TABLE IF NOT EXISTS transactions_2026_03 PARTITION OF transactions
    FOR VALUES FROM ('2026-03-01 00:00:00+00') TO ('2026-04-01 00:00:00+00');
CREATE TABLE IF NOT EXISTS transactions_2026_04 PARTITION OF transactions
    FOR VALUES FROM ('2026-04-01 00:00:00+00') TO ('2026-05-01 00:00:00+00');
CREATE TABLE IF NOT EXISTS transactions_2026_05 PARTITION OF transactions
    FOR VALUES FROM ('2026-05-01 00:00:00+00') TO ('2026-06-01 00:00:00+00');
CREATE TABLE IF NOT EXISTS transactions_2026_06 PARTITION OF transactions
    FOR VALUES FROM ('2026-06-01 00:00:00+00') TO ('2026-07-01 00:00:00+00');
CREATE TABLE IF NOT EXISTS transactions_2026_07 PARTITION OF transactions
    FOR VALUES FROM ('2026-07-01 00:00:00+00') TO ('2026-08-01 00:00:00+00');
CREATE TABLE IF NOT EXISTS transactions_2026_08 PARTITION OF transactions
    FOR VALUES FROM ('2026-08-01 00:00:00+00') TO ('2026-09-01 00:00:00+00');
CREATE TABLE IF NOT EXISTS transactions_2026_09 PARTITION OF transactions
    FOR VALUES FROM ('2026-09-01 00:00:00+00') TO ('2026-10-01 00:00:00+00');
CREATE TABLE IF NOT EXISTS transactions_2026_10 PARTITION OF transactions
    FOR VALUES FROM ('2026-10-01 00:00:00+00') TO ('2026-11-01 00:00:00+00');
CREATE TABLE IF NOT EXISTS transactions_2026_11 PARTITION OF transactions
    FOR VALUES FROM ('2026-11-01 00:00:00+00') TO ('2026-12-01 00:00:00+00');
CREATE TABLE IF NOT EXISTS transactions_2026_12 PARTITION OF transactions
    FOR VALUES FROM ('2026-12-01 00:00:00+00') TO ('2027-01-01 00:00:00+00');

CREATE TABLE IF NOT EXISTS transactions_2027_01 PARTITION OF transactions
    FOR VALUES FROM ('2027-01-01 00:00:00+00') TO ('2027-02-01 00:00:00+00');
CREATE TABLE IF NOT EXISTS transactions_2027_02 PARTITION OF transactions
    FOR VALUES FROM ('2027-02-01 00:00:00+00') TO ('2027-03-01 00:00:00+00');
CREATE TABLE IF NOT EXISTS transactions_2027_03 PARTITION OF transactions
    FOR VALUES FROM ('2027-03-01 00:00:00+00') TO ('2027-04-01 00:00:00+00');
CREATE TABLE IF NOT EXISTS transactions_2027_04 PARTITION OF transactions
    FOR VALUES FROM ('2027-04-01 00:00:00+00') TO ('2027-05-01 00:00:00+00');
CREATE TABLE IF NOT EXISTS transactions_2027_05 PARTITION OF transactions
    FOR VALUES FROM ('2027-05-01 00:00:00+00') TO ('2027-06-01 00:00:00+00');
CREATE TABLE IF NOT EXISTS transactions_2027_06 PARTITION OF transactions
    FOR VALUES FROM ('2027-06-01 00:00:00+00') TO ('2027-07-01 00:00:00+00');
CREATE TABLE IF NOT EXISTS transactions_2027_07 PARTITION OF transactions
    FOR VALUES FROM ('2027-07-01 00:00:00+00') TO ('2027-08-01 00:00:00+00');
CREATE TABLE IF NOT EXISTS transactions_2027_08 PARTITION OF transactions
    FOR VALUES FROM ('2027-08-01 00:00:00+00') TO ('2027-09-01 00:00:00+00');
CREATE TABLE IF NOT EXISTS transactions_2027_09 PARTITION OF transactions
    FOR VALUES FROM ('2027-09-01 00:00:00+00') TO ('2027-10-01 00:00:00+00');
CREATE TABLE IF NOT EXISTS transactions_2027_10 PARTITION OF transactions
    FOR VALUES FROM ('2027-10-01 00:00:00+00') TO ('2027-11-01 00:00:00+00');
CREATE TABLE IF NOT EXISTS transactions_2027_11 PARTITION OF transactions
    FOR VALUES FROM ('2027-11-01 00:00:00+00') TO ('2027-12-01 00:00:00+00');
CREATE TABLE IF NOT EXISTS transactions_2027_12 PARTITION OF transactions
    FOR VALUES FROM ('2027-12-01 00:00:00+00') TO ('2028-01-01 00:00:00+00');

-- Default partition for historical or far-future transactions
CREATE TABLE IF NOT EXISTS transactions_default PARTITION OF transactions DEFAULT;

-- 3. Partitioned B-Tree Indexes
CREATE INDEX IF NOT EXISTS idx_transactions_user_time ON transactions (user_id, txn_time DESC);
CREATE INDEX IF NOT EXISTS idx_transactions_fingerprint ON transactions (user_id, fingerprint, txn_time);
CREATE INDEX IF NOT EXISTS idx_transactions_saga ON transactions (saga_id);
CREATE INDEX IF NOT EXISTS idx_transactions_status ON transactions (status);
CREATE INDEX IF NOT EXISTS idx_transactions_lookup_id ON transactions (id);

-- 4. Transactional Outbox Table (ADR-005)
CREATE TABLE IF NOT EXISTS outbox_events (
    id                  UUID PRIMARY KEY,
    event_type          VARCHAR(100) NOT NULL,
    aggregate_id        UUID NOT NULL,
    aggregate_type      VARCHAR(50) NOT NULL,
    user_id             UUID NOT NULL,
    saga_id             UUID,
    payload             TEXT NOT NULL,
    published           BOOLEAN NOT NULL DEFAULT FALSE,
    retry_count         INT NOT NULL DEFAULT 0,
    created_at          TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    published_at        TIMESTAMPTZ
);

CREATE INDEX IF NOT EXISTS idx_outbox_unpublished ON outbox_events (published, created_at) WHERE published = FALSE;

-- 5. Saga Compensations Table (ADR-011: Idempotent Compensating Actions)
CREATE TABLE IF NOT EXISTS saga_compensations (
    saga_id             UUID PRIMARY KEY,
    transaction_id      UUID NOT NULL,
    user_id             UUID NOT NULL,
    reason              VARCHAR(255) NOT NULL,
    status              VARCHAR(30) NOT NULL,
    compensated_at      TIMESTAMPTZ NOT NULL DEFAULT NOW()
);
