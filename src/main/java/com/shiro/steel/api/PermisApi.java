package com.shiro.steel.api;

import com.baomidou.mybatisplus.plugins.Page;
import com.shiro.steel.Enum.EnumCode;
import com.shiro.steel.api.base.BaseApi;
import com.shiro.steel.entity.Permission;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.shiro.steel.pojo.dto.PermisDto;
import com.shiro.steel.pojo.vo.PermisVo;
import com.shiro.steel.service.PermissionService;
import com.shiro.steel.utils.ResultUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "PermisApi/v1")
public class PermisApi extends BaseApi{

    @Autowired
    private PermissionService permissionService;

    /**
     * @desc: 查询菜单
     * 
     * @author: jwy
     * @date: 2017/12/26
     */
    @RequestMapping(value = "/findPermissionByPage" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object findPermissionByPage(ParamsDto dto) {

        Page<Permission> page = new Page<Permission>(dto.getStartPage(),dto.getPageSize());
        List<Permission> list = permissionService.findPermissionByPage(page,dto);
        return ResultUtil.result(EnumCode.OK.getValue(),EnumCode.OK.getText(),list,page.getTotal());
    }

    /**
     * @desc: 新增菜单
     *
     * @author: jwy
     * @date: 2017/12/26
     */
    @RequestMapping(value = "/addPermissions" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object addPermissions(@Valid PermisVo vo, BindingResult bindingResult) {
      vo.setUserName(super.getUserName());
      vo.setUserId(super.getUserId());
      return permissionService.addPermissions(vo);
    }

    /**
     * @desc: 删除菜单
     * 
     * @author: jwy
     * @date: 2017/12/26
     */
    @RequestMapping(value = "/delPermis",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object delPermis(ParamsDto dto) {
        if (null == dto.getIds() || dto.getIds().length == 0) {
            return ResultUtil.result(EnumCode.BAD_REQUEST.getValue(), EnumCode.BAD_REQUEST.getText());
        }
        return permissionService.delPermis(dto.getIds());
    }

    /**
     * @desc: 查询父级菜单为0的所有菜单
     * 
     * @author: jwy
     * @date: 2018/1/2
     */
    @RequestMapping(value = "/findLastPermissionByType" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object findAllBasePermission(String type) {
        List<Permission> list = permissionService.findLastPermissionByType(type);
        if (null == list ||list.isEmpty()) {
            return ResultUtil.result(EnumCode.GONE.getValue(),"没有记录");
        }
        return ResultUtil.result(EnumCode.OK.getValue(),EnumCode.OK.getText(),list);
    }

    /**
     * @desc: 绑定树形菜单
     *
     * @author: jwy
     * @date: 2018/1/2
     */
    @RequestMapping(value = "/findBasePermission" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object findBasePermission() {
        List<PermisDto> list = permissionService.findBasePermission();
        return ResultUtil.result(EnumCode.OK.getValue(),EnumCode.OK.getText(),list);
    }
}
