/* WordPres版微信小程序 author: jianbo organization: 守望轩  www.watch-life.net github:    https://github.com/iamxjb/winxin-app-watch-life.net 技术支持微信号：iamxjb 开源协议：MIT Copyright (c) 2017 https://www.watch-life.net All rights reserved. */
var Auth = require('../../utils/auth.js');

var app = getApp();
Page({
    data: {
        textIndex: '随着技术的发展，学习方式也逐渐从传统的教室现场教学转变为以网络为媒介的远程教学，如今随着互联网的发展，远程教学越来越普及，人们可以做到坐在电脑前进行学习和实时下载资料并与教师进行互动，大大增强了学习的效率。我们希望通过利用微信小程序平台实现搭建一个通过观看相关教学视频帮助用户学习软件编程的学习平台。',
        gitHref: 'https://github.com/KanModel/OnlineTeach-WeChatMP',
        display: 'block',
    }, onLoad: function (options) {
        var self = this;
        wx.setNavigationBarTitle({
            title: '关于移动智能学习平台', success: function (res) {/* success*/
            }
        });
        Auth.setUserInfoData(self);
        Auth.checkLogin(self);
    },
})