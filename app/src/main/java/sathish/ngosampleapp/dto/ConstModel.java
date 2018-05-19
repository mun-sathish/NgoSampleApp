package sathish.ngosampleapp.dto;

public class ConstModel {
    private int method;
    private String uri;
    public ConstModel(String uri, int method) {
        this.method = method;
        this.uri = uri;
    }

    public int getMethod() {
        return method;
    }

    public void setMethod(int method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
