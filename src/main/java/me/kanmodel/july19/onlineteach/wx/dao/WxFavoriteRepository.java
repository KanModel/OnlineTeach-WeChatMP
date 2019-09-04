package me.kanmodel.july19.onlineteach.wx.dao;

import me.kanmodel.july19.onlineteach.entity.Post;
import me.kanmodel.july19.onlineteach.wx.entity.WxFavorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WxFavoriteRepository extends JpaRepository<WxFavorite, Long> {
    WxFavorite findByOpenidAndPost(String openid, Post post);
    List<WxFavorite> findAllByOpenid(String openid);
}
