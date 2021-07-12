package data.entities;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name="rol")
public class Rol implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;

    @Column(name="name")
    String name;

    public Rol() {
    }

    public Rol(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Rol{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
