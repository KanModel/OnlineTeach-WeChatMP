package me.kanmodel.july19.onlineteach.dao;

import me.kanmodel.july19.onlineteach.entity.Favorite;
import me.kanmodel.july19.onlineteach.entity.Post;
import me.kanmodel.july19.onlineteach.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * 收藏的持久化方法
 */
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    Optional<Favorite> findByPostAndUserAndIsDelete(Post post, User user,Boolean isDelete);
    List<Favorite> findAllByPostAndIsDelete(Post post,Boolean isDelete);
    List<Favorite> findAllByUserAndIsDelete(User user, Boolean isDelete);
    List<Favorite> findAllByUserAndIsDeleteFalse(User user);
    Optional<Favorite> findByPostAndUser(Post post, User user);

}
