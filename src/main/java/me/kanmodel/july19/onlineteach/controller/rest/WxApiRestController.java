package me.kanmodel.july19.onlineteach.controller.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import me.kanmodel.july19.onlineteach.dao.wx.WxUserInfoRepository;
import me.kanmodel.july19.onlineteach.dao.wx.WxUserRepository;
import me.kanmodel.july19.onlineteach.entity.wx.WxUser;
import me.kanmodel.july19.onlineteach.entity.wx.WxUserInfo;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Api("微信API")
@RestController
@RequestMapping("/api/wx")
public class WxApiRestController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private WxUserRepository wxUserRepository;
    @Autowired
    private WxUserInfoRepository wxUserInfoRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final String APPID = "wxa5f389c97aa8b032";
    private static final String SECRET = "2f465867f15c09e419c2d6caaec574b9";

    @PostMapping("/login")
    @ApiOperation(value = "微信登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "微信登录码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "name", value = "微信昵称", paramType = "String")
    })
    public ResponseEntity<Map<String, Object>>
    wxLogin(String code,
            String name) {
        Map<String, Object> modelMap = new HashMap<>();

        logger.info("wxlogin - code: " + code);

        String url = "https://api.weixin.qq.com/sns/jscode2session";
        Map<String, String> param = new HashMap<>();
        param.put("appid", APPID);
        param.put("secret", SECRET);
        param.put("js_code", code);
        param.put("grant_type", "authorization_code");

        String wxResult = doGet(url, param);
        logger.info(wxResult);

        JSONObject wxSessionJson = JSON.parseObject(wxResult);
        String openid = wxSessionJson.getString("openid");
        String session_key = wxSessionJson.getString("session_key");
        if (openid == null || session_key == null) {
            logger.warn("code[" + code + "]无效,请求失败!");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        WxUser wxUser = wxUserRepository.findByOpenid(openid);
        if (name == null) name = "";
        if (wxUser == null) {
            logger.info("无[" + openid + " ]用户");
            WxUserInfo userInfo = wxUserInfoRepository.findByOpenid(openid);
            if (userInfo == null) {
                userInfo = new WxUserInfo(openid, name);
                wxUserInfoRepository.save(userInfo);
            }
            wxUser = new WxUser(openid, session_key, bCryptPasswordEncoder.encode(session_key), userInfo);
        } else if (!wxUser.getSessionKey().equals(session_key)) {
            logger.info("更新[" + openid + " ]用户session");
            if (wxUser.getUserInfo() == null) {
                WxUserInfo userInfo = wxUserInfoRepository.findByOpenid(openid);
                if (userInfo == null) {
                    userInfo = new WxUserInfo(openid, name);
                    wxUserInfoRepository.save(userInfo);
                }
                wxUser.setUserInfo(userInfo);
            }
            wxUser.update(session_key, bCryptPasswordEncoder.encode(session_key));
        }
        wxUserRepository.save(wxUser);

        modelMap.put("openid", openid);
        modelMap.put("sig", wxUser.getSig());

        return new ResponseEntity<>(modelMap, HttpStatus.OK);
    }

    public static String doGet(String url, Map<String, String> param) {

        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();

        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();

            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);

            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }
}
