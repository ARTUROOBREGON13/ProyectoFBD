/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author usuario
 */
public class Familiar {
    
    private String id;
    private String nombre;
    private String apellido;
    private String tipoId;
    private String parentezco;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
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
     * @return the apellido
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * @param apellido the apellido to set
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipoId;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipoId = tipo;
    }

    /**
     * @return the password
     */
    public String getParentezco() {
        return parentezco;
    }

    /**
     * @param parentezco the password to set
     */
    public void setParentezco(String parentezco) {
        this.parentezco = parentezco;
    }

}
