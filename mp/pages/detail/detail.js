import config from '../../utils/config.js'

var Api = require('../../utils/api.js');
var Auth = require('../../utils/auth.js');
var wxRequest = require('../../utils/wxRequest.js')
var app = getApp();
import {ModalView} from '../../templates/modal-view/modal-view.js'
import Poster from '../../templates/components/wxa-plugin-canvas-poster/poster/poster';

Page({
    data: {
        title: '文章内容',
        detail: {},
        id: 0,
        display: 'none',
        showerror: 'none',
        detailTo: 0,
    },

    onLoad: function (options) {
        var that = this;
        if (app.globalData.userInfo != null) {
            wx.request({
                url: app.globalData.url + 'fav/' + options.id,
                method: "GET",
                header: {'content-type': 'application/x-www-form-urlencoded'},
                data: {openid: app.globalData.openid, sig: app.globalData.sig, postid: options.id},
                success: function (res) {
                    if (res.data) {
                        that.setData({detailTo: 1,})
                    } else {
                        that.setData({detailTo: 0,})
                    }
                },
                fail: function () {
                    console.log("index.js wx.request CheckCallUser fail");
                },
            })
        } else {
            that.setData({detailTo: 0,})
            /* console.log(that.data)*/
        }
        var self = this;
        self.setData({id: options.id,})
        self.fetchDetailData(options.id);
        Auth.setUserInfoData(self);
        Auth.checkLogin(self);
        wx.getSystemInfo({
            success: function (t) {
                var system = t.system.indexOf('iOS') != -1 ? 'iOS' : 'Android';
                self.setData({system: system});
            }
        })
        new ModalView;
    },

    postFavorite: function () {
        /* console.log('postFav', app.globalData.userInfo, app.globalData.isGetUserInfo, this.data.detailTo)*/
        var self = this;
        if (app.globalData.userInfo != null && self.data.detailTo === 0) {
            wx.request({
                url: app.globalData.url + 'fav/' + this.data.id,
                data: {openid: app.globalData.openid, sig: app.globalData.sig, postid: this.data.id},
                method: 'POST', /* OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT*/
                header: {'content-type': 'application/x-www-form-urlencoded'}, /* 设置请求的 header*/
                success: function (res) {
                    console.log(res.statusCode)
                    if (res.statusCode == 200 || res.statusCode == 202) {
                        wx.showToast({title: '添加成功', icon: 'success', duration: 2000})
                        /* setTimeout(function(){ wx.navigateBack({ }) },2000)*/
                        self.setData({detailTo: 1})
                    } else if (res.statusCode == 409) {
                        console.log("index.js wx.request CheckCallUser statusCode" + res.statusCode);
                        wx.showToast({title: '重复添加', icon: 'none', duration: 2000})
                        /* setTimeout(function(){ wx.navigateBack({ }) },2000)*/
                    }
                },
                fail: function () {
                    console.log("index.js wx.request CheckCallUser fail");
                },
            })
        } else if (app.globalData.userInfo != null && self.data.detailTo === 1) {
            wx.request({
                url: app.globalData.url + 'fav/' + this.data.id,
                data: {openid: app.globalData.openid, sig: app.globalData.sig, postid: this.data.id},
                method: 'DELETE', /* OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT*/
                header: {'content-type': 'application/x-www-form-urlencoded'}, /* 设置请求的 header*/
                success: function (res) {
                    console.log(res.statusCode)
                    if (res.statusCode == 200 || res.statusCode == 202) {
                        wx.showToast({title: '取消成功', icon: 'success', duration: 2000})
                        self.setData({detailTo: 0})
                        /* setTimeout(function(){ wx.navigateBack({ }) },2000)*/
                    } else if (res.statusCode == 409) {
                        console.log("index.js wx.request CheckCallUser statusCode" + res.statusCode);
                        wx.showToast({title: '重复添加', icon: 'none', duration: 2000})
                        /* setTimeout(function(){ wx.navigateBack({ }) },2000)*/
                    }
                },
                fail: function () {
                    console.log("index.js wx.request CheckCallUser fail");
                },
            })
        } else {
            wx.showToast({title: '请先登录', icon: 'none', duration: 2000})
        }
    },

    onShareAppMessage: function (res) {
        console.log(res);
        return {
            title: '分享"' + config.getWebsiteName + '"的文章：',
            path: 'pages/detail/detail?id=' + this.data.detail.id,
            imageUrl: this.data.detail.post_full_image,
            success: function (res) {/* 转发成功*/
                console.log(res);
            },
            fail: function (res) {
                console.log(res);
                /* 转发失败*/
            }
        }
    },

    fetchDetailData: function (id) {/* console.log(Api.getPostByID(id))*/
        var self = this;
        var getPostDetailRequest = wxRequest.getRequest(Api.getPostByID(id));
        var res;
        var _displayLike = 'none';
        getPostDetailRequest.then(response => {
            res = response;
            console.log(res.data.title)
            self.setData({title: res.data.title,})
            if (response.data.code && (response.data.data.status == "404")) {
                self.setData({showerror: 'block', display: 'none', errMessage: response.data.message});
                return false;
            }
            wx.setNavigationBarTitle({title: res.data.title});
            let data = app.towxml.toJson(res.data.content, 'markdown');
            /*设置数据*/
            self.setData({article: data});
        })
    },

    loadMore: function (e) {
        var self = this;
        if (!self.data.isLastPage) {
            self.setData({page: self.data.page + 1});
            console.log('当前页' + self.data.page);
            this.fetchCommentData();
        } else {
            wx.showToast({title: '没有更多内容', mask: false, duration: 1000});
        }
    },
})