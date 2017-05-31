package web;

import biz.exception.CategoryDoesNotExistException;
import biz.exception.CountryCodeDoesNotExistException;
import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import biz.manager.CountryCodeManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import model.CountryCode;

@WebServlet("/CountryCode")
public class CountryCodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@EJB
	CountryCodeManager countryCodeManager;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		CountryCode countryCode;
		try {
			countryCode = this.countryCodeManager.getByCode(req.getParameter("code"));
			req.setAttribute("code", countryCode);
                        System.out.println(countryCode);
			req.getRequestDispatcher("/WEB-INF/jsp/countryCode.jsp").forward(req, resp);
            } catch (CountryCodeDoesNotExistException ex) {
                Logger.getLogger(CountryCodeServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
	}
}
