package web;

import biz.exception.CategoryAlreadyExistingException;
import biz.manager.CategoryManager;
import java.io.IOException;
import javax.ejb.EJB;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Category;

@WebServlet("/categories")
//@WebServlet({"/categories", "/index.html"})
public class CategoriesServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    @EJB
    private CategoryManager categoryManager;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/readCategory.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        try {
            String categoryLabel = req.getParameter("categoryLabel");
            Category category = this.categoryManager.save("CategoriesServletL35");
            System.out.println("doPost(CategoriesServlet) - l36: "+categoryLabel);
            resp.sendRedirect(req.getContextPath() + "/category?categoryLabel=" + category.getLabel());
        } catch (CategoryAlreadyExistingException ex) {
            req.setAttribute("error", "category.already.exists");
            doGet(req, resp);
        }
    }
}
