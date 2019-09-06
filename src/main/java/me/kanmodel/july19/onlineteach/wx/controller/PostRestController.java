package me.kanmodel.july19.onlineteach.wx.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import me.kanmodel.july19.onlineteach.dao.PostRepository;
import me.kanmodel.july19.onlineteach.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "内容管理")
@RestController
@RequestMapping("/api/post")
public class PostRestController {
    @Autowired
    private PostRepository postRepository;

    @ApiOperation("获取内容列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "内容标题", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "no", value = "页号", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "页大小", dataType = "int")})
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    private ResponseEntity<List<Post>> getPostList(@RequestParam(value = "title", defaultValue = "", required = false) String title,
                                                   @RequestParam(value = "no", defaultValue = "1", required = false) int pageNo,
                                                   @RequestParam(value = "size", defaultValue = "6", required = false) int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<Post> postPage = postRepository.findAllByTitleAndIsDelete(title,pageable);
        return new ResponseEntity<>(postPage.getContent(), HttpStatus.OK);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "postid", value = "内容ID", dataType = "long", paramType = "query")})
    @ApiOperation("获取内容")
    @RequestMapping(value = "/{postid}", method = RequestMethod.GET)
    private ResponseEntity<Post> getPost(@PathVariable long postid) {
        return new ResponseEntity<>(postRepository.findById(postid).get(), HttpStatus.OK);
    }
}
