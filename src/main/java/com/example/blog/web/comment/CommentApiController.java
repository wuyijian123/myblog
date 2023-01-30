package com.example.blog.web.comment;

import com.alibaba.fastjson.JSONObject;
import com.example.blog.constant.Messages;
import com.example.blog.entity.Article;
import com.example.blog.entity.Comment;
import com.example.blog.entity.User;
import com.example.blog.service.ArticleService;
import com.example.blog.service.CommentService;
import com.example.blog.service.UserService;
import com.example.blog.util.StringUtils;
import com.example.blog.web.model.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@Api(tags = "评论管理")
@CrossOrigin
@RestController
@RequestMapping("api/v1/comment")
public class CommentApiController {
    private final CommentService commentService;
    private  final UserService userService;
    private  final ArticleService articleService;
    @Autowired
    public CommentApiController(CommentService commentService, UserService userService, ArticleService articleService) {
        this.commentService = commentService;
        this.userService = userService;
        this.articleService = articleService;
    }


    @ApiOperation("查询所有评论")
    @GetMapping("/")
    public ApiResult getAll(){
        List<Comment> comments=commentService.get();
        return new ApiResult(true, Messages.SUCCESS.toString(), comments);
    }
    @ApiOperation("根据文章查询")
    @GetMapping("/article")
    public  ApiResult getAllByaId(@RequestParam(name="aId")UUID aId){
        List<Comment> comments=commentService.getByArticleId(aId);
        return new ApiResult(true, Messages.SUCCESS.toString(), comments);
    }
    @ApiOperation("根据用户查询")
    @GetMapping("/user")
    public  ApiResult getByuId(@RequestParam(name="uId")UUID uId){
        List<Comment> comments=commentService.getByUserId(uId);
        return new ApiResult(true, Messages.SUCCESS.toString(), comments);
    }
    @ApiOperation("发布评论")
    @PostMapping("/add")
    public ApiResult addComment( @RequestBody JSONObject json){
        String content= json.getString("comment_text");
        String uId= json.getString("user_id");
        String aId= json.getString("article_id");

        if(StringUtils.isEmpty(content)||StringUtils.isEmpty(uId)||StringUtils.isEmpty(aId)){
            return new ApiResult(false,Messages.INVALID_FORMAT.toString(),null);
        }
        User user=userService.getById(UUID.fromString(uId));
        Article article=articleService.getById(UUID.fromString(aId));
        if(user==null||article==null){
            return new ApiResult(false,Messages.WRONG_ID.toString(), null);
        }
        Comment comment=new Comment();
        comment.setComment(content);
        comment.setArticle(article);
        comment.setUser(user);
        return new ApiResult(true,Messages.SUCCESS.toString(), commentService.add(comment));
    }
    @ApiOperation("删评论")
    @DeleteMapping("/delete")
    public ApiResult delete(@RequestParam(name = "token")String token,@RequestParam(name = "cId") UUID uuid){
        if(commentService.getById(uuid)==null){
            return new ApiResult(true,Messages.WRONG_ID.toString(),null);
        }
        return new ApiResult(true,commentService.remove(uuid,token).toString(),null);
    }

    @DeleteMapping("/aDelete")
    public ApiResult adminDelete(@RequestParam(name = "token")String token,@RequestParam(name = "cId") UUID uuid){
        if(commentService.getById(uuid)==null){
            return new ApiResult(true,Messages.WRONG_ID.toString(),null);
        }
        return new ApiResult(true,commentService.remove(uuid).toString(),null);
    }
//    @DeleteMapping()
}
