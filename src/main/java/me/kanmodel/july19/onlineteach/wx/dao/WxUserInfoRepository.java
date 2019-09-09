package me.kanmodel.july19.onlineteach.wx.dao;

import me.kanmodel.july19.onlineteach.wx.entity.WxUserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 获取微信端用户对象的持久化方法
 */
public interface WxUserInfoRepository extends JpaRepository<WxUserInfo, Long> {
    WxUserInfo findByOpenid(String openid);
    Page<WxUserInfo> findAllByNameLike(String name, Pageable pageable);
}

