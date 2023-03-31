package com.dz.app.serviceImpl.nativeSqlByHibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.dz.app.model.entity.BaseProperties;
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
	
	@Override
	public Employee saveEmployee(Employee employee) {
		
		
		try(Session session = Factory.getSessionFactory().openSession()) {
			
			tx = session.beginTransaction();
			
			SQLQuery query = session.createSQLQuery("SELECT * FROM adpemployee a where a.firstname='"+ employee.getFirstName() + "' and a.lastname='" + employee.getLastName() + "'");
			Object obj = query.uniqueResult();
			if (obj == null) {
				query=session.createSQLQuery("insert into AdpEmployee(firstname,lastname,gender,birthDate,salary,status,active,createdby,createdon)values"
						+ "('"+employee.getFirstName()+"','"+employee.getLastName()+"','"+employee.getGender()+"','"+DateUtils.convertJUtilDateTimeToString(employee.getBirthDate())+"','"+employee.getSalary()+"',"
						+"'"+employee.getStatus()+"','"+employee.getBaseProperties().getActive()+"','"+employee.getBaseProperties().getCreatedBy()+"','"+DateUtils.convertJUtilDateTimeToString(employee.getBaseProperties().getCreatedOn())+"')");
				
				int i=query.executeUpdate();
				if(i>0)
				{
					System.out.println("Record inserted succesfully ..");
					query = session.createSQLQuery("SELECT * FROM adpemployee a where a.firstname='"+ employee.getFirstName() + "' and a.lastname='" + employee.getLastName() + "'");
					Object[] obj1 = (Object[]) query.uniqueResult();
					if (obj1 != null) {
						employee = new Employee();
						employee.setEid(Long.parseLong(obj1[0]!=null?obj1[0].toString():""));
					}
				}else{
					System.err.println("Fail to Insert..");
				}
			}
			tx.commit();

		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} 
		return employee;
		
	}

	@Override
	public Employee updateEmployee(Employee employee) {
		
		try(Session session = Factory.getSessionFactory().openSession()) {
			
			tx = session.beginTransaction();
		
			SQLQuery query=session.createSQLQuery("update AdpEmployee set "
						+ "firstname='"+employee.getFirstName()+"',lastname='"+employee.getLastName()+"',gender='"+employee.getGender()+"',birthDate='"+DateUtils.convertJUtilDateTimeToString(employee.getBirthDate())+"',salary='"+employee.getSalary()+"',"
						+"status='"+employee.getStatus()+"',active='"+employee.getBaseProperties().getActive()+"',updatedby='"+employee.getBaseProperties().getUpdatedBy()+"',updatedon='"+DateUtils.convertJUtilDateTimeToString(employee.getBaseProperties().getUpdatedOn())+"'"
						+ " where eid='"+employee.getEid()+"'");
				
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
		return employee;
	}

	@Override
	public void deleteEmployee(Employee employee) {
		
		try(Session session = Factory.getSessionFactory().openSession()) {
			
			tx = session.beginTransaction();
		
			SQLQuery query=session.createSQLQuery("delete from AdpEmployee where eid='"+employee.getEid()+"'");
				
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
		
		employee =null;
		try(Session session = Factory.getSessionFactory().openSession()) {
			
			tx = session.beginTransaction();
//			employee=session.get(Employee.class,eid);
			
			SQLQuery<Employee> query =session.createSQLQuery("SELECT eid,firstname,lastname,gender,birthDate,salary,status,active,createdon,createdby,updatedon,updatedby FROM adpemployee where eid="+eid);
			
			/*List<Object[]> rows = query.list();
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
				
			}	*/
			
			query.addEntity("employee",Employee.class);
			
//			List<Employee> rows = query.list();
			
			employee = query.getSingleResult();
			
			tx.commit();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return employee;
	}

	@Override
	public List<Employee> getAllEmployees() {
		employees =new ArrayList<>();
		try(Session session = Factory.getSessionFactory().openSession()) {
			
			tx = session.beginTransaction();
//			employee=session.get(Employee.class,eid);
			
			SQLQuery<Employee> query =session.createSQLQuery("SELECT eid,firstname,lastname,gender,birthDate,salary,status,active,createdon,createdby,updatedon,updatedby FROM adpemployee order by eid desc");
			
			/*List<Object[]> rows = query.list();
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
			}*/	
			query.addEntity(Employee.class);
			employees= query.list();
			
			tx.commit();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return employees;
	}

	@Override
	public void deleteAll() {
		
	}

}
