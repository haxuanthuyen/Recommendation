package com.hust.soict.hxt.recommendation.webservice.handler;

import com.hust.soict.hxt.recommendation.bo.ItemCluster;
import com.hust.soict.hxt.recommendation.bo.ItemData;
import com.hust.soict.hxt.recommendation.services.HistorySuggestion;
import com.hust.soict.hxt.recommendation.utils.FileIO;
import org.codehaus.jackson.map.ObjectMapper;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by thuyenhx on 4/9/16.
 */
public class RecHandler extends AbstractHandler {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private ObjectMapper mapper = new ObjectMapper();
    private HistorySuggestion historySuggestion;

    public RecHandler() {
        historySuggestion = new HistorySuggestion();
    }

    @Override
    public void handle(String s, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);

        String guid = baseRequest.getParameter("guid");
        if (guid != null) {
            Calendar cal = Calendar.getInstance();
            cal.set(2016,02,13);
            String startDate = sdf.format(cal.getTime());
            cal.add(Calendar.DAY_OF_MONTH, -5);
            String endDate = sdf.format(cal.getTime());

            List<ItemData> lstItem = historySuggestion.buildListSuggest(guid, startDate, endDate);
            String jsonData = mapper.writeValueAsString(lstItem);
            response.getWriter().println(jsonData);
        }
    }
}
