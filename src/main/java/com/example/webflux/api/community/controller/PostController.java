package com.example.webflux.api.community.controller;

import com.example.webflux.security.JwtTokenProvider;
import com.example.webflux.security.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Collections;

import static com.example.webflux.security.JwtTokenProvider.getUserIdFromAuth;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/test")
    String testCont() {
        return jwtTokenProvider.createAccessToken("testUser", Collections.singletonList(UserRole.ROLE_USER));
    }

    @PostMapping("/test")
    Mono<String> testCont2() {
        return getUserIdFromAuth();
    }

}
