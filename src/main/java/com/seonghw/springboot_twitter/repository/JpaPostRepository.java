package com.seonghw.springboot_twitter.repository;

import com.seonghw.springboot_twitter.Post;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaPostRepository extends JpaRepository<Post, Long>, PostRepository {

    @Override
    default List<Post> findAllPaged(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return findAll(pageable).getContent();
    }
}
