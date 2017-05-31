package web;

import biz.exception.CountryCodeAlreadyExistingException;
import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import biz.manager.CountryCodeManager;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import model.CountryCode;

@WebServlet({"/countryCodes", "/index.html"})
public class CountryCodesServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @EJB
    private CountryCodeManager countryCodeManager;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/createCountryCode.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        try {
            String code = req.getParameter("code");
            CountryCode countryCode = countryCodeManager.save(req.getParameter("code"));
            resp.sendRedirect(req.getContextPath() + "/CountryCode?code=" + countryCode.getCode());
        } catch (NumberFormatException nfe) {
            req.setAttribute("error", "invalid.amount.format");
            getServletContext().getRequestDispatcher("/WEB-INF/jsp/createCountryCode.jsp").forward(req, resp);
        } catch (CountryCodeAlreadyExistingException ex) {
            log("le compte existe déjà", ex);
            req.setAttribute("error", "account.already.exists");
            getServletContext().getRequestDispatcher("/WEB-INF/jsp/createCountryCode.jsp").forward(req, resp);
        }
    }
}
