package com.example.webflux.security;

import com.example.webflux.api.community.ErrorResponse;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class JwtErrorWebExceptionHandler extends AbstractErrorWebExceptionHandler {

    public JwtErrorWebExceptionHandler(ErrorAttributes errorAttributes, ResourceProperties resourceProperties, ApplicationContext applicationContext, ServerCodecConfigurer configurer) {
        super(errorAttributes, resourceProperties, applicationContext);
        this.setMessageWriters(configurer.getWriters());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(
                RequestPredicates.all(), this::renderErrorResponse);
    }

    private Mono<ServerResponse> renderErrorResponse(
            ServerRequest request) {

        Map<String, Object> errorPropertiesMap = getErrorAttributes(request,
                ErrorAttributeOptions.of(ErrorAttributeOptions.Include.EXCEPTION));
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "4000", "Unknown Auth Error");
        String exception = (String) errorPropertiesMap.get("exception");
        switch (exception) {
            case "io.jsonwebtoken.security.SignatureException":
                errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN, "0003", "유효하지 않은 토큰입니다.");
                break;
            case "io.jsonwebtoken.MalformedJwtException":
                errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN, "0004", "손상된 토큰입니다.");
                break;
            case "io.jsonwebtoken.ExpiredJwtException":
                errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN, "0006", "만료된 토큰입니다.");
                break;
        }

        return ServerResponse.status(errorResponse.getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(errorResponse));
    }
}