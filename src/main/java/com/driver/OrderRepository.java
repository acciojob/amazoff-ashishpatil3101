package com.driver;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class OrderRepository {

    HashMap<String,Order> order_db =new HashMap<>();
    HashMap<String,DeliveryPartner> partner_db = new HashMap<>();

    //partner id vs order id
    HashMap<String,List<String>> partner_order_pair=new HashMap<>();
    HashMap<String,Boolean> isOrder_placed=new HashMap<>();

    //add order
    void addOrder(Order order){

        order_db.put(order.getId(),order);
    }

    //add partner
    public void addPartner(String Partner_id){

        partner_db.put(Partner_id,new DeliveryPartner(Partner_id));
    }
    public void  addOrderPartnerPair(String orderId,  String partnerId){

        //This is basically assigning that order to that partnerId
        //update number of orders
        if(isOrder_placed.get(orderId)==true) return;


        partner_db.get(partnerId).setNumberOfOrders(partner_db.get(partnerId).getNumberOfOrders()+1);
        if(partner_order_pair.containsKey(partnerId)==false) partner_order_pair.put(partnerId,new ArrayList<String>());
        partner_order_pair.get(partnerId).add(orderId);
        isOrder_placed.put(orderId,true);

    }
    //get orxder by id
    public Order getOrderById( String orderId){


        //order should be returned with an orderId.
        if(!order_db.containsKey(orderId)) return null;

        return order_db.get(orderId);
    }
    //get partner by id
    public DeliveryPartner getPartnerById( String partnerId){

        if(!partner_db.containsKey(partnerId)) return null;

        //deliveryPartner should contain the value given by partnerId

        return partner_db.get(partnerId);
    }
    //get order count number
    public Integer getOrderCountByPartnerId( String partnerId){



        //orderCount should denote the orders given by a partner-id
        return partner_order_pair.getOrDefault(partnerId,new ArrayList<>()).size();
    }
    //send list of order
    public List<String> getOrdersByPartnerId(@PathVariable String partnerId){
        List<String> orders = new ArrayList<>(partner_order_pair.getOrDefault(partnerId,new ArrayList<>()));





        //orders should contain a list of orders by PartnerId

        return orders;
    }
    //all orderd list
    public List<String> getAllOrders(){
        List<String> orders =  new ArrayList<>();
        for(String key:order_db.keySet()){
            orders.add(""+order_db.get(key));
        }

        //Get all orders
        return orders;
    }
    //count unassgned order
    public Integer getCountOfUnassignedOrders(){
        Integer countOfOrders = order_db.size()-isOrder_placed.size();
//        for(String key:order_db.keySet()){
//
//            if(isOrder_placed.containsKey(key)==false) countOfOrders +=1;
//        }

        //Count of orders that have not been assigned to any DeliveryPartner

        return countOfOrders;
    }
    public Integer getOrdersLeftAfterGivenTimeByPartnerId( String time,  String partnerId){

        Integer countOfOrders = 0;
        int t=Integer.parseInt(time.substring(0,2))*60 + Integer.parseInt(time.substring(3));

        List<String> list=partner_order_pair.get(partnerId);

        for(String orderID:list){

            if(Integer.parseInt(orderID) > t) countOfOrders++;
        }

        //countOfOrders that are left after a particular time of a DeliveryPartner

        return countOfOrders;
    }

    public String getLastDeliveryTimeByPartnerId(@PathVariable String partnerId){

        String time="";
        if(partner_order_pair.get(partnerId).size()==0) return time;

        time="0";
        List<String> list=partner_order_pair.get(partnerId);

        for(String orderID:list){

            time= String.valueOf(Math.max(Integer.parseInt(orderID),Integer.parseInt(time)));
        }

        //Return the time when that partnerId will deliver his last delivery order.

        return time;
    }

    public void  deletePartnerById(@PathVariable String partnerId){

        //Delete the partnerId
        //And push all his assigned orders to unassigned orders.
        List<String> list=partner_order_pair.get(partnerId);

        for(String orderID:list){

            if(isOrder_placed.containsKey(orderID)) isOrder_placed.remove(orderID);


        }
        partner_order_pair.remove(partnerId);
        partner_db.remove(partnerId);


    }
    public void deleteOrderById( String orderId){

        //Delete an order and also
        // remove it from the assigned order of that partnerId
        for(String key:partner_order_pair.keySet()){

            for(String list:partner_order_pair.get(key)){

                if(list.equals(orderId)) {

                    partner_order_pair.get(key).remove(list);
                }
            }
        }

        order_db.remove(orderId);
        if(isOrder_placed.containsKey(orderId)) isOrder_placed.remove(orderId);


    }
}
