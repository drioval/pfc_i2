/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author drioval
 */
@Entity
@Table(name = "Traballo")
public class Traballo implements java.io.Serializable{
    
    @Id @NotNull @Column(name="idTraballo")
    private Integer idTraballo;
    @NotNull @OneToMany @Column(name = "idUsuario")
    private Set<UserProfile> idUsuario=new HashSet<>(0);
    @NotNull @OneToMany @Column(name = "idCongreso")
    private Set<Congreso> idCongreso=new HashSet<>(0);
    @NotNull @OneToMany @Column(name = "idTraballo")
    private Set<TraballoDetalle> idTraballoDetalle;

    public Traballo() {
    }
    
    public Traballo(Set<UserProfile> idUsuario, Set<Congreso> idCongreso,
            Set<TraballoDetalle> idTraballoDetalle){
        this.idUsuario=idUsuario;
        this.idCongreso=idCongreso;
        this.idTraballoDetalle=idTraballoDetalle;
    }

    /**
     * @return the idTraballo
     */
    public Integer getIdTraballo() {
        return idTraballo;
    }

    /**
     * @return the idUsuario
     */
    public Set<UserProfile> getIdUsuario() {
        return idUsuario;
    }

    /**
     * @param idUsuario the idUsuario to set
     */
    public void setIdUsuario(Set<UserProfile> idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * @return the idCongreso
     */
    public Set<Congreso> getIdCongreso() {
        return idCongreso;
    }

    /**
     * @param idCongreso the idCongreso to set
     */
    public void setIdCongreso(Set<Congreso> idCongreso) {
        this.idCongreso = idCongreso;
    }

    /**
     * @return the idTraballoDetalle
     */
    public Set<TraballoDetalle> getIdTraballoDetalle() {
        return idTraballoDetalle;
    }

    /**
     * @param idTraballoDetalle the idTraballoDetalle to set
     */
    public void setIdTraballoDetalle(Set<TraballoDetalle> idTraballoDetalle) {
        this.idTraballoDetalle = idTraballoDetalle;
    }

    /**
     * @param idTraballo the idTraballo to set
     */
    public void setIdTraballo(Integer idTraballo) {
        this.idTraballo = idTraballo;
    }
    
}
