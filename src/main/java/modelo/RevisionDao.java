/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import java.util.List;

/**
 *
 * @author danielrios
 */
public interface RevisionDao {
    public void guardarRevision(Revision revision);
    public Revision obtenerRevision(Integer idRevision);
    public List<Revision> obtenerRevisionesCongreso(Integer idCongreso);
    public List<Revision> obtenerRevisionesCongresoTraballo(Integer idCongreso, Integer idTraballo);
    public List<Revision> obtenerRevisionCongresoTraballoRevisor(Integer idCongreso, Integer idTraballo, Integer idRevisor);
    public List<Revision> obtenerRevisionCongresoRevisor(Integer idCongreso, Integer idRevisor);
    public void eliminarRevision(Revision revision);
}
