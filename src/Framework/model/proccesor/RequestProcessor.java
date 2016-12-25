package Framework.model.proccesor;

import Framework.model.conection.Request;
import Framework.model.conection.Response;
import Framework.model.handler.Handler;

import java.io.IOException;

public class RequestProcessor implements Processor {

    private RequestDictionary dictionary;

    public RequestProcessor(RequestDictionary dictionary) {
        this.dictionary = dictionary;
    }

    @Override
    public Response process(Request request) throws IOException {
        Handler handler = dictionary.find(request);
        return handler.handle(request);
    }

}


