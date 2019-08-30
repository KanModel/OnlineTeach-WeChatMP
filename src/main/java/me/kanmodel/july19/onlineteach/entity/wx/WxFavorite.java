package me.kanmodel.july19.onlineteach.entity.wx;

import me.kanmodel.july19.onlineteach.entity.Post;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "wx_favorite")
public class WxFavorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wx_favorite_id")
    private Long id;
    @OneToOne
    @JoinColumn(name = "post_id")
    private Post post;
    private String openid;
    @Column(name = "time", columnDefinition = "datetime default current_timestamp")
    private Timestamp time;

    public WxFavorite(Post post, String openid) {
        this.post = post;
        this.openid = openid;
    }

    public WxFavorite() {
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

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
