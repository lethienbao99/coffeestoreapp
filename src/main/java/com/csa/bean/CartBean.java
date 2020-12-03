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
import java.util.HashMap;
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
    
    public String addItemtoCart(int productId, String productName, BigDecimal price) {
        Map<Integer, Object> cart;
        cart = (Map<Integer, Object>) FacesContext.getCurrentInstance()
                .getExternalContext()
                .getSessionMap().get("cart");
        if (cart.get(productId) == null) {
            Map<String, Object> d = new HashMap<>();
            d.put("productId", productId);
            d.put("productName", productName);
            d.put("price", price);
            d.put("count", 1);
            
            cart.put(productId, d);
        }
        else  {
            Map<String, Object> d = (Map<String, Object>) cart.get(productId);
            d.put("count",Integer.parseInt(d.get("count").toString()) + 1);
        }
        return "";
    }
    
}
