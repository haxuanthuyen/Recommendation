package com.hust.soict.hxt.recommendation;

import com.hust.soict.hxt.recommendation.services.GlobalResourceInit;
import com.hust.soict.hxt.recommendation.utils.FileIO;
import com.hust.soict.hxt.recommendation.webservice.handler.ExtractHandler;
import com.hust.soict.hxt.recommendation.webservice.handler.HistoryHandler;
import com.hust.soict.hxt.recommendation.webservice.handler.ModelHandler;
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

        ContextHandler contextNer = new ContextHandler("/ner");
        contextNer.setHandler(new ExtractHandler());

        ContextHandler contextModel = new ContextHandler("/model");
        contextModel.setHandler(new ModelHandler());

        ContextHandler extractItem = new ContextHandler("/extract.html");
        ResourceHandler resource_handler2 = new ResourceHandler();
        resource_handler2.setDirectoriesListed(true);
        resource_handler2.setWelcomeFiles(new String[]{"asset/js/extract.html"});
        resource_handler2.setResourceBase(".");
        extractItem.setHandler(resource_handler2);

        ContextHandler testModel = new ContextHandler("/model.html");
        ResourceHandler resource_handler3 = new ResourceHandler();
        resource_handler3.setDirectoriesListed(true);
        resource_handler3.setWelcomeFiles(new String[]{"asset/js/model.html"});
        resource_handler3.setResourceBase(".");
        testModel.setHandler(resource_handler3);

        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[] { context, contextXml, contextHistory, extractItem, contextNer, testModel, contextModel});
        server.setHandler(contexts);

        server.start();
        server.join();
    }
}
