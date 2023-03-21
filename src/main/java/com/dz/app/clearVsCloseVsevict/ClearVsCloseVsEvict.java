package com.dz.app.clearVsCloseVsevict;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.dz.app.model.entity.Student;
import com.dz.app.utility.Factory;

public class ClearVsCloseVsEvict {
	
	static SessionFactory sf = null;
	static Session session = null;
	static Transaction tx = null;
	
	public static void main(String[] args) {
		
		sf = Factory.getSessionFactory();
		testClearMethod();
		testCloseMethod();
		testEvictMethod();
	}

	//Removes the object from the session. 
	//This method is used to dissociate/disconnect the specified object from the session
	//student1 is not updated because we have called session.evict(student1) and student2 is updated.
	private static void testEvictMethod() {
		
		try(Session session=sf.openSession()) {
			
			tx=session.beginTransaction();
			
			Student student1=session.get(Student.class,Long.parseLong("1"));
			Student student2=session.get(Student.class,Long.parseLong("2"));
			//student1 and student2 are in persistent state
			
			student1.setName("suraj");
			student2.setName("naveen");
			
			//student1 and student2 are in detached state.
			
			session.evict(student1); //student1 object changes will not be affected 
			//student1 is in detached state and student2 is in persistent state.

			
			tx.commit();
			
		} catch (Exception e) {
			System.err.println("Exeception testClearMethod "+e.getMessage());
			if (tx != null)
				tx.rollback();
			
			throw e;
		}
		
	}

	private static void testCloseMethod() {

		try(Session session=sf.openSession()) {
			
			tx=session.beginTransaction();
			
			Student student1=session.get(Student.class,Long.parseLong("1"));
			
			student1.setName("suraj");
			
			session.close();	 
			
			tx.commit();//session already close exception raised 	
			
		} catch (Exception e) {
			System.err.println("Exeception testCloseMethod "+e.getMessage());
			if (tx != null)
				tx.rollback();
			
			throw e;
		}
		
	}
	
	
	//After calling session.clear(), both objects are disconnected from the session object. i.e this objects are moved from persistent state to detached state.
	//The table is not updated even though transaction is committed because this are in non-transaction state.
	private static void testClearMethod() {
		
		try(Session session=sf.openSession()) {
			
			tx=session.beginTransaction();
			
			Student student1=session.get(Student.class,Long.parseLong("1"));
			Student student2=session.get(Student.class,Long.parseLong("2"));
			//student1 and student2 are in persistent state
			
			student1.setName("suraj");
			student2.setName("naveen");
			
			//student1 and student2 are in detached state.
			session.clear();		//if we are not write this line it will update the record in db 
			
			tx.commit();
			
		} catch (Exception e) {
			System.err.println("Exeception testClearMethod "+e.getMessage());
			if (tx != null)
				tx.rollback();
			
			throw e;
		}
		
	}
}
