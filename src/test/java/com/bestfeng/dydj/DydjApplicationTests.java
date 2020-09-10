package com.bestfeng.dydj;

import com.bestfeng.dydj.mbg.mapper.TestMapper;
import com.bestfeng.dydj.mbg.model.NoteOrder;
import com.bestfeng.dydj.service.impl.OrderServiceImpl;
import com.bestfeng.dydj.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = DydjApplication.class)
class DydjApplicationTests {

    @Autowired
    private OrderServiceImpl orderService;
//    @Autowired
//    private RedisUtil redisUtil;

    @Test
    public void dataSourceTest(){
       orderService.delayOrderUnPay("JSAM355389453181075456");
        System.out.println();
    }

//    public void redisTest(){
//        redisUtil.set("test","1");
//    }
}
