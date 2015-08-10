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
public interface CongresoDetalleDao {
    public void guardarCongresoDetalle(CongresoDetalle congresoDetalle);
    public CongresoDetalle obtenerCongresoDetalle(Integer idCongreso);
}
