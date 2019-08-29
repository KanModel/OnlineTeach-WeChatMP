package me.kanmodel.july19.onlineteach.entity.wx;

import javax.persistence.*;

@Entity
@Table(name = "wx_users")
public class WxUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wx_id")
    private Long id;

    private String openId;
    private String sessionKey;
    private String sig;

    public WxUser(String openId, String sessionKey, String sig) {
        this.openId = openId;
        this.sessionKey = sessionKey;
        this.sig = sig;
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
}
