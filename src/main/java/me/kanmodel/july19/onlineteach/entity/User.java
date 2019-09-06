package me.kanmodel.july19.onlineteach.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @description: 用户账户信息
 * @author: KanModel
 * @create: 2019-03-23 09:05
 */
@Entity
@Table(name = "sys_users")
public class User implements UserDetails {
//    @Autowired
//    private RoleRepository roleRepository;
//    private UserRepository userRepository;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(name = "user_login", columnDefinition = "varchar(20)  default ''", unique = true, nullable = false)
    private String login;
    @Column(name = "user_pass", columnDefinition = "varchar(255)  default ''", nullable = false)
    private String pass;
    @ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    private List<SysRole> roles;
    @Column(name = "user_email", columnDefinition = "varchar(20)  default ''", nullable = false)
    private String email;
    @Column(name = "user_registered", columnDefinition = "datetime default current_timestamp")
    private Timestamp registered;
    @Column(name = "user_display", columnDefinition = "varchar(20)  default ''", nullable = false)
    private String display;
    @OneToMany
    @JoinColumn(name = "user_id")
    @OrderBy("id asc")
    private List<Favorite> favorites;

    private Boolean isDelete  = false;

    public User() {
    }

    public User(String user_login, String user_pass) {
        this.login = user_login;
        this.pass = user_pass;
        this.email = "";
        this.registered = new Timestamp(System.currentTimeMillis());
        this.display = user_login;
        this.roles = new ArrayList<>();
        roles.add(new SysRole(1L, "ROLE_USER"));
    }

    public User(String user_login, String user_pass, Role role) {
        this.login = user_login;
        this.pass = user_pass;
        this.email = "";
        this.registered = new Timestamp(System.currentTimeMillis());
        this.display = user_login;
        this.roles = new ArrayList<>();
        roles.add(new SysRole(role.getId(), role.getName()));
    }

    public User(String user_login, String user_pass, ArrayList<SysRole> roles) {
        this.login = user_login;
        this.pass = user_pass;
        this.email = "";
        this.registered = new Timestamp(System.currentTimeMillis());
        this.display = user_login;
        this.roles = roles;
    }

    public List<SysRole> getRoles() {
        return roles;
    }

    public void setRoles(List<SysRole> roles) {
        this.roles = roles;
    }

    public boolean haveRole(String roleName) {
        for (SysRole role : roles)
            if (role.getName().equals(roleName)) return true;
        return false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getRegistered() {
        return registered;
    }

    public void setRegistered(Timestamp registered) {
        this.registered = registered;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public List<Favorite> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Favorite> favorites) {
        this.favorites = favorites;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auths = new ArrayList<>();
        List<SysRole> roles = this.getRoles();
        for (SysRole role : roles) {
            auths.add(new SimpleGrantedAuthority(role.getName()));
        }
        return auths;
    }

    @Override
    public String getPassword() {
        return this.pass;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }
}