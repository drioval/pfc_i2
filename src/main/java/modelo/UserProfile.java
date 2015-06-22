package modelo;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * Clase entidad UserProfile
 *
 * @author insdrv00
 */
    @Entity
public class UserProfile implements java.io.Serializable {

    @Id
    @NotNull
    private Integer userId;
    private UserRol userRol;
    private String usuario;
    private String contrasinal;
    private Set<UserProfileDetails> userProfileDetailses = new HashSet<UserProfileDetails>(0);
    private Integer activo;

    public UserProfile() {
    }

    public UserProfile(UserRol userRol, String usuario, String contrasinal) {
        this.userRol = userRol;
        this.usuario = usuario;
        this.contrasinal = contrasinal;
        this.activo=0;
    }

    public UserProfile(UserRol userRol, String usuario, String contrasinal, Set<UserProfileDetails> userProfileDetailses) {
        this.userRol = userRol;
        this.usuario = usuario;
        this.contrasinal = contrasinal;
        this.userProfileDetailses = userProfileDetailses;
        this.activo=0;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public UserRol getUserRol() {
        return this.userRol;
    }

    public void setUserRol(UserRol userRol) {
        this.userRol = userRol;
    }

    public String getUsuario() {
        return this.usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasinal() {
        return this.contrasinal;
    }

    public void setContrasinal(String contrasinal) {
        this.contrasinal = contrasinal;
    }

    public Set<UserProfileDetails> getUserProfileDetailses() {
        return this.userProfileDetailses;
    }

    public void setUserProfileDetailses(Set<UserProfileDetails> userProfileDetailses) {
        this.userProfileDetailses = userProfileDetailses;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }
}