package me.kanmodel.july19.onlineteach.entity.wx;

import me.kanmodel.july19.onlineteach.entity.Post;

import javax.persistence.*;

@Entity
@Table(name = "wx_favorite")
public class WxFavorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wx_favorite_id")
    private Long id;
    private Long postid;
    private String openid;

    public WxFavorite(Long postid, String openid) {
        this.postid = postid;
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

    public Long getPostid() {
        return postid;
    }

    public void setPostid(Long postid) {
        this.postid = postid;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
}
