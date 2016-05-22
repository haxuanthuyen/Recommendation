package com.hust.soict.hxt.recommendation.webservice.handler;

import com.hust.soict.hxt.recommendation.algorithm.ner.BuildModel;
import com.hust.soict.hxt.recommendation.bo.ModelData;
import org.codehaus.jackson.map.ObjectMapper;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by thuyenhx on 5/22/16.
 */
public class ModelHandler extends AbstractHandler {
    private static Logger logger = LoggerFactory.getLogger("suggestLog");
    private ObjectMapper mapper = new ObjectMapper();
    private BuildModel buildModel;

    public ModelHandler() {
        buildModel = new BuildModel();
    }

    @Override
    public void handle(String s, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);

        String type = baseRequest.getParameter("type");

        List<ModelData> lstData = buildModel.testModel(type);
        String jsonData = mapper.writeValueAsString(lstData);
        response.getWriter().println(jsonData);
    }
}
