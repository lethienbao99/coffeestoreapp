/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csa.services;

import com.csa.coffeestoreapp.HibernateUtil;
import com.csa.pojo.Category;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author LTBao
 */
public class CategorySvc {
    private final static SessionFactory FACTORY = HibernateUtil.getSessionFactory();
    
    public List<Category> getCategories() {
        try(Session session = FACTORY.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Category> query = builder.createQuery(Category.class);
            Root<Category> root = query.from(Category.class);
            query.select(root);
            
            return session.createQuery(query).getResultList();
        }
    }
    
    public Category getCategoryById(int id) {
        try (Session session = FACTORY.openSession()) {
            Category c = session.get(Category.class, id);
            session.close();
            return c;
        }
    }
    
}
