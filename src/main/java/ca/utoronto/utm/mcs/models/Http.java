package ca.utoronto.utm.mcs.models;

import com.sun.net.httpserver.HttpServer;

import javax.inject.Inject;

public class Http {

    private HttpServer server;

    @Inject
    public Http(HttpServer server) {
        this.server = server;
    }

    public HttpServer getServer() {
        return this.server;
    }

    public void setServer(HttpServer server) {
        this.server = server;
    }
}
