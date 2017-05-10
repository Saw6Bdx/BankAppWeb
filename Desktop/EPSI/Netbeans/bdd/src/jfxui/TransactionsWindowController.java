package jfxui;

import db.home.bank.Account;
import db.home.bank.Transactions;
import java.util.Calendar;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author Nicolas
 */
public class TransactionsWindowController extends ControllerBase {

    @FXML
    private TableView<Transactions> listTransactions;
    @FXML
    private ChoiceBox<String> monthChooser;
    @FXML
    private Label labelBalance;
    @FXML
    private Label labelInterest;

    private int flagAccount;
    private String currency = "€";

    /**
     * Method which assigns the Account id under mouse_clicked in AppWindow to
     * this.flagAccount
     *
     * @param flagAccount id under mouse_clicked
     */
    public void setFlagAccount(int flagAccount) {
        this.flagAccount = flagAccount;
    }

    @Override
    public void initialize(Mediator mediator) {
        this.monthChooser.getItems().addAll("Month...", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
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

    public void initTransactionsWindowController() {
        EntityManager em = getMediator().createEntityManager();
        TypedQuery<Transactions> q = em.createQuery("SELECT t FROM Transactions t WHERE t.idAccount.id =:acc", Transactions.class);
        List<Transactions> transactionsList = q.setParameter("acc", this.flagAccount).getResultList();
        listTransactions.setItems(FXCollections.observableList(transactionsList));

        //Getting the first balance
        TypedQuery<Account> qFirstBalance = em.createQuery("SELECT a FROM Account a WHERE a.id =:acc", Account.class);
        List<Account> accountList = qFirstBalance.setParameter("acc", this.flagAccount).getResultList();

        // Getting the amount of transactions
        Transactions transactions = new Transactions();
        double sum = accountList.get(0).getFirstBalance();
        int nbTransactions = transactionsList.size();
        for (int i = 0; i < nbTransactions; i++) {
            transactions = transactionsList.get(i);
            sum += transactions.getAmount();
        }
        // Setting balance
        this.labelBalance.setText(new Double(round(sum, 2)).toString() + " " + this.currency);

        /*
        *   Code pour essayer d'afficher les interêts en temps réel
        *   Encore quelques manips à réaliser, fonctionalité non utilisée pour le moment...
         */
 /*TypedQuery<Transactions> qSavings = em.createQuery("SELECT t FROM Transactions t WHERE t.idAccount.id =:acc AND FUNC('YEAR', t.date) =:year", Transactions.class);
        List<Transactions> savings = qSavings.setParameter("acc", this.flagAccount).setParameter("year", Calendar.getInstance().getTime()).getResultList();
        
        
        // Getting the amount of transactions function of interest rate
        Transactions transactionsSavings = new Transactions();
        double interestRate = accountList.get(0).getInterestRate();
        int nbSavings = transactionsList.size();
        
        double sumSavings = 0, sumWithdrawal = 0, yearBalance, result = 0;
        
        int year = Calendar.getInstance().getTime().getYear() + 1900;
        int daysPerYear;
        if(year % 4 == 0 && year % 100 != 0 || year % 400 == 0){
            daysPerYear = 366;
        }
        else{
            daysPerYear = 365;
        }
        
        for (int i = 0; i < nbSavings; i++) {
            transactionsSavings = savings.get(i);
            int month = savings.get(i).getDate().getMonth()+1;
            if(transactionsSavings.getAmount() < 0){
                sumWithdrawal += transactionsSavings.getAmount();
            }
            else if(transactionsSavings.getAmount() > 0){
                sumSavings += transactionsSavings.getAmount();
            }
            yearBalance = sum + sumSavings - sumWithdrawal;
            result += yearBalance * interestRate / 100 / 24 * nbDaysFortnightly(month, year) * 360 / daysPerYear / 15;
        }
        
        // Setting balance
        this.labelInterest.setText(new Double(round(result,2)).toString() + " " + this.currency);*/
        em.close();

    }

    /**
     * Function which calculate the round value of a double
     *
     * @param A, double to be rounded
     * @param B, precision (number after the coma)
     * @return rounded value
     */
    private double round(double A, int B) {
        return (double) ((int) (A * Math.pow(10, B) + .5)) / Math.pow(10, B);
    }

    @FXML
    private void handleChoiceBoxMonthChooser() {
        EntityManager em = getMediator().createEntityManager();
        TypedQuery<Transactions> q = em.createQuery("SELECT t FROM Transactions t WHERE t.idAccount.id =:acc AND FUNC('MONTH', t.date) =:month AND FUNC('YEAR', t.date) =:year", Transactions.class);
        this.listTransactions.setItems(FXCollections.observableList(q.setParameter("acc", this.flagAccount).setParameter("year", Calendar.getInstance().getTime()).setParameter("month", monthChooser(this.monthChooser.getValue())).getResultList()));
        em.close();
    }

    private byte nbDaysFortnightly(int month, int year) {
        byte nbDaysFortnightly = 0;

        switch (month - 1) {
            case Calendar.JANUARY:
            case Calendar.MARCH:
            case Calendar.MAY:
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.OCTOBER:
            case Calendar.DECEMBER:
                nbDaysFortnightly = 16;
                break;
            case Calendar.APRIL:
            case Calendar.JUNE:
            case Calendar.SEPTEMBER:
            case Calendar.NOVEMBER:
                nbDaysFortnightly = 15;
                break;
            case Calendar.FEBRUARY:
                if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
                    nbDaysFortnightly = 14;
                } else {
                    nbDaysFortnightly = 13;
                }
                break;
        }
        return nbDaysFortnightly;
    }

}
