package Framework.model.conection;


public interface Response {
    String getBody();

    int getStatusCode();

    void setStatusCode(int code);
}