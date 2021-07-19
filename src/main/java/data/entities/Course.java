package data.entities;

public class Course {
    private String name;
    private Integer code;

    public Course(String name, Integer code) {
        this.name = name;
        this.code = code;
    }

    public Course() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
