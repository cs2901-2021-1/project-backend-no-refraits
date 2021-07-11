package data.entities;

public class AuthToken {

    private String token;
    private String username;
    private String direccion;

    public AuthToken(){

    }

    public AuthToken(String token, String username, String direccion){
        this.token = token;
        this.username = username;
        this.direccion = direccion;
    }

    public AuthToken(String token){
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


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
