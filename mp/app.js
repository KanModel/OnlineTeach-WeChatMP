/*
 * 
 * WordPres版微信小程序
 * author: jianbo
 * organization: 守望轩  www.watch-life.net
 * github:    https://github.com/iamxjb/winxin-app-watch-life.net
 * 技术支持微信号：iamxjb
 * Copyright (c) 2017 https://www.watch-life.net All rights reserved.
 * 
 */
const Towxml = require('/towxml/main');     //引入towxml库

App({
    globalData: {
        userInfo: null,
        openid: '',
        sig: null,
        isGetUserInfo: false,
        isGetOpenid: false,
        url: 'http://localhost:8088/api/',
        detailTo:-1,
    },
    onLaunch: function () {
        //调用API从本地缓存中获取数据
        var logs = wx.getStorageSync('logs') || []
        logs.unshift(Date.now())
        wx.setStorageSync('logs', logs)
        // 小程序主动更新
        this.updateManager();
    },
    towxml: new Towxml(),
    getUserInfo: function (cb) {
        var that = this
        if (this.globalData.userInfo) {
            typeof cb == "function" && cb(this.globalData.userInfo)
        } else {
            //调用登录接口
            wx.login({
                success: function () {
                    wx.getUserInfo({
                        success: function (res) {
                            console.log(res)
                            that.globalData.userInfo = res.userInfo
                            typeof cb == "function" && cb(that.globalData.userInfo)
                        }
                    })
                    that.isGetUserInfo = true
                }
            })
        }

    },/*小程序主动更新
    */
    updateManager() {
        if (!wx.canIUse('getUpdateManager')) {
            return false;
        }
        const updateManager = wx.getUpdateManager();
        updateManager.onCheckForUpdate(function (res) {
        });
        updateManager.onUpdateReady(function () {
            wx.showModal({
                title: '有新版本',
                content: '新版本已经准备好，即将重启',
                showCancel: false,
                success(res) {
                    if (res.confirm) {
                        updateManager.applyUpdate()
                    }
                }
            });
        });
        updateManager.onUpdateFailed(function () {
            wx.showModal({
                title: '更新提示',
                content: '新版本下载失败',
                showCancel: false
            })
        });
    }
})