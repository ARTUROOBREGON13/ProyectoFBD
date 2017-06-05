/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import control.DAO.LoginDAO;
import modelo.Usuario;

/**
 *
 * @author usuario
 */
public class ControlLogin {

    public Usuario Ingresar(String user, String password) throws Exception {
        return LoginDAO.ConsultarUsuario(user, password);
    }

}
