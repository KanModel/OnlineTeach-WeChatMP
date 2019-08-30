package me.kanmodel.july19.onlineteach.controller.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import me.kanmodel.july19.onlineteach.dao.wx.WxUserInfoRepository;
import me.kanmodel.july19.onlineteach.entity.wx.WxUserInfo;
import me.kanmodel.july19.onlineteach.service.WxValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "WX用户信息管理")
@RestController
@RequestMapping("/api/info")
public class WxUserController {
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
}
