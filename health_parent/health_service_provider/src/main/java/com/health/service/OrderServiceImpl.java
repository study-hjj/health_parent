package com.health.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.health.constant.MessageConstant;
import com.health.dao.MemberDao;
import com.health.dao.OrderDao;
import com.health.dao.OrderSettingDao;
import com.health.entity.Member;
import com.health.entity.Order;
import com.health.entity.OrderSetting;
import com.health.util.DateUtils;
import com.health.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderSettingDao orderSettingDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderDao orderDao;
    //体检预约
    public Result order(Map map) throws Exception {
        /**
         1、检查用户所选择的预约日期是否已经提前进行了预约设置，如果没有设置则无法进行预约
         2、检查用户所选择的预约日期是否已经约满，如果已经约满则无法预约
         3、检查用户是否重复预约（同一个用户在同一天预约了同一个套餐），如果是重复预约则无法完成再次预约
         4、检查当前用户是否为会员，如果是会员则直接完成预约，如果不是会员则自动完成注册并进行预约
         5、预约成功，更新当日的已预约人数
         */
        //获取日期
        String orderDate = (String) map.get("orderDate");
        Date date = DateUtils.parseString2Date(orderDate);
        OrderSetting orderSetting = orderSettingDao.selectByOrderDate(date);//根据日期查询预约设置信息
        if(orderSetting == null){
            //所选日期没有提前进行预约设置，不能完成预约
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }

        if(orderSetting.getReservations() >= orderSetting.getNumber()){
            //所选日期已经约满，无法预约
            return new Result(false, MessageConstant.ORDER_FULL);
        }

        //判断是否在重复预约
        String telephone = (String) map.get("telephone");
        Member member = memberDao.findByTelephone(telephone);
        if(member != null){
            Integer memberId = member.getId();//会员id
            Integer setmealId = Integer.parseInt((String)map.get("setmealId"));//套餐id
            Order order = new Order(memberId,date,setmealId);
            List<Order> orderList = orderDao.findByCondition(order);
            if(orderList != null && orderList.size() > 0){
                //用户在重复预约，不能完成预约
                return new Result(false,MessageConstant.HAS_ORDERED);
            }
        }

        if(member == null){
            //当前用户不是会员，需要自动完成注册
            member = new Member();
            member.setName((String) map.get("name"));
            member.setPhoneNumber(telephone);
            member.setIdCard((String) map.get("idCard"));
            member.setSex((String) map.get("sex"));
            member.setRegTime(new Date());
            memberDao.add(member);
        }

        //保存预约信息
        Order order = new Order(member.getId(),
                date,
                (String)map.get("orderType"),
                Order.ORDERSTATUS_NO,
                Integer.parseInt((String) map.get("setmealId")));
        orderDao.add(order);

        //更新已预约人数
        orderSetting.setReservations(orderSetting.getReservations() + 1);
        orderSettingDao.editReservationsByOrderDate(orderSetting);
        return new Result(true,MessageConstant.ORDER_SUCCESS,order.getId());
    }

    //根据id查询预约详细信息（包括会员姓名、套餐名称、预约基本信息）
    public Map findById(Integer id) {
        Map map = orderDao.findById4Detail(id);
        if(map != null){
            //处理日期格式
            Date orderDate = (Date) map.get("orderDate");
            try {
                map.put("orderDate", DateUtils.parseDate2String(orderDate));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }
}

