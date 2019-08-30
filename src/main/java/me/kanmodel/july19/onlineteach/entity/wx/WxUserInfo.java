package me.kanmodel.july19.onlineteach.entity.wx;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "wx_users_info")
public class WxUserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wx_info_id")
    private Long id;
    private String openid;
    private String name;
    @OneToMany
    @JoinColumn(name = "wx_info_id")
    @OrderBy("id asc")
    private List<WxFavorite> favorites;

    public WxUserInfo(String openId, String name) {
        this.openid = openId;
        this.name = name;
        favorites = new ArrayList<>();
    }

    public WxUserInfo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<WxFavorite> getFavorites() {
        return favorites;
    }

    public void setFavorites(ArrayList<WxFavorite> favorites) {
        this.favorites = favorites;
    }
}
