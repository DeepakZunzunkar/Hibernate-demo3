package com.dz.app.criteria;

import java.util.List;
import java.util.Scanner;

import com.dz.app.model.entity.Employee;
import com.dz.app.serviceImpl.RestrictionImpl;
import com.dz.app.utility.AppUtility;

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
		System.out.println("***************wecome***********************\n");
			
		do
		{
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
									
									case 4:
											String letter= AppUtility.likeManuChoise(sc);
											if(letter!=null && !letter.isEmpty()) {
												employees = RestrictionImpl.like(letter);
												AppUtility.displayRecords(employees);
												employees.clear();
											}
											break;
									
									case 5:
											List<String> choises= AppUtility.betweenManuChoise(sc);
											if(choises !=null && !choises.isEmpty()) {
												employees = RestrictionImpl.salaryBetween(Double.parseDouble(choises.get(0)),Double.parseDouble(choises.get(1)));
												AppUtility.displayRecords(employees);
												employees.clear();
											}
											break;
											
									case 6:
//											EmployeeDaoImpl.and();
											break;
											
									case 7:
//											EmployeeDaoImpl.Or();
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
								System.out.println("\t1]Aggrigate Functions :");
								System.out.println("\t2]sum");
								System.out.println("\t3]min");
								System.out.println("\t4]max");
								System.out.println("\t5]avg");
								System.out.println("\t6]rowcount");
								System.out.println("\t7]Exit");
								System.out.println("\n");
								
								System.out.println("\tselect your choice :");
								option=sc.nextInt();
								
								switch (option) 
								{
									case 1:	
//											ProjectionImpl.aggrigateFunction();
											break;
									case 2:
//											ProjectionImpl.sum();
											break;
									case 3:
//											ProjectionImpl.min();
											break;
									case 4:
//											ProjectionImpl.max();
											break;
									case 5:
//											ProjectionImpl.avg();
											break;
									case 6:
//											ProjectionImpl.countEmployee();
											break;
									case 7:
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
							System.out.println("\n\t1]OrderBy name Descending order");
							System.out.println("\t2]Order by salary Descending Order");
							
							System.out.println("Select yur choice :");
							option=sc.nextInt();
							
							switch (option) 
							{
								case 1:	
//										EmployeeDaoImpl.OrderByNameDesc();
										break;
		
								case 2:
//										EmployeeDaoImpl.OrderBySalaryDesc();
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
