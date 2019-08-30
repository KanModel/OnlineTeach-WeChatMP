package me.kanmodel.july19.onlineteach.service;

import me.kanmodel.july19.onlineteach.dao.wx.WxUserRepository;
import me.kanmodel.july19.onlineteach.entity.wx.WxUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 自定义登录态验证
 *
 * @author: KanModel
 * @create: 2019-07-29 16:34
 */
@Component
public class WxValidator {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private WxUserRepository wxUserRepository;
    /**
     * 静态有效期
     */
    private static long EXPIRED = 30 * 60 * 1000;

    public boolean valid(String openid, String sig){
        StackTraceElement[] temp = Thread.currentThread().getStackTrace();
        StackTraceElement parent = temp[2];
        String call = parent.getFileName() + "-" + parent.getMethodName();

        //判断是否参数完整
        if (sig == null || openid == null) {
            logger.warn(call + ":验证缺少参数！");
            return false;
        }

        WxUser wxUser = wxUserRepository.findByOpenid(openid);
        //判断用户是否存在
        if (wxUser == null) {
            logger.warn(call + ":用户[" + openid + "] 不存在");
            return false;
        }
        //校验密钥sig是否匹配
        if (!wxUser.getSig().equals(sig)) {
            logger.warn(call + ":用户[" + openid + "] sig验证失败");
            return false;
        }

        //检验密钥sig是否过期
        if (wxUser.getModified().getTime() + EXPIRED < System.currentTimeMillis()){
            logger.warn(call + ":用户[" + openid + "] sig过期! modified:" + wxUser.getModified().getTime());
            return false;
        }

        return true;
    }
}
