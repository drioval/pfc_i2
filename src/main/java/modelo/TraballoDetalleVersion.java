/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;



/**
 *
 * @author drioval
 */
@Entity
public class TraballoDetalleVersion implements java.io.Serializable{
    
    @Id @NotNull
    private Integer idTraballoDetalleVersion;
    @NotNull
    private Integer idTraballoDetalle;
    @NotNull
    private Integer idTraballo;
    @NotNull
    private String nomeTraballo;
    private String categoria;
    private String autores;
    @NotNull
    private Serializable traballo;
    @NotNull @ManyToOne @Column(name = "idEstadoTraballo")
    private EstadoTraballo estadoTraballo;
    private Timestamp fInicioEnvio;
    private Timestamp fFinEnvio;
    private Timestamp fIncioRevision;
    private Timestamp fFinRevision;

    public TraballoDetalleVersion(){
    }
    
    public TraballoDetalleVersion(Integer idTraballoDetalle, Integer idTraballo,
            String nomeTraballo, Serializable traballo, EstadoTraballo estadoTraballo){
        this.idTraballoDetalle=idTraballoDetalle;
        this.idTraballo=idTraballo;
        this.nomeTraballo=nomeTraballo;
        this.traballo=traballo;
        this.estadoTraballo=estadoTraballo;
    }
    
    public TraballoDetalleVersion(Integer idTraballoDetalle, Integer idTraballo,
            String nomeTraballo, String categoria, String autores, Serializable traballo,
            EstadoTraballo estadoTraballo,Timestamp fInicioEnvio, 
            Timestamp fFinEnvio, Timestamp fInicioRevision, Timestamp fFinRevision){
        this.idTraballoDetalle=idTraballoDetalle;
        this.idTraballo=idTraballo;
        this.nomeTraballo=nomeTraballo;
        this.categoria=categoria;
        this.autores=autores;
        this.traballo=traballo;
        this.estadoTraballo=estadoTraballo;
        this.fInicioEnvio=fInicioEnvio;
        this.fFinEnvio=fFinEnvio;
        this.fIncioRevision=fInicioRevision;
        this.fFinRevision=fFinRevision;
    }

    /**
     * @return the idTraballoDetalleVersion
     */
    public Integer getIdTraballoDetalleVersion() {
        return idTraballoDetalleVersion;
    }

    /**
     * @return the idTraballoDetalle
     */
    public Integer getIdTraballoDetalle() {
        return idTraballoDetalle;
    }

    /**
     * @param idTraballoDetalle the idTraballoDetalle to set
     */
    public void setIdTraballoDetalle(Integer idTraballoDetalle) {
        this.idTraballoDetalle = idTraballoDetalle;
    }

    /**
     * @return the idTraballo
     */
    public Integer getIdTraballo() {
        return idTraballo;
    }

    /**
     * @param idTraballo the idTraballo to set
     */
    public void setIdTraballo(Integer idTraballo) {
        this.idTraballo = idTraballo;
    }

    /**
     * @return the nomeTraballo
     */
    public String getNomeTraballo() {
        return nomeTraballo;
    }

    /**
     * @param nomeTraballo the nomeTraballo to set
     */
    public void setNomeTraballo(String nomeTraballo) {
        this.nomeTraballo = nomeTraballo;
    }

    /**
     * @return the categoria
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * @param categoria the categoria to set
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     * @return the autores
     */
    public String getAutores() {
        return autores;
    }

    /**
     * @param autores the autores to set
     */
    public void setAutores(String autores) {
        this.autores = autores;
    }

    /**
     * @return the traballo
     */
    public Serializable getTraballo() {
        return traballo;
    }

    /**
     * @param traballo the traballo to set
     */
    public void setTraballo(Serializable traballo) {
        this.traballo = traballo;
    }

    /**
     * @return the fInicioEnvio
     */
    public Timestamp getfInicioEnvio() {
        return fInicioEnvio;
    }

    /**
     * @param fInicioEnvio the fInicioEnvio to set
     */
    public void setfInicioEnvio(Timestamp fInicioEnvio) {
        this.fInicioEnvio = fInicioEnvio;
    }

    /**
     * @return the fFinEnvio
     */
    public Timestamp getfFinEnvio() {
        return fFinEnvio;
    }

    /**
     * @param fFinEnvio the fFinEnvio to set
     */
    public void setfFinEnvio(Timestamp fFinEnvio) {
        this.fFinEnvio = fFinEnvio;
    }

    /**
     * @return the fIncioRevision
     */
    public Timestamp getfIncioRevision() {
        return fIncioRevision;
    }

    /**
     * @param fIncioRevision the fIncioRevision to set
     */
    public void setfIncioRevision(Timestamp fIncioRevision) {
        this.fIncioRevision = fIncioRevision;
    }

    /**
     * @return the fFinRevision
     */
    public Timestamp getfFinRevision() {
        return fFinRevision;
    }

    /**
     * @param fFinRevision the fFinRevision to set
     */
    public void setfFinRevision(Timestamp fFinRevision) {
        this.fFinRevision = fFinRevision;
    }

    /**
     * @param idTraballoDetalleVersion the idTraballoDetalleVersion to set
     */
    public void setIdTraballoDetalleVersion(Integer idTraballoDetalleVersion) {
        this.idTraballoDetalleVersion = idTraballoDetalleVersion;
    }

    /**
     * @return the estadoTraballo
     */
    public EstadoTraballo getEstadoTraballo() {
        return estadoTraballo;
    }

    /**
     * @param estadoTraballo the estadoTraballo to set
     */
    public void setEstadoTraballo(EstadoTraballo estadoTraballo) {
        this.estadoTraballo = estadoTraballo;
    }
}
