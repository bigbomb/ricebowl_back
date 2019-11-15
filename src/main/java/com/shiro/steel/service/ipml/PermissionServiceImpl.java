package com.shiro.steel.service.ipml;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shiro.steel.Enum.EnumCode;
import com.shiro.steel.entity.Permission;
import com.shiro.steel.entity.ProcessOrderDetail;
import com.shiro.steel.entity.RolePermission;
import com.shiro.steel.mapper.PermissionMapper;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.shiro.steel.pojo.dto.PermisDto;
import com.shiro.steel.pojo.dto.UserInfoDto;
import com.shiro.steel.pojo.vo.PermisVo;
import com.shiro.steel.service.PermissionService;
import com.shiro.steel.service.RolePermissionService;
import com.shiro.steel.utils.ResultUtil;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jwy
 * @since 2017-12-16
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

	@Autowired
	private RolePermissionService rolePermissionService;
    /**
     * @desc: 查询菜单
     *
     * @author: jwy
     * @date: 2017/12/26
     */
    public List<Permission> findPermissionByPage(Page<Permission> page,ParamsDto dto) {
        return super.baseMapper.findPermissionByPage(page,dto);
    }

    /**
     * @desc: 新增菜单
     *
     * @author: jwy
     * @date: 2017/12/26
     */
    public Object addPermissions(PermisVo vo) {

    	UserInfoDto userInfoDto = new UserInfoDto();
        Subject subject = SecurityUtils.getSubject();   
	    userInfoDto = (UserInfoDto) subject.getPrincipal();
        Permission p = new Permission();
        p.setName(vo.getName());
        p.setUrl(vo.getUrl());
        p.setType(vo.getType());
        p.setFatherId(vo.getLastId());
        p.setCreateTime(new Date());
        p.setCreater(vo.getUserName());
        baseMapper.insert(p);
        if(!"0".equals(p.getFatherId()))
        {
//        	List<PermisDto> fatherlist = super.baseMapper.findPermissionByFatherId(p.getFatherId());
        	
        	RolePermission pp = new RolePermission();
        	pp.setRid(userInfoDto.getRoleid());
        	pp.setPid(p.getFatherId());
        	EntityWrapper<RolePermission> wrapper = new EntityWrapper<RolePermission>(pp);
        	rolePermissionService.delete(wrapper);
        	
        	
        	Permission ps = new Permission();
        	ps.setId(p.getFatherId());
        	ps = super.baseMapper.selectOne(ps);
        	if (ps!=null)
        	{
        		String sFatherId = ps.getFatherId();
        	    if(!"0".equals(sFatherId))
        	    {
        	    	RolePermission ppp = new RolePermission();
                	ppp.setRid(userInfoDto.getRoleid());
                	ppp.setPid(sFatherId);
                	EntityWrapper<RolePermission> wwrapper = new EntityWrapper<RolePermission>(ppp);
                	rolePermissionService.delete(wwrapper);
        	    }
        	}
        	
        }
    	
        return ResultUtil.result(EnumCode.OK.getValue(), "新增成功");

    }

    /**
     * @desc: 删除菜单
     *
     * @author: jwy
     * @date: 2017/12/26
     */
    public Object delPermis(String[] ids) {

        for (String id : ids) {
           super.baseMapper.deleteById(id);
        }
        return ResultUtil.result(EnumCode.OK.getValue(), "删除成功");
    }

    /**
     * @desc: 根据菜单查询菜单
     *
     * @author: jwy
     * @date: 2017/12/28
     */
    public List<Permission> findPermissionByName(String name) {
        return super.baseMapper.findPermissionByName(name);
    }

    /**
     * @desc: 根据父级id查询上级菜单
     *
     * @author: jwy
     * @date: 2018/1/2
     */
    public List<Permission> findLastPermissionByType(String type) {
        return super.baseMapper.findLastPermissionByType(type);
    }

    /**
     * @desc: 查询所有父级菜单绑定树形
     *
     * @author: jwy
     * @date: 2018/1/3
     */
    public List<PermisDto> findBasePermission() {
        List<PermisDto> list = super.baseMapper.findBasePermission();
        if (null != list && !list.isEmpty()) {
            for (int i = 0,j = list.size();i< j;i++) {
               List<PermisDto> children = super.baseMapper.findPermissionByFatherId(list.get(i).getId());
               if (null != children && !children.isEmpty()) {
                   list.get(i).setChildren((ArrayList<PermisDto>) children);
                   for (int i1 = 0, j1 = children.size();i1 < j1; i1++) {
                       List<PermisDto> children1 = super.baseMapper.findPermissionByFatherId(children.get(i1).getId());
                       if (null != children1 && !children1.isEmpty()) {
                           children.get(i1).setChildren((ArrayList<PermisDto>) children1);
                       }
                   }
               }
            }
        }
        return list;
    }

    /**
     * 根据url查询记录
     *
     * @param requestUrl
     * @return
     */
    public Integer findCountByUrl(String requestUrl) {
        return super.baseMapper.findCountByUrl(requestUrl);
    }
}
