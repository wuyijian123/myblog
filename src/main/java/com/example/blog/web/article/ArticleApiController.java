package com.example.blog.web.article;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.blog.constant.ApiConstant;
import com.example.blog.constant.Messages;
import com.example.blog.entity.Article;
import com.example.blog.entity.Category;
import com.example.blog.entity.Tag;
import com.example.blog.entity.User;
import com.example.blog.service.ArticleService;
import com.example.blog.service.CategoryService;
import com.example.blog.service.TagService;
import com.example.blog.service.UserService;
import com.example.blog.util.StringUtils;
import com.example.blog.web.model.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Api(tags = "文章管理")
@CrossOrigin
@RestController
@RequestMapping(ApiConstant.ROUTE_ARTICLE_ROOT)
public class ArticleApiController {
    private final ArticleService articleService;
    private final UserService userService;
    private final CategoryService categoryService;
    private final TagService tagService;

    @Autowired
    public ArticleApiController(ArticleService articleService, UserService userService, CategoryService categoryService, TagService tagService) {
        this.articleService = articleService;
        this.userService = userService;
        this.categoryService = categoryService;
        this.tagService = tagService;
    }


    @ApiOperation("查询全部文章")
    @GetMapping("/")
    public ApiResult getAll() {
        List<Article> articles = articleService.get();

        return new ApiResult(true, Messages.SUCCESS.toString(), articles);
    }
    @ApiOperation("分页查询")
    @GetMapping("/page")//分页
    public ApiResult getByPage(@RequestParam int page, @RequestParam int size) {
        List<Article> articles = articleService.get(page, size);
        return new ApiResult(true, Messages.SUCCESS.toString(), articles);
    }

    @ApiOperation("根据作者查询")
    @GetMapping("/creator")//根据作者查询
    public ApiResult getByCreatorId(@RequestParam UUID uuid) {
        List<Article> articles = articleService.getCreatorArticle(uuid);
        return new ApiResult(true, Messages.SUCCESS.toString(), articles);
    }
    @ApiOperation("根据分类查询")
    @GetMapping("/category")//根据分类查询
    public ApiResult getByCategory(@RequestParam String cName) {
        List<Article> articles = articleService.getByCategory(cName);
        return new ApiResult(true, Messages.SUCCESS.toString(), articles);
    }
    @ApiOperation("关键字查询")
    @GetMapping("/search")//关键字查询
    public ApiResult search(@RequestParam String kw) {
        List<Article> articles = articleService.search(kw);
        return new ApiResult(true, Messages.SUCCESS.toString(), articles);
    }

    @ApiOperation("新增文章")
    @PostMapping(value = "/add/", produces = "application/json")
    public ApiResult addArticle(@RequestParam(name = "token") String token, @RequestBody JSONObject json) {
        return addOrUpdate(null, json, token);
    }

    @ApiOperation("修改文章")
    @PutMapping(value = "/update", produces = "application/json")
    public ApiResult updateArticle(@RequestParam(name = "token") String token, @RequestBody JSONObject json, @RequestParam(name = "uuid") UUID uuid) {
        return addOrUpdate(uuid, json, token);
    }
    @ApiOperation("删除文章")
    @DeleteMapping("/delete")
    public ApiResult deleteArticle(@RequestParam(name = "aId")UUID uuid,@RequestParam(name = "token") String token){
        return new ApiResult(true,articleService.delete(uuid,token).toString(),null);
    }
    @ApiOperation("管理员删除文章")
    @DeleteMapping("/aDelete")//管理员删除
    public ApiResult adminDeleteArticle(@RequestParam(name = "aId")UUID uuid,@RequestParam(name = "token") String token){
        return new ApiResult(true,articleService.delete(uuid).toString(),null);
    }

    @ApiOperation("给博文添加标签")
    @PostMapping(value = "/addTag",produces = "application/json")
    public ApiResult addTag(@RequestParam(name="aId")UUID uuid ,@RequestParam(name = "token")String token,@RequestBody JSONObject jsonObject){
        Article article=articleService.getById(uuid);
        String tagId=jsonObject.getString("tag_Id");
        Tag tag=tagService.getById(UUID.fromString(tagId));
        if (article==null||StringUtils.isEmpty(tagId)||tag==null){
            return new ApiResult(false,Messages.WRONG_ID.toString(),null);
        }
        Set<Tag> tags=article.getTags();
        tags.add(tag);
        return new ApiResult(true,Messages.SUCCESS.toString(), articleService.update(article,token));
    }
    @ApiOperation("给博文删除标签")
    @DeleteMapping(value = "/removeTag",produces = "application/json")
    public  ApiResult removeTag(@RequestParam(name="aId")UUID uuid ,@RequestParam(name = "token")String token,@RequestBody JSONObject jsonObject){
        Article article=articleService.getById(uuid);
        String tagId=jsonObject.getString("tag_Id");
        Tag tag=tagService.getById(UUID.fromString(tagId));

        if (article==null||StringUtils.isEmpty(tagId)||tag==null){
            return new ApiResult(false,Messages.WRONG_ID.toString(),null);
        }
        Set<Tag> tags=article.getTags();
        tags.remove(tag);
        return new ApiResult(true,Messages.SUCCESS.toString(), articleService.update(article,token));
    }



    public ApiResult addOrUpdate(UUID uuid, JSONObject json, String token) {
        String title = json.getString("title");
        String content = json.getString("content");
        String cover = json.getString("cover");
        boolean open = json.getBoolean("open");
        String category = json.getString("category_id");
        String creatorId = json.getString("creator_id");
        JSONArray tagList = json.getJSONArray("tags");
        System.out.println(tagList);
        if (StringUtils.isEmpty(title) ||
                StringUtils.isEmpty(category) ||
                StringUtils.isEmpty(creatorId) ||
                StringUtils.isEmpty(content) ||
                StringUtils.isEmpty((cover))) return new ApiResult(false, Messages.INVALID_FORMAT.toString(), null);

        User creator = userService.getById(UUID.fromString(creatorId));
        Category category1 = categoryService.getById(UUID.fromString(category));
        if (creator == null || category1 == null) return new ApiResult(false, Messages.WRONG_ID.toString(), null);
        Article article = new Article();
        boolean create = uuid == null;
        article.setCreator(creator);
        article.setContent(content);
        article.setCategory(category1);
        article.setTitle(title);
        article.setOpen(open);
        if (tagList!=null) {//获取的tag列表不为空执行添加tag
            List<Tag> tags = JSONObject.parseArray(tagList.toJSONString(), Tag.class);
            Set<Tag> tags1 = new HashSet<>();

            tags.forEach(tag -> {//如果tag不存在就添加tag
                Tag tag1 = tagService.getByName(tag.getName());
                if (tag1 == null) {
                    tag1 = tagService.add(tag);
                }
                tags1.add(tag1);
            });
            article.setTags(tags1);
        }
//        List<Tag> tags=tagService.getAllByArticleId(uuid);
        if (create) {
            return new ApiResult(true, "博文创建成功", articleService.add(article));
        } else {
            article.setId(uuid);
            return new ApiResult(true, "修改成功", articleService.update(article, token));
        }
    }


}
