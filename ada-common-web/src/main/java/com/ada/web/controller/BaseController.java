package com.ada.web.controller;

import com.ada.common.util.AdaResponse;
import com.ada.web.biz.BaseBiz;
import com.ada.web.context.BaseContextHandler;
import com.ada.web.service.Query;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author jack
 */
@Slf4j
public class BaseController<Biz extends BaseBiz, Entity> {
    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected Biz baseBiz;

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public AdaResponse<Boolean> add(@RequestBody Entity entity) {
        baseBiz.insertSelective(entity);
        return AdaResponse.success(true);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public AdaResponse<Entity> get(@PathVariable long id) {
        Object o = baseBiz.selectById(id);
        return AdaResponse.success((Entity) o);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public AdaResponse<Entity> get(@PathVariable int id) {
        Object o = baseBiz.selectById(id);
        return AdaResponse.success((Entity) o);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public AdaResponse<Boolean> update(@RequestBody Entity entity) {
        baseBiz.updateSelectiveById(entity);
        return AdaResponse.success(true);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public AdaResponse<Boolean> remove(@PathVariable int id) {
        baseBiz.deleteById(id);
        return AdaResponse.success(true);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public AdaResponse<Boolean> remove(@PathVariable long id) {
        baseBiz.deleteById(id);
        return AdaResponse.success(true);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public AdaResponse<List<Entity>> all() {
        List<Entity> list = baseBiz.selectListAll();
        return AdaResponse.success(list);
    }


    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ResponseBody
    public AdaResponse<Page<Entity>> page(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        Page<Entity> list = baseBiz.selectByQuery(query);
        return AdaResponse.success(list);
    }


    @RequestMapping(value = "/pageInfo", method = RequestMethod.GET)
    @ResponseBody
    public AdaResponse<PageInfo<Entity>> pageInfo(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        PageInfo<Entity> list = baseBiz.selectPageInfoByQuery(query);
        return AdaResponse.success(list);
    }

    public String getCurrentUserName() {
        return BaseContextHandler.getUsername();
    }
}
