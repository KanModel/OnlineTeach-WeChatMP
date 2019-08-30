package me.kanmodel.july19.onlineteach.controller.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import me.kanmodel.july19.onlineteach.dao.PostRepository;
import me.kanmodel.july19.onlineteach.dao.wx.WxFavoriteRepository;
import me.kanmodel.july19.onlineteach.dao.wx.WxUserInfoRepository;
import me.kanmodel.july19.onlineteach.entity.Post;
import me.kanmodel.july19.onlineteach.entity.wx.WxFavorite;
import me.kanmodel.july19.onlineteach.entity.wx.WxUserInfo;
import me.kanmodel.july19.onlineteach.service.WxValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "WX收藏管理")
@RestController
@RequestMapping("/api/fav")
public class WxFavoriteController {
    @Autowired
    private WxValidator wxValidator;
    @Autowired
    private WxFavoriteRepository wxFavoriteRepository;
    @Autowired
    private WxUserInfoRepository wxUserInfoRepository;
    @Autowired
    private PostRepository postRepository;

    @RequestMapping(value = "/{postid}", method = RequestMethod.POST)
    @ApiOperation(value = "添加收藏")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openid", value = "用户openid", required = true, paramType = "String"),
            @ApiImplicitParam(name = "sig", value = "认证秘钥", required = true, paramType = "String"),
            @ApiImplicitParam(name = "postid", value = "内容ID", required = true, paramType = "Long")
    })
    private ResponseEntity<WxFavorite> postFavorite(String openid,
                                                    String sig,
                                                    @PathVariable Long postid) {
        if (!wxValidator.valid(openid, sig))
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

        WxFavorite favorite = wxFavoriteRepository.findByOpenidAndPost(openid, postRepository.findById(postid).get());
        if (favorite == null) {
            Post post = postRepository.findById(postid).get();
            favorite = new WxFavorite(post, openid);
        } else {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
        WxUserInfo userInfo = wxUserInfoRepository.findByOpenid(openid);
        userInfo.getFavorites().add(favorite);
        wxFavoriteRepository.save(favorite);
        wxUserInfoRepository.save(userInfo);

        return new ResponseEntity<>(favorite, HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/{postid}", method = RequestMethod.GET)
    @ApiOperation(value = "获取收藏")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openid", value = "用户openid", required = true, paramType = "String"),
            @ApiImplicitParam(name = "sig", value = "认证秘钥", required = true, paramType = "String"),
            @ApiImplicitParam(name = "postid", value = "内容ID", required = true, paramType = "Long")
    })
    private ResponseEntity<WxFavorite> getFavorite(String openid,
                                                   String sig,
                                                   @PathVariable Long postid) {
        if (!wxValidator.valid(openid, sig))
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

        return new ResponseEntity<>(wxFavoriteRepository.findByOpenidAndPost(openid, postRepository.findById(postid).get())
                , HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/{postid}", method = RequestMethod.DELETE)
    @ApiOperation(value = "取消收藏")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openid", value = "用户openid", required = true, paramType = "String"),
            @ApiImplicitParam(name = "sig", value = "认证秘钥", required = true, paramType = "String"),
            @ApiImplicitParam(name = "postid", value = "内容ID", required = true, paramType = "Long")
    })
    private ResponseEntity<WxFavorite> deleteFavorite(String openid,
                                                   String sig,
                                                   @PathVariable Long postid) {
        if (!wxValidator.valid(openid, sig))
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

        WxFavorite favorite = wxFavoriteRepository.findByOpenidAndPost(openid, postRepository.findById(postid).get());
        wxFavoriteRepository.delete(favorite);

        return new ResponseEntity<>(favorite, HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value = "获取收藏列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openid", value = "用户openid", required = true, paramType = "String"),
            @ApiImplicitParam(name = "sig", value = "认证秘钥", required = true, paramType = "String")
    })
    private ResponseEntity<List<WxFavorite>> listFavorite(String openid,
                                                          String sig) {
        if (!wxValidator.valid(openid, sig))
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

        return new ResponseEntity<>(wxFavoriteRepository.findAllByOpenid(openid)
                , HttpStatus.ACCEPTED);
    }
}
