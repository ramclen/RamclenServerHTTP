package connection.model.proccesor;


import connection.model.conection.Request;
import connection.model.conection.Response;

import java.io.IOException;

public interface Processor {
    Response process(Request request) throws IOException;
}
