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
import modelo.Familiar;
import modelo.Inscripcion;

/**
 *
 * @author R2D2
 */
public class InscripcionDAO {

    private static String cs_CONSULTAR_CONSECUTIVO = "SELECT COUNT(*) CONTADOR FROM INSCRIPCION WHERE K_CODIGOEVENTO = ?";
    private static String cs_CONSULTAR_CONSECUTIVO_PAGO = "SELECT COUNT(*) CONTADOR FROM COMPROBANTEPAGO ";
    private static String cs_INSERCION_INSCRIPCION = "INSERT INTO INSCRIPCION VALUES(?,?,?,?,?,?,?,?)";
    private static String cs_INSERCION_INSCRIPCION_DETALLE = "INSERT INTO DETALLE_INSCRIPCION VALUES(?,?,?,?)";
    private static String cs_CANCELAR_INSCRIPCION = "UPDATE INSCRIPCION SET I_ESTADO='C' WHERE K_CODIGO = ?";
    private static String cs_CONSULTAR_INSCRIPCIONES_ASOCIADO = "SELECT INS.K_CODIGO CODIGO,  INS.K_CODIGOEVENTO COD_EVT, INS.Q_CANTASISTENTES C_ASIST, INS.V_VALORTOTAL V_TOTAL, "
            + "CASE INS.I_ESTADO WHEN 'I' THEN 'INCOMPLETO' WHEN 'P' THEN 'PENDIENTE' END AS ESTADO, CASE INS.I_TIPOPAGO "
            + "WHEN 'V' THEN 'Efectivo' WHEN 'N' THEN 'Nomina' END AS T_PAGO, INS.Q_CANTCUOTAS C_CUOTAS "
            + "FROM INSCRIPCION INS, EVENTO EVT WHERE INS.K_IDASOCIADO = ? AND EVT.K_CODIGO = INS.K_CODIGOEVENTO AND "
            + "EVT.I_ESTADO='A' AND INS.I_ESTADO!='C' AND EVT.F_INICIO > SYSDATE";
    private static String cs_CONSULTAR_INSCRIPCIONES = "SELECT INS.K_CODIGO CODIGO,  INS.K_CODIGOEVENTO COD_EVT, "
            + "INS.K_IDASOCIADO ID_ASO, INS.Q_CANTASISTENTES C_ASIST, INS.V_VALORTOTAL V_TOTAL, "
            + "CASE INS.I_ESTADO WHEN 'I' THEN 'INCOMPLETO' WHEN 'P' THEN 'PENDIENTE' END AS ESTADO, CASE INS.I_TIPOPAGO "
            + "WHEN 'V' THEN 'Efectivo' WHEN 'N' THEN 'Nomina' END AS T_PAGO, INS.Q_CANTCUOTAS C_CUOTAS "
            + "FROM INSCRIPCION INS, EVENTO EVT WHERE EVT.K_CODIGO = INS.K_CODIGOEVENTO AND EVT.I_ESTADO='A' AND INS.I_ESTADO !='C' AND EVT.F_INICIO > SYSDATE";

    private static String cs_CONSULTAR_INSCRIPCIONES_PENDIENTES = "SELECT INS.K_CODIGO CODIGO,  INS.K_CODIGOEVENTO COD_EVT, "
            + "INS.K_IDASOCIADO ID_ASO, INS.Q_CANTASISTENTES C_ASIST, INS.V_VALORTOTAL V_TOTAL, "
            + "CASE INS.I_ESTADO WHEN 'I' THEN 'INCOMPLETO' WHEN 'P' THEN 'PENDIENTE' END AS ESTADO, CASE INS.I_TIPOPAGO "
            + "WHEN 'V' THEN 'Efectivo' WHEN 'N' THEN 'Nomina' END AS T_PAGO, INS.Q_CANTCUOTAS C_CUOTAS "
            + "FROM INSCRIPCION INS, EVENTO EVT WHERE EVT.K_CODIGO = INS.K_CODIGOEVENTO AND EVT.I_ESTADO='A' AND INS.I_ESTADO NOT IN ('C','P') AND EVT.F_INICIO > SYSDATE";

    private static String cs_CONSULTAR_DETALLE_INSCRIPCION = "SELECT DET.K_FAMILIAR F_ID, FAM.N_NOMBRE NOMBRE, "
            + "FAM.N_APELLIDO APELLIDO, FAM.I_TIPOID T_ID, FAM.PARENTEZCO PARENTEZCO FROM DETALLE_INSCRIPCION DET, "
            + "FAMILIAR FAM WHERE DET.K_INSCRIPCION = ? AND DET.K_ASOCIADO = FAM.K_IDUSUARIO AND DET.K_FAMILIAR = FAM.K_IDFAMILIAR";

    private static String cs_MODIFICACION_COPAGO = "UPDATE INSCRIPCION INS SET INS.V_VALORTOTAL = (SELECT EVT.V_VALORCOPAGO "
            + "FROM EVENTO EVT WHERE EVT.K_CODIGO = ? )*INS.Q_CANTASISTENTES WHERE INS.I_ESTADO = 'I'";
    private static String cs_INSERCION_COMPROBANTE_PAGO = "INSERT INTO COMPROBANTEPAGO VALUES (?,?,SYSDATE,?)";
    private static String cs_INSCRIPCION_PAGA = "UPDATE INSCRIPCION SET I_ESTADO='P' WHERE K_CODIGO = ?";

    public static int ConsultarConsecutivo(String codigo) throws Exception {
        ResultSet rs = null;
        PreparedStatement ps;
        int c = 0;
        try {
            ps = ConexionDB.getConexion().prepareStatement(cs_CONSULTAR_CONSECUTIVO);
            ps.setDouble(1, Double.parseDouble(codigo));
            rs = ps.executeQuery();
            if (rs.next()) {
                c = rs.getInt(1);
            }
        } catch (Exception ex) {
            throw new Exception("Error en la consulta - " + ex.getMessage());
        }
        return c;
    }

    public static void InsertarInscripcion(String evento, Inscripcion inscripcion) throws Exception {
        try {
            PreparedStatement ps = ConexionDB.getConexion().prepareStatement(cs_INSERCION_INSCRIPCION);
            ps.setInt(1, inscripcion.getCodigo());
            ps.setDouble(2, Double.parseDouble(evento));
            ps.setDouble(3, Double.parseDouble(inscripcion.getUser().getId()));
            ps.setInt(4, inscripcion.getAsistentes().size());
            ps.setString(5, inscripcion.getEstado());
            ps.setDouble(6, inscripcion.getValorTotal());
            ps.setString(7, inscripcion.getTipoPago());
            ps.setInt(8, inscripcion.getCantCuotas());
            int rs = ps.executeUpdate();
            if (rs == -1) {
                ConexionDB.getConexion().rollback();
                throw new Exception("No se ha logrado insertar la inscripcion");
            }
        } catch (Exception ex) {
            ConexionDB.getConexion().rollback();
            throw new Exception("Error: " + ex.getMessage());

        }
        ConexionDB.getConexion().commit();

    }

    public static void InsertarInscripcionDetalle(Inscripcion inscripcion, Familiar asistente, int consecutivo) throws Exception {
        try {
            PreparedStatement ps = ConexionDB.getConexion().prepareStatement(cs_INSERCION_INSCRIPCION_DETALLE);
            ps.setInt(1, inscripcion.getCodigo());
            ps.setInt(2, consecutivo);
            ps.setDouble(3, Double.parseDouble(asistente.getId()));
            ps.setDouble(4, Double.parseDouble(inscripcion.getUser().getId()));

            int rs = ps.executeUpdate();
            if (rs == -1) {
                ConexionDB.getConexion().rollback();
                throw new Exception("No se ha logrado insertar la inscripcion");
            }
        } catch (Exception ex) {
            ConexionDB.getConexion().rollback();
            throw new Exception("Error: " + ex.getMessage());

        }
        ConexionDB.getConexion().commit();
    }

    public static ArrayList<Inscripcion> ConsultarInscripcionesAsociado(String id) throws Exception {
        ResultSet rs = null;
        PreparedStatement ps;
        ArrayList<Inscripcion> lista = new ArrayList<Inscripcion>();
        Inscripcion ins;
        try {
            ps = ConexionDB.getConexion().prepareStatement(cs_CONSULTAR_INSCRIPCIONES_ASOCIADO);
            ps.setDouble(1, Double.parseDouble(id));
            rs = ps.executeQuery();
            while (rs.next()) {
                ins = new Inscripcion();
                ins.setUser(id);
                getInscripcion(ins, rs);
                lista.add(ins);
            }
        } catch (Exception ex) {
            throw new Exception("Error en la consulta - " + ex.getMessage());
        }
        if (lista.isEmpty()) {
            lista = null;
        }
        return lista;
    }

    private static void getInscripcion(Inscripcion ins, ResultSet rs) throws SQLException {
        ins.setCodigo(rs.getInt("CODIGO"));
        ins.setEstado(rs.getString("ESTADO"));
        ins.setTipoPago(rs.getString("T_PAGO"));
        ins.setCantCuotas(rs.getInt("C_CUOTAS"));
        ins.setValorTotal(rs.getDouble("V_TOTAL"));
    }

    public static ArrayList<Familiar> ConsultarInscripcionesDetalle(Inscripcion i) throws Exception {
        ResultSet rs = null;
        PreparedStatement ps;
        ArrayList<Familiar> asistentes = new ArrayList<Familiar>();
        try {
            ps = ConexionDB.getConexion().prepareStatement(cs_CONSULTAR_DETALLE_INSCRIPCION);
            ps.setDouble(1, i.getCodigo());
            rs = ps.executeQuery();
            while (rs.next()) {
                Familiar f = new Familiar();
                getFamiliar(f, rs);
                asistentes.add(f);
            }
        } catch (Exception ex) {
            throw new Exception("Error en la consulta - " + ex.getMessage());
        }
        return asistentes;
    }

    private static void getFamiliar(Familiar f, ResultSet rs) throws SQLException {
        f.setId(rs.getString("F_ID"));
        f.setNombre(rs.getString("NOMBRE"));
        f.setApellido(rs.getString("APELLIDO"));
        f.setTipo(rs.getString("T_ID"));
        f.setParentezco(rs.getString("PARENTEZCO"));
    }

    public static ArrayList<Inscripcion> ConsultarInscripciones() throws Exception {
        ResultSet rs = null;
        PreparedStatement ps;
        ArrayList<Inscripcion> lista = new ArrayList<Inscripcion>();
        Inscripcion ins;
        try {
            ps = ConexionDB.getConexion().prepareStatement(cs_CONSULTAR_INSCRIPCIONES);
            rs = ps.executeQuery();
            while (rs.next()) {
                ins = new Inscripcion();
                ins.setUser(rs.getString("ID_ASO"));
                getInscripcion(ins, rs);
                lista.add(ins);
            }
        } catch (Exception ex) {
            throw new Exception("Error en la consulta - " + ex.getMessage());
        }
        if (lista.isEmpty()) {
            lista = null;
        }
        return lista;
    }

    public static void CancelarInscripcion(Inscripcion i) throws Exception {
        PreparedStatement ps;
        try {
            int r = 0;
            ps = ConexionDB.getConexion().prepareStatement(cs_CANCELAR_INSCRIPCION);
            ps.setDouble(1, i.getCodigo());
            r = ps.executeUpdate();
            if (r == -1) {
                ConexionDB.getConexion().rollback();
            } else {
                ConexionDB.getConexion().commit();
            }
        } catch (Exception ex) {
            ConexionDB.getConexion().rollback();
            throw new Exception("Error en la actualizacion - " + ex.getMessage());
        }
    }

    public static void ModificarCopago(Evento e) throws Exception {
        try {
            int r;
            PreparedStatement ps = ConexionDB.getConexion().prepareStatement(cs_MODIFICACION_COPAGO);
            ps.setDouble(1, Double.parseDouble(e.getCodigo()));
            r = ps.executeUpdate();
            if (r == -1) {
                ConexionDB.getConexion().rollback();
            } else {
                ConexionDB.getConexion().commit();
            }
        } catch (Exception ex) {
            ConexionDB.getConexion().rollback();
            throw new Exception("Error en la actualizacion - " + ex.getMessage());

        }
    }

    public static ArrayList<Inscripcion> ConsultarInscripcionesPendientes() throws Exception {
        ResultSet rs = null;
        PreparedStatement ps;
        ArrayList<Inscripcion> lista = new ArrayList<Inscripcion>();
        Inscripcion ins;
        try {
            ps = ConexionDB.getConexion().prepareStatement(cs_CONSULTAR_INSCRIPCIONES_PENDIENTES);
            rs = ps.executeQuery();
            while (rs.next()) {
                ins = new Inscripcion();
                ins.setUser(rs.getString("ID_ASO"));
                getInscripcion(ins, rs);
                lista.add(ins);
            }
        } catch (Exception ex) {
            throw new Exception("Error en la consulta - " + ex.getMessage());
        }
        if (lista.isEmpty()) {
            lista = null;
        }
        return lista;
    }

    public static void GenerarPago(Inscripcion ins, int consecutivo) throws Exception {
        try {
            PreparedStatement ps = ConexionDB.getConexion().prepareStatement(cs_INSERCION_COMPROBANTE_PAGO);
            ps.setInt(1, consecutivo);
            ps.setInt(2, ins.getCodigo());
            ps.setString(3, "Efectivo");

            int rs = ps.executeUpdate();
            if (rs == -1) {
                ConexionDB.getConexion().rollback();
                throw new Exception("No se ha logrado insertar la inscripcion");
            }
        } catch (Exception ex) {
            ConexionDB.getConexion().rollback();
            throw new Exception("Error: " + ex.getMessage());
        }
    }

    public static int ObtenerConsecutivoPago() throws Exception {
        ResultSet rs = null;
        PreparedStatement ps;
        int c = 0;
        try {
            ps = ConexionDB.getConexion().prepareStatement(cs_CONSULTAR_CONSECUTIVO_PAGO);
            rs = ps.executeQuery();
            if (rs.next()) {
                c = rs.getInt(1);
            }
        } catch (Exception ex) {
            throw new Exception("Error en la consulta - " + ex.getMessage());
        }
        return c;
    }
    
    public static void CambiarEstadoInscripcion(Inscripcion i) throws Exception {
        PreparedStatement ps;
        try {
            int r = 0;
            ps = ConexionDB.getConexion().prepareStatement(cs_INSCRIPCION_PAGA);
            ps.setDouble(1, i.getCodigo());
            r = ps.executeUpdate();
            if (r == -1) {
                ConexionDB.getConexion().rollback();
            } else {
                ConexionDB.getConexion().commit();
            }
        } catch (Exception ex) {
            ConexionDB.getConexion().rollback();
            throw new Exception("Error en la actualizacion - " + ex.getMessage());
        }
    }
}
