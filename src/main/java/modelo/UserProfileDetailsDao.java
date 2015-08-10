/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author insdrv00
 */
public interface UserProfileDetailsDao {
    public void guardarUserProfileDetails(UserProfileDetails userProfileDetails);
    public boolean existeUserProfileDetails(Integer usuario);
    public UserProfileDetails obtenerUserProfileDetails(Integer idUsuario);
    public UserProfileDetails obtenerUserProfileDetailsEmail(String email);
}
