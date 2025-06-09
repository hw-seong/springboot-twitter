package com.seonghw.springboot_twitter.repository;

import com.seonghw.springboot_twitter.Post;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryPostRepository implements PostRepository {

    private final Map<Long, Post> posts = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Post save(Post post) {
        long id = post.getId() == null ? idGenerator.getAndIncrement() : post.getId();
        post.setId(id);
        posts.put(id, post);
        return post;
    }

    @Override
    public List<Post> findAll() {
        return new ArrayList<>(posts.values());
    }

    @Override
    public Optional<Post> findById(Long id) {
        return Optional.ofNullable(posts.get(id));
    }

    @Override
    public void deleteById(Long id) {
        posts.remove(id);
    }

    @Override
    public List<Post> findAllPaged(int page, int size) {
        return posts.values()
                .stream()
                .sorted((p1, p2) -> Long.compare(p2.getId(), p1.getId()))
                .skip((long) page * size)
                .limit(size)
                .toList();
    }
}
