package control.DAO;

import java.sql.Connection;
import java.sql.*;

/**
 * @author usuario
 */
public class ConexionDB {

    static String url = "jdbc:oracle:thin:@localhost:1521:xe";
    static String usuario = "Eventos";
    static String password = "Eventos";

    private static Connection conexion = null;

    private static Connection GetConexionDB() throws Exception {
        try {
            Connection conexion = null;
            // Se registra el Driver y se crea la conexion
            Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
            conexion = DriverManager.getConnection(url, usuario, password);
            conexion.setAutoCommit(false);
            return conexion;
        } catch (Exception e) {
            throw new Exception("ERROR_CONEXION_BD " + e);
        }
    }
    
    public static Connection getConexion() throws Exception{
        if(conexion==null){
            conexion = GetConexionDB();
        }
        return conexion;
    }
}
