package connection.model.proccesor;

import connection.model.conection.Request;
import connection.model.handler.Handler;

public interface RequestDictionary {
    public Handler find(Request request);
}
