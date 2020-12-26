package com.example.webflux.api.community.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/**
 * 게시글 엔터티
 *
 * @author always0ne
 * @version 1.0
 */
@NoArgsConstructor
@Getter
public class Post {
    /**
     * pk
     */
    @Id
    private Long postId;
    /**
     * 글 제목
     */
    private String title;
    /**
     * 본문
     */
    private String body;
    /**
     * 조회수
     */
    private Long views;
    /**
     * 댓글수
     */
    private Long commentNum;

    public Post(Long id, String title, String body) {
        super();
        this.postId = id;
        this.title = title;
        this.body = body;
        this.views = (long) 0;
        this.commentNum = (long) 0;
    }

    /**
     * 조회수 증가
     */
    public void increaseViews() {
        this.views++;
    }

    /**
     * 게시글 수정
     * 데이터 변경
     *
     * @param title 글 제목
     * @param body  글 본문
     */
    public void updatePost(String title, String body) {
        this.title = title;
        this.body = body;
    }
}
