package ca.utoronto.utm.mcs;

import ca.utoronto.utm.mcs.components.DaggerHttpComponent;
import ca.utoronto.utm.mcs.models.Http;
import java.io.IOException;


public class App {
    static int port = 8080;

    public static void main(String[] args) throws IOException {
        Http http = DaggerHttpComponent.create().buildHttpServer();

        //Create your server context here
        http.getServer().createContext("/api/v1/post", new PostHandle());

        //Start server
        http.getServer().start();

        System.out.printf("Server started on port %d\n", port);
    }
}
