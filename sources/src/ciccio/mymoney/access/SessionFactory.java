package ciccio.mymoney.access;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class SessionFactory {
    private static SqlSessionFactory sessionfactory;
    
    public static SqlSessionFactory getSessionFactory() throws IOException {
	// development | server
	String environment = "development";
	return getSessionFactory(environment);
    }
    
    public static SqlSessionFactory getSessionFactory(String environment) throws IOException {
	if (sessionfactory == null) {
	    String resource = "ciccio/mymoney/config/db_config.xml";
	    Reader reader = Resources.getResourceAsReader(resource);
	    sessionfactory = new SqlSessionFactoryBuilder().build(reader, environment);
	}
	
	return sessionfactory;
    }
}
