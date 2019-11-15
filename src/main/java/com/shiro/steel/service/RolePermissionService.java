package com.shiro.steel.service;

import com.baomidou.mybatisplus.service.IService;
import com.shiro.steel.entity.RolePermission;
import com.shiro.steel.pojo.dto.ParamsDto;
import com.shiro.steel.pojo.dto.RolePermisDto;
import com.shiro.steel.pojo.vo.RolePermisVo;

import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jwy
 * @since 2017-12-16
 */
public interface RolePermissionService extends IService<RolePermission> {

    /**
     * 添加角色权限
     *
     * @param vo
     * @return
     */
	@Transactional
    Object addRolesPermis(RolePermisVo vo);

    /**
     * 根据角色查询角色权限
     *
     * @param dto
     * @return
     */
    List<RolePermission> findRolesPermisByRole(ParamsDto dto);

    /**
     * 根据角色id查询记录数
     * @param roleId
     * @return
     */
    Integer findCountByRole(String roleId,String url);

    /**
     * 根据父id\角色id查询角色菜单
     *
     * @author: jwy
     * @date: 2018/1/20
     */
    List<RolePermisDto> findRolesPermisByFatherId(String fatherId, String rid);
}
