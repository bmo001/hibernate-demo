package com.demo.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.Test;

import com.demo.pojo.User;

public class QueryTest {

	@Test
	public void connectTest() {
		/*
		//1、新建Configuration对象 (Configuration管理加载配置文件)
		Configuration config = new Configuration().configure();
		//2、通过configuration得到SessionFactory对象
		//3、通过SessionFactory得到Session对象
		//hibernate3.x 中的写法
		SessionFactory session = config.buildSessionFactory();
		
		//hibernate4.3之前 ~~ 4.0
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
		SessionFactory sessionFactory = config.buildSessionFactory(serviceRegistry);
		Session session = sessionFactory.openSession();
	
		//hibernate4.3  其中的一种 获取SessionFactory的方法
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
		SessionFactory sessionFactory = config.buildSessionFactory(serviceRegistry);
		Session session = sessionFactory.openSession(); 
		*/

        //Hibernate5.1 的获取SessionFactory的方法
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
		
		//4、通过Session得到Transaction对象 (两种写法)
        //Transaction transaction = session.getTransaction();
        //transaction.begin();
		Transaction transaction = session.beginTransaction();
		//5、保存数据
		User user = new User();
		user.setName("张三");
		user.setPwd("123456");
		session.persist(user);
		//6、提交事务
		transaction.commit();
		//7、关闭Session
		session.close();
	}
	
	@Test
	public void queryTest() {
		//Hibernate5.1 的获取SessionFactory的方法
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
		
		//5、保存数据
		User user = session.get(User.class, 8L);
		System.out.println(user);
		//6、提交事务
		//7、关闭Session
		session.close();
	}
	
	@Test
	public void saveTest() {
        //Hibernate5.1 的获取SessionFactory的方法
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
		
		//4、通过Session得到Transaction对象
		Transaction transaction = session.beginTransaction();
		//5、保存数据
		User user = new User();
		user.setName("张三");
		user.setPwd("123456");
		session.save(user);
		//6、提交事务
		transaction.commit();
		//7、关闭Session
		session.close();
	}

}
