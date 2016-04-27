package com.hust.soict.hxt.recommendation;

import com.hust.soict.hxt.recommendation.services.GlobalResourceInit;
import com.hust.soict.hxt.recommendation.utils.FileIO;
import com.hust.soict.hxt.recommendation.webservice.handler.HistoryHandler;
import com.hust.soict.hxt.recommendation.webservice.handler.RecHandler;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;

/**
 * Created by thuyenhx on 1/24/16.
 */
public class ItemSuggestMain {

    public static void main(String[] args) throws Exception {

        //init load model and data
        GlobalResourceInit.initModelMap();
        GlobalResourceInit.loadDataCache();

        // start webservice
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
    }
}
