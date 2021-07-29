package data.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "usuario")
@NoArgsConstructor
public class Usuario implements Serializable {
    @Id @Getter @Setter
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Getter @Setter
    @Column(name = "email", nullable = false)
    private String email;

    @Getter @Setter
    @Column(name="nombre", nullable = false)
    private String nombre;

    @Getter @Setter
    @Column(name = "googleid", nullable = false)
    private String googleid;

    @Getter @Setter
    @Column(name="direccion", nullable = false)
    private String direccion;

    @Getter @Setter
    @ManyToOne
    @JoinColumn (name = "rol_id", nullable = false)
    private Rol rol;

    public Usuario(String email, String nombre, String googleId, String direccion, Rol rol) {
        this.email = email;
        this.nombre = nombre;
        this.googleid = googleId;
        this.direccion = direccion;
        this.rol = rol;
    }
}
