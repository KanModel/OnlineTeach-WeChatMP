package me.kanmodel.july19.onlineteach.controller;/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: KanModel
 * Date: 2019-07-04-18:25
 */

import me.kanmodel.july19.onlineteach.dao.OptionRepository;
import me.kanmodel.july19.onlineteach.dao.PostRepository;
import me.kanmodel.july19.onlineteach.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * @description: todo
 * @author: KanModel
 * @create: 2019-07-04 18:25
 */
@Controller
public class MainController {
    @Autowired
    private OptionRepository optionRepository;
    @Autowired
    private PostRepository postRepository;

    @ModelAttribute
    public void generalModel(Model model){
        model.addAttribute("site_name", optionRepository.findByKey("site_name").get().getValue());
    }

    @RequestMapping(value = {"/index", "/"})
    public String index(Model model) {
        model.addAttribute("list", postRepository.findAll());
        model.addAttribute("index_title", optionRepository.findByKey("index_title").get().getValue());
        model.addAttribute("index_subtitle", optionRepository.findByKey("index_subtitle").get().getValue());
        return "index";
    }

    @RequestMapping("/space")
    public String home(Model model,
                       @RequestParam(value = "res", required = false) String res) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user);
//        System.out.println(user.getTeacherInfo().getName());
        model.addAttribute("res", res);
        return "space";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/logout")
    public String logout(Model model,
                         HttpServletRequest request,
                         HttpServletResponse response,
                         RedirectAttributes attr) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("ID:" + ((User) auth.getPrincipal()).getId() + " Logout");
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        attr.addFlashAttribute("logout", true);
        return "redirect:/login";
    }

    @RequestMapping("/passerror")
    public String loginError(Model model,
                             RedirectAttributes attr) {
        attr.addFlashAttribute("passerror", true);
        return "redirect:/login";
    }
}
