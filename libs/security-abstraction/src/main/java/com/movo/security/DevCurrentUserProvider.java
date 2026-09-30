package com.movo.security;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;
@Component
@Profile({"local", "dev", "test", "default"})
public class DevCurrentUserProvider implements CurrentUserProvider{
    public static final String USER_ID_HEADER = "X-User-Id";
    public static final UUID DEFAULT_DEV_USER_ID = UUID.fromString("00000000-0000-0000-0000-000000000001");

    @Override
    public Mono<UserIdentity> getCurrentUser(ServerWebExchange exchange) {
        String headerValue = exchange.getRequest().getHeaders().getFirst(USER_ID_HEADER);
        if(headerValue!=null && !headerValue.isBlank()){
            try {

                return Mono.just(new UserIdentity(UUID.fromString(headerValue)));
            }catch (Exception e){
                return Mono.error(new IllegalArgumentException("Invalid UUID in X-User-Id header: " + headerValue));

            }

        }
        return Mono.just(new UserIdentity(DEFAULT_DEV_USER_ID));
    }
}
