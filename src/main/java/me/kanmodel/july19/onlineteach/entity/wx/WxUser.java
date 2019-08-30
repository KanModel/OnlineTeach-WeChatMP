package me.kanmodel.july19.onlineteach.entity.wx;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.sql.Timestamp;

@ApiModel("WX用户")
@Entity
@Table(name = "wx_users")
public class WxUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wx_user_id")
    private Long id;

    private String openid;
    private String sessionKey;
    private String sig;
    private Timestamp created;
    private Timestamp modified;
    @OneToOne
    @JoinColumn(name = "user_info_id")
    @ApiModelProperty("WX用戶信息")
    private WxUserInfo userInfo;

    public WxUser(String openid, String sessionKey, String sig, WxUserInfo userInfo) {
        this.openid = openid;
        this.sessionKey = sessionKey;
        this.sig = sig;
        this.userInfo = userInfo;
        created = new Timestamp(System.currentTimeMillis());
        modified = created;
    }

    public WxUser() {
    }

    public void update(String session_key, String sig) {
        this.setSessionKey(session_key);
        this.setSig(sig);
        this.setModified(new Timestamp(System.currentTimeMillis()));
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

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getSig() {
        return sig;
    }

    public void setSig(String sig) {
        this.sig = sig;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Timestamp getModified() {
        return modified;
    }

    public void setModified(Timestamp modified) {
        this.modified = modified;
    }

    public WxUserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(WxUserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
