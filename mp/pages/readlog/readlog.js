import config from '../../utils/config.js'

var app = getApp();
Page({
    data: {
        postsList: [],
        userInfo: {},
        hasUserInfo: false,
        canIUse: wx.canIUse('button.open-type.getUserInfo'),
        showerror: "none",
        shownodata: "none",
        openid: '',
        isLoginPopup: false
    },

    /** 生命周期函数--监听页面加载 */
    onLoad: function (options) {
        console.log(app.globalData.userInfo)
    },

    onShow: function () {
        console.log(app.globalData.userInfo)
        this.resetData();
    }, resetData() {
        var that = this;
        console.log('resetData', that.data.hasUserInfo);
        wx.request({
            url: app.globalData.url + 'fav/list',
            data: {openid: app.globalData.openid, sig: app.globalData.sig},
            method: 'GET', /* OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT*/
            header: {'content-type': 'application/x-www-form-urlencoded'}, /* 设置请求的 header*/
            success: function (res) {
                console.log(res.data)
                that.setData({postsList: res.data, hasUserInfo: true, userInfo: app.globalData.userInfo,});
            },
            fail: function () {
                console.log("index.js wx.request CheckCallUser fail");
            },
        })
    },

    deleteFavorite: function (e) {
        var that = this;
        console.log(e);
        wx.request({
            url: app.globalData.url + 'fav/' + e.currentTarget.id,
            data: {openid: app.globalData.openid, sig: app.globalData.sig, postid: this.data.id},
            method: 'DELETE', /* OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT*/
            header: {'content-type': 'application/x-www-form-urlencoded'}, /* 设置请求的 header*/
            success: function (res) {
                console.log(res.statusCode)
                if (res.statusCode == 200 || res.statusCode == 202) {
                    wx.showToast({title: '取消成功', icon: 'success', duration: 2000})
                } else if (res.statusCode == 409) {
                    console.log("index.js wx.request CheckCallUser statusCode" + res.statusCode);
                    wx.showToast({title: '重复添加', icon: 'none', duration: 2000})
                }
                that.resetData()
            },
            fail: function () {
                console.log("index.js wx.request CheckCallUser fail");
            },
        })
    },

    getUserInfo: function (e) {/* console.log(e)*/
        var that = this;
        // console.log(e);
        app.globalData.userInfo = e.detail.userInfo;
        this.setData({userInfo: e.detail.userInfo, hasUserInfo: true})
        console.log(app.globalData.openid, app.globalData.sig);
        wx.request({
            url: app.globalData.url + 'fav/list',
            data: {openid: app.globalData.openid, sig: app.globalData.sig},
            method: 'GET', /* OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT*/
            header: {'content-type': 'application/x-www-form-urlencoded'}, /* 设置请求的 header*/
            success: function (res) {
                console.log(res.data)
                that.setData({postsList: res.data});
            },
            fail: function () {
                console.log("index.js wx.request CheckCallUser fail");
            },
        })
        //向小程序端发送请求 发送用户昵称
        console.log(app.globalData.userInfo.nickName)
        wx.request({
            url: app.globalData.url + 'info',
            data: {openid: app.globalData.openid, sig: app.globalData.sig,name:app.globalData.userInfo.nickName},
            method: 'POST', /* OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT*/
            header: {'content-type': 'application/x-www-form-urlencoded'}, /* 设置请求的 header*/
            success: function (res) {
                console.log("发送nickname成功");
            },
            fail: function () {
                console.log("index.js wx.request CheckCallUser fail");
            },
        })
    }, redictDetail: function (e) {
        var id = e.currentTarget.id;
        var itemtype = e.currentTarget.dataset.itemtype;
        var url = "";
        if (itemtype == "1") {
            url = '../list/list?categoryID=' + id;
        } else {
            url = '../detail/detail?id=' + id;
        }
        wx.navigateTo({url: url})
    },
})