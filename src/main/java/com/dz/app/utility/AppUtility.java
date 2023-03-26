package com.dz.app.utility;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.dz.app.model.entity.Employee;
import com.dz.app.service.EmployeeService;
import com.dz.app.serviceImpl.EmployeeServiceImpl;


public class AppUtility {
	
	private static final DecimalFormat df = new DecimalFormat("0.00");
	private static final SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/YYYY");
	
	public static void loader(){
		System.out.print("Loading ");
		for(int i=0;i<=20;i++) {
			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.print("#");
		}
		System.out.println("\n");
	}
	
	public  static void displayRecords(List<Employee> employeeList) {
		
		System.out.println("\n-------------------------------------------------------------------------------------------------------------");
		System.out.println("ID	|	NAME		|	STATUS 	|	AGE	| 	SALARY 		|	CREATED ON	 ");
		System.out.println("---------------------------------------------------------------------------------------------------------------");   
		
		if(employeeList!=null && !employeeList.isEmpty()) {
			
			for(Employee emp:employeeList) {
				System.out.println(emp.getEid()+"\t|"+emp.getFirstName()+" "+emp.getLastName()+"\t\t|\t"+emp.getStatus()+"\t|\t"+DateUtils.getAge(DateUtils.convertJUtilDateTimeToString(emp.getBirthDate()))+"\t|\t"+df.format(emp.getSalary())+"\t|\t"+sdf.format(emp.getBaseProperties().getCreatedOn()));
			}
//			employeeList.clear();
		}else {
			System.err.println("Not Found ...");
		}
		System.out.println("-----------------------------------------------------------------------------------------------------------------\n");
	}
	
	public  static void displayRecord(Employee emp) {
		
		System.out.println("\n-------------------------------------------------------------------------------------------------------------");
		System.out.println("ID	|	NAME		|	STATUS 	|	AGE	| 	SALARY 		|	CREATED ON	 ");
		System.out.println("---------------------------------------------------------------------------------------------------------------");   
		
		if(emp!=null) {
			System.out.println(emp.getEid()+"\t|"+emp.getFirstName()+" "+emp.getLastName()+"\t\t|\t"+emp.getStatus()+"\t|\t"+DateUtils.getAge(DateUtils.convertJUtilDateTimeToString(emp.getBirthDate()))+"\t|\t"+df.format(emp.getSalary())+"\t|\t"+sdf.format(emp.getBaseProperties().getCreatedOn()));
		}else {
			System.err.println("Not Found ...");
		}
		System.out.println("-----------------------------------------------------------------------------------------------------------------\n");
	}

	@SuppressWarnings("unchecked")
	public static void dumpEmployeeData(){

		//added employee is from HibernateDemo1 application 
		
		List<Employee> employeesList=null;
//		try(Session session=Factory.getSessionFactory("secondConfig.xml").openSession()) {
		try{
			SessionFactory sf1 = HibernateUtility.getSecondConfigHibernateConnection();
			Session session= sf1.openSession();
			Criteria criteria=session.createCriteria(Employee.class);
			employeesList=criteria.list();
			displayRecords(employeesList);
			
			session.close();
			sf1.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			/*System.out.println(Factory.sf.isClosed());
			Factory.sf.close();
			System.out.println(Factory.sf.isClosed());*/
			
		}
		
		if(employeesList!=null && !employeesList.isEmpty()) {
			
			EmployeeService eService =new EmployeeServiceImpl();
			Transaction tx=null;
			try(Session session=Factory.getSessionFactory().openSession()) {
				
				tx=session.beginTransaction();
				eService.deleteAll();
				
				employeesList.stream().forEach(emp ->{
					eService.saveEmployee(emp);
				});
				
				tx.commit();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static String colunmChoise(Scanner sc){
		System.out.println("1.firstName \t2.lastName \t3.gender \t4.status \t5.baseProperties.active");
		
		String column="";
		int choise = sc.nextInt();
	
		switch(choise){
			
			case 1:
					column ="firstName";
					break;
					
			case 2: 
					column ="lastName";
					break;
			
			case 3: 
					column ="gender";
					break;
			case 4: 
					column ="status";
					break;
			case 5: 
					column ="baseProperties.active";
					break;		
			default:	
					System.err.println("invalid choise");
					break;
		}
		return column;
	}
	
	public static String likeManuChoise(Scanner sc) {
		
		System.out.println("\n1.check Employee Name Start with letter Enter By User");
		System.out.println("2.check Employee Name Ends with letter Enter By User");

		System.out.println("\nSelect your choice ");
		String letter ="";
		
		int option = sc.nextInt();

		/*if (option == 1) {
			System.out.println("Enter Letter :");
			letter = sc.next();
			letter = letter + "%";
			
		}else if (option == 2) {
			System.out.println("Enter Letter :");
			letter = sc.next();
			letter = "%" + letter;
		}*/
		
		switch(option){
		
			case 1:
					System.out.println("Enter Letter :");
					letter = sc.next();
					letter = letter + "%";
					break;
					
			case 2: 
					System.out.println("Enter Letter :");
					letter = sc.next();
					letter = "%" + letter;
					break;
					
			default:	
					System.err.println("invalid choise");
					break;
		}
		
		return letter;
	}

	public static Map<Integer, List<String>> betweenManuChoise(Scanner sc) {
		System.out.println("\n");
		System.out.println("\t1.check Employee salary Between user Enter salary :");
		System.out.println("\t2.check Employee birthDate[yyyy-MM-dd] Between user Enter dates :");
		
		List<String> list= new ArrayList<>();
		Map<Integer, List<String>> selectedChoice=new HashMap<>();
		
		Integer option = sc.nextInt();
		switch(option){
		
		case 1:
				System.out.println("Enter lowest salary :");
				list.add(sc.next());
	
				System.out.println("Enter Highest salary :");
				list.add(sc.next());
				
				selectedChoice.put(option, list);
				
				break;
				
		case 2: 
				System.out.println("Enter start date :");
				 list.add(sc.next());
	
				System.out.println("Enter end date  :");
				list.add(sc.next());
				
				selectedChoice.put(option, list);
				
				break;
				
		default:	
				System.err.println("invalid choise");
				break;
		}
		return selectedChoice;
	}
}	
