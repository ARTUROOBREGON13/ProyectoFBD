/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import control.DAO.EventoDAO;
import control.DAO.FamiliarDAO;
import control.DAO.MultivalorDAO;
import java.util.ArrayList;
import modelo.Familiar;

/**
 * @author R2D2
 */
public class ControlMultivalor {

    public ArrayList<String> consultarEventosActivos() throws Exception {
        return EventoDAO.ConsultarEventosActivos();
    }

    public ArrayList<String> consultarEventoActivo(String codigo, String nombre) throws Exception {
        ArrayList<String> lista = null;
        if (codigo.equals("") && !"".equals(nombre)) {
            lista = EventoDAO.ConsultarEventoActivo2(nombre);
        } else if (nombre.equals("") && !codigo.equals("")) {
            lista = EventoDAO.ConsultarEventoActivo(codigo);
        } else if (nombre.equals("") && codigo.equals("")) {
            lista = consultarEventosActivos();
        } else {
            lista = EventoDAO.ConsultarEventoActivo(codigo, nombre);
        }
        if (lista != null) {
            return lista;
        } else {
            throw new Exception("No existen eventos.");
        }

    }

    public ArrayList<String> consultarProveedores() throws Exception {
        return MultivalorDAO.ConsultarProveedores();
    }

    public ArrayList<String> consultarTiposEventos() throws Exception {
        return MultivalorDAO.ConsultarTiposEventos();
    }

    public ArrayList<Familiar> consultarFamiliares(String asociado, String evento) throws Exception {
        return FamiliarDAO.ConsultarPosiblesAsistentes(asociado, evento);
    }

    public ArrayList<String> consultarEventoCaducado(String codigo, String nombre) throws Exception {
        ArrayList<String> lista = null;
        if (codigo==null && nombre!=null) {
            lista = EventoDAO.ConsultarEventoCaducado2(nombre);
        } else if (codigo!=null && nombre==null) {
            lista = EventoDAO.ConsultarEventoCaducado(codigo);
        } else if (codigo==null && nombre==null) {
            lista = consultarEventosCaducados();
        } else {
            lista = EventoDAO.ConsultarEventosCaducados(codigo, nombre);
        }
        if (lista != null) {
            return lista;
        } else {
            throw new Exception("No existen eventos.");
        }
    }

    public ArrayList<String> consultarEventosCaducados() throws Exception {
        return EventoDAO.ConsultarEventosCaducados();
    }
}
