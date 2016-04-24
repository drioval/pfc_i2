/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import java.util.List;

/**
 *
 * @author drioval
 */
public interface TraballoDetalleVersionDao {
    public void guardarTraballoDetalleVersion(TraballoDetalleVersion traballoDetalleVersion);
    public List<TraballoDetalleVersion> obtenerTraballoDetalleVersion(Integer idTraballoDetalleVersion);
    public void eliminarTraballoDetalleVersion(TraballoDetalleVersion traballoDetalleVersion);
}
