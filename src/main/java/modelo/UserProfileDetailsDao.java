/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;

/**
 *
 * @author insdrv00
 */
public interface UserProfileDetailsDao {
    public void guardarUserProfileDetails(UserProfileDetails userProfileDetails);
    public boolean existeUserProfileDetails(Integer usuario);
    public UserProfileDetails obtenerUserProfileDetails(Integer usuario);
    public UserProfileDetails obtenerUserProfileDetailsEmail(String email);
}
