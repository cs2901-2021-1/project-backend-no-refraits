package data.entities;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class Prediccion {
    @NonNull @Getter @Setter
    private String value;
}
