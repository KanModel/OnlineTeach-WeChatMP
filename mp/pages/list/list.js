/*
 * 
 * WordPres版微信小程序
 * author: jianbo
 * organization: 守望轩  www.watch-life.net
 * github:    https://github.com/iamxjb/winxin-app-watch-life.net
 * 技术支持微信号：iamxjb
 * 开源协议：MIT
 * 
 *  *Copyright (c) 2017 https://www.watch-life.net All rights reserved.
 */

var Api = require('../../utils/api.js');
var util = require('../../utils/util.js');
var WxParse = require('../../wxParse/wxParse.js');
var wxApi = require('../../utils/wxApi.js')
var wxRequest = require('../../utils/wxRequest.js')

import config from '../../utils/config.js'

var pageCount = config.getPageCount;

var app = getApp()

Page({
    data: {
        title: '文章列表',
        postsList: {},
        pagesList: {},
        categoriesList: {},
        postsShowSwiperList: {},
        isLastPage: false,
        page: 1,
        search: '',
        categories: 0,
        categoriesName: '',
        categoriesImage: "",
        showerror: "none",
        isCategoryPage: "none",
        isSearchPage: "none",
        showallDisplay: "block",
        displaySwiper: "block",
        floatDisplay: "none",
        searchKey: "",
    },
    formSubmit: function (e) {
        var url = '../list/list'
        if (e.detail.value.input != '') {
            url = url + '?search=' + e.detail.value.input;
        }
        wx.navigateTo({
            url: url
        })
    },
    onShareAppMessage: function () {

        var title = "分享“守望轩”";
        var path = ""

        if (this.data.categories && this.data.categories != 0) {
            title += "的专题：" + this.data.categoriesList.name;
            path = 'pages/list/list?categoryID=' + this.data.categoriesList.id;

        } else {
            title += "的搜索内容：" + this.data.searchKey;
            path = 'pages/list/list?search=' + this.data.searchKey;
        }


        return {
            title: title,
            path: path,
            success: function (res) {
                // 转发成功
            },
            fail: function (res) {
                // 转发失败
            }
        }
    },
    onReachBottom: function () {

        var self = this;
        if (!self.data.isLastPage) {
            self.setData({
                page: self.data.page + 1
            });
            console.log('当前页' + self.data.page);
            this.fetchPostsData(self.data);
        } else {
            console.log('最后一页');
        }

    },
    reload: function (e) {
        var self = this;
        if (self.data.categories && self.data.categories != 0) {

            self.setData({
                // categories: options.categoryID,
                isCategoryPage: "block",
                showallDisplay: "none",
                showerror: "none",

            });
            self.fetchCategoriesData(self.data.categories);
        }
        if (self.data.search && self.data.search != '') {
            self.setData({
                //search: options.search,
                isSearchPage: "block",
                showallDisplay: "none",
                showerror: "none",
                searchKey: self.data.search
            })
        }
        self.fetchPostsData(self.data);
    },
    //加载分页
    loadMore: function (e) {
        var self = this;
        if (!self.data.isLastPage) {
            self.setData({
                page: self.data.page + 1
            });
            console.log('当前页' + self.data.page);
            this.fetchPostsData(self.data);
        } else {
            wx.showToast({
                title: '没有更多内容',
                mask: false,
                duration: 1000
            });
        }
    },
    onLoad: function (options) {
        var self = this;
        if (options.search && options.search != '') {
            wx.setNavigationBarTitle({
                title: "搜索关键字：" + options.search,
                success: function (res) {
                    // success
                }
            });
            self.setData({
                search: options.search,
                isSearchPage: "block",
                searchKey: options.search
            })

            this.fetchPostsData(self.data);
        }
    },
    //获取文章列表数据
    fetchPostsData: function (data) {

        var self = this;
        if (!data) data = {};
        if (!data.page) data.page = 1;
        // if (!data.categories) data.categories = 0;
        // if (!data.search) data.search = '';
        if (data.page === 1) {
            self.setData({
                postsList: []
            });
        }
        ;
        wx.showLoading({
            title: '正在加载',
            mask: true
        });
        console.log(data)
        wx.request({
            url: app.globalData.url + 'post/list',
            method: "GET",
            header: {
                'content-type': 'application/x-www-form-urlencoded'
            },
            data: {
                no : data.page,
                size : pageCount,
                title: data.search
            },
            success: function (result) {
                console.log(result);
                self.setData({
                    floatDisplay: "block",
                    postsList: self.data.postsList.concat(result.data)
                });
                setTimeout(function () {
                    wx.hideLoading();
                }, 900);
                if(result.data.length < pageCount)
                {
                    self.setData({
                        isLastPage: true
                    });
                    wx.showToast({
                        title: '没有更多内容',
                        mask: false,
                        duration: 1500
                    });
                }
            }
        })
    },


    // 跳转至查看文章详情
    redictDetail: function (e) {
        // console.log('查看文章');
        var id = e.currentTarget.id,
            url = '../detail/detail?id=' + id;
        wx.navigateTo({
            url: url
        })
    },

    //获取分类列表
    fetchCategoriesData: function (id) {
        var self = this;
        self.setData({
            categoriesList: []
        });

        var getCategoryRequest = wxRequest.getRequest(Api.getCategoryByID(id));

        getCategoryRequest.then(response => {

            var catImage = "";
            if (typeof (response.data.category_thumbnail_image) == "undefined" || response.data.category_thumbnail_image == "") {
                catImage = "../../images/website.png";
            } else {
                catImage = response.data.category_thumbnail_image;
            }

            self.setData({
                categoriesList: response.data,
                categoriesImage: catImage,
                categoriesName: response.data.name
            });

            wx.setNavigationBarTitle({
                title: response.data.name,
                success: function (res) {
                    // success
                }
            });

            self.fetchPostsData(self.data);

        })
    },

})



