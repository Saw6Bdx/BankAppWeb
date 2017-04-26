/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.home.bank;

import java.io.Serializable; 
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Guest
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transactions.findAll", query = "SELECT t FROM Transactions t")
    , @NamedQuery(name = "Transactions.findById", query = "SELECT t FROM Transactions t WHERE t.id = :id")
    , @NamedQuery(name = "Transactions.findByLabel", query = "SELECT t FROM Transactions t WHERE t.label = :label")
    , @NamedQuery(name = "Transactions.findByAmount", query = "SELECT t FROM Transactions t WHERE t.amount = :amount")
    , @NamedQuery(name = "Transactions.findByDate", query = "SELECT t FROM Transactions t WHERE t.date = :date")
    , @NamedQuery(name = "Transactions.findByEndDate", query = "SELECT t FROM Transactions t WHERE t.endDate = :endDate")
    , @NamedQuery(name = "Transactions.findByDayNb", query = "SELECT t FROM Transactions t WHERE t.dayNb = :dayNb")
    , @NamedQuery(name = "Transactions.findByComment", query = "SELECT t FROM Transactions t WHERE t.comment = :comment")})
public class Transactions implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Basic(optional = false)
    private String label;
    @Basic(optional = false)
    private double amount;
    @Basic(optional = false)
    @Temporal(TemporalType.DATE)
    private Date date;
    @Basic(optional = false)
    @Temporal(TemporalType.DATE)
    private Date endDate;
    private Integer dayNb;
    private String comment;
    @JoinColumn(name = "idAccount", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Account idAccount;
    @JoinColumn(name = "idCategory", referencedColumnName = "id")
    @ManyToOne
    private Category idCategory;
    @JoinColumn(name = "idPeriodUnit", referencedColumnName = "id")
    @ManyToOne
    private PeriodUnit idPeriodUnit;
    @JoinColumn(name = "idRecipient", referencedColumnName = "id")
    @ManyToOne
    private Recipient idRecipient;
    @JoinColumn(name = "idTransactionType", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TransactionType idTransactionType;

    public Transactions() {
    }

    public Transactions(Integer id) {
        this.id = id;
    }

    public Transactions(Integer id, String label, double amount, Date date, Date endDate) {
        this.id = id;
        this.label = label;
        this.amount = amount;
        this.date = date;
        this.endDate = endDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getDayNb() {
        return dayNb;
    }

    public void setDayNb(Integer dayNb) {
        this.dayNb = dayNb;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Account getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(Account idAccount) {
        this.idAccount = idAccount;
    }

    public Category getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Category idCategory) {
        this.idCategory = idCategory;
    }

    public PeriodUnit getIdPeriodUnit() {
        return idPeriodUnit;
    }

    public void setIdPeriodUnit(PeriodUnit idPeriodUnit) {
        this.idPeriodUnit = idPeriodUnit;
    }

    public Recipient getIdRecipient() {
        return idRecipient;
    }

    public void setIdRecipient(Recipient idRecipient) {
        this.idRecipient = idRecipient;
    }

    public TransactionType getIdTransactionType() {
        return idTransactionType;
    }

    public void setIdTransactionType(TransactionType idTransactionType) {
        this.idTransactionType = idTransactionType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transactions)) {
            return false;
        }
        Transactions other = (Transactions) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.label;//"db.home.bank.Transactions[ id=" + id + " ]";
    }
    
    public String getFormatDate() {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(this.date);
    }
    
}
