package me.kanmodel.july19.onlineteach.controller.rest;

import me.kanmodel.july19.onlineteach.dao.PostRepository;
import me.kanmodel.july19.onlineteach.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostRestController {
    @Autowired
    private PostRepository postRepository;

    @RequestMapping("/list")
    private ResponseEntity<List<Post>> getPostList() {
        return new ResponseEntity<>(postRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping("/{postid}")
    private ResponseEntity<Post> getPost(@PathVariable long postid) {
        return new ResponseEntity<>(postRepository.findById(postid).get(), HttpStatus.OK);
    }
}
