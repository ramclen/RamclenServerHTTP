package Framework.controller;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import Framework.model.conection.Request;
import Framework.model.conection.Response;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class HttpPetitionClient implements PetitionClient {
    ResponseFactory responseFactory;

    public HttpPetitionClient() {
        responseFactory = new ResponseFactory();
    }

    @Override
    public Response sendRequest(Request request) throws IOException {
        return send(request);
    }

    @Override
    public void sendRequest(Request request, Action doneAction){
        new Thread(() -> doneAction.execute(send(request))).start();
    }

    private Response send(Request request) {
        try {
            return responseFactory.create(executeClient(request));
        } catch (IOException error) {
            return responseFactory.NotRespondErrorMessage(request, error);
        }
    }

    private CloseableHttpResponse executeClient(Request request) throws IOException {
        return HttpClients.createDefault().execute(adaptRequest(request));
    }

    private HttpRequestBase adaptRequest(Request request) throws UnsupportedEncodingException {
        HttpEntityEnclosingRequestBase requestB = new HttpEntityEnclosingRequestBase() {
            @Override
            public String getMethod() {
                return request.getMethod();
            }
        };
        requestB.setURI(request.getURI());
        if(request.getMethod().equals("POST"))
            requestB.setEntity(new StringEntity(request.getBody()));
        return requestB;
    }

}
