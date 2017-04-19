/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.home.bank;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    @NamedQuery(name = "Recipient.findAll", query = "SELECT r FROM Recipient r")
    , @NamedQuery(name = "Recipient.findById", query = "SELECT r FROM Recipient r WHERE r.id = :id")
    , @NamedQuery(name = "Recipient.findByName", query = "SELECT r FROM Recipient r WHERE r.name = :name")
    , @NamedQuery(name = "Recipient.findByIban", query = "SELECT r FROM Recipient r WHERE r.iban = :iban")})
public class Recipient implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Basic(optional = false)
    private String name;
    private String iban;
    @OneToMany(mappedBy = "idRecipient")
    private Collection<Transactions> transactionsCollection;

    public Recipient() {
    }

    public Recipient(Integer id) {
        this.id = id;
    }

    public Recipient(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    @XmlTransient
    public Collection<Transactions> getTransactionsCollection() {
        return transactionsCollection;
    }

    public void setTransactionsCollection(Collection<Transactions> transactionsCollection) {
        this.transactionsCollection = transactionsCollection;
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
        if (!(object instanceof Recipient)) {
            return false;
        }
        Recipient other = (Recipient) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.home.bank.Recipient[ id=" + id + " ]";
    }
    
}
