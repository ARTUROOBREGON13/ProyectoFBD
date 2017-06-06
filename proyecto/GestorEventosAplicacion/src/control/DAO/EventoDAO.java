/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Evento;
import modelo.Proveedor;

/**
 *
 * @author R2D2
 */
public class EventoDAO {

    private static String cs_CONSULTA_EVENTOS_ACTIVOS = "SELECT K_CODIGO Codigo, N_NOMBRE Nombre FROM EVENTO  WHERE I_ESTADO = 'A' AND F_FIN > SYSDATE";
    private static String cs_CONSULTA_EVENTOS_ACTIVOS_CN = "SELECT K_CODIGO Codigo, N_NOMBRE Nombre FROM EVENTO WHERE I_ESTADO = 'A' AND K_CODIGO = ? AND N_NOMBRE = ? AND F_FIN > SYSDATE";
    private static String cs_CONSULTA_EVENTOS_ACTIVOS_C = "SELECT K_CODIGO Codigo, N_NOMBRE Nombre FROM EVENTO WHERE I_ESTADO = 'A' AND K_CODIGO = ? AND F_FIN > SYSDATE";
    private static String cs_CONSULTA_EVENTOS_ACTIVOS_N = "SELECT K_CODIGO Codigo, N_NOMBRE Nombre FROM EVENTO WHERE I_ESTADO = 'A' AND N_NOMBRE = ? AND F_FIN > SYSDATE";
    private static String cs_CONSULTA_EVENTOS_CADUCADOS = "SELECT K_CODIGO Codigo, N_NOMBRE Nombre FROM EVENTO  WHERE F_FIN <= SYSDATE";
    private static String cs_CONSULTA_EVENTOS_CADUCADOS_CN = "SELECT K_CODIGO Codigo, N_NOMBRE Nombre FROM EVENTO WHERE K_CODIGO = ? AND N_NOMBRE = ? AND F_FIN <= SYSDATE";
    private static String cs_CONSULTA_EVENTOS_CADUCADOS_C = "SELECT K_CODIGO Codigo, N_NOMBRE Nombre FROM EVENTO WHERE K_CODIGO = ? AND F_FIN <= SYSDATE";
    private static String cs_CONSULTA_EVENTOS_CADUCADOS_N = "SELECT K_CODIGO Codigo, N_NOMBRE Nombre FROM EVENTO WHERE N_NOMBRE = ? AND F_FIN <= SYSDATE";
    private static String cs_CONSULTA_EVENTO_DETALLE = "SELECT EVENTO.K_CODIGO CODIGO, TIPO.N_TIPO TIPO, EVENTO.K_NITPROVEEDOR PROVEE, PROVEEDOR.N_NOMBRE PRO_NOM, "
            + "EVENTO.N_NOMBRE NOMBRE, EVENTO.N_OBSERVACION OBSER, EVENTO.N_LUGAR LUGAR,TO_CHAR(EVENTO.F_INICIO, 'DD/MM/YYYY') FINICIO, "
            + "TO_CHAR(EVENTO.F_FIN, 'DD/MM/YYYY') FFIN, TO_CHAR(EVENTO.F_LIMITEINSCRIPCION, 'DD/MM/YYYY') FLIM, "
            + "EVENTO.Q_LIMITEPARTICIPANTES PARTICI,(EVENTO.Q_LIMITEPARTICIPANTES - EVENTO.Q_CUPOSDISPONIBLES) ASISTEN, EVENTO.V_COSTOTOTAL COSTO, "
            + "EVENTO.V_PORCSUBSIDIO PORCSUB, V_VALORCOPAGO COPAGO, CASE EVENTO.I_ESTADO WHEN 'A' THEN 'Activo' WHEN 'T' THEN 'Terminado' END AS ESTADO "
            + "FROM EVENTO, TIPO, PROVEEDOR WHERE EVENTO.K_TIPO=TIPO.K_CODIGO AND EVENTO.K_NITPROVEEDOR = PROVEEDOR.K_NITPROVEEDOR AND "
            + "EVENTO.Q_CUPOSDISPONIBLES>0 AND EVENTO.K_CODIGO = ?";
    private static String cs_INSERCION_EVENTO = "INSERT INTO EVENTO VALUES (?,?,?,?,?,?,TO_DATE(?),TO_DATE(?),TO_DATE(?),?,?,?,?,?,?)";
    private static String cs_MODIFICACION_EVENTO = "UPDATE EVENTO EVT SET EVT.N_OBSERVACION = ?, EVT.F_LIMITEINSCRIPCION = TO_DATE(?), EVT.Q_LIMITEPARTICIPANTES = ?, "
            + "EVT.Q_CUPOSDISPONIBLES = EVT.Q_LIMITEPARTICIPANTES - (SELECT COUNT(DET.K_CONSECUTIVO||' '||DET.K_FAMILIAR) AS PRUEBA FROM DETALLE_INSCRIPCION DET, INSCRIPCION INS "
            + "WHERE INS.K_CODIGOEVENTO = EVT.K_CODIGO AND INS.I_ESTADO IN ('P','I') AND DET.K_INSCRIPCION=INS.K_CODIGO), "
            + "EVT.V_PORCSUBSIDIO = ?, EVT.V_VALORCOPAGO = ? WHERE EVT.K_CODIGO = ?";
    private static String cs_CIERRE_EVENTO = "UPDATE EVENTO SET I_ESTADO = 'T' WHERE K_CODIGO = ?";
    private static String cs_ASISTENCIA_EVENTO = "SELECT COUNT(*) FROM DETALLE_INSCRIPCION DET, INSCRIPCION INS, EVENTO EVT "
            + "WHERE EVT.K_CODIGO = INS.K_CODIGOEVENTO AND INS.K_CODIGO = DET.K_INSCRIPCION "
            + "AND INS.I_ESTADO = 'P' AND EVT.K_CODIGO = ?";
    private static String cs_GENERAR_PAGO = "INSERT INTO PAGOLOGISTICA VALUES (?,SYSDATE,?)";

    public static void CrearEvento(Evento e) throws Exception {
        try {
            int cPart = e.getCantParticipantes();
            double vTotal = e.getCostoTotal();
            int subs = e.getPorcSubsidio();
            double copago = (vTotal / cPart);
            copago -= (vTotal / cPart) * (subs / 100);
            Proveedor p = e.getProveedor();

            PreparedStatement ps = ConexionDB.getConexion().prepareStatement(cs_INSERCION_EVENTO);
            ps.setDouble(1, Double.parseDouble(e.getCodigo()));
            ps.setInt(2, Integer.parseInt(e.getTipo()));
            ps.setDouble(3, Double.parseDouble(p.getNit()));
            ps.setString(4, e.getNombre());
            ps.setString(5, e.getObservacion());
            ps.setString(6, e.getLugar());
            ps.setString(7, e.getfInicio());
            ps.setString(8, e.getfFin());
            ps.setString(9, e.getfLimite());
            ps.setInt(10, cPart);
            ps.setInt(11, cPart);
            ps.setDouble(12, vTotal);
            ps.setInt(13, subs);
            ps.setDouble(14, copago);
            ps.setString(15, "A");

            int r = ps.executeUpdate();
            if (r == -1) {
                ConexionDB.getConexion().rollback();
            } else {
                ConexionDB.getConexion().commit();
            }
        } catch (Exception ex) {
            ConexionDB.getConexion().rollback();
            throw new Exception("Error en la insercion" + ex.getMessage());
        }
    }

    public static ArrayList<String> ConsultarEventoActivo(String codigo, String nombre) throws Exception {
        try {
            ArrayList<String> lista = new ArrayList<String>();
            ResultSet rs = null;
            PreparedStatement ps = ConexionDB.getConexion().prepareStatement(cs_CONSULTA_EVENTOS_ACTIVOS_CN);
            ps.setDouble(1, Double.parseDouble(codigo));
            ps.setString(2, nombre);
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(rs.getString("Codigo") + " - " + rs.getString("Nombre"));
            }
            if (lista.isEmpty()) {
                lista = null;
            }
            return lista;
        } catch (Exception ex) {
            throw new Exception("Error en la consulta, puede que no exista el registro.");
        }
    }

    public static ArrayList<String> ConsultarEventoActivo(String codigo) throws Exception {
        try {
            ArrayList<String> lista = new ArrayList<String>();
            ResultSet rs = null;
            PreparedStatement ps = ConexionDB.getConexion().prepareStatement(cs_CONSULTA_EVENTOS_ACTIVOS_C);
            ps.setDouble(1, Double.parseDouble(codigo));
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(rs.getString("Codigo") + " - " + rs.getString("Nombre"));
            }
            if (lista.isEmpty()) {
                lista = null;
            }
            return lista;
        } catch (Exception ex) {
            throw new Exception("Error en la consulta, puede que no exista el registro.");
        }
    }

    public static ArrayList<String> ConsultarEventoActivo2(String nombre) throws Exception {
        try {
            ArrayList<String> lista = new ArrayList<String>();
            ResultSet rs = null;
            PreparedStatement ps = ConexionDB.getConexion().prepareStatement(cs_CONSULTA_EVENTOS_ACTIVOS_N);
            ps.setString(1, nombre);
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(rs.getString("Codigo") + " - " + rs.getString("Nombre"));
            }
            if (lista.isEmpty()) {
                lista = null;
            }
            return lista;

        } catch (Exception ex) {
            throw new Exception("Error en la consulta, puede que no exista el registro.");
        }
    }

    public static ArrayList<String> ConsultarEventosActivos() throws Exception {
        try {
            ArrayList<String> lista = new ArrayList<String>();
            ResultSet rs = null;
            PreparedStatement ps = ConexionDB.getConexion().prepareStatement(cs_CONSULTA_EVENTOS_ACTIVOS);
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(rs.getString("Codigo") + " - " + rs.getString("Nombre"));
            }
            if (lista.isEmpty()) {
                lista = null;
            }
            return lista;
        } catch (Exception ex) {
            throw new Exception("Error en la consulta, puede que no existan registros.");
        }
    }

    public static Evento ConsultarEventoDetalle(String codigo) throws Exception {
        ResultSet rs = null;
        PreparedStatement ps;
        Evento e = null;
        try {
            ps = ConexionDB.getConexion().prepareStatement(cs_CONSULTA_EVENTO_DETALLE);
            ps.setDouble(1, Double.parseDouble(codigo));
            rs = ps.executeQuery();
            if (rs.next()) {
                e = getEvento(rs);
            }
        } catch (Exception ex) {
            throw new Exception("Error en la consulta - " + ex.getMessage());
        }
        return e;
    }

    private static Evento getEvento(ResultSet rs) throws SQLException {
        Evento prototipo = new Evento();
        prototipo.setCodigo(rs.getString("CODIGO"));
        prototipo.setTipo(rs.getString("TIPO"));
        Proveedor p;
        p = new Proveedor((rs.getString("PROVEE")), (rs.getString("PRO_NOM")));
        prototipo.setProveedor(p);
        prototipo.setNombre(rs.getString("NOMBRE"));
        prototipo.setObservacion(rs.getString("OBSER"));
        prototipo.setLugar(rs.getString("LUGAR"));
        prototipo.setfInicio(rs.getString("FINICIO"));
        prototipo.setfFin(rs.getString("FFIN"));
        prototipo.setfLimite(rs.getString("FLIM"));
        prototipo.setCantParticipantes(rs.getInt("PARTICI"));
        prototipo.setAsistentes(rs.getInt("ASISTEN"));
        prototipo.setCostoTotal(rs.getDouble("COSTO"));
        prototipo.setPorcSubsidio(rs.getInt("PORCSUB"));
        prototipo.setCopago(rs.getDouble("COPAGO"));
        prototipo.setEstado(rs.getString("ESTADO"));

        return prototipo;
    }

    public static String ModificarEvento(Evento e) throws Exception {
        try {
            int cPart = e.getCantParticipantes();
            double vTotal = e.getCostoTotal();
            int subs = e.getPorcSubsidio();
            Proveedor p = e.getProveedor();

            PreparedStatement ps = ConexionDB.getConexion().prepareStatement(cs_MODIFICACION_EVENTO);

            ps.setString(1, e.getObservacion());
            ps.setString(2, e.getfLimite());
            ps.setInt(3, cPart);
            ps.setInt(4, subs);
            ps.setDouble(5, e.getCopago());
            ps.setDouble(6, Double.parseDouble(e.getCodigo()));
            int r = ps.executeUpdate();
            if (r == -1) {
                ConexionDB.getConexion().rollback();
            } else {
                ConexionDB.getConexion().commit();
            }

            return "Modificacion Exitosa";
        } catch (Exception ex) {
            ConexionDB.getConexion().rollback();
            throw new Exception("Error en la modificacion" + ex.getMessage());
        }
    }

    public static ArrayList<String> ConsultarEventosCaducados() throws Exception {
        try {
            ArrayList<String> lista = new ArrayList<String>();
            ResultSet rs = null;
            PreparedStatement ps = ConexionDB.getConexion().prepareStatement(cs_CONSULTA_EVENTOS_CADUCADOS);
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(rs.getString("Codigo") + " - " + rs.getString("Nombre"));
            }
            if (lista.isEmpty()) {
                lista = null;
            }
            return lista;
        } catch (Exception ex) {
            throw new Exception("Error en la consulta, puede que no existan registros.");
        }
    }

    public static ArrayList<String> ConsultarEventosCaducados(String codigo, String nombre) throws Exception {
        try {
            ArrayList<String> lista = new ArrayList<String>();
            ResultSet rs = null;
            PreparedStatement ps = ConexionDB.getConexion().prepareStatement(cs_CONSULTA_EVENTOS_CADUCADOS_CN);
            ps.setDouble(1, Double.parseDouble(codigo));
            ps.setString(2, nombre);
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(rs.getString("Codigo") + " - " + rs.getString("Nombre"));
            }
            if (lista.isEmpty()) {
                lista = null;
            }
            return lista;
        } catch (Exception ex) {
            throw new Exception("Error en la consulta, puede que no existan registros.");
        }
    }

    public static ArrayList<String> ConsultarEventoCaducado(String codigo) throws Exception {
        try {
            ArrayList<String> lista = new ArrayList<String>();
            ResultSet rs = null;
            PreparedStatement ps = ConexionDB.getConexion().prepareStatement(cs_CONSULTA_EVENTOS_CADUCADOS_C);
            ps.setDouble(1, Double.parseDouble(codigo));
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(rs.getString("Codigo") + " - " + rs.getString("Nombre"));
            }
            if (lista.isEmpty()) {
                lista = null;
            }
            return lista;
        } catch (Exception ex) {
            throw new Exception("Error en la consulta, puede que no existan registros.");
        }
    }

    public static ArrayList<String> ConsultarEventoCaducado2(String nombre) throws Exception {
        try {
            ArrayList<String> lista = new ArrayList<String>();
            ResultSet rs = null;
            PreparedStatement ps = ConexionDB.getConexion().prepareStatement(cs_CONSULTA_EVENTOS_CADUCADOS_N);
            ps.setString(1, nombre);
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(rs.getString("Codigo") + " - " + rs.getString("Nombre"));
            }
            if (lista.isEmpty()) {
                lista = null;
            }
            return lista;
        } catch (Exception ex) {
            throw new Exception("Error en la consulta, puede que no existan registros.");
        }
    }

    public static void CerrarEvento(Evento e) throws Exception {
        try {
            int r = 0;
            PreparedStatement ps = ConexionDB.getConexion().prepareStatement(cs_CIERRE_EVENTO);
            ps.setDouble(1, Double.parseDouble(e.getCodigo()));
            r = ps.executeUpdate();
            if (r == -1) {
                ConexionDB.getConexion().rollback();
                throw new Exception("");
            }
        } catch (Exception ex) {
            ConexionDB.getConexion().rollback();
            throw new Exception("Error en la actualizacion.");
        }

    }

    public static int ObtenerTotalAsistentes(Evento e) throws Exception {
        int total = -1;
        try {
            ResultSet rs;
            PreparedStatement ps = ConexionDB.getConexion().prepareStatement(cs_ASISTENCIA_EVENTO);
            ps.setDouble(1, Double.parseDouble(e.getCodigo()));
            rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (Exception ex) {
            throw new Exception("Error en la consulta.");
        }
        return total;
    }

    public static void GenerarPagoProveedor(Evento e) throws Exception {
        try {
            int r = 0;
            PreparedStatement ps = ConexionDB.getConexion().prepareStatement(cs_GENERAR_PAGO);
            ps.setDouble(1, Double.parseDouble(e.getCodigo()));
            ps.setDouble(2, e.getCostoTotal());
            r = ps.executeUpdate();
            if (r == -1) {
                ConexionDB.getConexion().rollback();
                throw new Exception("");
            }
            ConexionDB.getConexion().commit();
        } catch (Exception ex) {
            ConexionDB.getConexion().rollback();
            throw new Exception("Error en la insercion. " + ex.getMessage());
        }
    }
}
