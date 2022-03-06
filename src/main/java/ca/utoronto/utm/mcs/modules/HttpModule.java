package ca.utoronto.utm.mcs.modules;

import com.sun.net.httpserver.HttpServer;
import dagger.Module;
import dagger.Provides;

import java.io.IOException;
import java.net.InetSocketAddress;

@Module
public class HttpModule {
    private static HttpServer server;
    
    @Provides
    public HttpServer provideHttpServer() {
        try {
            server = HttpServer.create(new InetSocketAddress(8080), 0);
            return server;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
