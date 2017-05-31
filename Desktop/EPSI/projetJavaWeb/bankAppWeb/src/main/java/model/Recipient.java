/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

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
import utils.Check;

/**
 *
 * @author Charlotte
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Recipient.findAll", query = "SELECT r FROM Recipient r")
    , @NamedQuery(name = "Recipient.findById", query = "SELECT r FROM Recipient r WHERE r.id = :id")
    , @NamedQuery(name = "Recipient.findByName", query = "SELECT r FROM Recipient r WHERE r.name = :name")
    , @NamedQuery(name = "Recipient.findByIban", query = "SELECT r FROM Recipient r WHERE r.iban = :iban")})
public class Recipient implements Serializable {
    /*  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(250) NOT NULL,
	IBAN VARCHAR(250)*/
    
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
        Check.checkIsEmpty(name, "name");
        this.id = id;
        this.name = name;
    }
    
    public Recipient(Integer id, String name, String iban) {
        Check.checkIsEmpty(name, "name");
        Check.checkIsEmpty(iban, "iban");
        this.id = id;
        this.name = name;
        this.iban = iban;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        Check.checkIsEmpty(name, "name");
        this.name = name;
    }

    public String getIban() {
        return this.iban;
    }

    public void setIban(String iban) {
        Check.checkIsNull(iban, "iban");
        if(!iban.matches("^[A-Z]+[A-Z].*")) {
            throw new IllegalArgumentException("iban must start with two upper letters (contrycode).");
        }
        this.iban = iban;
    }

    @XmlTransient
    public Collection<Transactions> getTransactionsCollection() {
        return this.transactionsCollection;
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
        return this.name;
    }
    
}
