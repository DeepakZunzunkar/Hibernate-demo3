package com.dz.app.saveVspersist;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.dz.app.model.entity.Student;
import com.dz.app.utility.Factory;

public class TestSavePersist {

	static SessionFactory sf = null;
	static Session session = null;
	static Transaction tx = null;

	public static void main(String[] args) {

		sf = Factory.getSessionFactory();
		// normal way to save object
		test1();

		test2();

//		System.out.println(sf.isClosed());
		System.out.println("session factory status " + sf.isOpen());
	}

	private static void test2() {

		try {

			session = sf.openSession();
			//save method work out of transaction boundary even if we don't open transaction but persist method not work without transaction 
			Student s = new Student("pankaj", "M");
			session.save(s);

		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			System.err.println("Exeception in test 2 " + e.getMessage());
		} finally {
			session.close();
		}

	}

	private static void test1() {

		try {

			session = sf.openSession();
			tx = session.beginTransaction();

			Student s = new Student("aditya", "M");

			// return serilizable object
//			Serializable primKey=session.save(s);
//			Long primKey=(Long) session.save(s);

			// return type is void
			session.persist(s);

			tx.commit();

		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			System.err.println("Exeception in test 1 " + e.getMessage());
		} finally {
			session.close();
		}
	}
}
