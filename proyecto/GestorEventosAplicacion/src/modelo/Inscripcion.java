/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;

/**
 * @author usuario
 */
public class Inscripcion {
   private int codigo;
   private Usuario user;
   private ArrayList<Familiar> asistentes;
   private String estado;
   private String tipoPago;
   private double valorTotal;
   private int cantCuotas;

    /**
     * @return the codigo
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the user
     */
    public Usuario getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = new Usuario(user);
    }

    /**
     * @return the asistentes
     */
    public ArrayList<Familiar> getAsistentes() {
        return asistentes;
    }

    /**
     * @param asistentes the asistentes to set
     */
    public void setAsistentes(ArrayList<Familiar> asistentes) {
        this.asistentes = asistentes;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @return the valorTotal
     */
    public double getValorTotal() {
        return valorTotal;
    }

    /**
     * @param valorTotal the valorTotal to set
     */
    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    /**
     * @return the cantCuotas
     */
    public int getCantCuotas() {
        return cantCuotas;
    }

    /**
     * @param cantCuotas the cantCuotas to set
     */
    public void setCantCuotas(int cantCuotas) {
        this.cantCuotas = cantCuotas;
    }

    /**
     * @return the tipoPago
     */
    public String getTipoPago() {
        return tipoPago;
    }

    /**
     * @param tipoPago the tipoPago to set
     */
    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }
   
   
}
