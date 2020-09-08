package com.bestfeng.dydj.service.impl;

import com.bestfeng.dydj.constants.Constants;
import com.bestfeng.dydj.dto.OrderDto;
import com.bestfeng.dydj.enums.OrderEnums;
import com.bestfeng.dydj.enums.TravelTypeEnums;
import com.bestfeng.dydj.enums.UserEnums;
import com.bestfeng.dydj.manager.travel.TravelServiceSupport;
import com.bestfeng.dydj.mbg.mapper.*;
import com.bestfeng.dydj.mbg.model.*;
import com.bestfeng.dydj.service.OrderService;
import com.bestfeng.dydj.utils.IDUtils;
import lombok.extern.slf4j.Slf4j;
import org.aurochsframework.boot.commons.service.AbstractGeneralService;
import org.aurochsframework.boot.core.exceptions.BusinessException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
public class OrderServiceImpl extends AbstractGeneralService<NoteOrder> implements OrderService {

    @Autowired
    private NoteOrderMapper orderMapper;
    @Autowired
    private MsgContentMapper msgContentMapper;
    @Autowired
    private NoteMapper noteMapper;
    @Autowired
    private CompanyAccountMapper companyAccountMapper;
    @Autowired
    private CouponOrderMapper couponOrderMapper;
    @Autowired
    private TravelServiceSupport travelServiceSupport;
    @Autowired
    private ContentGoodItemsMapper goodItemsMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private MsgIdListMapper msgIdListMapper;

    @Override
    public Object getMapper() {
        return orderMapper;
    }

    @Override
    public Object getExample() {
        return new OrderExample();
    }

//    private static List<Integer> ARTIFICER_DO_STATUS =new ArrayList<>();
//    private static List<Integer> USER_DO_STATUS =new ArrayList<>();
//    static {
//        ARTIFICER_DO_STATUS.add(OrderEnums.OrderStatusEnum.PAY_SUCCESS.getCode());
//        ARTIFICER_DO_STATUS.add(OrderEnums.OrderStatusEnum.DOOR_ARRIVE.getCode());
//        ARTIFICER_DO_STATUS.add(OrderEnums.OrderStatusEnum.SERVICE_START.getCode());
//
//        USER_DO_STATUS.add(OrderEnums.OrderStatusEnum.WAIT_EVALUATE.getCode());
//    }

    /**
     * 下订单
     * @param orderDto
     */
    @Override
//    @RedisLock(name = Constants.SAVE_ORDER_LOCK_KEY,key = "#orderDto.uid")
    @Transactional(rollbackFor = Exception.class)
    public OrderDto saveOrder(OrderDto orderDto) {
        Integer uid = orderDto.getUid();
        Integer contentId = orderDto.getCurrentid();
        Integer noteId = orderDto.getShopid();
        Integer couponId = orderDto.getCouponid();
        ContentGoodItems goodItems = goodItemsMapper.selectByPrimaryKey(contentId);
        /**项目价格*/
        Float contentMoney = goodItems.getMoney();
        Integer msgContentPid = goodItems.getPid();
        MsgContent content = msgContentMapper.selectByPrimaryKey(msgContentPid);
        Assert.notNull(content,"服务项目不存在");
        /**技师*/
        Note note = noteMapper.selectByPrimaryKey(noteId);
        Assert.notNull(note,"技师不存在");
        Integer trafficType = orderDto.getTrafficType();
        BigDecimal trafficReckonMile = orderDto.getTrafficReckonMile();
        /**上门交通费用*/
        Double trafficMoneyDouble =travelServiceSupport.travelFare(TravelTypeEnums.ofValue(trafficType),String.valueOf(trafficReckonMile));
        BigDecimal trafficMoney= BigDecimal.valueOf(trafficMoneyDouble);
        /**卡券金额*/
        BigDecimal couponPrice = new BigDecimal("0");
        if(null!=couponId && 0!=couponId){
            //todo 查询具体的卡券表
            CouponOrder couponOrder = couponOrderMapper.selectByPrimaryKey(couponId);
            String couponMoney = couponOrder.getMoney();
            couponPrice = new BigDecimal(couponMoney);
        }
        //todo 总金额
        BigDecimal totalMoney = new BigDecimal(String.valueOf(contentMoney)).add(trafficMoney).subtract(couponPrice);
        String orderId = IDUtils.getId(Constants.ORDER_NO_PRE);
        NoteOrder order = new NoteOrder();
        BeanUtils.copyProperties(orderDto,order);
        order.setOrderid(orderId);
        order.setPaid(OrderEnums.OrderPayStatusEnum.PAY_OFF.getCode());
        order.setPid(msgContentPid);
        order.setShopid(noteId);
        order.setCurrentid(contentId);
        order.setMoney(totalMoney);
        order.setUniacid(Constants.WECHAT_PID);
        order.setContentThumb(content.getThumb());
        order.setContentName(content.getTitle());
        order.setNoteName(note.getShopname());
        order.setNoteAvatarUrl(note.getAvatarurl());
        order.setTrafficMoney(trafficMoney);
        order.setCreatetime((int)(System.currentTimeMillis()/1000));
        orderMapper.insertSelective(order);
        OrderDto responseOrder = new OrderDto();
        responseOrder.setOrderid(orderId);

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderid(orderId);
        orderDetail.setUid(uid);
        orderDetail.setUniacid(Constants.WECHAT_PID);
        orderDetail.setPid(order.getId());
        orderDetailMapper.insertSelective(orderDetail);

        MsgIdList msgIdList = new MsgIdList();
        msgIdList.setUid(uid);
        msgIdList.setUniacid(Constants.WECHAT_PID);
        msgIdList.setCreatetime((int)System.currentTimeMillis()/1000);
//        msgIdList.setFormId();
        msgIdListMapper.insertSelective(msgIdList);
        return responseOrder;
    }

    /**
     * 用户退款
     * @param orderDto
     */
    @Override
//    @RedisLock(name = Constants.REFUND_ORDER_LOCK_KEY,key = "#orderDto.uid")
    public void userRefund(OrderDto orderDto) {
       NoteOrder order = this.checkOrderExist(orderDto.getId());
       //todo 退款业务
    }

    /**
     * 技师操作订单
     * @param orderDto
     */
    @Override
//    @RedisLock(name = Constants.UPDATE_ORDER_LOCK_KEY,key = "#orderDto.uid")
    @Transactional(rollbackFor = Exception.class)
    public void operationOrder(OrderDto orderDto) {
        OrderEnums.OrderStatusEnum updateStatusEnum = orderDto.getOrderStatusEnum();
        NoteOrder order = this.checkOrderExist(orderDto.getId());
        Integer status = order.getPaid();
        OrderEnums.OrderStatusEnum orderStatusEnum = OrderEnums.OrderStatusEnum.getNameByCode(status);
        this.checkUserIdentity(order.getUid(),orderDto.getUserIdentityEnum());
        this.checkOrderStatus(orderStatusEnum,updateStatusEnum,orderDto.getUserIdentityEnum());
        order.setPaid(updateStatusEnum.getCode());
        order.setUpdatetime((int)(System.currentTimeMillis()/1000));
        orderMapper.updateByPrimaryKeySelective(order);
    }

    /**
     * 用户评价订单
     * @param orderId
     */
    @Override
    public void userEvaluateCallBack(Integer orderId) {
        NoteOrder order = this.checkOrderExist(orderId);
        Integer orderStatus = order.getPaid();
//        if(OrderEnums.OrderStatusEnum.WAIT_EVALUATE.getCode()!=orderStatus){
//             throw new BusinessException("订单非待评价状态不可评价成功修改");
//        }
//        order.setPaid(OrderEnums.OrderStatusEnum.EVALUATE_SUCCESS.getCode());
//        orderMapper.updateByPrimaryKeySelective(order);
    }

    /***
     * 通过技师id查询已完成订单的数量
     * @param noteId
     * @return
     */
    @Override
    public Integer getOrderNumByNoteId(Integer noteId) {
        return orderMapper.selectListByNoteId(noteId);
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
//                if(!ARTIFICER_DO_STATUS.contains(orderStatus)){
//                    throw new BusinessException("非技师可操作的订单状态");
//                }
//                /**应该去掉支付成功状态,但是不影响整个校验,暂时不处理*/
//                if(!ARTIFICER_DO_STATUS.contains(updateOrderStatus)){
//                    throw new BusinessException("技师操作超出可变更为的订单状态");
//                }
                if(updateOrderStatus-orderStatus!=1){
                    throw new BusinessException("技师操作的订单状态异常");
                }
                break;
            case USER:
//                if(!USER_DO_STATUS.contains(orderStatus)){
//                    throw new BusinessException("非普通用户可操作的订单状态");
//                }
//                if(OrderEnums.OrderStatusEnum.EVALUATE_SUCCESS != updateStatusEnum){
//                    throw new BusinessException("普通用户只可以操作为已评价订单状态");
//                }
            default:
                throw new BusinessException("身份异常");
        }
    }

    /**
     * 校验订单是否存在
     * @param id
     * @return
     */
    private NoteOrder checkOrderExist(Integer id){
        NoteOrder order = orderMapper.selectByPrimaryKey(id);
        Optional.ofNullable(order).orElseThrow(()->new BusinessException("订单不存在"));
        return order;
    }
}
