package com.codecool.hackernews;

import com.codecool.hackernews.common.NewsConst;
import com.codecool.hackernews.common.NewsHandler;
import com.codecool.hackernews.dao.NewsDao;
import com.codecool.hackernews.models.NewsModel;
import com.codecool.hackernews.templates.NewsTemplate;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Supports news pages.
 */
@WebServlet(name = "hackerNewsServlet", urlPatterns = {"", "/top", "/newest", "/jobs"}, loadOnStartup = 1)
public class HackerNewsServlet extends javax.servlet.http.HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String newsType = getNewsType(request.getServletPath());
        String pageNumber = request.getParameter("page");

        List<NewsModel> news = new NewsDao(newsType, pageNumber).getNews();
        String pageTemplate = new NewsTemplate(news, newsType, pageNumber).getTemplate();

        PrintWriter out = response.getWriter();
        out.println(pageTemplate);
    }

    /* Returns the news type based on the servlet path. */
    private String getNewsType(String servletPath) {
        String newsType = NewsHandler.getNewsType(servletPath);
        if (newsType == null)  // default page
            newsType = NewsConst.TOP.getType();

        return newsType;
    }
}
