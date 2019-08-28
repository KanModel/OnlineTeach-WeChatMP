package me.kanmodel.july19.onlineteach.entity;

import me.kanmodel.july19.onlineteach.dao.PostRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: KanModel
 * Date: 2019-07-09-8:02
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostTest {
    @Autowired
    PostRepository postRepository;

    @Test
    public void getCreatedTime() {
        Post post = postRepository.findById(1L).get();
        Timestamp create = post.getCreatedTime();
        System.out.println(create.getTime());
        SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH mm ss");
        System.out.println(time.format(create.getTime()));

        System.out.println(post.getCreatedTimeFormat());
    }
}