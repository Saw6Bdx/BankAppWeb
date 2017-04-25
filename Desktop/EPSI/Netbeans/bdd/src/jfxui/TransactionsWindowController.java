package jfxui;

import db.home.bank.Transactions;
import java.util.Calendar;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author Nicolas
 */
public class TransactionsWindowController extends ControllerBase{
    @FXML private TableView<Transactions> listTransactions;
    @FXML private ChoiceBox<Integer> monthChooser;
    
    private String flagAccountType;
    
    /**
     * 
     * @param flagAccountType Current or Savings
     */
    public void setFlagAccountType(String flagAccountType) {
        this.flagAccountType = flagAccountType;
    }
    
    @Override
    public void initialize(Mediator mediator){       
        //this.monthChooser.getItems().addAll("Month...", "January", "February", "March", "April", "May", "June", "July",  "August", "September", "October", "November", "December");
        this.monthChooser.getItems().addAll(Calendar.JANUARY, Calendar.FEBRUARY, Calendar.MARCH, Calendar.APRIL, Calendar.MAY, Calendar.JUNE, Calendar.JULY, Calendar.AUGUST, Calendar.SEPTEMBER, Calendar.OCTOBER, Calendar.NOVEMBER, Calendar.DECEMBER);
        
        //TypedQuery<Transactions> q = em.createQuery("SELECT t FROM Transactions t WHERE t.idAccount.id =:acc AND t.", Transactions.class);
        //this.monthChooser.setItems(FXCollections.observableList(q.setParameter("acc",  1).getResultList()));
        
    }
    
    public void initTransactionsWindowController(Mediator mediator){
        if (flagAccountType.equals("Current")) {
            EntityManager em = mediator.createEntityManager();
            TypedQuery<Transactions> q = em.createQuery("SELECT t FROM Transactions t WHERE t.idAccount.id =:acc", Transactions.class);
            this.listTransactions.setItems(FXCollections.observableList(q.setParameter("acc",  1).getResultList()));
        }       
        
        else { // Savings
            EntityManager em2 = mediator.createEntityManager();
            TypedQuery<Transactions> q2 = em2.createQuery("SELECT t FROM Transactions t WHERE t.idAccount.id =:acc", Transactions.class);
            this.listTransactions.setItems(FXCollections.observableList(q2.setParameter("acc", 2).getResultList()));
        }
    }

}
