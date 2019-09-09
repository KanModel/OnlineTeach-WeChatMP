package me.kanmodel.july19.onlineteach.dao;

import me.kanmodel.july19.onlineteach.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * 课程的持久化方法
 */
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(nativeQuery = true, value = "select * from sys_post where title like %:title% ")
    Page<Post> findAllByTitle(@Param("title") String title, Pageable pageable);
    @Query(nativeQuery = true, value = "select * from sys_post where title like %:title% AND is_delete = 0")
    Page<Post> findAllByTitleAndIsDelete(@Param("title") String title,Pageable pageable);
    List<Post> findAllByIsDelete(Boolean isDelete);
    Optional<Post>findByIdAndIsDelete(Long id,Boolean isDelete);

}
