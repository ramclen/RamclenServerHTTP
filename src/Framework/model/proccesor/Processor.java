package Framework.model.proccesor;


import Framework.model.conection.Request;
import Framework.model.conection.Response;

import java.io.IOException;

public interface Processor {
    Response process(Request request) throws IOException;
}
