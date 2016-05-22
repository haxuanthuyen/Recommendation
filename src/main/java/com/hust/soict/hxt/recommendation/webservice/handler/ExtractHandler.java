package com.hust.soict.hxt.recommendation.webservice.handler;

import com.hust.soict.hxt.recommendation.algorithm.ner.NERProcess;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by thuyenhx on 5/22/16.
 */
public class ExtractHandler extends AbstractHandler {
    private static Logger logger = LoggerFactory.getLogger("suggestLog");
    private NERProcess nerProcess;

    public ExtractHandler() {
        nerProcess = NERProcess.getInstance();
    }

    @Override
    public void handle(String s, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);

        String title = baseRequest.getParameter("title");
        String type = baseRequest.getParameter("type");
        String output = nerProcess.tokenizer(title, Integer.valueOf(type));

        response.getWriter().println(output);
    }
}
