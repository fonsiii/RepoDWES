package conexion;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.pool.OracleDataSource;

public class Conexion {

	public Conexion() {

	}

	public Connection getConexion() throws SQLException {

		// String JNDI = "jdbc/bibliotecaInsti";
		String JNDI = "jdbc/bibliotecaCloud";

		Connection con = null;
		try {
			InitialContext initialContext = new InitialContext();
			DataSource ds = (DataSource) initialContext.lookup(JNDI);
			con = ds.getConnection();
		} catch (SQLException se) {
			throw se;
		} catch (NamingException ne) {
			ne.printStackTrace();
		}
		return con;
	}

}
