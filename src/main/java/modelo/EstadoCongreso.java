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
@Table(name = "EstadoCongreso")
public class EstadoCongreso implements java.io.Serializable {
    
    @Id @NotNull @Column(name = "idEstadoCongreso")
    private Integer idEstadoCongreso;
    @NotNull @Column(name = "nomeCongreso")
    private String nomeEstadoCongreso;
    @OneToMany(mappedBy = "idEstadoCongreso")
    private Set<Congreso> congreso=new HashSet<>(0);

    public EstadoCongreso() {
    }

    public EstadoCongreso(Integer idEstadoCongreso, String nomeEstadoCongreso) {
        this.idEstadoCongreso= idEstadoCongreso;
        this.nomeEstadoCongreso = nomeEstadoCongreso;
    }

    /**
     * @return the idEstadoCongreso
     */
    public Integer getIdEstadoCongreso() {
        return this.idEstadoCongreso;
    }

    /**
     * @param idEstadoCongreso the idEstadoCongreso to set
     */
    public void setIdEstadoCongreso(Integer idEstadoCongreso) {
        this.idEstadoCongreso = idEstadoCongreso;
    }

    /**
     * @return the nomeEstadoCongreso
     */
    public String getNomeEstadoCongreso() {
        return this.nomeEstadoCongreso;
    }

    /**
     * @param nomeEstadoCongreso the nomeEstadoCongreso to set
     */
    public void setNomeEstadoCongreso(String nomeEstadoCongreso) {
        this.nomeEstadoCongreso = nomeEstadoCongreso;
    }

    /**
     * @return the congreso
     */
    public Set<Congreso> getCongreso() {
        return congreso;
    }

    /**
     * @param congreso the congreso to set
     */
    public void setCongreso(Set<Congreso> congreso) {
        this.congreso = congreso;
    }

}
