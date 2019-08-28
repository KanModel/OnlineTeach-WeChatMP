package me.kanmodel.july19.onlineteach.dao;

import me.kanmodel.july19.onlineteach.entity.Post;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: KanModel
 * Date: 2019-07-09-15:48
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PostRepositoryTest {
    @Autowired
    PostRepository postRepository;

    @Test
    public void findByTitle(){
        int pageNum = 1;
        int pageSize = 3;

        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Page<Post> postPage = postRepository.findAllByTitle("", pageable);

        List<Post> postList = postPage.getContent();

        System.out.println("count:" + postPage.getTotalPages());

        for (Post p: postList){
            System.out.println(p.getTitle());
        }
    }
}