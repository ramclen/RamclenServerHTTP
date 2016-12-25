package connection;

import connection.controller.HttpService;
import connection.model.conection.Request;
import connection.model.handler.Handler;
import connection.model.proccesor.MapRequestDictionary;
import connection.model.proccesor.RequestDictionary;
import connection.model.proccesor.RequestProcessor;

import java.lang.reflect.Constructor;
import java.util.HashMap;

import static com.ramclen.exceptionRunner.ExceptionHandler.getSafe;

public class GenericServerHTTP {
    private static MapRequestDictionary requestDictionary = new MapRequestDictionary();
    private static HttpService httpService;

    public static <T extends HttpService> void setServiceAndPort(int port, Class<T> service) {
        Constructor<T> serviceConstructor = getSafe(()-> service.getConstructor(RequestProcessor.class, int.class),
                "Impossible get constructor with types RequestProcessor and int as ports");

        httpService = getSafe(()-> serviceConstructor.newInstance(new RequestProcessor(requestDictionary), port),
                "Error trying to to get a new instance of service");

        httpService.start();
    }

    public static void setPath(String url, Handler handler) {
        requestDictionary.put(url, handler);
    }

    public static void get(String path, Handler handler) {
        setPath("GET:" + path, handler);
    }

    public static void post(String path, Handler handler) {
        setPath("POST:" + path, handler);
    }

    public static void delete(String path, Handler handler) {
            setPath("DELETE:" + path, handler);
        }

}
