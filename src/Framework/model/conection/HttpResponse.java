package Framework.model.conection;

public class HttpResponse implements Response{
    private final String message;
    private int code;

    public HttpResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getBody() {
        return message;
    }

    @Override
    public int getStatusCode() {
        return code;
    }

    @Override
    public void setStatusCode(int code) {
        this.code = code;
    }
}
