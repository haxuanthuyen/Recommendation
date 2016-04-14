package com.hust.soict.hxt.recommendation.webservice.handler;

import com.hust.soict.hxt.recommendation.utils.FileIO;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by thuyenhx on 4/9/16.
 */
public class RecHandler extends AbstractHandler {
    @Override
    public void handle(String s, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter out = response.getWriter();

        String content = FileIO.getContentFile("asset/test.json");
        out.format(content);
        baseRequest.setHandled(true);
    }
}
