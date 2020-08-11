package com.bestfeng.dydj;

import com.bestfeng.dydj.mbg.mapper.TestMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = DydjApplication.class)
class DydjApplicationTests {

    @Autowired
    private TestMapper testMapper;

//    @Test
//    public void dataSourceTest(){
//        com.bestfeng.dydj.mbg.model.Test test = testMapper.selectByPrimaryKey(1);
//    }
}
