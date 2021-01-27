package com.bob.web.system.mapper;

import com.bob.web.system.domain.SystemUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: zhang bob
 * @date: 2021-01-27 11:58
 * @description:
 */
@Mapper
public interface SystemUserMapper {
    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    SystemUser findUserByUsername(String username);

}
