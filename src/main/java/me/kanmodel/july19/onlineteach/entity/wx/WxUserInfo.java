package me.kanmodel.july19.onlineteach.entity.wx;

import javax.persistence.*;

@Entity
@Table(name = "wx_users_info")
public class WxUserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wx_id")
    private Long id;
    private String openId;
    private String name;

    public WxUserInfo(String openId, String name) {
        this.openId = openId;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
