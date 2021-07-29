package data.entities;

import lombok.*;

@NoArgsConstructor
@RequiredArgsConstructor
public class PredictionRequest {
    @NonNull @Getter @Setter private String curso;
}
