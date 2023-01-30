package com.example.blog.web.tag;

import com.alibaba.fastjson.JSONObject;
import com.example.blog.constant.ApiConstant;
import com.example.blog.constant.Messages;
import com.example.blog.entity.Tag;
import com.example.blog.service.TagService;
import com.example.blog.util.StringUtils;
import com.example.blog.web.model.ApiResult;
import com.example.blog.web.model.VmTag;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Api(tags = "标签管理")
@CrossOrigin
@RestController
@RequestMapping("api/v1/tag")
public class TagApiController {

    private final TagService tagService;

    @Autowired
    public TagApiController(TagService tagService) {
        this.tagService = tagService;
    }
    @ApiOperation("查询全部标签")
    @GetMapping("/")
    public ApiResult getAll(){
        List<VmTag> tags=new ArrayList<>();
                tagService.getAll().forEach(tag ->tags.add(new VmTag(tag)) );
                return new ApiResult(true, Messages.SUCCESS.toString(), tags);
    }
    @ApiOperation("新增标签")
    @PostMapping("/add")
    public  ApiResult add(@RequestBody JSONObject json){
        String name= json.getString("name");
        if(StringUtils.isEmpty(name))return new ApiResult(false,Messages.INVALID_FORMAT.toString(),null);
        Tag tag=new Tag();
        tag.setName(name);
        return new ApiResult(true,Messages.SUCCESS.toString(), new VmTag(tagService.add(tag)));
    }
    @ApiOperation("删除标签")
    @DeleteMapping ("/remove")
    public  ApiResult remove(@RequestParam(name = "tId")UUID uuid){
        Tag tag=tagService.getById(uuid);
        if(tag==null){
            return new ApiResult(false,Messages.INVALID_FORMAT.toString(),null);
        }
        return new ApiResult(true,tagService.remove(uuid).toString(), null);
    }
}
