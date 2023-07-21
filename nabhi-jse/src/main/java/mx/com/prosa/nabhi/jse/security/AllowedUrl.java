package mx.com.prosa.nabhi.jse.security;

import org.springframework.http.HttpMethod;

public class AllowedUrl {

    private String[] url;
    private HttpMethod method;

    public AllowedUrl( HttpMethod method, String... url ) {
        this.url = url;
        this.method = method;
    }

    public String[] getUrl() {
        return url;
    }

    public void setUrl( String[] url ) {
        this.url = url;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod( HttpMethod method ) {
        this.method = method;
    }
}
