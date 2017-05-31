package web;

import biz.exception.CategoryDoesNotExistException;
import biz.exception.CountryCodeDoesNotExistException;
import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

import biz.manager.CountryCodeManager;
import model.CountryCode;

@Path("/api/countryCode")
public class CountryCodeResource {

	@EJB
	CountryCodeManager countryCodeManager;
	
	@GET
	public void getByCode(@QueryParam("code") String code, @Context HttpServletRequest req, @Context HttpServletResponse resp) throws ServletException, IOException, CategoryDoesNotExistException, CountryCodeDoesNotExistException{
		CountryCode countryCode = countryCodeManager.getByCode(code);
		req.setAttribute("code", countryCode);
		req.getRequestDispatcher("/WEB-INF/jsp/countryCode.jsp").forward(req, resp);
	}
}
