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
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author drioval
 */
@Entity
@Table(name = "Traballo")
public class Traballo implements java.io.Serializable{
    
    @Id @NotNull @Column(name="idTraballo") @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTraballo;
    @NotNull @ManyToOne @Column(name = "userId")
    private UserProfile userProfile;
    @NotNull @ManyToOne @Column(name = "idCongreso")
    private Congreso congreso;

    public Traballo() {
    }
    
    public Traballo(UserProfile usuario, Congreso congreso){
        this.userProfile=usuario;
        this.congreso=congreso;
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
     * @return the userProfile
     */
    public UserProfile getUserProfile() {
        return userProfile;
    }

    /**
     * @param userProfile the userProfile to set
     */
    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    /**
     * @return the congreso
     */
    public Congreso getCongreso() {
        return congreso;
    }

    /**
     * @param congreso the congreso to set
     */
    public void setCongreso(Congreso congreso) {
        this.congreso = congreso;
    }
}
