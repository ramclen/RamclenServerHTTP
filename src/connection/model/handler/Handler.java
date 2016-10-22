package connection.model.handler;

import connection.model.conection.Request;
import connection.model.conection.Response;

import java.io.IOException;

public interface Handler {
    Response handle(Request request) throws IOException;
}
