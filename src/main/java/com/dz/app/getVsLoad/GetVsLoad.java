package com.dz.app.getVsLoad;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.dz.app.model.entity.Student;
import com.dz.app.utility.Factory;

public class GetVsLoad {

	static SessionFactory sf = null;
	static Session session = null;
	static Transaction tx = null;

	
	public static void main(String[] args) {
		
		sf = Factory.getSessionFactory();
		
		//testGetMethod();
		
		testLoadMethod();
	}

	private static void testLoadMethod() {
		
		try(Session session=sf.openSession()) {
			
			Student student1=session.load(Student.class,Long.parseLong("1"));
			Student student2=session.load(Student.class,Long.parseLong("1"));
			
			// it will not hit the database immediately ,it will always return a “proxy” object,  
			//what's the meaning of proxy object ?
			//Proxy means, hibernate will prepare some fake object with given identifier value in the memory 
			//we came to know that session.load() will hit the database only when we start retrieving the object (row) values.
			//if object is not availavle with given identifier in database ,session.load() method, will first create proxy object with given identifier 
			//and whenever we try to use object value it will throw an exception org.hibernate.ObjectNotFoundException: No row with the given identifier exists: [com.model.Employee#2]
			//if availbel start retriving the data
			
			if(student1!=null && student2!=null)
				System.out.println(student1.hashCode()==student2.hashCode());
			
			session.clear();
			
			Student student3=session.load(Student.class,Long.parseLong("1"));
			if(student1!=null && student3!=null)
				System.out.println(student1.hashCode()==student3.hashCode());
			
			
			Student student4=session.load(Student.class,Long.parseLong("2"));
			
			//here it will fired query to db and retrive data 
			System.out.println("name "+student4.getName());
			student4.getAddresses().stream().forEach(adr->{
				System.out.println("city "+adr.getCity());
			});
			
			
			// if object not available in DB then it will throw ObjectNotFoundException
			Student student5=session.load(Student.class,Long.parseLong("500000"));
			System.out.println("sid "+student5.getSid());
			
			//it throw exception only when we we use  non identifier value
			System.out.println("sid "+student5.getName());
			
		} catch (Exception e) {
			System.err.println("Exeception testLoadMethod "+e.getMessage());
		}
	}

	private static void testGetMethod() {

		//try with resources
		try(Session session=sf.openSession()) {
			
			Student student1=session.get(Student.class,Long.parseLong("1"));
			Student student2=session.get(Student.class,Long.parseLong("1"));
			
			//it will hit the database immediately and returns the original object
			//Return the persistent instance of the given entity class with the given identifier,
			//or null if there is no such persistent instance. (If the instance is already associated
			//with the session, return that instance. This method never returns an uninitialized instance.)
			if(student1!=null && student2!=null)
				System.out.println(student1.hashCode()==student2.hashCode());
			
			session.clear();
			
			//it will fired query to get same object because at this moment this object is not available in current session
			Student student3=session.get(Student.class,Long.parseLong("1"));
			if(student1!=null && student3!=null)
				System.out.println(student1.hashCode()==student3.hashCode());
			
			
		} catch (Exception e) {
			System.err.println("Exeception testGetMethod "+e.getMessage());
		}

	}
}
