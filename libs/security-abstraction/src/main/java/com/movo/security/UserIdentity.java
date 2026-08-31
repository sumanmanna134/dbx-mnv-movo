package com.movo.security;

import java.util.Objects;
import java.util.UUID;

public record UserIdentity(UUID userId) {
    public UserIdentity{
        Objects.requireNonNull(userId, "userId is required");

    }
    public static UserIdentity of(String userId){
        return new UserIdentity(UUID.fromString(userId));
    }


}
