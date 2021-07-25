package data.entities;

import lombok.Getter;

public class AuthData {

    @Getter private final String token;
    @Getter private final String username;
    @Getter private final String direccion;
    @Getter private final String rol;

    public AuthData(String token, String username, String rol){
        this.token = token;
        this.username = username;
        this.rol = rol;
        this.direccion = null;
    }
}
