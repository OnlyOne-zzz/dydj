package com.bestfeng.dydj.service.impl;

import com.bestfeng.dydj.annotation.RedisLock;
import com.bestfeng.dydj.constants.Constants;
import com.bestfeng.dydj.dto.OrderDto;
import com.bestfeng.dydj.enums.OrderEnums;
import com.bestfeng.dydj.enums.UserEnums;
import com.bestfeng.dydj.mbg.mapper.CompanyAccountMapper;
import com.bestfeng.dydj.mbg.mapper.ContentMapper;
import com.bestfeng.dydj.mbg.mapper.NoteMapper;
import com.bestfeng.dydj.mbg.mapper.OrderMapper;
import com.bestfeng.dydj.mbg.model.*;
import com.bestfeng.dydj.service.OrderService;
import com.bestfeng.dydj.utils.IDUtils;
import lombok.extern.slf4j.Slf4j;
import org.aurochsframework.boot.commons.service.AbstractGeneralService;
import org.aurochsframework.boot.core.exceptions.BusinessException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author bsetfeng
 * @since 1.0
 **/
@Service
@Slf4j
public class OrderServiceImpl extends AbstractGeneralService<Order> implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ContentMapper contentMapper;
    @Autowired
    private NoteMapper noteMapper;
    @Autowired
    private CompanyAccountMapper companyAccountMapper;

    @Override
    public Object getMapper() {
        return orderMapper;
    }

    @Override
    public Object getExample() {
        return new OrderExample();
    }

    private static List<Integer> ARTIFICER_DO_STATUS =new ArrayList<>();
    private static List<Integer> USER_DO_STATUS =new ArrayList<>();
    static {
        ARTIFICER_DO_STATUS.add(OrderEnums.OrderStatusEnum.PAY_SUCCESS.getCode());
        ARTIFICER_DO_STATUS.add(OrderEnums.OrderStatusEnum.DOOR_ARRIVE.getCode());
        ARTIFICER_DO_STATUS.add(OrderEnums.OrderStatusEnum.SERVICE_START.getCode());

        USER_DO_STATUS.add(OrderEnums.OrderStatusEnum.WAIT_EVALUATE.getCode());
    }

    /**
     * 下订单
     * @param orderDto
     */
    @Override
    @RedisLock(name = Constants.SAVE_ORDER_LOCK_KEY,key = "#orderDto.uid")
    public void saveOrder(OrderDto orderDto) {
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

    /**
     * 用户退款
     * @param orderDto
     */
    @Override
    public void userRefund(OrderDto orderDto) {
       Order order = this.checkOrderExist(orderDto.getId());
       //todo 退款业务
    }

    /**
     * 技师操作订单
     * @param orderDto
     */
    @Override
    public void operationOrder(OrderDto orderDto) {
        OrderEnums.OrderStatusEnum updateStatusEnum = orderDto.getOrderStatusEnum();
        Order order = this.checkOrderExist(orderDto.getId());
        Integer status = order.getStatus();
        OrderEnums.OrderStatusEnum orderStatusEnum = OrderEnums.OrderStatusEnum.getNameByCode(status);
        this.checkUserIdentity(order.getUid(),orderDto.getUserIdentityEnum());
        this.checkOrderStatus(orderStatusEnum,updateStatusEnum,orderDto.getUserIdentityEnum());
        order.setStatus(updateStatusEnum.getCode());
        order.setUpdatetime((int)(System.currentTimeMillis()/1000));
        orderMapper.updateByPrimaryKeySelective(order);
    }

    /**
     * 用户评价订单
     * @param orderId
     */
    @Override
    public void userEvaluateCallBack(Integer orderId) {
        Order order = this.checkOrderExist(orderId);
        Integer orderStatus = order.getStatus();
        if(OrderEnums.OrderStatusEnum.WAIT_EVALUATE.getCode()!=orderStatus){
             throw new BusinessException("订单非待评价状态不可评价成功修改");
        }
        order.setStatus(OrderEnums.OrderStatusEnum.EVALUATE_SUCCESS.getCode());
        orderMapper.updateByPrimaryKeySelective(order);
    }

    /**
     * 校验是否是对应的身份在操作订单
     * @param uid
     * @return
     */
    private void checkUserIdentity(Integer uid, UserEnums.UserIdentityEnum identityEnum){
        log.info("校验是否是对应的身份在操作订单 uid={} identityEnum={}",uid,identityEnum.getName());
        switch (identityEnum){
            case ARTIFICER:
                CompanyAccount companyAccount = companyAccountMapper.selectObjByUid(uid);
                Optional.ofNullable(companyAccount).orElseThrow(()->new BusinessException("用户不是技师,没有对应的操作权限"));
                break;
            case USER:
                log.debug("普通用户暂时不用校验身份");
                break;
            default:
                throw new BusinessException("身份异常");
        }
    }

    /**
     * 校验是否正确的操作订单状态
     * @param orderStatusEnum
     * @param updateStatusEnum
     * @return
     */
    private void checkOrderStatus(OrderEnums.OrderStatusEnum orderStatusEnum,OrderEnums.OrderStatusEnum updateStatusEnum,UserEnums.UserIdentityEnum identityEnum){
        log.info("校验是否正确的操作订单状态 订单实际状态={} 订单期望修改后状态={}",orderStatusEnum.getName(),updateStatusEnum.getName());
        Integer orderStatus = orderStatusEnum.getCode();
        Integer updateOrderStatus= updateStatusEnum.getCode();
        switch (identityEnum){
            case ARTIFICER:
                if(!ARTIFICER_DO_STATUS.contains(orderStatus)){
                    throw new BusinessException("非技师可操作的订单状态");
                }
                /**应该去掉支付成功状态,但是不影响整个校验,暂时不处理*/
                if(!ARTIFICER_DO_STATUS.contains(updateOrderStatus)){
                    throw new BusinessException("技师操作超出可变更为的订单状态");
                }
                if(updateOrderStatus-orderStatus!=1){
                    throw new BusinessException("技师操作的订单状态异常");
                }
                break;
            case USER:
                if(!USER_DO_STATUS.contains(orderStatus)){
                    throw new BusinessException("非普通用户可操作的订单状态");
                }
                if(OrderEnums.OrderStatusEnum.EVALUATE_SUCCESS != updateStatusEnum){
                    throw new BusinessException("普通用户只可以操作为已评价订单状态");
                }
            default:
                throw new BusinessException("身份异常");
        }
    }

    /**
     * 校验订单是否存在
     * @param id
     * @return
     */
    private Order checkOrderExist(Integer id){
        Order order = orderMapper.selectByPrimaryKey(id);
        Optional.ofNullable(order).orElseThrow(()->new BusinessException("订单不存在"));
        return order;
    }
}
