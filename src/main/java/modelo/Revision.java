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
 * @author danielrios
 */

@Entity
@Table(name = "Revision")
public class Revision implements java.io.Serializable{
    
    @Id @NotNull @Column(name="idRevision") @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRevision;
    @NotNull @ManyToOne @Column(name = "idCongreso")
    private Congreso congreso;
    @NotNull @ManyToOne @Column(name = "idTraballo")
    private Traballo traballo;
    @NotNull @ManyToOne @Column(name = "userId")
    private UserProfile userProfileAutor;
    @NotNull @ManyToOne @Column(name = "userId")
    private UserProfile userProfileRevisor;
    @Column(name = "revisionPublica")
    private String revisionPublica;
    @Column(name = "revisionPrivada")
    private String revisionPrivada;
    @Column(name = "puntuacion")
    private Integer puntuacion;
    @Column(name = "recomendacion")
    private String recomendacion;
    
    public Revision(){
        
    }
    
    public Revision(Congreso congreso, Traballo traballo, UserProfile userProfileAutor, UserProfile userProfileRevisor){
        this.congreso=congreso;
        this.traballo=traballo;
        this.userProfileAutor=userProfileAutor;
        this.userProfileRevisor=userProfileRevisor;
        this.revisionPublica=null;
        this.revisionPrivada=null;
        this.puntuacion=null;
        this.recomendacion=null;
    }
    
    public Revision(Congreso congreso, Traballo traballo, UserProfile userProfileAutor, UserProfile userProfileRevisor,
            String revisionPublica, String revisionPrivada, Integer puntuacion, String recomendacion){
        this.congreso=congreso;
        this.traballo=traballo;
        this.userProfileAutor=userProfileAutor;
        this.userProfileRevisor=userProfileRevisor;
        this.revisionPublica=revisionPublica;
        this.revisionPrivada=revisionPrivada;
        this.puntuacion=puntuacion;
        this.recomendacion=recomendacion;
    }
    
     /**
     * @return the idRevision
     */
    public Integer getIdRevision() {
        return idRevision;
    }

    /**
     * @param idRevision the idRevision to set
     */
    public void setIdRevision(Integer idRevision) {
        this.idRevision = idRevision;
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

    /**
     * @return the traballo
     */
    public Traballo getTraballo() {
        return traballo;
    }

    /**
     * @param traballo the traballo to set
     */
    public void setTraballo(Traballo traballo) {
        this.traballo = traballo;
    }

    /**
     * @return the userProfileAutor
     */
    public UserProfile getUserProfileAutor() {
        return userProfileAutor;
    }

    /**
     * @param userProfileAutor the userProfileAutor to set
     */
    public void setUserProfileAutor(UserProfile userProfileAutor) {
        this.userProfileAutor = userProfileAutor;
    }

    /**
     * @return the userProfileRevisor
     */
    public UserProfile getUserProfileRevisor() {
        return userProfileRevisor;
    }

    /**
     * @param userProfileRevisor the userProfileRevisor to set
     */
    public void setUserProfileRevisor(UserProfile userProfileRevisor) {
        this.userProfileRevisor = userProfileRevisor;
    }

    /**
     * @return the revisionPublica
     */
    public String getRevisionPublica() {
        return revisionPublica;
    }

    /**
     * @param revisionPublica the revisionPublica to set
     */
    public void setRevisionPublica(String revisionPublica) {
        this.revisionPublica = revisionPublica;
    }

    /**
     * @return the revisionPrivada
     */
    public String getRevisionPrivada() {
        return revisionPrivada;
    }

    /**
     * @param revisionPrivada the revisionPrivada to set
     */
    public void setRevisionPrivada(String revisionPrivada) {
        this.revisionPrivada = revisionPrivada;
    }

    /**
     * @return the puntuacion
     */
    public Integer getPuntuacion() {
        return puntuacion;
    }

    /**
     * @param puntuacion the puntuacion to set
     */
    public void setPuntuacion(Integer puntuacion) {
        this.puntuacion = puntuacion;
    }

    /**
     * @return the recomendacion
     */
    public String getRecomendacion() {
        return recomendacion;
    }

    /**
     * @param recomendacion the recomendacion to set
     */
    public void setRecomendacion(String recomendacion) {
        this.recomendacion = recomendacion;
    }
    
}
