package br.com.simplechat.app.application;

import br.com.simplechat.app.application.annotation.Get;
import br.com.simplechat.app.application.annotation.Rest;
import br.com.simplechat.app.handler.GenericHandler;
import com.sun.net.httpserver.HttpServer;
import org.reflections.Reflections;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Application {
    public static void init() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(4200), 0);
        initiateHttpContexts(server);
        server.setExecutor(null);
        server.start();
    }

    private static void initiateHttpContexts(HttpServer server) {
        Reflections reflections = new Reflections("br.com.simplechat.app");

        var classes = reflections.getTypesAnnotatedWith(Rest.class);

        for (Class<?> classy : classes) {
            Rest restClass = classy.getAnnotation(Rest.class);

            String basePath = restClass.path();

            var methods = classy.getMethods();

            Map<String, GenericHandler> pathsToHandler = new HashMap<String, GenericHandler>();

            for (var method : methods) {
                var methodGet = method.getAnnotation(Get.class);

                if (Objects.nonNull(methodGet)) {
                    var handler = new GenericHandler();
                    String getResource = methodGet.path();

                    StringBuilder pathBuilder = new StringBuilder(basePath);
                    pathBuilder.append(getResource);

                    handler.setController(classy);
                    handler.setMethod(method);

                    pathsToHandler.putIfAbsent(pathBuilder.toString(), handler);
                }
            }

            for (String key : pathsToHandler.keySet()) {
                pathsToHandler.get(key);
                server.createContext(key, pathsToHandler.get(key));
            }
        }


    }
}
