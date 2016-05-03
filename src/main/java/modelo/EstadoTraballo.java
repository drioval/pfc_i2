/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author drioval
 */
@Entity
@Table(name = "EstadoTraballo")

public class EstadoTraballo implements java.io.Serializable{
    
    @Id @NotNull @Column(name = "idEstadoTraballo")
    private Integer idEstadoTraballo;
    @NotNull @Column(name = "nomeEstado")
    private String nomeEstado;
    
    public EstadoTraballo(){
    }
    
    public EstadoTraballo(String nomeEstado){
        this.nomeEstado=nomeEstado;
    }

    /**
     * @return the idEstadoTraballo
     */
    public Integer getIdEstadoTraballo() {
        return idEstadoTraballo;
    }

    /**
     * @return the nomeEstado
     */
    public String getNomeEstado() {
        return nomeEstado;
    }

    /**
     * @param nomeEstado the nomeEstado to set
     */
    public void setNomeEstado(String nomeEstado) {
        this.nomeEstado = nomeEstado;
    }

    /**
     * @param idEstadoTraballo the idEstadoTraballo to set
     */
    public void setIdEstadoTraballo(Integer idEstadoTraballo) {
        this.idEstadoTraballo = idEstadoTraballo;
    }
    
}
