package web;

import model.Category;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.transaction.UserTransaction;

@WebServlet("/MyServlet")
public class MyServlet extends HttpServlet {

    @PersistenceUnit(unitName = "BankAppPU")
    //@PersistenceUnit("BankAppPU")
    private EntityManagerFactory emf;

/*    @Resource(name = "BankAppDataSource")
    private DataSource dataSource;

    @Resource
    private UserTransaction userTransaction;*/

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        EntityManager em = emf.createEntityManager();
        int firstResult = em.createNativeQuery("select 1", Long.class).getFirstResult();
        resp.getWriter().write("Test "+firstResult);
        em.close();
        /*try (Connection connection = dataSource.getConnection()) {

            try {
                userTransaction.begin();
                System.out.println("test userTransaction");
            } catch (Exception e) {
                // ...
            }

            //this.emf = Persistence.createEntityManagerFactory("BankAppPU");
            EntityManager em = this.emf.createEntityManager(); 
            TypedQuery<Category> qCategory = em.createQuery("SELECT a FROM Category a", Category.class);
            List<Category> categoryList = qCategory.getResultList();

            resp.setContentType("text/plain");
            resp.setCharacterEncoding("utf-8");
            resp.getWriter().write("Connexion établie");

        } catch (SQLException ex) {
            Logger.getLogger(MyServlet.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
    }

}
