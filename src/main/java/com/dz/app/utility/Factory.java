package com.dz.app.utility;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class Factory {

	public static SessionFactory sf;
	
	private Factory() {
		//to disallow creating objects by other classes.
	}
	/**
	 * @return
	 */
	public static synchronized SessionFactory getSessionFactory() {
        if (sf == null) {
        	
        	//Loading class `com.mysql.jdbc.Driver'. This is deprecated. 
        	//The new driver class is `com.mysql.cj.jdbc.Driver'.
        	//The driver is automatically registered via the SPI and
        	//manual loading of the driver class is generally unnecessary.
        	// the above logs seen when we load driver class through configuration using  below code .
        	
            sf = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        	
        	//the buildSessionFactory() method is deprecated from hibernate 4.x version. And it was replaced with the below syntax :
        
        	/*Configuration configuration = new Configuration().configure();
        	StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        	sf = configuration.buildSessionFactory(builder.build());*/
        	
        	//There are a number of different dialects that could potentially be specified in the hibernate.cfg.xml for MySQL
        	//The correct dialect to choose depends upon the MySQL version in use and the type of database engine in play, like MyISAM or InnoDB.
        	
        }
        return sf;
    }
	
	public static synchronized SessionFactory getSessionFactory(String configFileName) {
        if (sf == null) {
        	sf = new Configuration().configure(configFileName).buildSessionFactory();
        }
        return sf;
    }
}
