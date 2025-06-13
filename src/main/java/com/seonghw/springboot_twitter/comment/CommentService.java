package com.seonghw.springboot_twitter.comment;

import com.seonghw.springboot_twitter.post.Post;
import com.seonghw.springboot_twitter.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentResponse createComment(Long postId, CommentRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        Comment comment = Comment.builder()
                .content(request.content())
                .postId(postId)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Comment newComment = commentRepository.save(comment);
        post.increaseCommentCount();
        postRepository.save(post);

        return CommentResponse.from(newComment);
    }

    public List<CommentResponse> getComments(Long postId) {
        return commentRepository.findByPostIdOrderByIdDesc(postId)
                .stream()
                .map(CommentResponse::from)
                .toList();
    }

    public CommentResponse updateComment(Long postId, Long commentId, CommentRequest request) {
        Comment comment = commentRepository.findByIdAndPostId(commentId, postId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

        comment.updateContent(request.content());
        commentRepository.save(comment);

        return CommentResponse.from(comment);
    }

    public void deleteComment(Long postId, Long commentId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        Comment comment = commentRepository.findByIdAndPostId(commentId, postId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

        post.decreaseCommentCount();
        postRepository.save(post);
        commentRepository.delete(comment);
    }
}