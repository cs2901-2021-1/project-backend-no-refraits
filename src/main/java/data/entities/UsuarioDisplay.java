package data.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

@RequiredArgsConstructor
public class UsuarioDisplay {
    @NonNull @Getter @Setter private String nombre;
    @NonNull @Getter @Setter  private String email;
    @NonNull @Getter @Setter  private String direccion;
    @NonNull @Getter @Setter  private Long id;
    @NonNull @Getter @Setter  private String rol;
}
