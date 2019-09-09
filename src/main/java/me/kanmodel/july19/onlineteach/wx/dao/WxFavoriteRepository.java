package me.kanmodel.july19.onlineteach.wx.dao;

import me.kanmodel.july19.onlineteach.entity.Post;
import me.kanmodel.july19.onlineteach.wx.entity.WxFavorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 对微信端用户收藏 取消收藏的持久化方法支持
 */
public interface WxFavoriteRepository extends JpaRepository<WxFavorite, Long> {
    WxFavorite findByOpenidAndPost(String openid, Post post);
    List<WxFavorite> findAllByOpenid(String openid);
}
