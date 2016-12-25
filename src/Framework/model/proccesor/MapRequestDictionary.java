package Framework.model.proccesor;

import Framework.model.conection.Request;
import Framework.model.handler.Handler;

import java.util.HashMap;

/**
 * Created by ramcl on 25/12/2016.
 */
public class MapRequestDictionary extends HashMap<String, Handler> implements RequestDictionary{
    @Override
    public Handler find(Request request) {
        return get(request.getMethod()+":"+request.getPath());
    }
}
