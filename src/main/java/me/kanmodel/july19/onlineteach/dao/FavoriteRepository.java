package me.kanmodel.july19.onlineteach.dao;

import me.kanmodel.july19.onlineteach.entity.Favorite;
import me.kanmodel.july19.onlineteach.entity.Post;
import me.kanmodel.july19.onlineteach.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    Optional<Favorite> findByPostAndUser(Post post, User user);
    List<Favorite> findAllByPost(Post post);
}
