package com.dz.app.serviceImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.dz.app.model.entity.Employee;
import com.dz.app.utility.Factory;

public class RestrictionImpl {

	
	public static Employee equals(String name) {

		Employee employee=null;
		try(Session session=Factory.getSessionFactory().openSession()){
			
			Criteria cr = session.createCriteria(Employee.class);
			
			//here it take mapped property name of that column ,not a column name of that table .
			cr.add(Restrictions.eq("firstName",name));
			employee = (Employee) cr.uniqueResult();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return employee;
	}
	
	public static List<Employee> equals(String columnName,String value) {

		List<Employee> employees=null;
		try(Session session=Factory.getSessionFactory().openSession()){
			
			Criteria cr = session.createCriteria(Employee.class);
			
			//here it take mapped property name of that column ,not a column name of that table .
			cr.add(Restrictions.eq(columnName,value));
			employees =cr.list();

		} catch (Exception e) {
			e.printStackTrace();
//			throw e;
		}
		return employees;
	}
}
