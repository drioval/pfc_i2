/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author insdrv00
 */
public interface UserProfileDao extends GenericDao{
    public void guardarUserProfile(UserProfile userProfile);
    public UserProfile obtenerUserProfile(String usuario);
    public UserProfile obtenerUserProfile(Integer idUsuario);
    public void eliminarUserProfile(UserProfile usuario);
    public boolean existeUserProfile(String usuario);
    public boolean existeUserProfileActivo(String usuario);
}
