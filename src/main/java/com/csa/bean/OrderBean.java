/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csa.bean;

import com.csa.pojo.Orders;
import com.csa.pojo.OrderDetail;
import com.csa.pojo.Product;
import com.csa.pojo.User;
import com.csa.services.OrderSvc;
import com.csa.services.ProductSvc;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

/**
 *
 * @author LTBao
 */
@ManagedBean
@Named(value = "orderBean")
@RequestScoped
public class OrderBean {

    private static final ProductSvc productSvc = new ProductSvc();
    private static final OrderSvc orderSvc = new OrderSvc();

    /**
     * Creates a new instance of OrderBean
     */
    public OrderBean() {
    }

    public String add() {
        Map<Integer, Object> cart = (Map<Integer, Object>) FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getSessionMap()
                .get("cart");

        //Nếu có giỏ
        if (cart != null) {
            Orders od = new Orders();
            od.setCreatedDate(new Date());
            od.setUser((User) FacesContext
                    .getCurrentInstance()
                    .getExternalContext()
                    .getSessionMap().get("user"));

            List<OrderDetail> details = new ArrayList<>();

            List<Map<String, Object>> kq = new ArrayList<>();
            for (Object o : cart.values()) {
                Map<String, Object> d = (Map<String, Object>) o;
                Product product = productSvc.getProductById(
                        Integer.parseInt(d.get("productId").toString()));

                OrderDetail detail = new OrderDetail();
                detail.setProduct(product);
                detail.setOrders(od);
                detail.setPrice(product.getPrice());
                detail.setCount(Integer.parseInt(d.get("count").toString()));
                detail.setDatecreated(new Date());
                details.add(detail);
            }
            if (orderSvc.addOrder(od, details) == true) {
                FacesContext.getCurrentInstance()
                        .getExternalContext().getSessionMap().remove("cart");

                return "index?faces-redirect=true";
            }
        }

        return "";
    }

    public List<OrderDetail> getTotalProduct() {
        return orderSvc.getTotalProduct();

    }

    public List<OrderDetail> getTotalPrice() {
        return orderSvc.getTotalPrice();

    }

    public List<OrderDetail> getDateCreated() {
        return orderSvc.getdateCreated();

    }

}
