package Framework.controller;

import Framework.model.conection.Request;
import Framework.model.conection.Response;

import java.io.IOException;

public interface PetitionClient {
    Response sendRequest(Request request) throws IOException;
    void sendRequest(Request request, Action done) throws IOException;

    interface Action{
        void execute(Response response);
    }
}
