package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepository order_repo ;

    public OrderService() {
    }

    //add order
    void addOrder(Order order){

        order_repo.addOrder(order);
    }
    //addpartner
    public void addPartner(String Partner_id){

       order_repo.addPartner(Partner_id);

    }
    //addpair
    public void  addOrderPartnerPair(String orderId,  String partnerId){

        //This is basically assigning that order to that partnerId
        order_repo.addOrderPartnerPair(orderId,partnerId);

    }
    //getorderby id
    public Order getOrderById( String orderId){


        //order should be returned with an orderId.
        Order order=order_repo.getOrderById(orderId);

        return order;
    }
    //get partner by id
    public DeliveryPartner getPartnerById( String partnerId){

        DeliveryPartner deliveryPartner = order_repo.getPartnerById(partnerId);

        //deliveryPartner should contain the value given by partnerId

        return deliveryPartner;
    }
    //get order count number
    public Integer getOrderCountByPartnerId( String partnerId){

        Integer orderCount = order_repo.getOrderCountByPartnerId(partnerId);

        //orderCount should denote the orders given by a partner-id

        return orderCount;
    }
    //send list of order
    public List<String> getOrdersByPartnerId(@PathVariable String partnerId){
        List<String> orders = order_repo.getOrdersByPartnerId(partnerId);

        //orders should contain a list of orders by PartnerId

        return orders;
    }
    //all orderd list
    public List<String> getAllOrders(){
        List<String> orders = order_repo.getAllOrders();

        //Get all orders
        return orders;
    }
    public Integer getCountOfUnassignedOrders(){
        Integer countOfOrders = order_repo.getCountOfUnassignedOrders();

        //Count of orders that have not been assigned to any DeliveryPartner

        return countOfOrders;
    }
    //after particular time
    public Integer getOrdersLeftAfterGivenTimeByPartnerId( String time,  String partnerId){

        Integer countOfOrders = order_repo.getOrdersLeftAfterGivenTimeByPartnerId(time,partnerId);

        //countOfOrders that are left after a particular time of a DeliveryPartner

        return countOfOrders;
    }
    //last delivery
    public String getLastDeliveryTimeByPartnerId( String partnerId){
        String time = order_repo.getLastDeliveryTimeByPartnerId(partnerId);

        //Return the time when that partnerId will deliver his last delivery order.

        return time;
    }

    public void  deletePartnerById( String partnerId){

        //Delete the partnerId
        //And push all his assigned orders to unassigned orders.
        order_repo.deletePartnerById(partnerId);


    }

    public void deleteOrderById( String orderId){

        //Delete an order and also
        // remove it from the assigned order of that partnerId
        order_repo.deleteOrderById(orderId);


    }

}
