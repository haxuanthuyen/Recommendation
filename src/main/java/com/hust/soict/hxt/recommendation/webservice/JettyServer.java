package com.hust.soict.hxt.recommendation.webservice;

import com.hust.soict.hxt.recommendation.webservice.handler.HistoryHandler;
import com.hust.soict.hxt.recommendation.webservice.handler.RecHandler;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;

/**
 * Created by thuyen225 on 20/10/2015.
 */
public class JettyServer {

    public static void main(String[] args) {

        try {
            Server server = new Server(6677);
            ContextHandler context = new ContextHandler("/index.html");
            ResourceHandler resource_handler = new ResourceHandler();
            resource_handler.setDirectoriesListed(true);
            resource_handler.setWelcomeFiles(new String[]{"asset/js/recommend.html"});
            resource_handler.setResourceBase(".");
            context.setHandler(resource_handler);

            ContextHandler contextXml = new ContextHandler("/json");
            contextXml.setHandler(new RecHandler());

            ContextHandler contextHistory = new ContextHandler("/history");
            contextHistory.setHandler(new HistoryHandler());

            ContextHandlerCollection contexts = new ContextHandlerCollection();
            contexts.setHandlers(new Handler[] { context, contextXml, contextHistory});
            server.setHandler(contexts);

            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
