package Framework.controller;

import com.sun.net.httpserver.HttpServer;
import Framework.model.handler.HttpPetitionHandler;
import Framework.model.proccesor.Processor;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpNativeService implements HttpService{
    private final HttpPetitionHandler petitionsHandler;
    private final InetSocketAddress inetSocketAddress;

    public HttpNativeService(Processor processor, int port) {
        this.petitionsHandler = new HttpPetitionHandler(processor);
        this.inetSocketAddress = new InetSocketAddress(port);
    }


    public void start() {
        try {
            HttpServer httpServer = HttpServer.create(inetSocketAddress, 500);
            httpServer.createContext("/", petitionsHandler);
            httpServer.start();
            System.out.println("Server running on the port : "+ inetSocketAddress.getPort());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void enableRequestQueue() {
        petitionsHandler.enableRequestQueue();
    }

    public void disableRequestQueue() {
        petitionsHandler.disableRequestQueue();
    }

    public synchronized void executeStoreRequests() {
        petitionsHandler.runRequestsQueue();
    }
}
