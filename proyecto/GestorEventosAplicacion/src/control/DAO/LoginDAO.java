package control.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import modelo.Usuario;

/**
 * @author usuario
 */
public class LoginDAO {

    private static String cs_CONSULTA_USUARIO = "SELECT K_ID ID, N_NOMBRE NOMBRE, N_APELLIDO APELLIDO, "
            + "CASE I_TIPO WHEN 'A' THEN 'Asociado' WHEN 'F' THEN 'Funcionario' END AS Tipo FROM USUARIO "
            + "WHERE K_ID = ? AND N_PASSWORD = ?";
    private static String cs_INSERCION_USUARIO = "";

    public static Usuario ConsultarUsuario(String user, String password) throws Exception {
        try {
            ResultSet rs;
            PreparedStatement ps = ConexionDB.getConexion().prepareStatement(cs_CONSULTA_USUARIO);
            ps.setDouble(1, Double.parseDouble(user));
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }
            Usuario prototipo;
            prototipo = getUsuario(rs);
            return prototipo;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    private static Usuario getUsuario(ResultSet rs) throws Exception {
        Usuario u = null;
        try {
            u = new Usuario(rs.getString("ID"));
            u.setNombre(rs.getString("Nombre"));
            u.setApellido(rs.getString("Apellido"));
            u.setTipo(rs.getString("Tipo"));
        } catch (Exception ex) {
            throw new Exception("Error en la definicion de datos despues de la consulta");
        }
        return u;
    }

    public static void InsertarUsuario(Usuario user) throws Exception {
        try {
            int r = 0;
            PreparedStatement ps = ConexionDB.getConexion().prepareStatement(cs_INSERCION_USUARIO);
            ps.setDouble(0, Double.parseDouble(user.getId()));
            ps.setString(1, user.getNombre());
            ps.setString(2, user.getApellido());
            ps.setString(3, "A");
            ps.setString(4, user.getPassword());
            r = ps.executeUpdate();
            if (r == -1) {
                ConexionDB.getConexion().rollback();
            } else {
                ConexionDB.getConexion().commit();
            }
        } catch (Exception ex) {
            ConexionDB.getConexion().rollback();
            throw new Exception("Error en la insercion");
        }

    }

}
