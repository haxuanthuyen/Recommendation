package com.hust.soict.hxt.recommendation.webservice.handler;

import com.hust.soict.hxt.recommendation.bo.ItemCluster;
import com.hust.soict.hxt.recommendation.services.HistorySuggestion;
import com.hust.soict.hxt.recommendation.utils.FileIO;
import org.codehaus.jackson.map.ObjectMapper;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by thuyenhx on 4/27/16.
 */
public class HistoryHandler extends AbstractHandler{
    private static Logger logger = LoggerFactory.getLogger("suggestLog");
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private ObjectMapper mapper = new ObjectMapper();
    private HistorySuggestion historySuggestion;

    public HistoryHandler() {
        historySuggestion = new HistorySuggestion();
    }

    @Override
    public void handle(String s, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);

        String guid = baseRequest.getParameter("guid");
        if (guid != null) {
            Calendar cal = Calendar.getInstance();
            cal.set(2016,02,13);
            String startDate = sdf.format(cal.getTime());
            cal.add(Calendar.DAY_OF_MONTH, -5);
            String endDate = sdf.format(cal.getTime());

            List<ItemCluster> lstItem = historySuggestion.buildListHistory(guid, startDate, endDate);
            String jsonData = mapper.writeValueAsString(lstItem);
            response.getWriter().println(jsonData);
        }
    }

}
