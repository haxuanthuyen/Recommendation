//package com.hust.soict.hxt.recommendation.webservice.handler;
//
//import com.hxt.demo.xml.global.GlobalObject;
//import com.hxt.demo.xml.services.XmlService;
//import org.eclipse.jetty.server.Request;
//import org.eclipse.jetty.server.handler.AbstractHandler;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.HashMap;
//
///**
// * Created by thuyen225 on 20/10/2015.
// */
//public class MyHandler extends AbstractHandler {
//
//    private XmlService xmlService;
//
//    public MyHandler() {
//        xmlService = new XmlService();
//    }
//
//    @Override
//    public void handle(String s, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//        response.setContentType("text/xml;charset=utf-8");
//        response.setStatus(HttpServletResponse.SC_OK);
//        PrintWriter out = response.getWriter();
//
//
//        String paramShow = baseRequest.getParameter("show");
//        String paramType = baseRequest.getParameter("type");
//        String paramInput = baseRequest.getParameter("input");
//        String paramDelete = baseRequest.getParameter("delete");
//
//        String paramId = baseRequest.getParameter("id");
//        String paramCategory = baseRequest.getParameter("category");
//        String paramUrl = baseRequest.getParameter("url");
//        String paramTitle = baseRequest.getParameter("title");
//        String paramPrice = baseRequest.getParameter("price");
//        String paramDate = baseRequest.getParameter("date");
//
//        if (paramShow != null) {
//            if (paramShow.equals("1")) {
//                String contentResponse = GlobalObject.xmlContent;
//                out.format(contentResponse);
//                baseRequest.setHandled(true);
//            }
//        }else if (paramType != null && paramInput != null) {
//            String contentResponse = xmlService.searchXmlDoc(GlobalObject.xmlContent,paramType,paramInput);
//            out.format(contentResponse);
//            baseRequest.setHandled(true);
//        }else if (paramType != null && paramDelete != null) {
//            String contentResponse = xmlService.deleteXmlElement(GlobalObject.xmlContent,paramType,paramDelete);
//            GlobalObject.xmlContent = contentResponse;
//            out.format(contentResponse);
//            baseRequest.setHandled(true);
//        }else if (paramType != null &&
//                paramId != null &&
//                paramCategory != null &&
//                paramUrl != null &&
//                paramTitle != null &&
//                paramPrice != null &&
//                paramDate != null) {
//            HashMap<String,String> data = new HashMap<>();
//            data.put("id", paramId);
//            data.put("categoryId", paramCategory);
//            data.put("url", paramUrl);
//            data.put("title", paramTitle);
//            data.put("price", paramPrice);
//            data.put("date", paramDate);
//
//            String contentResponse = xmlService.updateXmlElement(GlobalObject.xmlContent, data);
//            GlobalObject.xmlContent = contentResponse;
//            out.format(contentResponse);
//            baseRequest.setHandled(true);
//        }
//
//    }
//}
