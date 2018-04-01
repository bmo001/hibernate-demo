package com.demo.Utils;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * ��ȡHibernate������
 */
public class HibernateUtil {
	/**
	 * ����sessionFactory  
	 */
    public static final SessionFactory sessionFactory;  
    
    /**
     * ThreadLocal���Ը������̵߳����ݹ�����˲�����Ҫ���߳�ͬ��
     */
    public static final ThreadLocal<Session> session  = new ThreadLocal<Session>();
    
    static {  
        try{  
        	 StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure().build();
             sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory(); 
        }catch (Throwable ex){  
            System.err.println("Initial SessionFactory creation failed." + ex);  
            throw new ExceptionInInitializerError(ex);  
        }  
    }  
  
    /**
     * ����Session  
     * @return
     * @throws HibernateException
     */
    public static Session currentSession() throws HibernateException{  
        //ͨ���̶߳���.get()������ȫ����Session  
        Session s = session.get();  
        // ������̻߳�û��Session,�򴴽�һ���µ�Session  
        if (s == null){  
            s = sessionFactory.openSession();  
            // ����õ�Session�����洢��ThreadLocal����session��  
            session.set(s);  
        }  
        return s;  
    }  
    
    /**
     * �ر�Session 
     * @throws HibernateException
     */
    public static void closeSession() throws HibernateException {  
        Session s = session.get();  
        if (s != null) {
        	s.close();  
        }  
        session.set(null);  
    } 
}