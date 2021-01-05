package com.codecool.hackernews;

import com.codecool.hackernews.service.NewsService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Handles an API queries.
 */
@WebServlet(name = "ApiServlet", urlPatterns = {"/api/*"}, loadOnStartup = 0)
public class ApiServlet extends HttpServlet {

    /**
     * Handles all API queries sent to the server by GET method.
     *
     * @param request            received request
     * @param response           reply response
     * @throws IOException       IO unexpected error exception
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        makeResponse(response, getData(request.getPathInfo(), request.getParameter("page")));
    }

    /*
     * Checks the boundary conditions and returns data from external API.
     * If anything goes wrong, return the information or the error in JSON format.
     */
    // TODO: implement or delete pageNumber!
    private String getData(String pathInfo, String pageNumber) throws IOException {
        if (pathInfo == null)  // entered "/api"
            return "{\"information\": \"This is the correct way to get an answer. However, please make a better request!\"}";

        String dataType = setDataType(pathInfo);
        if (dataType == null)  // bad API request
            return "{\"error\": \"Bad API request.\"}";

        NewsService data = new NewsService(dataType);
        return data.getData();
    }

    /* Sets the inquiries number and data type based on the path of the API request. */
    private String setDataType(String pathInfo) {
        switch (pathInfo) {
            case "/top" :
                return "news";
            default :
                return null;
        }
    }

    /* Sets the response and returns the data as JSON. */
    private void makeResponse(HttpServletResponse response, String data) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        out.println(data);
    }
}