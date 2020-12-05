/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csa.bean;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

/**
 *
 * @author LTBao
 */
@ManagedBean
@Named(value = "cartBean")
@SessionScoped
public class CartBean implements Serializable {

    /**
     * Creates a new instance of CartBean
     */
    public CartBean() {
    }

    @PostConstruct
    public void init() {
        if (FacesContext.getCurrentInstance()
                .getExternalContext()
                .getSessionMap().get("cart") == null) {
            FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getSessionMap().put("cart", new HashMap<>());
        }
    }

    public List<Map<String, Object>> getCarts() {
        Map<Integer, Object> cart = (Map<Integer, Object>) FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getSessionMap()
                .get("cart");
        List<Map<String, Object>> kq = new ArrayList<>();
        for (Object o : cart.values()) {
            Map<String, Object> d = (Map<String, Object>) o;

            kq.add(d);
        }

        return kq;
    }

    public String addItemtoCart(int productId, String productName, BigDecimal price, String image) {
        Map<Integer, Object> cart = (Map<Integer, Object>) FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getSessionMap().get("cart");

        if (cart.get(productId) == null) {
            //Nếu chưa có sp thì tạo mới 1 sp vô cart
            Map<String, Object> d = new HashMap<>();
            d.put("productId", productId);
            d.put("productName", productName);

            d.put("productPrice", price);
            d.put("image", image);
            d.put("count", 1);

            cart.put(productId, d);

        } else {
            Map<String, Object> d = (Map<String, Object>) cart.get(productId);
            d.put("count", Integer.parseInt(d.get("count").toString()) + 1);
        }

        return "";
    }

}
