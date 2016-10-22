package connection.model.conection;

import java.net.URI;

public interface Request {

    void setParameter(String nameParameter, String value);

    void removeParameter(String nameParameter);

    String getPath();

    URI getURI();

    String getBody();

    String getMethod();

    String getParameter(String nameParameter);
}
