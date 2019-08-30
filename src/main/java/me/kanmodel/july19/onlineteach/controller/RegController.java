package me.kanmodel.july19.onlineteach.controller;

import me.kanmodel.july19.onlineteach.dao.UserRepository;
import me.kanmodel.july19.onlineteach.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @RequestMapping(value = "/reg", method = RequestMethod.GET)
    private String reg() {
        return "reg.html";
    }

    @RequestMapping(value = "/reg", method = RequestMethod.POST)
    private String reg(@RequestParam(value = "username") String login,
                       @RequestParam(value = "password") String pass,
                       Model model) {
        if (!userRepository.findByLogin(login).isPresent()) {
            User user = new User(login, bCryptPasswordEncoder.encode(pass));
            userRepository.save(user);
            model.addAttribute("add_res", "用户[" + login + "]注册成功！");
            System.out.println("Add user [" + login + "] : " + pass);
        } else {
            model.addAttribute("add_res", "用户[" + login + "]已注册！");
            System.out.println("User[" + login + "] is existed!");
            return "reg";
        }
        return "login";
    }
}