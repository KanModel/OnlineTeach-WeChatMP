package me.kanmodel.july19.onlineteach.dao;

import me.kanmodel.july19.onlineteach.entity.Favorite;
import me.kanmodel.july19.onlineteach.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FavoriteRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FavoriteRepository favoriteRepository;

    @Test
    public void findAllByUserAndIsDeleteTrue() {
        User user = userRepository.findByLogin("admin").get();
        List<Favorite> favoriteList = favoriteRepository.findAllByUserAndIsDeleteFalse(user);
        for (Favorite favorite: favoriteList){
            System.out.println(favorite.getPost().getTitle());
        }
    }

    @Test
    public void findAllByUserAndIsDelete() {
        User user = userRepository.findByLogin("admin").get();
        List<Favorite> favoriteList = favoriteRepository.findAllByUserAndIsDelete(user, false);
        for (Favorite favorite: favoriteList){
            System.out.println(favorite.getPost().getTitle());
        }
    }
}