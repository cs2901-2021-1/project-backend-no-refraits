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

    @Column(name="nombre", nullable = false)
    private String nombre;

    @Column(name = "googleid", nullable = false)
    private String googleid;

    @Column(name="direccion", nullable = false)
    private String direccion;

    @ManyToOne
    @JoinColumn (name = "rol_id", nullable = false)
    private Rol rol;


    public Usuario() {
    }

    public Usuario(Long id, String email, String nombre, String direccion) {
        this.id = id;
        this.email = email;
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public Usuario(String email, String googleid) {
        this.email = email;
        this.googleid = googleid;
    }

    public Usuario(String email, String nombre, String googleid) {
        this.email = email;
        this.nombre = nombre;
        this.googleid = googleid;
    }

    public Usuario(String email, String nombre, String googleid, String direccion, Rol rol) {
        this.email = email;
        this.nombre = nombre;
        this.googleid = googleid;
        this.direccion = direccion;
        this.rol = rol;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", rol=" + rol +
                '}';
    }
}
