/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csa.services;

import com.csa.coffeestoreapp.HibernateUtil;
import com.csa.pojo.Product;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author LTBao
 */
public class ProductSvc {
    private final static SessionFactory FACTORY = HibernateUtil.getSessionFactory();
    public List<Product> getProducts(String kw) {
        try (Session session = FACTORY.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Product> query = builder.createQuery(Product.class);
            Root<Product> root = query.from(Product.class);
            query.orderBy(builder.desc(root.get("id")));
            query.select(root);

            if (kw != null && kw.isEmpty()) {
                String p = String.format("%%%s%%", kw);
                Predicate p1 = builder.like(root.get("name").as(String.class), p);
                Predicate p2 = builder.like(root.get("description").as(String.class), p);
                query = query.where(builder.or(p1, p2));
            }
            return session.createQuery(query).getResultList();
        }
    }

    public List<Product> getProductPaging(int page, int size) {
        try (Session session = FACTORY.openSession()) {
            Criteria criteria = session.createCriteria(Product.class);
            criteria.setFirstResult(page);
            criteria.setMaxResults(size);
            return criteria.list();
        }
    }

    public Product getProductById(int id) {
        try (Session session = FACTORY.openSession()) {
            return session.get(Product.class, id);
        }
    }

    public boolean addOrSaveProduct(Product pro) {
        try (Session session = FACTORY.openSession()) {
            try {
                session.getTransaction().begin();
                session.saveOrUpdate(pro);
                session.getTransaction().commit();
            } catch (Exception ex) {
                session.getTransaction().rollback();
                return false;
            }
        }
        return true;
    }
    
    public boolean removeProduct(Product pro) {
        try (Session session = FACTORY.openSession()) {
            try {
                session.getTransaction().begin();
                session.delete(pro);
                session.getTransaction().commit();
            } catch (Exception ex) {
                session.getTransaction().rollback();
                return false;
            }
        }
        return true;
    }

}
