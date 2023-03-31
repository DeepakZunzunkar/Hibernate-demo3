package com.dz.app.serviceImpl.hql;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.dz.app.model.entity.Employee;
import com.dz.app.service.EmployeeService;
import com.dz.app.utility.DateUtils;
import com.dz.app.utility.Factory;

public class EmployeeServiceImpl implements EmployeeService{

	static Employee employee=null;
	static Session session=null;
	static Transaction tx=null;
	static Integer rs=0;
	List<Employee> employees = new ArrayList<Employee>();
	
	// here instead of table name in query we used class name and it's property.
	
	
	@Override
	public Employee saveEmployee(Employee employee) {
		//In HQL , we can not do insert operation using the old-fashioned SQL way of INSERT INTO ... VALUES ... , because it is not supported in HQL.
		//we have to use Hibernate object mapping to do the insert (or raw SQL if you can expose it).
		return employee;
	}

	@Override
	public Employee updateEmployee(Employee empTrn) {
		
		try(Session session = Factory.getSessionFactory().openSession()) {
			
			tx = session.beginTransaction();
			
			Query<Employee> query=session.createQuery("update Employee set firstname=:fname,lastname=:lname,gender=:gender,birthDate=:birthDate,salary=:salary,status=:status,active=:active,updatedby=:updatedby,updatedon=:updatedon where eid=:eid");
			
			query.setParameter("fname",empTrn.getFirstName());
			query.setParameter("lname",empTrn.getLastName());
			query.setParameter("gender",empTrn.getGender());
			query.setParameter("birthDate",empTrn.getBirthDate());
			query.setParameter("salary",empTrn.getSalary());
			query.setParameter("status",empTrn.getStatus());
			query.setParameter("active",empTrn.getBaseProperties().getActive());
			query.setParameter("updatedby",empTrn.getBaseProperties().getUpdatedBy());
			query.setParameter("updatedon",empTrn.getBaseProperties().getUpdatedOn());
			query.setParameter("eid",empTrn.getEid());
			
			int i=query.executeUpdate();
			if(i>0){
				System.out.println("Record updated succesfully ..");
			}else{
				System.err.println("Fail to update..");
			}
			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} 
		return empTrn;
	}

	@Override
	public void deleteEmployee(Employee empTrn) {
		
		try(Session session = Factory.getSessionFactory().openSession()) {
			
			tx = session.beginTransaction();
		
			Query query=session.createQuery("delete Employee where eid=:eid");
			query.setParameter("eid",empTrn.getEid());	
			
			int i=query.executeUpdate();
			if(i>0){
				System.out.println("Record deleted succesfully ..");
			}else{
				System.err.println("Fail to delete..");
			}
			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} 
		
	}

	@Override
	public Employee findById(long eid) {
		employee = null;
		try(Session session = Factory.getSessionFactory().openSession()) {
			
			tx = session.beginTransaction();
			
//			Query query =session.createQuery("SELECT eid,firstname,lastname,gender,birthDate,salary,status,active,createdon,createdby,updatedon,updatedby FROM adpemployee where eid="+eid);
			
			Query<Employee> query =session.createQuery("FROM Employee where eid=:eid");
			query.setParameter("eid",eid);
			
			employee= query.getSingleResult();
			tx.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return employee;
	}

	@Override
	public List<Employee> getAllEmployees() {
		
		try(Session session = Factory.getSessionFactory().openSession()) {
			

			tx = session.beginTransaction();
			
//			Query<Employee> query=session.createQuery("select {emp.*} from Employee emp");
			
			Query<Employee> query=session.createQuery("from Employee order by eid desc");
			
			employees = query.getResultList();
			tx.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return employees;
	}

	@Override
	public void deleteAll() {
		
		try(Session session = Factory.getSessionFactory().openSession()) {
			
//			Query query=session.createQuery("delete Employee");
//			query.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
