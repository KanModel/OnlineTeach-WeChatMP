package me.kanmodel.july19.onlineteach.service

import me.kanmodel.july19.onlineteach.wx.dao.WxUserRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * 自定义登录态验证
 *
 * @author: KanModel
 * @create: 2019-07-29 16:34
 */
@Component
class WxValidator {
    private val logger = LoggerFactory.getLogger(javaClass)
    @Autowired
    private val wxUserRepository: WxUserRepository? = null

    /**
     * 验证微信用户是否有效
     * @param openid 微信用户openid
     * @param sig 加密后的sessionKey
     * @return 是否有效
     */
    fun valid(openid: String?, sig: String?): Boolean {
        val temp = Thread.currentThread().stackTrace
        val parent = temp[2]
        val call = "${parent.fileName}-${parent.methodName}"

        //判断是否参数完整
        if (sig == null || openid == null) {
            logger.warn("$call:验证缺少参数！")
            return false
        }

        val wxUser = wxUserRepository!!.findByOpenid(openid)
        //判断用户是否存在
        if (wxUser == null) {
            logger.warn("$call:用户[$openid] 不存在")
            return false
        }
        //校验密钥sig是否匹配
        if (wxUser.sig != sig) {
            logger.warn("$call:用户[$openid] sig验证失败")
            return false
        }

        //检验密钥sig是否过期
        if (wxUser.modified.time + EXPIRED < System.currentTimeMillis()) {
            logger.warn("$call:用户[$openid] sig过期! modified:${wxUser.modified.time}")
            return false
        }

        return true
    }

    companion object {
        /**
         * 静态有效期
         */
        private const val EXPIRED = (30 * 60 * 1000).toLong()
    }
}
