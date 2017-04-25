/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.home.bank; 

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Guest
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Account.findAll", query = "SELECT a FROM Account a")
    , @NamedQuery(name = "Account.findById", query = "SELECT a FROM Account a WHERE a.id = :id")
    , @NamedQuery(name = "Account.findByNumber", query = "SELECT a FROM Account a WHERE a.number = :number")
    , @NamedQuery(name = "Account.findByCreationDate", query = "SELECT a FROM Account a WHERE a.creationDate = :creationDate")
    , @NamedQuery(name = "Account.findByFirstBalance", query = "SELECT a FROM Account a WHERE a.firstBalance = :firstBalance")
    , @NamedQuery(name = "Account.findByOverdraft", query = "SELECT a FROM Account a WHERE a.overdraft = :overdraft")
    , @NamedQuery(name = "Account.findByDescription", query = "SELECT a FROM Account a WHERE a.description = :description")
    , @NamedQuery(name = "Account.findByInterestRate", query = "SELECT a FROM Account a WHERE a.interestRate = :interestRate")})
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Basic(optional = false)
    private String number;
    @Basic(optional = false)
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    @Basic(optional = false)
    private double firstBalance;
    @Basic(optional = false)
    private double overdraft;
    private String description;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private Double interestRate;
    @JoinTable(name = "assign", joinColumns = {
        @JoinColumn(name = "idAccount", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "idHolder", referencedColumnName = "id")})
    @ManyToMany
    private Collection<Holder> holderCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAccount")
    private Collection<Transactions> transactionsCollection;
    @JoinColumn(name = "idAccountType", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private AccountType idAccountType;
    @JoinColumn(name = "idAgency", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Agency idAgency;
    @JoinColumn(name = "idCountryCode", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private CountryCode idCountryCode;

    public Account() {
    }

    public Account(Integer id) {
        this.id = id;
    }

    public Account(Integer id, String number, Date creationDate, double firstBalance, double overdraft) {
        this.id = id;
        this.number = number;
        this.creationDate = creationDate;
        this.firstBalance = firstBalance;
        this.overdraft = overdraft;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public double getFirstBalance() {
        return firstBalance;
    }

    public void setFirstBalance(double firstBalance) {
        this.firstBalance = firstBalance;
    }

    public double getOverdraft() {
        return overdraft;
    }

    public void setOverdraft(double overdraft) {
        this.overdraft = overdraft;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    @XmlTransient
    public Collection<Holder> getHolderCollection() {
        return holderCollection;
    }

    public void setHolderCollection(Collection<Holder> holderCollection) {
        this.holderCollection = holderCollection;
    }

    @XmlTransient
    public Collection<Transactions> getTransactionsCollection() {
        return transactionsCollection;
    }

    public void setTransactionsCollection(Collection<Transactions> transactionsCollection) {
        this.transactionsCollection = transactionsCollection;
    }

    public AccountType getIdAccountType() {
        return idAccountType;
    }

    public void setIdAccountType(AccountType idAccountType) {
        this.idAccountType = idAccountType;
    }

    public Agency getIdAgency() {
        return idAgency;
    }

    public void setIdAgency(Agency idAgency) {
        this.idAgency = idAgency;
    }

    public CountryCode getIdCountryCode() {
        return idCountryCode;
    }

    public void setIdCountryCode(CountryCode idCountryCode) {
        this.idCountryCode = idCountryCode;
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
        if (!(object instanceof Account)) {
            return false;
        }
        Account other = (Account) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.home.bank.Account[ id=" + id + " ]";
    }
    
}
