package data.entities;

public class AiDto {
    private String direccion;
    private String curso;

    public AiDto(String direccion, String curso) {
        this.direccion = direccion;
        this.curso = curso;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }
}
