package modelo;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * Clase entidad UserProfileDetails
 *
 * @author insdrv00
 */

@Entity
public class UserRol implements java.io.Serializable {

    @Id
    @NotNull
    private int rolId;
    private String descricion;
    private Set<UserProfile> userProfiles = new HashSet<>(0);

    public UserRol() {
    }

    public UserRol(int rolId) {
        this.rolId = rolId;
    }

    public UserRol(int rolId, String descricion, Set<UserProfile> userProfiles) {
        this.rolId = rolId;
        this.descricion = descricion;
        this.userProfiles = userProfiles;
    }

    public int getRolId() {
        return this.rolId;
    }

    public void setRolId(int rolId) {
        this.rolId = rolId;
    }

    public String getDescricion() {
        return this.descricion;
    }

    public void setDescricion(String descricion) {
        this.descricion = descricion;
    }

    public Set<UserProfile> getUserProfiles() {
        return this.userProfiles;
    }

    public void setUserProfiles(Set<UserProfile> userProfiles) {
        this.userProfiles = userProfiles;
    }
}
