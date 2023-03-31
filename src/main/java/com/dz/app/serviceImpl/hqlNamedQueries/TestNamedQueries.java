package com.dz.app.serviceImpl.hqlNamedQueries;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.dz.app.model.entity.Employee;
import com.dz.app.service.EmployeeService;
import com.dz.app.utility.AppUtility;

public class TestNamedQueries {

	
	public static void main(String[] args) {
		
		Scanner sc =new Scanner(System.in);
		EmployeeService eservice = new EmployeeServiceImpl();
    	
		Employee empTrn = null;
		List<Employee> employeeList =null;
		String status;
    	long eid =0;
    	
    	do
		{
    		//here initializeLandingPage does't used native sql EmployeService  
    		AppUtility.initializeLandingPage();
    		
    		System.out.println("select your choice \n");
			
    		System.out.println("1]Add  ");
    		System.out.println("2]update");
			System.out.println("3]delete");
			System.out.println("4]Search");
			System.out.println("5]View All Record by hql Named Query ");
			System.out.println("6]View All Record by sql named query ");
    		System.out.println("7]Exit from App ");
			
			int num =sc.nextInt();
		
			switch (num)
			{
				case 1:	
							
							System.err.println("can't do insert operation using hql named query");
							break;
				case 2:
							System.out.println("****** UPDATE BY ID ***** \n");
							System.out.println("Enter Employee EID : ");
							eid =sc.nextLong();
							AppUtility.loader();
							empTrn = eservice.findById(eid);
							if(empTrn !=null) {
								employeeList = new ArrayList<Employee>();
								employeeList.add(empTrn);
								AppUtility.displayRecords(employeeList);
								empTrn= AppUtility.updateEmployeeForm(sc,empTrn);
								eservice.updateEmployee(empTrn);
							}else {
								System.err.println("employee not found by EID ");
							}
							break;
				case 3:
							System.out.println("****** DELETE BY ID ***** \n");
							System.out.println("Enter Employee EID : ");
							eid=sc.nextLong();
							AppUtility.loader();
							empTrn = eservice.findById(eid);
							if(empTrn !=null) {
								employeeList = new ArrayList<Employee>();
								employeeList.add(empTrn);
								AppUtility.displayRecords(employeeList);
								AppUtility.loader();
								eservice.deleteEmployee(empTrn);
								System.out.println("deleted successfully !");
							}
							break;
				case 4:
							System.out.println("****** SEARCH BY ID ***** \n");
							System.out.println("Enter Employee EID : ");
							eid =sc.nextLong();
							AppUtility.loader();
							empTrn = eservice.findById(eid);
							if(empTrn !=null) {
								employeeList = new ArrayList<Employee>();
								employeeList.add(empTrn);
								AppUtility.displayRecords(employeeList);
							}else {
								System.err.println("employee not found by EID ");
							}
							break;
				case 5:
							employeeList=eservice.getAllEmployees();
							if(employeeList!=null && !employeeList.isEmpty()) {
								System.out.println("\n\nAll Employee Records  by HQL Named Query  ::");
								AppUtility.displayRecords(employeeList);
							}else {
								System.err.println("no data found");
							}
							break;
							
				case 6:
							employeeList=EmployeeServiceImpl.getAllEmployeesBySQlNamedQuery();
							if(employeeList!=null && !employeeList.isEmpty()) {
								System.out.println("\n\nAll Employee Records by SQl Named Query ::");
								AppUtility.displayRecords(employeeList);
							}else {
								System.err.println("no data found");
							}
							break;
							
				case 7:
							System.exit(0);
							break;
				default:
							System.err.println("Invalid Choice,try again");
							break;
			}
    		
			System.out.println("\n\n\tDo u want to continue other operation (yes[y] / no[n] ) ? : ");
			status=sc.next();	
		
		
		}while(status.equalsIgnoreCase("yes") || status.equalsIgnoreCase("y"));
	
		
	}
}
