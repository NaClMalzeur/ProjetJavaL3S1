package modele;

import java.sql.SQLException;
import javax.sql.DataSource;

public class DataSourceFactory {
    
    	public static DataSource getDataSourceTest() throws SQLException {
            org.hsqldb.jdbc.JDBCDataSource ds = new org.hsqldb.jdbc.JDBCDataSource();
            ds.setDatabase("jdbc:hsqldb:mem:testcase;shutdown=true");
            ds.setUser("sa");
            ds.setPassword("sa");
            
            return ds;
        }
        
        public static DataSource getDataSource() throws SQLException {
        org.apache.derby.jdbc.ClientDataSource ds = new org.apache.derby.jdbc.ClientDataSource();
	ds.setDatabaseName("sample");
	ds.setUser("app");
	ds.setPassword("app");
	// The host on which Network Server is running
	ds.setServerName("localhost");
	// port on which Network Server is listening
	ds.setPortNumber(1527);
        return ds;
    }
}
