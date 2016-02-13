/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
    
    @Id @NotNull @Column(name="idTraballo") @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTraballo;
    @NotNull @ManyToOne @Column(name = "idUsuario")
    private Integer userId;
    @NotNull @ManyToOne @Column(name = "idCongreso")
    private Integer idCongreso;

    public Traballo() {
    }
    
    public Traballo(Integer idUsuario, Integer idCongreso){
        this.userId=idUsuario;
        this.idCongreso=idCongreso;
    }

    /**
     * @return the idTraballo
     */
    public Integer getIdTraballo() {
        return idTraballo;
    }

    /**
     * @return the userId
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * @return the idCongreso
     */
    public Integer getIdCongreso() {
        return idCongreso;
    }

    /**
     * @param idCongreso the idCongreso to set
     */
    public void setIdCongreso(Integer idCongreso) {
        this.idCongreso = idCongreso;
    }

    /**
     * @param idTraballo the idTraballo to set
     */
    public void setIdTraballo(Integer idTraballo) {
        this.idTraballo = idTraballo;
    }
}
