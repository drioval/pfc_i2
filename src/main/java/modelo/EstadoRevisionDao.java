/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

/**
 *
 * @author danielrios
 */
public interface EstadoRevisionDao {
    public void guardarEstadoRevision(EstadoRevision estadoRevision);
    public EstadoRevision obtenerEstadoRevision(Integer idEstadoRevision);
}
