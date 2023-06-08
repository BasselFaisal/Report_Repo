package com.example.stimulsoftdemo.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stimulsoft.base.StiJsonSaveMode;
import com.stimulsoft.base.json.JSONException;
import com.stimulsoft.base.serializing.StiDeserializationException;
import com.stimulsoft.report.StiReport;
import com.stimulsoft.report.StiSerializeManager;
import com.stimulsoft.webviewer.StiWebViewerOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

//@WebServlet(urlPatterns = "/hello")
//@RestController
//@CrossOrigin("*")
@RestController
public class HelloServlet {

    @Autowired
    ServletContext context;

    //extends HttpServlet {

//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        try {
//            resp.setContentType("text/html");
//            String html = "<!DOCTYPE html>\n" +
//                    "<html>\n" +
//                    "<body>\n" +
//                    "\n" +
//                    "<h1>This is a Heading</h1>\n" +
//                    "<p>This is a paragraph.</p>\n" +
//                    "<p>This is another paragraph.</p>\n" +
//                    "\n" +
//                    "</body>\n" +
//                    "</html>";
//            resp.getWriter().write(html);
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    //    @Override
    @GetMapping("/hellooo")
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        String reportPath = context.getRealPath("/reports/Dashboards.mrt");
        StiReport report = null;
        try {
            report = StiSerializeManager.deserializeReport(new File(reportPath));
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (StiDeserializationException e) {
            throw new RuntimeException(e);
        }

        report.render();
        StiWebViewerOptions options = new StiWebViewerOptions();

        req.setAttribute("reportPath", reportPath);
        req.setAttribute("report", report);
        req.setAttribute("options", options);

        HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper(resp) {
            @Override
            public PrintWriter getWriter() {
                return pw;
            }
        };

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/hello.jsp");
        dispatcher.forward(req, resp);

        System.out.println(sw);

    }

    @GetMapping("/addition")
    protected ResponseEntity<?> doGetNet() throws ServletException, IOException, JSONException {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        String reportPath = context.getRealPath("/reports/Dashboards.mrt");
        StiReport report = null;
        try {
            report = StiSerializeManager.deserializeReport(new File(reportPath));
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (StiDeserializationException e) {
            throw new RuntimeException(e);
        }

        report.render();
        ObjectMapper mapper = new ObjectMapper();

        Object reportFileContent = mapper.readValue(report.saveToJsonInternal(StiJsonSaveMode.Report), Map.class);

        return new ResponseEntity<>(reportFileContent, HttpStatus.OK);

    }
    //    @Override
    @GetMapping("/helloo")
    protected String doGet(Model model) throws ServletException, IOException {
//    protected void doGett(HttpServletRequest req, HttpServletResponse resp, Model model) throws ServletException, IOException {
        try {

            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);

//        req.setAttribute("message", "Hello, World!");

            String reportPath = context.getRealPath("/reports/Dashboards.mrt");
            StiReport report = null;
            report = StiSerializeManager.deserializeReport(new File(reportPath));

            report.render();
            StiWebViewerOptions options = new StiWebViewerOptions();
//            req.setAttribute("reportPath", reportPath);
//            req.setAttribute("report", report);
//            req.setAttribute("options", options);
//////
        model.addAttribute("reportPath", reportPath);
        model.addAttribute("report", report);
        model.addAttribute("options", options);
//
//            req.setAttribute("model", model);

//            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/hello.jsp");
//            dispatcher.forward(req, resp);

//            HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper(resp) {
//                @Override
//                public PrintWriter getWriter() {
//                    return pw;
//                }
//            };

//            dispatcher.forward(req, responseWrapper);

            System.out.println(sw);

        } catch (SAXException | StiDeserializationException e) {
            throw new RuntimeException(e);
        }
        return "hello";
    }

    @GetMapping("/hello1")
//    protected String doGet(Model model) throws ServletException, IOException {
    protected ResponseEntity<?> doGettt(HttpServletRequest req, HttpServletResponse resp, Model model) throws ServletException, IOException {
        try {

            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);

//        req.setAttribute("message", "Hello, World!");

            String reportPath = context.getRealPath("/reports/Dashboards.mrt");
            StiReport report = null;
            report = StiSerializeManager.deserializeReport(new File(reportPath));

            report.render();
            StiWebViewerOptions options = new StiWebViewerOptions();
            req.setAttribute("reportPath", reportPath);
            req.setAttribute("report", report);
            req.setAttribute("options", options);
////
//        model.addAttribute("reportPath", reportPath);
//        model.addAttribute("report", report);
//        model.addAttribute("options", options);
//
//            req.setAttribute("model", model);

            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/hello.jsp");
//            dispatcher.forward(req, resp);

            HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper(resp) {
                @Override
                public PrintWriter getWriter() {
                    return pw;
                }
            };

            dispatcher.forward(req, responseWrapper);

            System.out.println(sw);
            return new ResponseEntity<>(sw.toString(),HttpStatus.OK);

        } catch (SAXException | StiDeserializationException e) {
            throw new RuntimeException(e);
        }
//        return "hello";
    }


//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        try {
//            RequestDispatcher view = req.getRequestDispatcher("hello.jsp");
//            view.forward(req, resp);
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}