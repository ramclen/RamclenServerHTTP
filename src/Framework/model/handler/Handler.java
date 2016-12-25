package Framework.model.handler;

import Framework.model.conection.Request;
import Framework.model.conection.Response;

import java.io.IOException;

public interface Handler {
    Response handle(Request request) throws IOException;
}
