package com.movo.security;

import reactor.core.publisher.Mono;

public interface CurrentUserProvider {
    Mono<UserIdentity> getCurrentUser(org.springframework.web.server.ServerWebExchange exchange);
}
