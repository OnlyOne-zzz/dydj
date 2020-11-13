package com.bestfeng.dydj;

import com.bestfeng.dydj.controller.request.NoteListRequest;
import com.bestfeng.dydj.enums.NoteServiceStatusEnums;
import com.bestfeng.dydj.enums.OrderEnums;
import com.bestfeng.dydj.mbg.mapper.NoteOrderMapper;
import com.bestfeng.dydj.mbg.mapper.TestMapper;
import com.bestfeng.dydj.mbg.model.NoteOrder;
import com.bestfeng.dydj.service.CouponOrderService;
import com.bestfeng.dydj.service.NoteService;
import com.bestfeng.dydj.service.OrderService;
import com.bestfeng.dydj.service.impl.OrderServiceImpl;
import com.bestfeng.dydj.utils.RedisUtil;
import com.bestfeng.dydj.vo.NoteVo;
import com.github.pagehelper.Page;
import lombok.Getter;
import lombok.Setter;
import org.aurochsframework.boot.commons.api.CommonPage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest(classes = DydjApplication.class)
class DydjApplicationTests {

    @Autowired
    private OrderServiceImpl orderService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private NoteOrderMapper noteOrderMapper;
    @Autowired
    private CouponOrderService couponOrderService;

    @Autowired
    private NoteService noteService;

    @Test
    public void dataSourceTest(){
//       orderService.delayOrderUnPay("JSAM355389453181075456");
//        System.out.println();
    }

    public void redisTest(){
        redisUtil.set("test","1");
    }

    @Test
    public void testNotePaging(){
        NoteListRequest request = new NoteListRequest();
        request.setContentPid(79);
        request.setServiceStatus(0);
        CommonPage<NoteVo> pages = noteService.paging(request);
        System.out.println(pages.getPageSize());
    }

    @Test
    public void delayTest(String orderNo) {
//        NoteOrder noteOrder = noteOrderMapper.selectObjByOrderNo(orderNo);
//        Optional.ofNullable(noteOrder).filter(order->order.getPaid() == OrderEnums.OrderPayStatusEnum.PAY_OFF.getCode()).ifPresent(
//                order->{
//                    Integer couponId = noteOrder.getCouponid();
//                    order.setStatus(OrderEnums.OrderStatusEnum.USER_CLOSE.getCode());
//                    //订单
//                    noteOrderMapper.updateByPrimaryKeySelective(order);
//                    //技师表
//                    orderService.noteServiceStatusHandel(noteOrder.getShopid(), NoteServiceStatusEnums.SERVICEABLE);
//                    if(null!=couponId && 0!=couponId){
//                        //卡券
//                        couponOrderService.cancelUse(couponId);
//                    }
//                    System.out.println("完成了");
////                    log.info("订单未支付订单关闭订单延迟消息消费 orderNo={} 关闭成功",orderNo);
//                }
//        );
    }
}
