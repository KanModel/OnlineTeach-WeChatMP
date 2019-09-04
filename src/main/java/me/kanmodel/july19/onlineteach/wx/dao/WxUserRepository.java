package me.kanmodel.july19.onlineteach.wx.dao;

import me.kanmodel.july19.onlineteach.wx.entity.WxUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WxUserRepository extends JpaRepository<WxUser, Long> {
    WxUser findByOpenid(String openid);
}
