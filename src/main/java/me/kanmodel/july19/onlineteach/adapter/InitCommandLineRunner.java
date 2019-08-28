package me.kanmodel.july19.onlineteach.adapter;/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: KanModel
 * Date: 2019-07-09-9:05
 */

import me.kanmodel.july19.onlineteach.dao.OptionRepository;
import me.kanmodel.july19.onlineteach.dao.RoleRepository;
import me.kanmodel.july19.onlineteach.dao.UserRepository;
import me.kanmodel.july19.onlineteach.entity.Option;
import me.kanmodel.july19.onlineteach.entity.Role;
import me.kanmodel.july19.onlineteach.entity.SysRole;
import me.kanmodel.july19.onlineteach.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @description: todo
 * @author: KanModel
 * @create: 2019-07-09 09:05
 */
@Component
public class InitCommandLineRunner implements CommandLineRunner {

    @Value("${me.kanmodel.first}")
    private Boolean isFirst;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private OptionRepository optionRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("...init resources by implements CommandLineRunner");

        if(isFirst && !optionRepository.findByKey("index_title").isPresent()){
            ArrayList<Option> options = new ArrayList<>();
            options.add(new Option("site_name", "在线学习网站", "网站名称"));
            options.add(new Option("index_title", "Java Web学习网站", "首页标题"));
            options.add(new Option("index_subtitle", "学你所想", "首页副标题"));
            optionRepository.saveAll(options);
        }

        if (isFirst && !userRepository.findByLogin("admin").isPresent()) {
            ArrayList<SysRole> roles = new ArrayList<>();
            roles.add(new SysRole(Role.ROLE_USER));
            roles.add(new SysRole(Role.ROLE_ADMIN));
            roles.add(new SysRole(Role.ROLE_SUPER));
            roleRepository.saveAll(roles);
//            User user = new User("admin", bCryptPasswordEncoder.encode("admin"), Role.ROLE_SUPER);
            User user = new User("admin", bCryptPasswordEncoder.encode("admin"), roles);
            userRepository.save(user);
        }

        System.out.println("...init completed !");
    }

}
