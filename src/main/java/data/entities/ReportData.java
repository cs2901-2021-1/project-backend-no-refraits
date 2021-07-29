package data.entities;

import lombok.*;

@NoArgsConstructor
public class ReportData {
    @NonNull @Getter @Setter String semester;
    @NonNull @Getter @Setter Integer predicted;
    @NonNull @Getter @Setter Integer enrolled;
}
