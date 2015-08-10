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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author drioval
 */
@Entity
@Table(name = "Congreso")
public class Congreso implements java.io.Serializable {

    @Id @NotNull @Column(name = "idCongreso") @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCongreso;
    @NotNull @Column(name = "nomeCongreso")
    private String nomeCongreso;
    @NotNull @OneToOne @Column(name = "idDetalleCongreso")
    private Set<CongresoDetalle> idDetalleCongreso = new HashSet<>(0);
    @NotNull @ManyToOne @Column(name = "idEstadoCongreso")
    private EstadoCongreso estadoCongreso;

    public Congreso() {
    }

    public Congreso(String nomeCongreso, Set<CongresoDetalle> idCongresoDetalle, EstadoCongreso estadoCongreso) {
        this.nomeCongreso = nomeCongreso;
        this.idDetalleCongreso = idCongresoDetalle;
        this.estadoCongreso = estadoCongreso;
    }

    /**
     * @return the idCongreso
     */
    public Integer getIdCongreso() {
        return this.idCongreso;
    }

    /**
     * @return the nomeCongreso
     */
    public String getNomeCongreso() {
        return this.nomeCongreso;
    }

    /**
     * @param nomeCongreso the nomeCongreso to set
     */
    public void setNomeCongreso(String nomeCongreso) {
        this.nomeCongreso = nomeCongreso;
    }

    /**
     * @return the idDetalleCongreso
     */
    public Set<CongresoDetalle> getIdDetalleCongreso() {
        return this.idDetalleCongreso;
    }

    /**
     * @param idDetalleCongreso the idDetalleCongreso to set
     */
    public void setIdDetalleCongreso(Set<CongresoDetalle> idDetalleCongreso) {
        this.idDetalleCongreso = idDetalleCongreso;
    }

    /**
     * @param idCongreso the idCongreso to set
     */
    public void setIdCongreso(Integer idCongreso) {
        this.idCongreso = idCongreso;
    }

    /**
     * @return the estadoCongreso
     */
    public EstadoCongreso getEstadoCongreso() {
        return this.estadoCongreso;
    }

    /**
     * @param estadoCongreso the estadoCongreso to set
     */
    public void setEstadoCongreso(EstadoCongreso estadoCongreso) {
        this.estadoCongreso = estadoCongreso;
    }
}
