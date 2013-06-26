/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author insdrv00
 */
public interface UserRolDao {
    public void guardarUserRol(UserRol userRol);
    public void eliminarUserRol(UserRol usuario);
    public boolean existeUserRol(Integer userRolId);
    
}
