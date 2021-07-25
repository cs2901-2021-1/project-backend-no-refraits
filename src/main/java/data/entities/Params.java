package data.entities;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class Params {
    @NonNull @Getter @Setter
    private  String rol;

    @NonNull @Getter @Setter
    private  String direccion;
}
