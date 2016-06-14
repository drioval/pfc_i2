/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author danielrios
 */

@Entity
@Table(name = "EstadoRevision")
public class EstadoRevision  implements java.io.Serializable{
    
    @Id @NotNull @Column(name = "idEstadoRevision")
    private int idEstadoRevision;
    @NotNull @Column(name = "estadoRevision")
    private String estadoRevision;

    public EstadoRevision() {
    }

    public EstadoRevision(Integer idEstadoRevision, String estadoRevision) {
        this.idEstadoRevision= idEstadoRevision;
        this.estadoRevision = estadoRevision;
    }

    /**
     * @return the idEstadoRevision
     */
    public int getIdEstadoRevision() {
        return idEstadoRevision;
    }

    /**
     * @param idEstadoRevision the idEstadoRevision to set
     */
    public void setIdEstadoRevision(int idEstadoRevision) {
        this.idEstadoRevision = idEstadoRevision;
    }

    /**
     * @return the estadoRevision
     */
    public String getEstadoRevision() {
        return estadoRevision;
    }

    /**
     * @param estadoRevision the estadoRevision to set
     */
    public void setEstadoRevision(String estadoRevision) {
        this.estadoRevision = estadoRevision;
    }
}
