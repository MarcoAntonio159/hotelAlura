package factory;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionFactory {

	public DataSource dataSource;
	
	public ConnectionFactory() {
		ComboPooledDataSource controlPool = new ComboPooledDataSource();
		controlPool.setJdbcUrl("jdbc:mysql://localhost/hotel_alura?useTimeZone=true&serverTimeZone=UTC");
		controlPool.setUser("root");
		controlPool.setPassword("Datos2023%");
		
		this.dataSource = controlPool;
	}
	
	public Connection conectar() {
		try {
			return this.dataSource.getConnection();
		} catch (SQLException e) {
			System.out.println("error de conexión");
			throw new RuntimeException(e);
		}
	}
}
