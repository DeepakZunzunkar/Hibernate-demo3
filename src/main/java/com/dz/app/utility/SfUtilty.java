package com.dz.app.utility;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class SfUtilty {

	private static StandardServiceRegistry standardServiceRegistry;
	private static SessionFactory sessionFactory;

	static {
		
		try {
			if(sessionFactory==null) {
				
				//create standardServiceRegistry
				standardServiceRegistry = new StandardServiceRegistryBuilder().configure().build();
				
				//create MetaDataSources
				MetadataSources metadataSources =new MetadataSources(standardServiceRegistry);
				
				//create MetatData
				Metadata metadata= metadataSources.getMetadataBuilder().build();
				
				//create Session Factory 
				sessionFactory=metadata.getSessionFactoryBuilder().build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			if(standardServiceRegistry!=null) {
				StandardServiceRegistryBuilder.destroy(standardServiceRegistry);
			}
		}
	}
	
	// utility method to get session factory 
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	
	
}
