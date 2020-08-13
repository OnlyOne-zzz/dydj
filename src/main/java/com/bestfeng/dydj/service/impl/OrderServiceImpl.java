package com.bestfeng.dydj.service.impl;

import com.bestfeng.dydj.constants.Constants;
import com.bestfeng.dydj.dto.OrderDto;
import com.bestfeng.dydj.mbg.mapper.ContentMapper;
import com.bestfeng.dydj.mbg.mapper.NoteMapper;
import com.bestfeng.dydj.mbg.mapper.OrderMapper;
import com.bestfeng.dydj.mbg.model.Content;
import com.bestfeng.dydj.mbg.model.Note;
import com.bestfeng.dydj.mbg.model.Order;
import com.bestfeng.dydj.mbg.model.OrderExample;
import com.bestfeng.dydj.service.OrderService;
import com.bestfeng.dydj.utils.IDUtils;
import org.aurochsframework.boot.commons.service.AbstractGeneralService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;

/**
 * @author bsetfeng
 * @since 1.0
 **/
@Service
public class OrderServiceImpl extends AbstractGeneralService<Order> implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ContentMapper contentMapper;
    @Autowired
    private NoteMapper noteMapper;

    @Override
    public Object getMapper() {
        return orderMapper;
    }

    @Override
    public Object getExample() {
        return new OrderExample();
    }

    /**
     * 下订单
     * @param orderDto
     */
    @Override
    public void doOrder(OrderDto orderDto) {
        Integer contentId = orderDto.getContentId();
        Integer noteid = orderDto.getNoteid();
        Content content = contentMapper.selectByPrimaryKey(contentId);
        Assert.notNull(content,"服务项目不存在");
        Note note = noteMapper.selectByPrimaryKey(noteid);
        Assert.notNull(note,"技师不存在");
        /**项目价格*/
        Float money = content.getMoney();
        /**上门交通费用*/
        BigDecimal trafficPrice = orderDto.getTrafficPrice();
        //todo 总金额
        BigDecimal totalMoney = new BigDecimal(String.valueOf(money)).add(trafficPrice);
        String orderId = IDUtils.getId(Constants.ORDER_NO_PRE);
        Order order = new Order();
        BeanUtils.copyProperties(orderDto,order);
        order.setOrderid(orderId);
        order.setUniacid(3);
        order.setContentThumb(content.getThumb());
        order.setContentName(content.getTitle());
        order.setNoteName(note.getShopname());
        orderMapper.insert(order);
    }
}
