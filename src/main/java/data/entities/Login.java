package data.entities;

import lombok.Getter;

public class Login {

    @Getter private final String email;
    @Getter private final String password;
    @Getter private final String nombre;


    public Login(String email, String password) {
        this.email = email;
        this.password = password;
        this.nombre = "";
    }
}
