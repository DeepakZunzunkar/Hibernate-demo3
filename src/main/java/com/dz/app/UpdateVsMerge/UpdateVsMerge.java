package com.dz.app.UpdateVsMerge;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.dz.app.model.entity.Student;
import com.dz.app.utility.Factory;

public class UpdateVsMerge {

	static SessionFactory sf = null;
	static Session session = null;
	static Transaction tx = null;
	
	public static void main(String[] args) {
		
		sf = Factory.getSessionFactory();
		
		// normal update process 
		//: open the session ,load the entity ,
		//  begin transaction and do some changes and  use session update method to update and then commit transaction.
//		testUpdate();
		
		
		testMerge();
		
	}

	private static void testMerge() {
		Student s1=null;
		System.out.println(sf.isOpen());
//		sf.close();
//		System.out.println(sf.isOpen());
		
		try (Session session=Factory.getSessionFactory().openSession()){
			s1=session.get(Student.class,Long.parseLong("1"));
		} catch (Exception e) {
			System.err.println("Exeception in testMerge  " + e.getMessage());
		}
		
		s1.setName("test merge1 ");
		// s1 is now in transient state [ not attach to any session ]
		
		try (Session session=Factory.getSessionFactory().openSession()){
			
			// if we point same reference variable to newly loaded object then it will not update above changes to db because s1 is now pointing to newly loaded object.
			//s1=session.get(Student.class,Long.parseLong("1"));
			
			// if we load same object with difference reference variable and then use session update method 
			// it will throw exception " A different object with the same identifier value was already associated with the session"
			// so instead of update here if we use merge then it will update the changes to db.
			Student s2 = session.get(Student.class,Long.parseLong("1"));
			
			// if we don't load the same object in this session then it will update above changes in the current session using session update/merge method.
			System.out.println("is contain "+session.contains(s1));
			tx=session.beginTransaction();
			
//			session.update(s1);
			
			//merge method basically used to merge another session entity or transient /detach entity in current session
			session.merge(s1);
			
			tx.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private static void testUpdate() {
		
		try(Session session=sf.openSession()) {

			Student s1=session.get(Student.class,Long.parseLong("1"));

			//it will fired update query only when there is change in value/data i.e called dirty checking
			//if previous name is same then it will not update if different then only update .
			s1.setName("update test3");
			
			//below two lines will update the s1
			session.beginTransaction();
			session.flush();
			
			System.out.println("is contain "+session.contains(s1));
			
			//loading same object again but it will not fired query because same object with same identifier is available in current session
			Student s2=session.get(Student.class,Long.parseLong("1"));
			
		} catch (Exception e) {
			System.err.println("Exeception in testUpdate  " + e.getMessage());
			if (tx != null)
				tx.rollback();
		}
		
	}
}
