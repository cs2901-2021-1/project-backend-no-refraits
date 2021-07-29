package data.entities;

import lombok.*;

@NoArgsConstructor
public class ReportData {
    @NonNull @Getter @Setter String semester;
    @NonNull @Getter @Setter String predicted;
    @NonNull @Getter @Setter String enrolled;
}
