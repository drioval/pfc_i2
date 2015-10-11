/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
    @NotNull @OneToOne
    private CongresoDetalle congresoDetalle;
    @NotNull @ManyToOne @Column(name = "idEstadoCongreso")
    private EstadoCongreso estadoCongreso;

    public Congreso() {
    }

    public Congreso(String nomeCongreso, CongresoDetalle congresoDetalle, EstadoCongreso estadoCongreso) {
        this.nomeCongreso = nomeCongreso;
        this.congresoDetalle = congresoDetalle;
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

    /**
     * @return the congresoDetalle
     */
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "Congreso", cascade = CascadeType.ALL)
    public CongresoDetalle getCongresoDetalle() {
        return congresoDetalle;
    }

    /**
     * @param congresoDetalle the congresoDetalle to set
     */
    public void setCongresoDetalle(CongresoDetalle congresoDetalle) {
        this.congresoDetalle = congresoDetalle;
    }
}
