package data.entities;

public class AuthData {

    private String token;
    private String username;
    private String direccion;
    private String rol;

    public AuthData() {

    }

    public AuthData(String token, String username, String direccion){
        this.token = token;
        this.username = username;
        this.direccion = direccion;
    }

    public AuthData(String token){
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getRol() {
        return this.rol;
    }

    public void setRol(String roleName) {
        this.rol = roleName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
