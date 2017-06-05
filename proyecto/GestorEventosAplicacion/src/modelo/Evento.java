/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.Date;

/**
 * @author usuario
 */
public class Evento {

    private String codigo;
    private String tipo;
    private Proveedor proveedor;
    private String nombre;
    private String lugar;
    private String fInicio;
    private String fFin;
    private String fLimite;
    private int cantParticipantes;
    private int asistentes;
    private double costoTotal;
    private int porcSubsidio;
    private double copago;
    private String estado;
    private String observacion;

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the proveedor
     */
    public Proveedor getProveedor() {
        return proveedor;
    }

    /**
     * @param proveedor the proveedor to set
     */
    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public void setProveedor(String proveedor) {
        String[] s = proveedor.split(" - ");
        this.proveedor = new Proveedor(s[0], s[1]);
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the lugar
     */
    public String getLugar() {
        return lugar;
    }

    /**
     * @param lugar the lugar to set
     */
    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    /**
     * @return the fInicio
     */
    public String getfInicio() {
        return fInicio;
    }

    /**
     * @param fInicio the fInicio to set
     */
    public void setfInicio(String fInicio) {
        this.fInicio = fInicio;
    }

    /**
     * @return the fFin
     */
    public String getfFin() {
        return fFin;
    }

    /**
     * @param fFin the fFin to set
     */
    public void setfFin(String fFin) {
        this.fFin = fFin;
    }

    /**
     * @return the fLimite
     */
    public String getfLimite() {
        return fLimite;
    }

    /**
     * @param fLimite the fLimite to set
     */
    public void setfLimite(String fLimite) {
        this.fLimite = fLimite;
    }

    /**
     * @return the cantParticipantes
     */
    public int getCantParticipantes() {
        return cantParticipantes;
    }

    /**
     * @param cantParticipantes the cantParticipantes to set
     */
    public void setCantParticipantes(int cantParticipantes) {
        this.cantParticipantes = cantParticipantes;
    }

    public void setCantParticipantes(String cantParticipantes) {
        this.cantParticipantes = Integer.parseInt(cantParticipantes);
    }

    /**
     * @return the asistentes
     */
    public int getAsistentes() {
        return asistentes;
    }

    /**
     * @param asistentes the asistentes to set
     */
    public void setAsistentes(int asistentes) {
        this.asistentes = asistentes;
    }

    /**
     * @return the costoTotal
     */
    public double getCostoTotal() {
        return costoTotal;
    }

    /**
     * @param costoTotal the costoTotal to set
     */
    public void setCostoTotal(double costoTotal) {
        this.costoTotal = costoTotal;
    }

    public void setCostoTotal(String costoTotal) {
        this.costoTotal = Double.parseDouble(costoTotal);
    }

    /**
     * @return the porcSubsidio
     */
    public int getPorcSubsidio() {
        return porcSubsidio;
    }

    /**
     * @param porcSubsidio the porcSubsidio to set
     */
    public void setPorcSubsidio(int porcSubsidio) {
        this.porcSubsidio = porcSubsidio;
    }

    public void setPorcSubsidio(String porcSubsidio) {
        this.porcSubsidio = Integer.parseInt(porcSubsidio);
    }

    /**
     * @return the copago
     */
    public double getCopago() {
        return copago;
    }

    /**
     * @param copago the copago to set
     */
    public void setCopago(double copago) {
        this.copago = copago;
    }

    public void setCopago() {
        this.copago = (getCostoTotal() / getCantParticipantes());
        this.copago -= (getCostoTotal() / getCantParticipantes()) * (porcSubsidio / 100);
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

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getObservacion() {
        return observacion;
    }

}
