package com.bob.web.system.service;


import com.bob.web.system.domain.SystemMenu;
import com.bob.web.system.domain.SystemUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SystemMenuServiceTest {

    @Autowired
    private SystemMenuService systemMenuService;
    @Test
    public void testService(){

        //获取用户信息
        SystemUser user = new SystemUser();
        user.setLoginName("admin");
        user.setUserId(new Long("1"));

        //根据用户id获取菜单
        List<SystemMenu> systemMenuList = systemMenuService.findMenuListByUser(user);

        systemMenuList.stream().forEach(e ->{
            System.out.println(e);
        });

    }
}