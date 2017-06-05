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
import modelo.Familiar;
import modelo.Usuario;

/**
 *
 * @author R2D2
 */
public class MultivalorDAO {

    private static String cs_CONSULTA_TIPOS_EVENTOS = "SELECT N_TIPO FROM TIPO";
    private static String cs_CONSULTA_TIPO_EVENTO = "SELECT K_CODIGO Codigo FROM TIPO WHERE N_TIPO = ?";
    private static String cs_CONSULTA_PROVEEDORES = "SELECT K_NITPROVEEDOR ID, N_NOMBRE Nombre FROM PROVEEDOR";
    private static String cs_CONSULTA_POSIBLES_ASISTENTES = "SELECT CASE FAM.I_TIPOID WHEN 'C' THEN 'CC' WHEN 'T' THEN 'TI' END AS TIPO_ID, POS_FAM.ID_FAMILIAR IDF, FAM.N_NOMBRE NOMBRE, "
            + "FAM.N_APELLIDO APELLIDO, FAM.PARENTEZCO PARENTEZCO FROM FAMILIAR FAM,"
            + "(SELECT FAMILIAR.K_IDFAMILIAR ID_FAMILIAR, FAMILIAR.K_IDUSUARIO ID_ASOCIADO FROM FAMILIAR WHERE K_IDUSUARIO = ? MINUS "
            + "(SELECT DETALLE_INSCRIPCION.K_FAMILIAR ID_FAMILIAR, DETALLE_INSCRIPCION.K_ASOCIADO FROM DETALLE_INSCRIPCION, INSCRIPCION WHERE "
            + "DETALLE_INSCRIPCION.K_INSCRIPCION = INSCRIPCION.K_CODIGO AND INSCRIPCION.K_CODIGOEVENTO = ? )) POS_FAM "
            + "WHERE FAM.K_IDUSUARIO = POS_FAM.ID_ASOCIADO AND FAM.K_IDFAMILIAR = POS_FAM.ID_FAMILIAR";

    public static ArrayList<String> ConsultarTiposEventos() throws Exception {
        try {
            ArrayList<String> lista = new ArrayList<String>();
            ResultSet rs;
            PreparedStatement ps = ConexionDB.getConexion().prepareStatement(cs_CONSULTA_TIPOS_EVENTOS);
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(rs.getString("N_TIPO"));
            }

            return lista;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public static ArrayList<String> ConsultarProveedores() throws Exception {
        try {
            ArrayList<String> lista = new ArrayList<String>();
            ResultSet rs;
            PreparedStatement ps = ConexionDB.getConexion().prepareStatement(cs_CONSULTA_PROVEEDORES);
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(rs.getString("ID") + " - " + rs.getString("Nombre"));
            }

            return lista;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public static int consultarTipoEvento(String tipo) throws Exception {
        try {
            int tipoEvento = -1;
            ResultSet rs;
            PreparedStatement ps = ConexionDB.getConexion().prepareStatement(cs_CONSULTA_TIPO_EVENTO);
            ps.setString(1, tipo);
            rs = ps.executeQuery();
            if (rs.next()) {
                tipoEvento = rs.getInt("Codigo");
            }
            return tipoEvento;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    

    

}
