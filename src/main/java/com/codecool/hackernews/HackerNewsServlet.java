package com.codecool.hackernews;

import com.codecool.hackernews.common.Const;
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
@WebServlet(name = "hackerNewsServlet", urlPatterns = {"", "/top"}, loadOnStartup = 1)
public class HackerNewsServlet extends javax.servlet.http.HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String pageNumber = request.getParameter("page");

        List<NewsModel> news = new NewsDao(Const.DataType.TOP, pageNumber).getNews();
        String pageTemplate = new NewsTemplate("", pageNumber, news).getTemplate();

        PrintWriter out = response.getWriter();
        out.println(pageTemplate);
    }
}
