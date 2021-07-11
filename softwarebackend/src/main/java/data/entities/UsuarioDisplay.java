package data.entities;

public class UsuarioDisplay {
    private String nombre;
    private String email;
    private String direccion;
    private Long id;
    private String rol;

    public UsuarioDisplay(String nombre, String email, String direccion, Long id, String rol) {
        this.nombre = nombre;
        this.email = email;
        this.direccion = direccion;
        this.id = id;
        this.rol = rol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
