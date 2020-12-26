package com.example.webflux.api.community.service;

import com.example.webflux.api.community.exception.PostNotFoundException;
import com.example.webflux.api.community.model.entity.Post;
import com.example.webflux.api.community.model.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public Mono<Post> savePost(Post post) {
        return postRepository.save(post);
    }

    @Transactional
    public Mono<Post> getPost(Long postId) {
        return postRepository.findByPostId(postId)
                .switchIfEmpty(Mono.error(new PostNotFoundException()))
                .map(post -> {
                    post.increaseViews();
                    return post;
                });
    }
}
