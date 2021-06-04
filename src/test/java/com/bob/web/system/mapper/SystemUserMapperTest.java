package com.bob.web.system.mapper;

import com.bob.web.system.domain.SystemUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SystemUserMapperTest {

    @Autowired
    private SystemUserMapper mapper;

    @Test
    public void testFindUserByUsername () {
        SystemUser user = mapper.findUserByUsername("admin");
        System.out.println(user);
    }

}