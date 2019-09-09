package me.kanmodel.july19.onlineteach.wx.dao;

import me.kanmodel.july19.onlineteach.wx.entity.WxUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 获取微信端用户的持久化方法
 */
public interface WxUserRepository extends JpaRepository<WxUser, Long> {
    WxUser findByOpenid(String openid);
}
