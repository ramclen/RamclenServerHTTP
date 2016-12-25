package Framework.model.proccesor;

import Framework.model.conection.Request;
import Framework.model.handler.Handler;

public interface RequestDictionary {
    public Handler find(Request request);
}
