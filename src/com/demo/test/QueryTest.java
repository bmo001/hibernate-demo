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
		//1���½�Configuration���� (Configuration������������ļ�)
		Configuration config = new Configuration().configure();
		//2��ͨ��configuration�õ�SessionFactory����
		//3��ͨ��SessionFactory�õ�Session����
		//hibernate3.x �е�д��
		SessionFactory session = config.buildSessionFactory();
		
		//hibernate4.3֮ǰ ~~ 4.0
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
		SessionFactory sessionFactory = config.buildSessionFactory(serviceRegistry);
		Session session = sessionFactory.openSession();
	
		//hibernate4.3  ���е�һ�� ��ȡSessionFactory�ķ���
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
		SessionFactory sessionFactory = config.buildSessionFactory(serviceRegistry);
		Session session = sessionFactory.openSession(); 
		*/

        //Hibernate5.1 �Ļ�ȡSessionFactory�ķ���
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
		
		//4��ͨ��Session�õ�Transaction���� (����д��)
        //Transaction transaction = session.getTransaction();
        //transaction.begin();
		Transaction transaction = session.beginTransaction();
		//5����������
		User user = new User();
		user.setName("����");
		user.setPwd("123456");
		session.persist(user);
		//6���ύ����
		transaction.commit();
		//7���ر�Session
		session.close();
	}
	
	@Test
	public void queryTest() {
		//Hibernate5.1 �Ļ�ȡSessionFactory�ķ���
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
		
		//5����������
		User user = session.get(User.class, 8L);
		System.out.println(user);
		//6���ύ����
		//7���ر�Session
		session.close();
	}
	
	@Test
	public void saveTest() {
        //Hibernate5.1 �Ļ�ȡSessionFactory�ķ���
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
		
		//4��ͨ��Session�õ�Transaction����
		Transaction transaction = session.beginTransaction();
		//5����������
		User user = new User();
		user.setName("����");
		user.setPwd("123456");
		session.save(user);
		//6���ύ����
		transaction.commit();
		//7���ر�Session
		session.close();
	}

}
