package web;

import biz.exception.CategoryDoesNotExistException;
import biz.manager.CategoryManager;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Category;


@WebServlet("/category")
public class CategoryServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    @EJB
    private CategoryManager categoryManager;
    
    /*@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Category category = this.categoryManager.getByLabel(req.getParameter("categoryLabel"));
            //Category category = this.categoryManager.getByLabel("Car");
            req.setAttribute("category", category);
            System.out.println("doGet(CategoryServlet): "+req.getParameter("categoryLabel"));
            req.getRequestDispatcher("/WEB-INF/jsp/readCategory.jsp").forward(req, resp);
        } catch (CategoryDoesNotExistException ex) {
            log("Category does not exist.", ex);
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        } 
    }*/
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Category category = this.categoryManager.getByLabel(req.getParameter("categoryLabel"));
            //Category category = this.categoryManager.getByLabel("Car");
            req.setAttribute("category", category);
            System.out.println("doPost(CategoryServlet): "+req.getParameter("categoryLabel"));
            req.getRequestDispatcher("/WEB-INF/jsp/readCategory.jsp").forward(req, resp);
        } catch (CategoryDoesNotExistException ex) {
            log("Category does not exist.", ex);
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }  
    }
    
}
