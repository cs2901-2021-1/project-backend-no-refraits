package data.entities;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public class ReportData {
    @NonNull @Getter @Setter String semester;
    @NonNull @Getter @Setter Integer predicted;
    @NonNull @Getter @Setter Integer enrolled;
}
