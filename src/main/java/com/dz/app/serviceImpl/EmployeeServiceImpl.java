package com.dz.app.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.dz.app.model.entity.Employee;
import com.dz.app.service.EmployeeService;
import com.dz.app.utility.Factory;


/**
 * @author dz Mar 25, 2023
 *
 */
public class EmployeeServiceImpl implements EmployeeService {
	
	static Employee employee=null;
	static Session session=null;
	static Transaction tx=null;
	static Integer rs=0;
	List<Employee> employees = new ArrayList<Employee>();
	
	@Override
	public Employee saveEmployee(Employee employee) {
		
		try(Session session = Factory.getSessionFactory().openSession()) {
			
			tx = session.beginTransaction();
			
			/*SQLQuery query = session.createSQLQuery("SELECT * FROM adpemployee a where a.firstname='"+ employee.getFirstName() + "' and a.lastname='" + employee.getLastName() + "'");
			Object obj = query.uniqueResult();
			if (obj == null) {
				session.save(employee);
			}*/
			session.save(employee);
			tx.commit();

		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			throw e;
		} 
		return employee;
	}

	@Override
	public Employee updateEmployee(Employee employee) {
		
	try(Session session = Factory.getSessionFactory().openSession()) {
			
			tx = session.beginTransaction();
			session.update(employee);
			tx.commit();

		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			throw e;
		} 
		return employee;
	}

	@Override
	public void deleteEmployee(Employee empTrn) {
		
		try(Session session = Factory.getSessionFactory().openSession()) {
			
			tx = session.beginTransaction();
			session.delete(employee);
			tx.commit();

		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public Employee findById(long eid) {
		
		try(Session session = Factory.getSessionFactory().openSession()) {
			
			tx = session.beginTransaction();
			employee=session.get(Employee.class,eid);
			tx.commit();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return employee;
	}

	@Override
	public List<Employee> getAllEmployees() {
		
		try(Session session = Factory.getSessionFactory().openSession()) {
			
			Criteria criteria=session.createCriteria(Employee.class);
			employees=criteria.list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return employees;
	}

	@Override
	public void deleteAll() {
		
		try(Session session = Factory.getSessionFactory().openSession()) {
			
			tx = session.beginTransaction();
			
			SQLQuery query =session.createSQLQuery("delete from AdpEmployee");
			Object obj = query.executeUpdate();
			
			tx.commit();

		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			throw e;
		}
		
	}

}
