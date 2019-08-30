package me.kanmodel.july19.onlineteach.entity.wx;

import javax.persistence.*;

@Entity
@Table(name = "wx_users_info")
public class WxUserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wx_id")
    private Long id;
    private String openid;
    private String name;

    public WxUserInfo(String openId, String name) {
        this.openid = openId;
        this.name = name;
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
}
