package me.kanmodel.july19.onlineteach.wx.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import me.kanmodel.july19.onlineteach.wx.dao.WxUserInfoRepository;
import me.kanmodel.july19.onlineteach.wx.entity.WxUserInfo;
import me.kanmodel.july19.onlineteach.service.WxValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 获取微信端用户的信息
 */
@Api(tags = "WX用户信息管理")
@RestController
@RequestMapping("/api/info")
public class WxUserController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private WxUserInfoRepository wxUserInfoRepository;
    @Autowired
    private WxValidator wxValidator;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiOperation(value = "获取用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openid", value = "用户openid", required = true, paramType = "String"),
            @ApiImplicitParam(name = "sig", value = "认证秘钥", required = true, paramType = "String")
    })
    private ResponseEntity<WxUserInfo> getUserInfo(String openid,
                                                   String sig){
        if (!wxValidator.valid(openid, sig))
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

        return new ResponseEntity<>(wxUserInfoRepository.findByOpenid(openid), HttpStatus.OK);
    }
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ApiOperation(value = "接收微信端发来的nickname")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openid", value = "用户openid", required = true, paramType = "String"),
            @ApiImplicitParam(name = "sig", value = "认证秘钥", required = true, paramType = "String"),
            @ApiImplicitParam(name = "name", value = "用户nickname", required = true, paramType = "String")
    })
    private ResponseEntity<WxUserInfo> getUserNickname(String openid,
                                                   String sig,String name){
        WxUserInfo wxUserInfo = wxUserInfoRepository.findByOpenid(openid);
        wxUserInfo.setName(name);
        wxUserInfoRepository.save(wxUserInfo);
        logger.info(name);
        return new ResponseEntity<>(wxUserInfo, HttpStatus.OK);
    }
}
