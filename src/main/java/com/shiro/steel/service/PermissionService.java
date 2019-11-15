package com.shiro.steel.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.shiro.steel.entity.Permission;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.shiro.steel.pojo.dto.PermisDto;
import com.shiro.steel.pojo.vo.PermisVo;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jwy
 * @since 2017-12-16
 */
public interface PermissionService extends IService<Permission> {

    /**
     * @desc: 查询菜单
     * 
     * @author: jwy
     * @date: 2017/12/26
     */
    List<Permission> findPermissionByPage(Page<Permission> page, ParamsDto dto);

    /**
     * @desc: 新增菜单
     *
     * @author: jwy
     * @date: 2017/12/26
     */
    @Transactional
    Object addPermissions(PermisVo vo);

    /**
     * @desc: 删除菜单
     *
     * @author: jwy
     * @date: 2017/12/26
     */
    @Transactional
    Object delPermis(String[] ids);

    /**
     * @desc: 根据菜单查询菜单
     *
     * @author: jwy
     * @date: 2017/12/28
     */
    List<Permission> findPermissionByName(String name);

    /**
     * @desc: 根据父级id查询上级菜单
     * 
     * @author: jwy
     * @date: 2018/1/2
     */
    List<Permission> findLastPermissionByType(String type);

    /**
     * @desc: 查询所有父级菜单绑定树形
     *
     * @author: jwy
     * @date: 2018/1/3
     */
    List<PermisDto> findBasePermission();

    /**
     * 根据url查询记录
     *
     * @param requestUrl
     * @return
     */
    Integer findCountByUrl(String requestUrl);
}
