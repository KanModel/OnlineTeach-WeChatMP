package me.kanmodel.july19.onlineteach.wx.dao;

import me.kanmodel.july19.onlineteach.wx.entity.WxUserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WxUserInfoRepository extends JpaRepository<WxUserInfo, Long> {
    WxUserInfo findByOpenid(String openid);
    Page<WxUserInfo> findAllByNameLike(String name, Pageable pageable);
}

