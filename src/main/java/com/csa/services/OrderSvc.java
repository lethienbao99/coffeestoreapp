/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csa.services;

import com.csa.coffeestoreapp.HibernateUtil;
import com.csa.pojo.Orders;
import com.csa.pojo.OrderDetail;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

/**
 *
 * @author LTBao
 */
public class OrderSvc {

    private final static SessionFactory FACTORY = HibernateUtil.getSessionFactory();

    public boolean addOrder(Orders order, List<OrderDetail> details) {
        try (Session session = FACTORY.openSession()) {
            try {
                session.getTransaction().begin();
                session.save(order);
                for (OrderDetail detail : details) 
                    session.save(detail);
                session.getTransaction().commit();
                return true;
            } catch (Exception ex) {
                session.getTransaction().rollback();
            }
        }
        return false;
    }
    
 
    public List<OrderDetail> getTotalProduct() {
        try(Session session = FACTORY.openSession()) {
            Query q = session.createQuery("SELECT COUNT(product)" 
                    + "FROM OrderDetail "
                    + "GROUP BY datecreated");
            List<OrderDetail> r = (List<OrderDetail>) q.list();
            
            return r;
        }
    }
    
  
    public List<OrderDetail> getTotalPrice() {
        try(Session session = FACTORY.openSession()) {
            Query q = session.createQuery("SELECT SUM(price)" 
                    + "FROM OrderDetail "
                    + "GROUP BY datecreated");
            List<OrderDetail> r = (List<OrderDetail>) q.list();
            
            return r;
        }
    }
    
        public List<OrderDetail> getdateCreated() {
        try(Session session = FACTORY.openSession()) {
            Query q = session.createQuery("SELECT datecreated " 
                    + "FROM OrderDetail "
                    + "GROUP BY datecreated");
            List<OrderDetail> r = (List<OrderDetail>) q.list();
            
            return r;
        }
    }
    
    
//    @SuppressWarnings("unchecked")
//    public List<OrderDetail> getTotals() {
//        try(Session session = FACTORY.openSession()) {
//            Query q = session.createQuery("SELECT COUNT(product) as productTotal, SUM(price) as priceTotal, datecreated " 
//                    + "FROM OrderDetail "
//                    + "GROUP BY datecreated");
//            List<OrderDetail> r = (List<OrderDetail>) q.list();
//            
//            return r;
//        }
//    }

}   
    