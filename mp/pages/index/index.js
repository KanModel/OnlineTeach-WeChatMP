/* WordPres版微信小程序 author: jianbo organization: 守望轩  www.watch-life.net github:    https://github.com/iamxjb/winxin-app-watch-life.net 技术支持微信号：iamxjb 开源协议：MIT *Copyright (c) 2017 https://www.watch-life.net All rights reserved. */
var Auth = require('../../utils/auth.js');
import config from '../../utils/config.js'

var pageCount = config.getPageCount;
var app = getApp()
Page({
    data: {
        postsList: [],
        isLastPage: false,
        page: 1,
        search: '',
        showerror: "none",
        showallDisplay: "block",
    }, formSubmit: function (e) {
        var url = '../list/list'
        var key = '';
        if (e.currentTarget.id == "search-input") {
            key = e.detail.value;
        } else {
            key = e.detail.value.input;
        }
        if (key != '') {
            url = url + '?search=' + key;
            wx.navigateTo({url: url})
        } else {
            wx.showModal({title: '提示', content: '请输入内容', showCancel: false,});
        }
    }, onShareAppMessage: function () {
        let users = wx.getStorageSync('user');
        return {
            title: '“' + config.getWebsiteName + '”网站微信小程序 在线转发',
            path: 'pages/index/index',
            success: function (res) {/* 转发成功*/
            },
            fail: function (res) {/* 转发失败*/
            }
        }
    }, onPullDownRefresh: function () {
        var self = this;
        self.setData({
            showerror: "none",
            showallDisplay: "block",
            displaySwiper: "none",
            floatDisplay: "none",
            isLastPage: false,
            page: 1,
        });
        this.fetchPostsData(self.data);
    }, onReachBottom: function () {
        var self = this;
        if (!self.data.isLastPage) {
            self.setData({page: self.data.page + 1});
            console.log('当前页' + self.data.page);
            this.fetchPostsData(self.data);
        } else {
            console.log('最后一页');
        }
    }, onLoad: function (options) {
        var self = this;
        self.fetchPostsData(self.data);
        Auth.wxLogin()
    }, onShow: function (options) {
        var self = this;
        self.fetchPostsData(self.data);
        wx.setStorageSync('openLinkCount', 0);
    }, /*获取文章列表数据*/ fetchPostsData: function (data) {
        var self = this;
        if (!data) data = {};
        if (!data.page) data.page = 1;
        if (!data.categories) data.categories = 0;
        if (!data.search) data.search = '';
        if (data.page === 1) {
            self.setData({postsList: []});
        }
        ;wx.showLoading({title: '正在加载', mask: true});
        /* console.log(data)*/
        wx.request({
            url: app.globalData.url + 'post/list',
            method: "GET",
            header: {'content-type': 'application/x-www-form-urlencoded'},
            data: {no: data.page, size: pageCount},
            success: function (result) {/* console.log(result);*/
                self.setData({floatDisplay: "block", postsList: self.data.postsList.concat(result.data)});
                setTimeout(function () {
                    wx.hideLoading();
                }, 900);
                if (result.data.length < pageCount) {
                    self.setData({isLastPage: true});
                    wx.showToast({title: '没有更多内容', mask: false, duration: 1500});
                }
            }
        })
    }, /*加载分页*/ loadMore: function (e) {
        var self = this;
        if (!self.data.isLastPage) {
            self.setData({page: self.data.page + 1});
            /*console.log('当前页' + self.data.page);*/
            this.fetchPostsData(self.data);
        } else {
            wx.showToast({title: '没有更多内容', mask: false, duration: 1000});
        }
    }, /* 跳转至查看文章详情*/ redictDetail: function (e) {
        var id = e.currentTarget.id, url = '../detail/detail?id=' + id;
        wx.navigateTo({url: url})
    },
})
