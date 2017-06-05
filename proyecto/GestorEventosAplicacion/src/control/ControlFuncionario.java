/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import control.DAO.EventoDAO;
import control.DAO.FamiliarDAO;
import control.DAO.InscripcionDAO;
import control.DAO.LoginDAO;
import control.DAO.MultivalorDAO;
import java.util.ArrayList;
import modelo.Familiar;
import modelo.Usuario;
import modelo.Evento;

/**
 * @author usuario
 */
public class ControlFuncionario {

    public String CrearEvento(Evento e) throws Exception {
        int tipo = MultivalorDAO.consultarTipoEvento(e.getTipo());
        e.setTipo("" + tipo);
        EventoDAO.CrearEvento(e);
        return "El evento se ha creado satisfactoriamente";
    }

    public String InsertarAsociado(Usuario user, ArrayList<Familiar> familia) throws Exception {
        LoginDAO.InsertarUsuario(user);
        FamiliarDAO.InsertarFamiliares(familia, user);
        return "";
    }

    public String ModificarEvento(Evento e) throws Exception {
        String m = EventoDAO.ModificarEvento(e);
        InscripcionDAO.ModificarCopago(e);
        return m;
    }

    public String GenerarEstadisticas(Evento e) throws Exception {
        if (e.getEstado() == "Activo") {
            EventoDAO.CerrarEvento(e);
            EventoDAO.GenerarPagoProveedor(e);
        }
        double t_Recaudado = EventoDAO.ObtenerTotalAsistentes(e);
        t_Recaudado *= e.getCopago();
        String cadena = "Codigo: " + e.getCodigo() + "\n"
                + "Tipo Evento: " + e.getTipo() + "\n"
                + "Nombre Evento: " + e.getNombre() + "\n"
                + "Observacion: " + e.getObservacion() + "\n"
                + "Lugar: " + e.getLugar() + "\n"
                + "Fecha de Inicio: " + e.getfInicio() + "\n"
                + "Fecha de Finalizacion: " + e.getfFin() + "\n"
                + "Fecha Limite de Inscripcion: " + e.getfLimite() + "\n"
                + "Cant Max Participantes: " + e.getCantParticipantes() + "\n"
                + "Cant Participantes: " + e.getAsistentes() + "\n"
                + "Valor Copago Unitario: " + e.getCopago() + "\n"
                + "Proveedor: " + e.getProveedor().getNombre() + "\n"
                + "Costo Total: " + String.valueOf(e.getCostoTotal()) + "\n"
                + "% Subsidio: " + e.getPorcSubsidio() + "%\n"
                + "ESTADO: " + e.getEstado() + "\n\n"
                + "Total Recaudado: "+ t_Recaudado +"\n\n"
                + "Total a pagar la entidad: " + (e.getCostoTotal()-t_Recaudado);

        return cadena;
    }

}
