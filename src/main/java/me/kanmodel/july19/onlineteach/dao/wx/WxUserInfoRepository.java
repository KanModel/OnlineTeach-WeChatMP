package me.kanmodel.july19.onlineteach.dao.wx;

import me.kanmodel.july19.onlineteach.entity.wx.WxUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WxUserInfoRepository extends JpaRepository<WxUserInfo, Long> {
    WxUserInfo findByOpenId(String openid);
}
