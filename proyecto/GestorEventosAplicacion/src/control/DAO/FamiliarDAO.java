package control.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Familiar;
import modelo.Usuario;

/**
 * @author usuario
 */
public class FamiliarDAO {
    
    private static String cs_CONSULTA_POSIBLES_ASISTENTES = "SELECT CASE FAM.I_TIPOID WHEN 'C' THEN 'CC' WHEN 'T' THEN 'TI' END AS TIPO_ID, POS_FAM.ID_FAMILIAR IDF, FAM.N_NOMBRE NOMBRE, "
            + "FAM.N_APELLIDO APELLIDO, FAM.PARENTEZCO PARENTEZCO FROM FAMILIAR FAM,"
            + "(SELECT FAMILIAR.K_IDFAMILIAR ID_FAMILIAR, FAMILIAR.K_IDUSUARIO ID_ASOCIADO FROM FAMILIAR WHERE K_IDUSUARIO = ? MINUS "
            + "(SELECT DETALLE_INSCRIPCION.K_FAMILIAR ID_FAMILIAR, DETALLE_INSCRIPCION.K_ASOCIADO FROM DETALLE_INSCRIPCION, INSCRIPCION WHERE "
            + "DETALLE_INSCRIPCION.K_INSCRIPCION = INSCRIPCION.K_CODIGO AND INSCRIPCION.K_CODIGOEVENTO = ?  AND INSCRIPCION.I_ESTADO IN ('I','P'))) POS_FAM "
            + "WHERE FAM.K_IDUSUARIO = POS_FAM.ID_ASOCIADO AND FAM.K_IDFAMILIAR = POS_FAM.ID_FAMILIAR";

    public static void InsertarFamiliares(ArrayList<Familiar> familia, Usuario user) throws Exception {
        try {
            PreparedStatement ps = null;
            ps = ConexionDB.getConexion().prepareStatement("");
            int r = 0;
            for (Familiar f : familia) {
                ps.setDouble(0, Double.parseDouble(f.getId()));
                ps.setDouble(1, Double.parseDouble(user.getId()));
                ps.setString(2, f.getNombre());
                ps.setString(3, f.getApellido());
                ps.setString(4, f.getParentezco());
                ps.setString(5, f.getTipo());
                r = ps.executeUpdate();
                if (r == -1) {
                    break;
                }
            }
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
    
    public static ArrayList<Familiar> ConsultarPosiblesAsistentes(String asociado, String evento) throws Exception {
        ArrayList<Familiar> lista = new ArrayList<Familiar>();
        ResultSet rs;
        PreparedStatement ps = ConexionDB.getConexion().prepareStatement(cs_CONSULTA_POSIBLES_ASISTENTES);
        ps.setDouble(1, Double.parseDouble(asociado));
        ps.setInt(2, Integer.parseInt(evento));
        rs = ps.executeQuery();
        Familiar item;
        while (rs.next()) {
            item = new Familiar();
            getFamiliar(item, rs);
            lista.add(item);
        }

        return lista;
    }
    
    private static void getFamiliar(Familiar item, ResultSet rs) throws SQLException {
        item.setId(rs.getString("IDF"));
        item.setTipo(rs.getString("TIPO_ID"));
        item.setNombre(rs.getString("NOMBRE"));
        item.setApellido(rs.getString("APELLIDO"));
        item.setParentezco(rs.getString("PARENTEZCO"));
    }
}
