package me.kanmodel.july19.onlineteach.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @description: 收藏 实体
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

    @Column(name = "is_delete", columnDefinition = "bit(1) default 0")
    private Boolean isDelete  = false;

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

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }
}
