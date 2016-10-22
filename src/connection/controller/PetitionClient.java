package connection.controller;

import connection.model.conection.Request;
import connection.model.conection.Response;

import java.io.IOException;

public interface PetitionClient {
    Response sendRequest(Request request) throws IOException;
    void sendRequest(Request request, Action done) throws IOException;

    interface Action{
        void execute(Response response);
    }
}
