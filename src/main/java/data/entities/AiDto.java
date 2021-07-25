package data.entities;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class AiDto {
    @NonNull @Getter @Setter
    private String direccion;

    @NonNull @Getter @Setter
    private String curso;
}
