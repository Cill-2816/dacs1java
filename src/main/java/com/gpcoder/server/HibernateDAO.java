package com.gpcoder.server;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.gpcoder.Utils.HibernateUtils;

public class HibernateDAO {

    public static <T> List<T> getAll(Class<T> clazz) {
        List<T> resultList = new ArrayList<>();
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            String hql = "FROM " + clazz.getSimpleName();
            resultList = session.createQuery(hql, clazz).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public static <T> void saveAll(List<T> items) {
        if (items == null || items.isEmpty()) return;

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            for (T item : items) {
                session.saveOrUpdate(item); 
            }

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
