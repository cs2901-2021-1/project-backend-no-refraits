package data.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name="rol")
@NoArgsConstructor
@RequiredArgsConstructor
public class Rol implements Serializable {
    @Id @Getter @Setter
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;

    @Getter @Setter @NonNull
    @Column(name="name")
    String name;
}
