package modelo;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Entity;

/**
 * Clase entidad UserProfileDetails
 *
 * @author insdrv00
 */
@Entity
public class UserProfileDetails implements java.io.Serializable {

    @Id
    @NotNull
    private Integer userprofileid;
    private String nome;
    private String apelido1;
    private String apelido2;
    private String telefono;
    private String email;
    private Integer userid;

    public UserProfileDetails() {
    }

    public UserProfileDetails(Integer userprofileid, Integer userid) {
        this.userprofileid = userprofileid;
        this.userid = userid;
    }

    public UserProfileDetails(String nome, String apelido1, String apelido2, String email, String telefono, Integer userid) {
        this.nome = nome;
        this.apelido1 = apelido1;
        this.apelido2 = apelido2;
        this.telefono = telefono;
        this.email = email;
        this.userid = userid;
    }

    public Integer getUserprofileid() {
        return this.userprofileid;
    }

    public void setUserprofileid(Integer userprofileid) {
        this.userprofileid = userprofileid;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getApelido1() {
        return this.apelido1;
    }

    public void setApelido1(String apelido1) {
        this.apelido1 = apelido1;
    }

    public String getApelido2() {
        return this.apelido2;
    }

    public void setApelido2(String apelido2) {
        this.apelido2 = apelido2;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }
}
