package me.kanmodel.july19.onlineteach.controller;

import me.kanmodel.july19.onlineteach.dao.FavoriteRepository;
import me.kanmodel.july19.onlineteach.dao.OptionRepository;
import me.kanmodel.july19.onlineteach.dao.PostRepository;
import me.kanmodel.july19.onlineteach.dao.UserRepository;
import me.kanmodel.july19.onlineteach.entity.Favorite;
import me.kanmodel.july19.onlineteach.entity.Post;
import me.kanmodel.july19.onlineteach.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;

/**
 * @description: 收藏控制类 接收对收藏进行增删改查的请求
 * @author: KanModel
 * @create: 2019-07-09 10:30
 */
@Controller
@RequestMapping("/favorite")
public class FavoriteController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FavoriteRepository favoriteRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private OptionRepository optionRepository;

    @ModelAttribute
    public void generalModel(Model model){
        model.addAttribute("site_name", optionRepository.findByKey("site_name").get().getValue());
    }

    @RequestMapping("")
    public String listFavorite(Model model,
                               String res) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("fav_list", favoriteRepository.findAllByUserAndIsDeleteFalse(user));
        model.addAttribute("res", res);
        return "favorite/list";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView addFavorite(ModelAndView modelAndView, Long post) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = user.getId();
        //找到這個用戶
        User updateUser = userRepository.findById(userId).get();
//        updateUser.getFavorites().add(new Favorite(postRepository.findById(post).get()));
        //找到这个课程
        Post favPost = postRepository.findById(post).get();
//        如果数据库没有这个字段
        if (!favoriteRepository.findByPostAndUser(favPost, updateUser).isPresent()) {
            updateUser.getFavorites().add(new Favorite(postRepository.findById(post).get()
                    , new Timestamp(System.currentTimeMillis())));
            favoriteRepository.saveAll(updateUser.getFavorites());
        } else if(favoriteRepository.findByPostAndUserAndIsDelete(favPost, updateUser,true).isPresent()){
            //这里添加 之前收藏 删除收藏后又添加收藏的逻辑
            Favorite favorite = favoriteRepository.findByPostAndUserAndIsDelete(favPost, updateUser,true).get();
            favorite.setIsDelete(false);
            favorite.setTime( new Timestamp(System.currentTimeMillis()));
            favoriteRepository.save(favorite);
        }
        else{
            System.out.println("<" + favPost.getTitle() + ">已收藏!");
            modelAndView.addObject("res", "<" + favPost.getTitle() + ">已收藏!");
        }
//        userRepository.save(updateUser);
        modelAndView.setViewName("redirect:/favorite");
        return modelAndView;
    }

    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public ModelAndView deleteFavorite(ModelAndView modelAndView,
                                       Long id) {
        Favorite favorite = favoriteRepository.findById(id).get();
        //将收藏的isDelete改为true 保存到数据库
        favorite.setIsDelete(true);
        favoriteRepository.save(favorite);
        modelAndView.setViewName("redirect:/favorite");
        return modelAndView;
    }
}
