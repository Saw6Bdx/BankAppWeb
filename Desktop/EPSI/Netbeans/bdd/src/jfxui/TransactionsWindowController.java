package jfxui;

import db.home.bank.Transactions;
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
    @FXML private ChoiceBox<String> monthChooser;
    
    private int flagAccount;
    
    /**
     * Method which assigns the flagAccount id under mouse_clicked in AppWindow to this.flagAccount
     * @param flagAccount id under mouse_clicked
     */
    public void setFlagAccount(int flagAccount) {
        this.flagAccount = flagAccount;
    }
    
    @Override
    public void initialize(Mediator mediator){       
        this.monthChooser.getItems().addAll("Month...", "January", "February", "March", "April", "May", "June", "July",  "August", "September", "October", "November", "December");
        this.monthChooser.getSelectionModel().selectFirst(); 
    }
    
    public int monthChooser(String str) {
        int id = 0;
        switch (str) {
            case "January":
                id = 1;
                break;
            case "February":
                id = 2;
                break;
            case "March":
                id = 3;
                break;
            case "April":
                id = 4;
                break;
            case "May":
                id = 5;
                break;
            case "June":
                id = 6;
                break;
            case "July":
                id = 7;
                break;
            case "August":
                id = 8;
                break;
            case "September":
                id = 9;
                break;
            case "October":
                id = 10;
                break;
            case "November":
                id = 11;
                break;
            case "December":
                id = 12;
                break;
        }
        return id;
    }
    
    public void initTransactionsWindowController(Mediator mediator){
        EntityManager em = mediator.createEntityManager();
        TypedQuery<Transactions> q = em.createQuery("SELECT t FROM Transactions t WHERE t.idAccount.id =:acc", Transactions.class);
        this.listTransactions.setItems(FXCollections.observableList(q.setParameter("acc", this.flagAccount).getResultList()));
        em.close();
    }
    
    @FXML
    private void handleChoiceBoxMonthChooser(){
        EntityManager em = getMediator().createEntityManager();
        TypedQuery<Transactions> q = em.createQuery("SELECT t FROM Transactions t WHERE t.idAccount.id =:acc AND FUNC('MONTH', t.date) =:month", Transactions.class);
        this.listTransactions.setItems(FXCollections.observableList(q.setParameter("acc", this.flagAccount).setParameter("month", monthChooser(this.monthChooser.getValue())).getResultList()));
        em.close();
    }
    
}