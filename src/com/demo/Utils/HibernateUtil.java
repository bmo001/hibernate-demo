package com.demo.Utils;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * 抽取Hibernate工具类
 */
public class HibernateUtil {
	/**
	 * 创建sessionFactory  
	 */
    public static final SessionFactory sessionFactory;  
    
    /**
     * ThreadLocal可以隔离多个线程的数据共享，因此不再需要对线程同步
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
     * 创建Session  
     * @return
     * @throws HibernateException
     */
    public static Session currentSession() throws HibernateException{  
        //通过线程对象.get()方法安全创建Session  
        Session s = session.get();  
        // 如果该线程还没有Session,则创建一个新的Session  
        if (s == null){  
            s = sessionFactory.openSession();  
            // 将获得的Session变量存储在ThreadLocal变量session里  
            session.set(s);  
        }  
        return s;  
    }  
    
    /**
     * 关闭Session 
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