/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csa.bean;

import com.csa.pojo.Category;
import com.csa.pojo.Product;
import com.csa.services.ProductSvc;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;
    
/**
 *
 * @author LTBao
 */
@ManagedBean
@Named(value = "productBean")
@RequestScoped
public class ProductBean {
    private int proId;
    private String name;
    private String description;
    private BigDecimal price;
    private Category category;
    private Part imgFile;

    private Product product;

    private static ProductSvc proSvc = new ProductSvc();

    /**
     * Creates a new instance of productBean
     */
    public ProductBean() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            String productId = FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getRequestParameterMap()
                    .get("product_id");
            if (productId != null && !productId.isEmpty()) {
                Product pro = proSvc.getProductById(Integer.parseInt(productId));
                this.proId = pro.getId();
                this.name = pro.getName();
                this.category = pro.getCategory();
                this.description = pro.getDescription();
                this.price = pro.getPrice();
            }

            //this.imgFile = pro.getImage();
        }
    }

    public List<Product> getProducts() {
        return proSvc.getProducts(null);
    }

    public List<Product> getProductPaging(int page, int size) {
        return proSvc.getProductPaging(page, size);
    }

    public Product getProductById() {
        String productId = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap()
                .get("product_id");
        return proSvc.getProductById(Integer.parseInt(productId));
    }

    public String addOrSaveProduct() {
        Product pro;
        if (this.proId > 0) 
            pro = proSvc.getProductById(this.proId);
        else 
            pro = new Product();
        
        pro.setName(this.name);
        pro.setCategory(this.category);
        pro.setDescription(this.description);
        pro.setPrice(this.price);
        try {
            if (this.imgFile != null) {
                this.upLoadFile();
                pro.setImage("upload/" + this.imgFile.getSubmittedFileName());
            }
            if (proSvc.addOrSaveProduct(pro) == true) {
                return "index?faces-redirect=true";
            }
        } catch (IOException ex) {
            Logger.getLogger(ProductBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "create";
    }

    private void upLoadFile() throws IOException {
        String path = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getInitParameter("uploadImagePathWebConfig") + this.imgFile.getSubmittedFileName();
        try (InputStream in = this.imgFile.getInputStream();
                FileOutputStream out = new FileOutputStream(path)) {
            byte[] data = new byte[1024];
            int byteRead;
            while ((byteRead = in.read(data)) != -1) {
                out.write(data, 0, byteRead);
            }
        }
    }

    public String removeProduct(Product pro) throws Exception {
        if (proSvc.removeProduct(pro)) {
            return "Successful";
        }

        throw new Exception("Something wrong!");
    }

    /**
     * @param product the product to set
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * @return the category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * @return the product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * @return the proSvc
     */
    public static ProductSvc getProSvc() {
        return proSvc;
    }

    /**
     * @param aProSvc the proSvc to set
     */
    public static void setProSvc(ProductSvc aProSvc) {
        proSvc = aProSvc;
    }

    /**
     * @return the imgFile
     */
    public Part getImgFile() {
        return imgFile;
    }

    /**
     * @param imgFile the imgFile to set
     */
    public void setImgFile(Part imgFile) {
        this.imgFile = imgFile;
    }

    /**
     * @return the proId
     */
    public int getProId() {
        return proId;
    }

    /**
     * @param proId the proId to set
     */
    public void setProId(int proId) {
        this.proId = proId;
    }

}
