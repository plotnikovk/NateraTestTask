package enums;

public enum HttpCode {
    OK(200, "OK"),
    Unauthorized(401, "Unauthorized"),
    NotFound(404, "Not found"),
    Unprocessable(422, "Unprocessable");

    private int code;
    private String description;

    HttpCode(int code, String description){
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }
    public String getDescription() {
        return description;
    }
}
