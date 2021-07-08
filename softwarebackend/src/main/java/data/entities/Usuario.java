package data.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Set;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "googleid", nullable = false)
    private String googleid;

    @ManyToOne
    @JoinColumn (name = "rol_id", nullable = false)
    private Rol rol;


    public Usuario() {
    }

    public Usuario( String email, String googleid) {
        this.email = email;
        this.googleid = googleid;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGoogleid (){
        return googleid;
    }

    public void setGoogleid(String passwd) {
        this.googleid = passwd;
    }

}
