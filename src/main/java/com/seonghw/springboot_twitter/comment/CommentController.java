package com.seonghw.springboot_twitter.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public CommentResponse createComment(
            @PathVariable Long postId,
            @RequestBody CommentRequest request) {

        return commentService.createComment(postId, request);
    }

    @GetMapping
    public List<CommentResponse> getComments(@PathVariable Long postId) {
        return commentService.getComments(postId);
    }

    @PutMapping("/{commentId}")
    public CommentResponse updateComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @RequestBody CommentRequest request
    ) {
        return commentService.updateComment(postId, commentId, request);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(
            @PathVariable Long postId,
            @PathVariable Long commentId
    ) {
        commentService.deleteComment(postId, commentId);
    }
}
