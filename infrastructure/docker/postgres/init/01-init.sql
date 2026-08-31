-- ============================================================
-- Movo — PostgreSQL Local Development Initialization
-- ============================================================
--
-- PURPOSE
-- -------
-- This script runs automatically ONLY when PostgreSQL initializes
-- a brand-new data directory.
--
-- Responsibilities:
--   1. Create the shared local-development PostgreSQL role.
--   2. Create one database per microservice.
--   3. Enable common PostgreSQL extensions in each service DB.
--
-- IMPORTANT
-- ---------
-- Application tables are NOT created here.
-- Flyway owns application schema/migrations for each service.
--
-- DATABASE OWNERSHIP
-- ------------------
-- Each database is owned by exactly one service.
-- Services MUST NOT directly access another service's database.
--
-- Cross-service communication:
--   REST APIs
--   Kafka events
--
-- ============================================================


-- ============================================================
-- 1. CREATE APPLICATION ROLE
-- ============================================================
--
-- All local services currently use one PostgreSQL role for
-- development simplicity.
--
-- Production should use a dedicated least-privilege DB user
-- per microservice.
--
-- ============================================================

DO
$$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM pg_catalog.pg_roles
        WHERE rolname = 'movo_admin'
    ) THEN

CREATE ROLE movo_admin
    WITH LOGIN
    PASSWORD 'localdev_password';

END IF;
END
$$;


-- ============================================================
-- 2. DEFAULT POSTGRES DATABASE EXTENSIONS
-- ============================================================
--
-- These are enabled in the default postgres DB.
-- Each service DB is also initialized below.
--
-- pgcrypto:
--   gen_random_uuid()
--
-- uuid-ossp:
--   uuid_generate_v4()
--
-- ============================================================

CREATE EXTENSION IF NOT EXISTS pgcrypto;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";


-- ============================================================
-- 3. TRANSACTION SERVICE DATABASE
-- ============================================================
--
-- Owner:
--   transaction-service
--
-- Responsibilities:
--   - canonical transactions
--   - transaction state
--   - balance journal
--   - transaction-related saga state
--
-- ============================================================

CREATE DATABASE movo_transactions
    WITH
        OWNER = movo_admin
        ENCODING = 'UTF8'
        LC_COLLATE = 'en_US.utf8'
        LC_CTYPE = 'en_US.utf8'
        TEMPLATE = template0;


-- ============================================================
-- 4. ACCOUNT SERVICE DATABASE
-- ============================================================
--
-- Responsibilities:
--   - account profiles
--   - salary declarations
--   - bank account metadata
--   - debit cards
--   - credit cards
--   - card limits
--
-- ============================================================

CREATE DATABASE movo_accounts
    WITH
        OWNER = movo_admin
        ENCODING = 'UTF8'
        LC_COLLATE = 'en_US.utf8'
        LC_CTYPE = 'en_US.utf8'
        TEMPLATE = template0;


-- ============================================================
-- 5. INGESTION SERVICE DATABASE
-- ============================================================
--
-- Responsibilities:
--   - SMS ingestion metadata
--   - email ingestion metadata
--   - raw message metadata
--   - fingerprints
--   - duplicate detection
--   - ingestion cursors
--
-- ============================================================

CREATE DATABASE movo_ingestion
    WITH
        OWNER = movo_admin
        ENCODING = 'UTF8'
        LC_COLLATE = 'en_US.utf8'
        LC_CTYPE = 'en_US.utf8'
        TEMPLATE = template0;


-- ============================================================
-- 6. GOAL SERVICE DATABASE
-- ============================================================
--
-- Responsibilities:
--   - financial goals
--   - goal contributions
--   - milestones
--   - goal progress
--   - goal simulation metadata
--
-- ============================================================

CREATE DATABASE movo_goals
    WITH
        OWNER = movo_admin
        ENCODING = 'UTF8'
        LC_COLLATE = 'en_US.utf8'
        LC_CTYPE = 'en_US.utf8'
        TEMPLATE = template0;


-- ============================================================
-- 7. LOAN SERVICE DATABASE
-- ============================================================
--
-- Responsibilities:
--   - loan accounts
--   - EMI information
--   - amortization schedules
--   - repayment history
--   - payoff simulations
--
-- ============================================================

CREATE DATABASE movo_loans
    WITH
        OWNER = movo_admin
        ENCODING = 'UTF8'
        LC_COLLATE = 'en_US.utf8'
        LC_CTYPE = 'en_US.utf8'
        TEMPLATE = template0;


-- ============================================================
-- 8. SPLITWISE SERVICE DATABASE
-- ============================================================
--
-- Responsibilities:
--   - shared expense groups
--   - group memberships
--   - expenses
--   - expense splits
--   - balances
--   - settlements
--
-- ============================================================

CREATE DATABASE movo_splitwise
    WITH
        OWNER = movo_admin
        ENCODING = 'UTF8'
        LC_COLLATE = 'en_US.utf8'
        LC_CTYPE = 'en_US.utf8'
        TEMPLATE = template0;


-- ============================================================
-- 9. SYNC SERVICE DATABASE
-- ============================================================
--
-- Responsibilities:
--   - device registration
--   - sync cursors
--   - local operation tracking
--   - conflict metadata
--   - synchronization state
--
-- ============================================================

CREATE DATABASE movo_sync
    WITH
        OWNER = movo_admin
        ENCODING = 'UTF8'
        LC_COLLATE = 'en_US.utf8'
        LC_CTYPE = 'en_US.utf8'
        TEMPLATE = template0;


-- ============================================================
-- 10. NOTIFICATION SERVICE DATABASE
-- ============================================================
--
-- Responsibilities:
--   - notification metadata
--   - delivery status
--   - retry state
--   - user notification preferences
--
-- ============================================================

CREATE DATABASE movo_notifications
    WITH
        OWNER = movo_admin
        ENCODING = 'UTF8'
        LC_COLLATE = 'en_US.utf8'
        LC_CTYPE = 'en_US.utf8'
        TEMPLATE = template0;


-- ============================================================
-- 11. DATABASE CONNECT PRIVILEGES
-- ============================================================
--
-- Explicitly allow movo_admin to connect to each service DB.
--
-- ============================================================

GRANT CONNECT ON DATABASE movo_transactions  TO movo_admin;
GRANT CONNECT ON DATABASE movo_accounts      TO movo_admin;
GRANT CONNECT ON DATABASE movo_ingestion     TO movo_admin;
GRANT CONNECT ON DATABASE movo_goals         TO movo_admin;
GRANT CONNECT ON DATABASE movo_loans         TO movo_admin;
GRANT CONNECT ON DATABASE movo_splitwise     TO movo_admin;
GRANT CONNECT ON DATABASE movo_sync          TO movo_admin;
GRANT CONNECT ON DATABASE movo_notifications TO movo_admin;


-- ============================================================
-- 12. TRANSACTION SERVICE EXTENSIONS
-- ============================================================

\connect movo_transactions

CREATE EXTENSION IF NOT EXISTS pgcrypto;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";


-- ============================================================
-- 13. ACCOUNT SERVICE EXTENSIONS
-- ============================================================

\connect movo_accounts

CREATE EXTENSION IF NOT EXISTS pgcrypto;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";


-- ============================================================
-- 14. INGESTION SERVICE EXTENSIONS
-- ============================================================

\connect movo_ingestion

CREATE EXTENSION IF NOT EXISTS pgcrypto;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";


-- ============================================================
-- 15. GOAL SERVICE EXTENSIONS
-- ============================================================

\connect movo_goals

CREATE EXTENSION IF NOT EXISTS pgcrypto;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";


-- ============================================================
-- 16. LOAN SERVICE EXTENSIONS
-- ============================================================

\connect movo_loans

CREATE EXTENSION IF NOT EXISTS pgcrypto;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";


-- ============================================================
-- 17. SPLITWISE SERVICE EXTENSIONS
-- ============================================================

\connect movo_splitwise

CREATE EXTENSION IF NOT EXISTS pgcrypto;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";


-- ============================================================
-- 18. SYNC SERVICE EXTENSIONS
-- ============================================================

\connect movo_sync

CREATE EXTENSION IF NOT EXISTS pgcrypto;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";


-- ============================================================
-- 19. NOTIFICATION SERVICE EXTENSIONS
-- ============================================================

\connect movo_notifications

CREATE EXTENSION IF NOT EXISTS pgcrypto;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";


-- ============================================================
-- 20. RETURN TO DEFAULT DATABASE
-- ============================================================

\connect postgres


-- ============================================================
-- 21. COMPLETION MESSAGE
-- ============================================================

\echo '=================================================================='
\echo 'PostgreSQL initialization complete.'
\echo 'Created 8 service-owned databases (ADR-013):'
\echo '  movo_transactions  -> transaction-service'
\echo '  movo_accounts      -> account-service'
\echo '  movo_ingestion     -> ingestion-service'
\echo '  movo_goals         -> goal-service'
\echo '  movo_loans         -> loan-service'
\echo '  movo_splitwise     -> splitwise-service'
\echo '  movo_sync          -> sync-service'
\echo '  movo_notifications -> notification-service'
\echo 'Analytics workloads (analytics-service, financial-coach-service)'
\echo 'use ClickHouse — see infrastructure/docker/clickhouse/init/'
\echo 'Service schemas created by Flyway on first service startup.'
\echo '=================================================================='
