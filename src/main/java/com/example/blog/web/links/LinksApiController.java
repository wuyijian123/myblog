package com.example.blog.web.links;

import com.alibaba.fastjson.JSONObject;
import com.example.blog.constant.Messages;
import com.example.blog.entity.Links;
import com.example.blog.service.LinksService;
import com.example.blog.util.StringUtils;
import com.example.blog.web.model.ApiResult;
import com.example.blog.web.model.VmLinks;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Api(tags = "友链管理")
@CrossOrigin
@RestController
@RequestMapping("api/v1/links")
public class LinksApiController {
    private final LinksService linksService;

    @Autowired
    public LinksApiController(LinksService linksService) {
        this.linksService = linksService;
    }

    @ApiOperation("查询所有友链")
    @GetMapping("/")
    public ApiResult get() {
        List<VmLinks> vmLinks = new ArrayList<>();
        linksService.getAll().forEach(links -> vmLinks.add(new VmLinks(links)));
        return new ApiResult(true, "success", vmLinks);
    }

    @ApiOperation("新增友链")
    @PostMapping(value = "/add", produces = "application/json")
    public ApiResult addLinks(@RequestParam(name = "token") String token, @RequestBody JSONObject json) {
        return addOrUpdate(null, json);
    }

    @ApiOperation("修改友链")
    @PutMapping(value = "/update/{id}", produces = "application/json")
    public ApiResult updateLinks(@RequestBody JSONObject json, @PathVariable(name = "id") UUID uuid, @RequestParam(name = "token") String token) {
        return addOrUpdate(uuid, json);
    }

    @ApiOperation("删除友链")
    @DeleteMapping("/delete/")
    public ApiResult deleteLinks(@RequestParam(name = "token") String token, @RequestBody JSONObject json) {
        String strId = json.getString("Links_id");
        if (StringUtils.isEmpty(strId)) {
            return new ApiResult(false, Messages.WRONG_ID.toString(), null);
        }
        UUID uuid = UUID.fromString(strId);

        return new ApiResult(true, linksService.remove(uuid).toString(), null);
    }

    private ApiResult addOrUpdate(UUID uuid, JSONObject json) {
        String name = json.getString("name");
        String link = json.getString("link");
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(link))
            return new ApiResult(false, Messages.INVALID_FORMAT.toString(), null);

        boolean create = uuid == null;
        Links links = new Links();
        links.setName(name);
        links.setLink(link);
        if (create) {
            return new ApiResult(true, linksService.add(links).toString(), new VmLinks(links));
        }
        links.setId(uuid);
        return new ApiResult(true, linksService.update(links).toString(), new VmLinks(links));

    }


}
