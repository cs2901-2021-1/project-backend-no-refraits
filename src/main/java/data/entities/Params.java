package data.entities;

public class Params {

    private  String rol;
    private  String direccion;

    public Params(String rol, String direccion) {
        this.rol = rol;
        this.direccion = direccion;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
