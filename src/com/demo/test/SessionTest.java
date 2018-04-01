package com.demo.test;

import org.hibernate.Session;
import org.junit.Test;

import com.demo.Utils.HibernateUtil;
import com.demo.pojo.User;

public class SessionTest {

	@Test
	public void saveTest() {
		Session session = null;
		User user = null;
		try {
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			//����˲ʱ״̬����
			user = new User();
			user.setName("��������");
			user.setPwd("123456");
			//user����һ��˲̬����
			
			//�־�״̬��user��session��������id��ֵ   -- oid
			//��ʱuser�ѱ�� �־�̬����
			session.save(user);
			
			//�ڳ־�״̬�������ݼ��:���ύ����������ʱ����session�е����������ݿ�����ݲ�һ��ʱ�����session�����ݸ��µ����ݿ�
			//�����Ժ����޸Ķ��󣬻��������sql��䣬Ч�ʽ��ͣ�������saveǰ�޸�����
			user.setName("�������");
			session.getTransaction().commit();		
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			HibernateUtil.closeSession();
		}
		//close��clear��evict��������ʹ�־�̬����  ��� ����״̬ -- user
		user.setName("�����");

		try {
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			//updateʹ������ �־�״̬
			session.update(user);
			session.getTransaction().commit();		
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			HibernateUtil.closeSession();
		}
	}
	
	@Test
	public void getTest() {
		Session session = null;
		try {
			session = HibernateUtil.currentSession();
			//get���Ϊ�־�״̬
			//get������������ѯ�ö��󣺷�Χ����ѯ����ȥ��һ����ѯ����session -> sessionFactory ->���ݿ�
			//get�����Ҳ������󣬲������쳣������nul
			User user = session.get(User.class, 7L);
			System.out.println(user.toString());
			
			User user2 = session.get(User.class, 17L);
			System.out.println(user2);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			HibernateUtil.closeSession();
		}
	}
	
	@Test
	public void loadTest() {
		Session session = null;
		try {
			session = HibernateUtil.currentSession();
			//load���Ϊ�־�״̬
			//���󲻴���ʱ���׳��쳣
			User user = session.load(User.class, 7L);
			System.out.println(user.toString());
			
			User user2 = session.load(User.class, 17L);
			System.out.println(user2.toString());
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			HibernateUtil.closeSession();
		}
	}
	
	@Test
	public void clearTest() {
		Session session = null;
		User user = null;
		try {
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			user = session.get(User.class,7L);
			System.out.println(user.getName());
			session.getTransaction().commit();	
			session.clear();
			//����״̬������session�������ݿⲻ�ᱻ����
			user.setName("������");
			System.out.println(user.getName());
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			HibernateUtil.closeSession();
		}
	}
	
	@Test
	public void updateTest() {
		Session session = null;
		User user = null;
		try {
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			//˲ʱ״̬
			//�ֶ�������� Ҳ�����޸ģ�������Ҫָ�����е�����ֵ�����򣬲����õĶ�����ÿ�,����������update����
			/*user = new User();
			user.setId(7L);
			user.setName("ӯӯ");
			session.update(user);*/
			//һ�������޸�
			//��ȥ��ѯ
			user = session.get(User.class,7L);
			if(user != null) {
				user.setName("ӯӯ");
				session.update(user);
			}
			session.getTransaction().commit();	
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			HibernateUtil.closeSession();
		}
	}
	
	@Test
	public void deleteTest() {
		Session session = null;
		User user = null;
		try {
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			//˲ʱ״̬
			//�ֶ�����һ������ָ�������ǿ���ɾ�����ݵģ�������������
			/*user = new User();
			user.setId(7L);
			session.delete(user);*/
			//Ӧ������ɾ������
			//�������ȼ��ظö���Ȼ��ɾ�������Ա����쳣
			user = session.get(User.class, 7L);
			if(user != null) {
				session.delete(user);
			}
			session.getTransaction().commit();	
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			HibernateUtil.closeSession();
		}
	}
	
	
}
