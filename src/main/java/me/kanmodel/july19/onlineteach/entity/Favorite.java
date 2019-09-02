package me.kanmodel.july19.onlineteach.entity;/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: KanModel
 * Date: 2019-07-09-10:09
 */

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @description: todo
 * @author: KanModel
 * @create: 2019-07-09 10:09
 */
@Entity
@Table(name = "sys_favorite")
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "time", columnDefinition = "datetime default current_timestamp")
    private Timestamp time;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    public Favorite() {
    }

    public Favorite(Post post) {
        this.post = post;
    }

    public Favorite(Post post, Timestamp time) {
        this.post = post;
        this.time = time;
        this.time = new Timestamp(System.currentTimeMillis());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
