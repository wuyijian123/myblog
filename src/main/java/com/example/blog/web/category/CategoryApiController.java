package com.example.blog.web.category;

import com.alibaba.fastjson.JSONObject;
import com.example.blog.constant.Messages;
import com.example.blog.entity.Category;
import com.example.blog.service.CategoryService;
import com.example.blog.util.StringUtils;
import com.example.blog.web.model.ApiResult;
import com.example.blog.web.model.VmCategory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Api(tags = "分类管理")
@CrossOrigin
@RestController
@RequestMapping("api/v1/Category")
public class CategoryApiController {
    private  final CategoryService categoryService;
    @Autowired
    public CategoryApiController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @ApiOperation("查询分类")
    @GetMapping("/")
    public ApiResult getAll(){
        List<VmCategory> categories=new ArrayList<>();
        categoryService.getAll().forEach( category ->categories.add(new VmCategory(category)));
        return  new ApiResult(true,"success",categories);
    }

    private ApiResult addOrUpdate(UUID uuid, JSONObject json,String token){
        String name= json.getString("name");

        if(StringUtils.isEmpty(name)){
            return new ApiResult(false, Messages.INCOMPLETE_INFO.toString(), null);
        }
        boolean create = uuid == null;

        Category category = new Category();
        category.setName(name);
        if(create){
           Category add= categoryService.add(category,token);
           System.out.println("1");
           if(add==null){
               return new ApiResult(false,Messages.NO_PERMISSION.toString(),null);
           }
            return new ApiResult(true,"success",new VmCategory( categoryService.add(category,token)));
        }else{
            System.out.println("0");
            category.setId(uuid);
           Category update= categoryService.update(category,token);
           if(update==null){
               return new ApiResult(false,Messages.NO_PERMISSION.toString(),null);
           }
            return  new ApiResult(true,"success",new VmCategory(update));
        }
    }

    @ApiOperation("新增分类")
    @PostMapping(value = "/add",produces = "application/json")
    public  ApiResult addCategory(@RequestBody JSONObject json,@RequestParam(name = "token") String token){
        return addOrUpdate(null,json,token);
    }

    @ApiOperation("修改分类")
    @PutMapping (value = "/update/{id}",produces = "application/json")
    public  ApiResult updateCategory(@RequestBody JSONObject json,@PathVariable(name = "id") UUID uuid,@RequestParam(name = "token") String token){
        return addOrUpdate(uuid,json,token);
    }

    @ApiOperation("删除分类")
    @DeleteMapping("/delete/")
    public  ApiResult delete(@RequestParam(name = "token") String token,@RequestBody JSONObject json){
        String struId= json.getString("category_id");
        if(StringUtils.isEmpty(struId)){
            return new ApiResult(false, Messages.INCOMPLETE_INFO.toString(), null);
        }
        try{
            UUID uuid=UUID.fromString(struId);
            Messages msg=categoryService.remove(uuid,token);
            return new ApiResult(true, msg.toString(), null);
        }catch (Exception e) {
            return new ApiResult(false, Messages.WRONG_ID.toString(), e.getMessage());
        }
    }

}
