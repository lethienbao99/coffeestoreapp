/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csa.bean;

import com.csa.pojo.Category;
import com.csa.services.CategorySvc;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author LTBao
 */
@ManagedBean
@Named(value = "categoryBean")
@SessionScoped
public class CategoryBean implements Serializable {
 private final static CategorySvc cateSvc = new CategorySvc();
    /**
     * Creates a new instance of categoryBean
     */
    public CategoryBean() {
    }
    
    public List<Category> getCategories() {
        return cateSvc.getCategories();
    }
    
}
