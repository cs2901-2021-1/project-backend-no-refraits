package data.entities;


import lombok.Getter;

public class Response<T> {

    @Getter private final int status;
    @Getter private final String message;
    @Getter private final T result;

    public Response(int status, String message, T result) {
        this.status = status;
        this.message = message;
        this.result = result;
    }

}
