/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import control.DAO.EventoDAO;
import control.DAO.InscripcionDAO;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Evento;
import modelo.Familiar;
import modelo.Inscripcion;

/**
 *
 * @author usuario
 */
public class ControlAsociado {

    public Evento ConsultarEventoDetalle(String codigo) throws Exception {
        return EventoDAO.ConsultarEventoDetalle(codigo);
    }

    public String InsertarInscripcion(String evento, Inscripcion inscripcion) throws Exception {

        try {
            int consecutivo = InscripcionDAO.ConsultarConsecutivo(evento);
            inscripcion.setCodigo(Integer.parseInt(evento + consecutivo));
            InscripcionDAO.InsertarInscripcion(evento, inscripcion);
            int i = 1;
            for (Familiar f : inscripcion.getAsistentes()) {
                InscripcionDAO.InsertarInscripcionDetalle(inscripcion, f, i);
                i++;
            }

            return evento + "-" + consecutivo;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public ArrayList<Inscripcion> ConsultarInscripcionesAsociado(String id) throws Exception {
        ArrayList<Inscripcion> lista;
        lista = InscripcionDAO.ConsultarInscripcionesAsociado(id);
        if (lista != null) {
            for (Inscripcion i : lista) {
                i.setAsistentes(ConsultarDetalleInscripcion(i));
            }
        }
        return lista;
    }

    private ArrayList<Familiar> ConsultarDetalleInscripcion(Inscripcion i) throws Exception {
        return InscripcionDAO.ConsultarInscripcionesDetalle(i);
    }

    public ArrayList<Inscripcion> ConsultarTodasInscripciones() throws Exception {
        ArrayList<Inscripcion> lista;
        lista = InscripcionDAO.ConsultarInscripciones();
        for (Inscripcion i : lista) {
            i.setAsistentes(ConsultarDetalleInscripcion(i));
        }
        return lista;
    }

    public void CancelarInscripcion(Inscripcion i) throws Exception {
        InscripcionDAO.CancelarInscripcion(i);
    }

}
