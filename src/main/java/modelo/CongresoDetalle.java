/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 *
 * @author drioval
 */
@Entity
@Table(name = "CongresoDetalle")
public class CongresoDetalle implements java.io.Serializable {

    @Id
    @NotNull @GeneratedValue(generator = "generator")
    @GenericGenerator(name = "generator", strategy = "foreing", parameters = @Parameter(name = "property", value = "congreso"))
    @Column(name = "idDetalleCongreso")
    private Integer idDetalleCongreso;
    @Column(name = "fInicioEnvio")
    private Timestamp fInicioEnvio;
    @Column(name = "fFinEnvio")
    private Timestamp fFinEnvio;
    @Column(name = "fInicioRevision")
    private Timestamp fInicioRevision;
    @Column(name = "fFinRevision")
    private Timestamp fFinRevision;
    @OneToOne
    @PrimaryKeyJoinColumn
    private Congreso congreso;

    public CongresoDetalle() {
    }

    public CongresoDetalle(Timestamp fInicioEnvio, Timestamp fFinEnvio,
            Timestamp fInicioRevion, Timestamp fFinRevision, Congreso congreso) {
        this.fInicioEnvio = fInicioEnvio;
        this.fFinEnvio = fFinEnvio;
        this.fInicioRevision = fInicioRevion;
        this.fFinRevision = fFinRevision;
        this.congreso=congreso;
    }

    /**
     * @return the idDetalleCongreso
     */
    public Integer getIdDetalleCongreso() {
        return idDetalleCongreso;
    }

    /**
     * @param idDetalleCongreso the idDetalleCongreso to set
     */
    public void setIdDetalleCongreso(Integer idDetalleCongreso) {
        this.idDetalleCongreso = idDetalleCongreso;
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
     * @return the fInicioRevision
     */
    public Timestamp getfInicioRevision() {
        return fInicioRevision;
    }

    /**
     * @param fInicioRevision the fInicioRevision to set
     */
    public void setfInicioRevision(Timestamp fInicioRevision) {
        this.fInicioRevision = fInicioRevision;
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
