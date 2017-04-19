/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.home.bank;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Guest
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Agency.findAll", query = "SELECT a FROM Agency a")
    , @NamedQuery(name = "Agency.findById", query = "SELECT a FROM Agency a WHERE a.id = :id")
    , @NamedQuery(name = "Agency.findByAgencyName", query = "SELECT a FROM Agency a WHERE a.agencyName = :agencyName")
    , @NamedQuery(name = "Agency.findByAgencyCode", query = "SELECT a FROM Agency a WHERE a.agencyCode = :agencyCode")})
public class Agency implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Basic(optional = false)
    private String agencyName;
    @Basic(optional = false)
    private String agencyCode;
    @JoinColumn(name = "idAddress", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Address idAddress;
    @JoinColumn(name = "idBank", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Bank idBank;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAgency")
    private Collection<AccountManager> accountManagerCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAgency")
    private Collection<Account> accountCollection;

    public Agency() {
    }

    public Agency(Integer id) {
        this.id = id;
    }

    public Agency(Integer id, String agencyName, String agencyCode) {
        this.id = id;
        this.agencyName = agencyName;
        this.agencyCode = agencyCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getAgencyCode() {
        return agencyCode;
    }

    public void setAgencyCode(String agencyCode) {
        this.agencyCode = agencyCode;
    }

    public Address getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(Address idAddress) {
        this.idAddress = idAddress;
    }

    public Bank getIdBank() {
        return idBank;
    }

    public void setIdBank(Bank idBank) {
        this.idBank = idBank;
    }

    @XmlTransient
    public Collection<AccountManager> getAccountManagerCollection() {
        return accountManagerCollection;
    }

    public void setAccountManagerCollection(Collection<AccountManager> accountManagerCollection) {
        this.accountManagerCollection = accountManagerCollection;
    }

    @XmlTransient
    public Collection<Account> getAccountCollection() {
        return accountCollection;
    }

    public void setAccountCollection(Collection<Account> accountCollection) {
        this.accountCollection = accountCollection;
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
        if (!(object instanceof Agency)) {
            return false;
        }
        Agency other = (Agency) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.home.bank.Agency[ id=" + id + " ]";
    }
    
}
