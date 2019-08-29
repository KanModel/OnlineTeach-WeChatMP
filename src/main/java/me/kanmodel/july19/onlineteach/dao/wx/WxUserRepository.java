package me.kanmodel.july19.onlineteach.dao.wx;

import me.kanmodel.july19.onlineteach.entity.wx.WxUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WxUserRepository extends JpaRepository<WxUser, Long> {
    WxUser findByOpenId(String openid);
}
