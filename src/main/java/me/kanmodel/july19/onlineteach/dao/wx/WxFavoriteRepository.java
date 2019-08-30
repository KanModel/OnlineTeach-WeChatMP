package me.kanmodel.july19.onlineteach.dao.wx;

import me.kanmodel.july19.onlineteach.entity.wx.WxFavorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WxFavoriteRepository extends JpaRepository<WxFavorite, Long> {
    WxFavorite findByOpenidAndPostid(String openid, Long postid);
    List<WxFavorite> findAllByOpenid(String openid);
}
