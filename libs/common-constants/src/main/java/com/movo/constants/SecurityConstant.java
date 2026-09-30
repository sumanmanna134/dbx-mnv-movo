package com.movo.constants;

import java.util.UUID;

/**
 * Security-related constants — HTTP headers, default identities, etc.
 */
public interface SecurityConstant {
    String USER_ID_HEADER = "X-User-Id";
    UUID DEFAULT_DEV_USER_ID = UUID.fromString("00000000-0000-0000-0000-000000000001");
}
