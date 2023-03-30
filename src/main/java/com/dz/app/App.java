package com.dz.app;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;

import com.dz.app.model.entity.Employee;
import com.dz.app.utility.AppUtility;
import com.dz.app.utility.Factory;

/**
 * @author dz Mar 28, 2023
 *
 */
public class App 
{
    public static void main( String[] args )
    {
      
    	List<Employee> employees=null;
		try(Session session=Factory.getSessionFactory().openSession()){
			
			Criteria cr = session.createCriteria(Employee.class);
			
//			Projection p1=Projection.property("name");			
//			cr.setProjection(p1);
			
			
			
			employees =cr.list();
			
			AppUtility.displayRecords(employees);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
