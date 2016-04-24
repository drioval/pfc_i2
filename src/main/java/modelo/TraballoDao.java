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
public interface TraballoDao {
    public void guardarTraballo(Traballo traballo);
    public Traballo obtenerTraballo(Integer idTraballo);
    public List<Traballo> obtenerTraballoUsuarioCongreso(Integer idUsuario, Integer idCongreso);
    public void eliminarTraballo(Traballo traballo);
}
