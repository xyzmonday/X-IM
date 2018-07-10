package com.yff.xim;

import com.yff.xim.model.UserAccountEntity;
import com.yff.xim.module.user.mapper.UserAccountMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserAccountMaaperTest {

    @Autowired
    UserAccountMapper userAccountMapper;

    @Test
    public void test() {
        UserAccountEntity userAccount = userAccountMapper.findUserAccount("1", "1");
        System.out.println(userAccount);
    }


}
