package me.kanmodel.july19.onlineteach.controller;/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: KanModel
 * Date: 2019-07-08-10:15
 */

import me.kanmodel.july19.onlineteach.dao.FavoriteRepository;
import me.kanmodel.july19.onlineteach.dao.OptionRepository;
import me.kanmodel.july19.onlineteach.dao.PostRepository;
import me.kanmodel.july19.onlineteach.entity.Favorite;
import me.kanmodel.july19.onlineteach.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

/**
 * @description: todo
 * @author: KanModel
 * @create: 2019-07-08 10:15
 */
@Controller
@RequestMapping("/post")
public class PostController {
    @Autowired
    PostRepository postRepository;
    @Autowired
    FavoriteRepository favoriteRepository;
    @Autowired
    private OptionRepository optionRepository;

    @ModelAttribute
    public void generalModel(Model model){
        model.addAttribute("site_name", optionRepository.findByKey("site_name").get().getValue());
    }

    @RequestMapping("/p{id}")
    public String post(Model model,
                       @PathVariable Long id) {
        Optional<Post> op = postRepository.findById(id);
        op.ifPresent(post -> model.addAttribute("post", post));
        return "post/post";
    }

    @RequestMapping("/add")
    public String addPost() {
        return "post/add";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createPost(String title,
                             String content) {
        Timestamp time = new Timestamp(System.currentTimeMillis());
        Post post = new Post(title, content, time);
        Long id = postRepository.save(post).getId();
//        Long id = postRepository.findByCreatedTime(time).getId();
        return "redirect:/post/edit/p" + id;
    }

    @RequestMapping("/edit/p{id}")
    public String editPost(Model model,
                           @PathVariable Long id) {
        Optional<Post> op = postRepository.findById(id);
        op.ifPresent(post -> model.addAttribute("post", post));
        return "post/edit";
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public String modifyPost(Model model,
                             Long id,
                             String title,
                             String content) {
        Optional<Post> op = postRepository.findById(id);
        if (op.isPresent()){
            Post post = op.get();
            post.setTitle(title);
            post.setContent(content);
            post.setModifiedTime(new Timestamp(System.currentTimeMillis()));
            postRepository.save(post);
        }
        return "redirect:/post/edit/p" + id;
    }

    @RequestMapping("/list")
    public String listPost(Model model,
                           @RequestParam(value = "title", required = false, defaultValue = "") String title,
                           @RequestParam(value = "no", defaultValue = "1", required = false) int pageNo,
                           @RequestParam(value = "size", defaultValue = "6", required = false) int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<Post> postPage = postRepository.findAllByTitle(title ,pageable);
//        model.addAttribute("list", postRepository.findAll());
        model.addAttribute("list", postPage.getContent());
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("pageCount", postPage.getTotalPages());
        model.addAttribute("title", title);
        return "/post/list";
    }

    @RequestMapping("/del/p{id}")
    public String deletePost(@PathVariable Long id){
        Post post = postRepository.findById(id).get();
        List<Favorite> favoriteList = favoriteRepository.findAllByPost(post);
        favoriteRepository.deleteAll(favoriteList);
        postRepository.deleteById(id);
        return "redirect:/post/list";
    }
}
