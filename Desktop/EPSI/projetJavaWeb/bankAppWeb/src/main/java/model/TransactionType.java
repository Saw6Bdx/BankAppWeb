/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
    @NamedQuery(name = "TransactionType.findAll", query = "SELECT t FROM TransactionType t")
    , @NamedQuery(name = "TransactionType.findById", query = "SELECT t FROM TransactionType t WHERE t.id = :id")
    , @NamedQuery(name = "TransactionType.findByType", query = "SELECT t FROM TransactionType t WHERE t.type = :type")})
public class TransactionType implements Serializable {
    /*  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	type VARCHAR(250) NOT NULL*/
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Basic(optional = false)
    private String type;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTransactionType")
    private Collection<Transactions> transactionsCollection;

    public TransactionType() {
    }

    public TransactionType(Integer id) {
        this.id = id;
    }

    public TransactionType(Integer id, String type) {
        Check.checkIsEmpty(type, "type");
        if(!type.matches("[a-zA-Z]+")){
            throw new IllegalArgumentException("type must contain only letters.");
        }
        this.id = id;
        this.type = type;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        Check.checkIsEmpty(type, "type");
        if(!type.matches("[a-zA-Z]+")){
            throw new IllegalArgumentException("type must contain only letters.");
        }
        this.type = type;
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
        if (!(object instanceof TransactionType)) {
            return false;
        }
        TransactionType other = (TransactionType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.type;
    }
    
}
