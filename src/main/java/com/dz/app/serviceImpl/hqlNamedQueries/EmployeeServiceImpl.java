package com.dz.app.serviceImpl.hqlNamedQueries;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.dz.app.model.entity.BaseProperties;
import com.dz.app.model.entity.Employee;
import com.dz.app.service.EmployeeService;
import com.dz.app.utility.DateUtils;
import com.dz.app.utility.SfUtilty;

public class EmployeeServiceImpl implements EmployeeService {

	static Employee employee=null;
	static Session session=null;
	static Transaction tx=null;
	static Integer rs=0;
	static List<Employee> employees = new ArrayList<Employee>();
	
	@Override
	public Employee saveEmployee(Employee employee) {
		
		// we can not do insert operation using hql named queries.
		return null;
	}

	@Override
	public Employee updateEmployee(Employee empTrn) {
		
		try(Session session = SfUtilty.getSessionFactory().openSession()) {
			
			tx=session.beginTransaction();	
				Query<Employee> query=session.getNamedQuery("hql_updateAll");
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
		
		try(Session session = SfUtilty.getSessionFactory().openSession()) {
			
		tx=session.beginTransaction();	
			Query<Employee> query=session.getNamedQuery("hql_deleteById");
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
		employee=null;
		try(Session session = SfUtilty.getSessionFactory().openSession()) {
		Query<Employee> query=session.getNamedQuery("hql_findById");
		query.setParameter("eid",eid);

		employee=query.getSingleResult();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return employee;
	}

	@Override
	public List<Employee> getAllEmployees() {
		
		try(Session session = SfUtilty.getSessionFactory().openSession()) {
		Query<Employee> query=session.getNamedQuery("hql_findAll");
//		employees=query.getResultList();
		employees=query.list();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return employees;
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	public static List<Employee> getAllEmployeesBySQlNamedQuery() {
		
		employees = new ArrayList<>();
		try(Session session = SfUtilty.getSessionFactory().openSession()) {
			
		org.hibernate.Query query=session.getNamedSQLQuery("sql_findAll");
		
//		employees=query.getResultList();
//		employees=query.list();
		
		//using above both list and gerResultList method we can cast to list of employee object directly.
		
		
			List<Object[]> rows = query.list();
			for(Object[] row : rows){
				employee = new Employee();
				
				employee.setEid(Long.parseLong(row[0].toString()));
				employee.setFirstName(row[1].toString());
				employee.setLastName(row[2].toString());
				employee.setGender(row[3].toString());
				employee.setBirthDate(row[4]!=null?DateUtils.convertStringToJUtilDateTime(row[4].toString()):null);
				employee.setSalary(row[5]!=null?Double.parseDouble(row[5].toString()):0.00);
				employee.setStatus(row[6]!=null?row[6].toString():"");
				
				employee.setBaseProperties(new BaseProperties());
				employee.getBaseProperties().setActive(row[7]!=null?row[7].toString():"");
				employee.getBaseProperties().setCreatedOn(row[8]!=null?DateUtils.convertStringToJUtilDateTime(row[8].toString()):null);
				employee.getBaseProperties().setCreatedBy(row[9]!=null?row[9].toString():"");
				employee.getBaseProperties().setUpdatedOn(row[10]!=null?DateUtils.convertStringToJUtilDateTime(row[10].toString()):null);
				employee.getBaseProperties().setUpdatedBy(row[11]!=null?row[11].toString():"");
				
				employees.add(employee);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return employees;
	}
}
