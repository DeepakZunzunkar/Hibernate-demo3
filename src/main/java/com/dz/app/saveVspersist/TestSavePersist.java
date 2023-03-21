package com.dz.app.saveVspersist;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.dz.app.model.entity.Address;
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
			
			List<Address> addresses=new ArrayList<>();
			Address address1=new Address("mull road ","chandrapur");
			
			//if we don't associate student in address then sid column in address table will remain blanks, other fields will be persisted
			address1.setStudent(s);
			
			addresses.add(address1);
			
			s.setAddresses(addresses);
			
			session.save(s);
			
			//below changes will not get persisted
			s.setName("praful");
			
			
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

			List<Address> addresses=new ArrayList<>();
			addresses.add(new Address("tukum road ","chandrapur"));
			
			s.setAddresses(addresses);
			
			// return serilizable object
//			Serializable primKey=session.save(s);
//			Long primKey=(Long) session.save(s);
			
			// return type is void
			session.persist(s);
			//
			
			//within transaction boundary after save method if we do some changes then it will get persisted  
			//also work with the persist method 
			s.setName("govinda");
			
			s.getAddresses().stream().forEach(adr ->{
				adr.setCity("pune");
			});
			
			tx.commit();

			
			// after transaction boundary if we do changes and try to flush then we will get exception "Cannot rollback transaction in current status [COMMITTED]"
//			s.setName("govinda");
//			session.flush();
			
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			System.err.println("Exeception in test 1 " + e.getMessage());
		} finally {
			session.close();
		}
	}
}
