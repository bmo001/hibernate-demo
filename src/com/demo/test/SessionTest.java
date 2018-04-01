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
			//创建瞬时状态对象
			user = new User();
			user.setName("东方不败");
			user.setPwd("123456");
			//user仍是一个瞬态对象
			
			//持久状态，user被session管理，并且id有值   -- oid
			//此时user已变成 持久态对象
			session.save(user);
			
			//在持久状态下脏数据检查:当提交事务，清理缓存时发现session中的数据与数据库的数据不一致时，会把session的数据更新到数据库
			//保存以后，再修改对象，会产生多条sql语句，效率降低，所以在save前修改数据
			user.setName("西方求败");
			session.getTransaction().commit();		
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			HibernateUtil.closeSession();
		}
		//close、clear、evict方法都会使持久态对象  变成 游离状态 -- user
		user.setName("令狐冲");

		try {
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			//update使对象变成 持久状态
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
			//get后变为持久状态
			//get方法会立即查询该对象：范围（查询不到去下一级查询）从session -> sessionFactory ->数据库
			//get方法找不到对象，不会有异常，返回nul
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
			//load后变为持久状态
			//对象不存在时，抛出异常
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
			//游离状态，不被session管理，数据库不会被更改
			user.setName("任我行");
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
			//瞬时状态
			//手动构造对象 也可以修改，但是需要指定所有的属性值，否则，不设置的对象会置空,不建议这样update数据
			/*user = new User();
			user.setId(7L);
			user.setName("盈盈");
			session.update(user);*/
			//一般这样修改
			//先去查询
			user = session.get(User.class,7L);
			if(user != null) {
				user.setName("盈盈");
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
			//瞬时状态
			//手动构造一个对象，指定主键是可以删除数据的，不建议这样搞
			/*user = new User();
			user.setId(7L);
			session.delete(user);*/
			//应该这样删除数据
			//从数据先加载该对象，然后删除，可以避免异常
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
