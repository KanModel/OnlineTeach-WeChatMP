package me.kanmodel.july19.onlineteach.service;

import me.kanmodel.july19.onlineteach.dao.UserRepository;
import me.kanmodel.july19.onlineteach.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

/**
 * @description: Security用户认证用查找用户
 * @author: KanModel
 * @create: 2019-04-07 12:22
 */
public class CustomUserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByLogin(s);
        if(!user.isPresent()){
            throw new UsernameNotFoundException(s + " 用户名不存在");
        }
        return user.get();
    }
}
