package data.entities;

import lombok.*;

@NoArgsConstructor
public class PredictionResponse {
    @NonNull @Getter @Setter private Integer prediction;
    @NonNull @Getter @Setter private Float fiability;
}
