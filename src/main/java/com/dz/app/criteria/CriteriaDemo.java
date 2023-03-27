package com.dz.app.criteria;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.dz.app.model.entity.Employee;
import com.dz.app.serviceImpl.ProjectionImpl;
import com.dz.app.serviceImpl.RestrictionImpl;
import com.dz.app.utility.AppUtility;
import com.dz.app.utility.DateUtils;

/**
 * @author dz Mar 24, 2023
 *
 */
public class CriteriaDemo {

	//using criteria we can make only select operation by applaying some condition.

	public static void main(String[] args) {
		
		
		Scanner sc=new Scanner(System.in);
		int option=0;
		String choice="yes";
		String status="";
		List<Employee> employees=null;
		Double salary=0.00;
		Double sum=0.00;
		Double maxSalary=0.00;
		Double minSalary=0.00;
		Double avSalary=0.00;
		Long totalEmp=0L;
		System.out.println("***************wecome***********************\n");
			
		do
		{
			AppUtility.initializeLandingPage();
			AppUtility.loader();
			System.out.println("\n1]Restriction .");
			System.out.println("2]Projections .");
			System.out.println("3]OrderBy .");
			System.out.println("4]Pagination.");
			System.out.println("5]dump employee data from anaother db schema.");
			System.out.println("6]Exit.");
			
			
			System.out.println("\nSelect you choice :");
			
			int num =sc.nextInt();
			
			switch (num)
			{
				case 1:
						do{
												
//								EmployeeDaoImpl.getAllRecord();
//								ProjectionImpl.aggrigateFunction();
								AppUtility.loader();
								System.out.println("\n************Restrictions Menu**********************************");
								System.out.println("\n");
								System.out.println("\t1]Equals");
								System.out.println("\t2]greter than");
								System.out.println("\t3]less than");
								System.out.println("\t4]like");
								System.out.println("\t5]between");
								System.out.println("\t6]And");
								System.out.println("\t7]Or");
								System.out.println("\t8]Exit from this menu ");
								System.out.println("\n");
								
								System.out.println("\tselect your choice :");
								option=sc.nextInt();
								
								switch (option) 
								{
									case 1:	
											System.out.println("Enter your choise from below property name mapped with column: ");
											String column= AppUtility.colunmChoise(sc);
											if(column!=null && !column.isEmpty()) {
												System.out.println("Enter value  : ");
												String value = sc.next();
												employees= RestrictionImpl.equals(column,value);
												AppUtility.displayRecords(employees);
												employees.clear();
											}
											break;
									case 2:
											System.out.println("check Employee Salary greater than user Enter salary ");
											System.out.println("Enter salary :");
											salary=sc.nextDouble();
											employees = RestrictionImpl.greaterThan(salary);
											AppUtility.displayRecords(employees);
											employees.clear();
											break;
											
									case 3:
											System.out.println("check Employee Salary less than user Enter salary ");
											System.out.println("Enter salary :");
											salary=sc.nextDouble();
											employees = RestrictionImpl.lessThan(salary);
											AppUtility.displayRecords(employees);
											employees.clear();
											break;
											// i have DOB column but not age in db then 
											// if how do query to check age greater than some age ?
											// if want to check with year of dob ? like many employee join in this year ?
									case 4:
											String letter= AppUtility.likeManuChoise(sc);
											if(letter!=null && !letter.isEmpty()) {
												employees = RestrictionImpl.like(letter);
												AppUtility.displayRecords(employees);
												employees.clear();
											}
											break;
									
									case 5:
											Map<Integer, List<String>> choises= AppUtility.betweenManuChoise(sc);
											if(choises !=null && !choises.isEmpty()) {
												
												for (Map.Entry<Integer,List<String>> entry : choises.entrySet()) {
//												    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
													if(entry.getKey() == 1) {
														
														List<String> values = entry.getValue();
														employees = RestrictionImpl.salaryBetween(Double.parseDouble(values.get(0)),Double.parseDouble(values.get(1)));
														AppUtility.displayRecords(employees);
														employees.clear();
													
													}else if(entry.getKey() == 2) {
														List<String> values = entry.getValue();
														employees = RestrictionImpl.dateBetween(DateUtils.convertStringToJUtilDateTime(values.get(0)),DateUtils.convertStringToJUtilDateTime(values.get(1)));
														AppUtility.displayRecords(employees);
														employees.clear();
													}else {
														System.err.println("invalid choise");
													}
												}
												
											
											}
											break;
											
									case 6:
											System.out.println("check Employee salary Between user Enter salary using restiction 'and' ");
											System.out.println("Enter salary greater than equal to :");
											double gteqsalary=sc.nextDouble();
											System.out.println("Enter salary less than equal to :");
											double ltsalary=sc.nextDouble();
											
											employees = RestrictionImpl.and(gteqsalary,ltsalary);
											AppUtility.displayRecords(employees);
											employees.clear();
											
											break;
											
									case 7:
											System.out.println("Employee whose salary greater than user enter salary or whose name start with user eneter letter");
											System.out.println("Enter salary greater than equal to :");
											double gtsalary=sc.nextDouble();
											
											System.out.println("Enter Letter :");
											letter = sc.next();
											letter = letter + "%";
											
											employees = RestrictionImpl.or(gtsalary,letter);
											AppUtility.displayRecords(employees);
											employees.clear();
											
											break;
								   case 8 :
									   		System.exit(0);
									   		break;		
									default:
											System.err.println("INVALID Choice ...try again..");
											break;
								}
								System.out.println("\nDo you want continue other operations from Restriction Menu (yes[y] / no[n] ):");
								choice=sc.next();
								
							}while(choice.equalsIgnoreCase("yes") || choice.equalsIgnoreCase("y"));
							break;
				case 2:
							do{
								
//								EmployeeDaoImpl.getAllRecord();
//								ProjectionImpl.aggrigateFunction();
								AppUtility.loader();
								System.out.println("\n************Aggrigate Functions Menu**********************************");
								System.out.println("\n");
								System.out.println("\t1]sum");
								System.out.println("\t2]max");
								System.out.println("\t3]min");
								System.out.println("\t4]avg");
								System.out.println("\t5]rowcount");
								System.out.println("\t6]Exit");
								System.out.println("\n");
								
								System.out.println("\tselect your choice :");
								option=sc.nextInt();
								
								switch (option) 
								{

									case 1:
											sum=ProjectionImpl.sum("salary");
											System.out.println("sum of salary of All Employees -> "+sum);
											break;
									case 2:
											maxSalary=ProjectionImpl.max("salary");
											System.out.println("Maximum salary among all Employee ->"+maxSalary);
											break;
									case 3:
											minSalary=ProjectionImpl.min("salary");
											System.out.println("Minimum salary among all Employee ->"+minSalary);
											break;
									case 4:
											avSalary=ProjectionImpl.avg("salary");
											System.out.println("Average of Employees salary :"+avSalary);
											break;
									case 5:
											totalEmp=ProjectionImpl.totalEmployeeCount();
											System.out.println("Total Employees :"+totalEmp);
											break;
									case 6:
											System.exit(0);
											break;
											
									default:
											System.err.println("INVALID Choice ...try again..");
											break;
								}
								
							System.out.println("\nDo you want continue other operations from Aggrigate Functions Menu(yes[y] / no[n] ):");
							choice=sc.next();
								
							}while(choice.equalsIgnoreCase("yes") || choice.equalsIgnoreCase("y"));
							break;
				case 3:		
							AppUtility.loader();
							System.out.println("\n\tElgible columns");
							System.out.println("== > firstName \tlastName \tsalary");

							System.out.println("\n\t1]OrderBy entered column name Descending order");
							System.out.println("\t2]Order by entered column name Ascending Order");
							
							System.out.println("Select your choice :");
							option=sc.nextInt();
							System.out.println("Enter from above ,coulmn name : ");
							String coulmnName=sc.next();
							switch (option) 
							{
								case 1:	
										AppUtility.OrderByDesc(coulmnName);
										break;
		
								case 2:
									AppUtility.OrderByAsc(coulmnName);
										break;
								default:	
										System.err.println("Invalid choice try againnnn...");
										break;
							}
							break;
				case 4:		
							AppUtility.loader();
//							EmployeeDaoImpl.pagination(0,5);
							break;
				case 5:		
							AppUtility.loader();
							AppUtility.dumpEmployeeData();
							break;
				case 6:
							AppUtility.loader();
							System.exit(0);
							break;
				default:
							System.err.println("Invalid Choice,try again");
							break;
			}
			System.out.println("\n\n\tDo u want to continue other operation from Main Menu (yes[y] / no[n] ) ? : ");
			status=sc.next();	
		
		}while(status.equalsIgnoreCase("yes") || status.equalsIgnoreCase("y"));
		
    	if(!(status.equalsIgnoreCase("yes") || status.equalsIgnoreCase("y"))){
    		System.err.println("invalid choise "+status);
    		System.exit(0);
    	}
	}	
}
