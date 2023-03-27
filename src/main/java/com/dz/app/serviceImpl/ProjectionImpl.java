package com.dz.app.serviceImpl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.dz.app.model.entity.Employee;
import com.dz.app.utility.Factory;

public class ProjectionImpl {


	public static Double sum(String columnName) {

		Double sum=0.00;
		try(Session session=Factory.getSessionFactory().openSession()){
			
			Criteria cr = session.createCriteria(Employee.class);
				
			cr.setProjection(Projections.sum(columnName));
			
			sum=(Double)cr.uniqueResult();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return sum;
	}
	
	public static Double sum(String className,String columnName) {

		Double sum=0.00;
		try(Session session=Factory.getSessionFactory().openSession()){
			
			Criteria cr = session.createCriteria(className+".class");
				
			cr.setProjection(Projections.sum(columnName));
			
			sum=(Double)cr.uniqueResult();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return sum;
	}

	public static Double max(String columnName) {
		Double max=0.00;
		try(Session session=Factory.getSessionFactory().openSession()){
			
			Criteria cr = session.createCriteria(Employee.class);
				
			cr.setProjection(Projections.max(columnName));
			
			max=(Double)cr.uniqueResult();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return max;
	}

	public static Double min(String columnName) {
		Double min=0.00;
		try(Session session=Factory.getSessionFactory().openSession()){
			
			Criteria cr = session.createCriteria(Employee.class);
				
			cr.setProjection(Projections.min(columnName));
			
			min=(Double)cr.uniqueResult();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return min;
	}

	public static Double avg(String columnName) {
		Double avg=0.00;
		try(Session session=Factory.getSessionFactory().openSession()){
			
			Criteria cr = session.createCriteria(Employee.class);
				
			cr.setProjection(Projections.avg(columnName));
			
			avg=(Double)cr.uniqueResult();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return avg;
	}

	public static Long totalEmployeeCount() {
		Long count=0l;
		try(Session session=Factory.getSessionFactory().openSession()){
			
			Criteria cr = session.createCriteria(Employee.class);
				
			cr.setProjection(Projections.rowCount());
			
			count=(Long)cr.uniqueResult();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public static Long employeeCountByStatus(String status) {
		Long count=0l;
		try(Session session=Factory.getSessionFactory().openSession()){
			
			Criteria cr = session.createCriteria(Employee.class);
				
			cr.setProjection(Projections.rowCount());
			cr.add(Restrictions.eq("status",status));
			count=(Long)cr.uniqueResult();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
}
