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
    private UserProfile idUsuario;
    @NotNull @ManyToOne @Column(name = "idCongreso")
    private Congreso idCongreso;

    public Traballo() {
    }
    
    public Traballo(UserProfile usuario, Congreso congreso){
        this.idUsuario=usuario;
        this.idCongreso=congreso;
    }

    /**
     * @return the idTraballo
     */
    public Integer getIdTraballo() {
        return idTraballo;
    }

    /**
     * @return the idCongreso
     */
    public Congreso getIdCongreso() {
        return idCongreso;
    }

    /**
     * @param idCongreso the idCongreso to set
     */
    public void setIdCongreso(Congreso idCongreso) {
        this.idCongreso = idCongreso;
    }

    /**
     * @param idTraballo the idTraballo to set
     */
    public void setIdTraballo(Integer idTraballo) {
        this.idTraballo = idTraballo;
    }

    /**
     * @return the idUsuario
     */
    public UserProfile getIdUsuario() {
        return idUsuario;
    }

    /**
     * @param idUsuario the idUsuario to set
     */
    public void setIdUsuario(UserProfile idUsuario) {
        this.idUsuario = idUsuario;
    }
}
