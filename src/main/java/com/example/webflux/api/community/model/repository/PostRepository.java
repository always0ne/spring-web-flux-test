package com.example.webflux.api.community.model.repository;

import com.example.webflux.api.community.model.entity.Post;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface PostRepository extends ReactiveCrudRepository<Post, Long> {
    Mono<Post> findByPostId(long postId);
}
