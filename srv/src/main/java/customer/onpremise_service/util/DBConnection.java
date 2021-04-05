package customer.onpremise_service.util;

import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    
    private static ConnectivitySocks5ProxySocket proxySocket = new ConnectivitySocks5ProxySocket();

	public static Connection getSQLServerConnection(String socketFactoryClass, String username, String password, String databaseName) throws SQLException, ClassNotFoundException {
		Connection con = null;
		
        InetSocketAddress tmp = proxySocket.getProxyAddress();
        String host = tmp.getAddress().getHostAddress();
        int port =tmp.getPort();
        String connectionUrl = "jdbc:sqlserver://" + host + ":" + port + ";databaseName=" + databaseName;

        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        
        Properties props = new Properties();
        props.setProperty("socketFactoryClass", socketFactoryClass);
        props.setProperty("user", username);
        props.setProperty("password", password);
        
        con = DriverManager.getConnection(connectionUrl, props);
		return con;
	}
}
