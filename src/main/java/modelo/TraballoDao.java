/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

/**
 *
 * @author drioval
 */
public interface TraballoDao {
    public void guardarTraballo(Traballo traballo);
    public Traballo obtenerTraballo(Integer idTraballo);
    public Traballo obtenerTraballoUsuarioCongreso(Integer idUsuario, Integer idCongreso);
}
