package Framework.model.conection;

import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static com.ramclen.exceptionRunner.ExceptionHandler.getSafe;

public class HttpRequest implements Request{

    private String body;
    private final String method;
    private final String path;
    private Map<String, Object> parameters;

    public HttpRequest(String method, String path, String body) throws ParseException {
        this.body = body;
        this.method = method;
        this.path = path;
        createParametersMap();
    }

    @Override
    public String getBody() {
        return body;
    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public String getParameter(String nameParameter) {
        if (parameters.get(nameParameter) != null)
            return parameters.get(nameParameter).toString();
        return null;
    }

    @Override
    public void setParameter(String nameParameter, String value) {
        parameters.put(nameParameter, value);
        body = JSONValue.toJSONString(parameters);
    }

    @Override
    public void removeParameter(String nameParameter) {
        parameters.remove(nameParameter);
        body = JSONValue.toJSONString(parameters);
    }


    private void createParametersMap() throws ParseException {
        if (!body.equals(""))
            parameters = parseBody();
        else
            parameters = new HashMap<>();
    }

    private Map<String, Object> parseBody() throws ParseException {
        try {
            return (Map<String, Object>) new JSONParser().parse(body);
        } catch (ParseException e) {
            System.err.println("The body pass on Request is not a valid JSON\\n");
            throw e;
        }
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public URI getURI() {
        if(!method.equals("POST"))
            return getSafe(()->new URI("http://" + path + "?" + getValuesUri()));
        else
            return getSafe(()->new URI("http://" + path));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HttpRequest that = (HttpRequest) o;

        if (body != null ? !body.equals(that.body) : that.body != null) return false;
        if (method != null ? !method.equals(that.method) : that.method != null) return false;
        if (path != null ? !path.equals(that.path) : that.path != null) return false;

        return true;
    }


    private String getValuesUri() {
        String values = "";
        for (String key : parameters.keySet()) {
            values += "&" + key + "=" + parameters.get(key);
        }
        return values.replaceFirst("&","");
    }
}
