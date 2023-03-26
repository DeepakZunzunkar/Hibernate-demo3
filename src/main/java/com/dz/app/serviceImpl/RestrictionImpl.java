package com.dz.app.serviceImpl;

import java.util.Date;
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
		}
		return employees;
	}
	
	public static List<Employee> greaterThan(Double value) {
		
		List<Employee> employees=null;
		try(Session session=Factory.getSessionFactory().openSession()){
			
			Criteria cr = session.createCriteria(Employee.class);
			cr.add(Restrictions.gt("salary",value));
			employees =cr.list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return employees;
	}
	
	public static List<Employee> lessThan(Double value) {
		
		List<Employee> employees=null;
		try(Session session=Factory.getSessionFactory().openSession()){
			
			Criteria cr = session.createCriteria(Employee.class);
			cr.add(Restrictions.lt("salary",value));
			employees =cr.list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return employees;
	}
	
	public static List<Employee> like(String letter) {
		
		List<Employee> employees=null;
		try(Session session=Factory.getSessionFactory().openSession()){
			
			Criteria cr = session.createCriteria(Employee.class);
			cr.add(Restrictions.like("firstName",letter));
			employees =cr.list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return employees;
	}

	public static List<Employee> salaryBetween(Double low, Double high) {
		
		List<Employee> employees=null;
		try(Session session=Factory.getSessionFactory().openSession()){
			
			Criteria cr = session.createCriteria(Employee.class);
			cr.add(Restrictions.between("salary", low, high));
			employees =cr.list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return employees;
	}
	
	public static List<Employee> dateBetween(Date startDate, Date endDate) {
		
		List<Employee> employees=null;
		try(Session session=Factory.getSessionFactory().openSession()){
			
			Criteria cr = session.createCriteria(Employee.class);
			cr.add(Restrictions.between("birthDate", startDate, endDate));
			employees =cr.list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return employees;
	}
}
