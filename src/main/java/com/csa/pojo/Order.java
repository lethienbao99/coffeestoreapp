/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csa.pojo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author LTBao
 */
@Entity
@Table(name = "order")
public class Order implements Serializable {
    private int id;
    private Date createdDate;
    
}
