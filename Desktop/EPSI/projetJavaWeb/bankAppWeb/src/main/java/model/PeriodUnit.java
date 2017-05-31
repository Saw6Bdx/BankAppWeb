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
 * @author Guest
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PeriodUnit.findAll", query = "SELECT p FROM PeriodUnit p")
    , @NamedQuery(name = "PeriodUnit.findById", query = "SELECT p FROM PeriodUnit p WHERE p.id = :id")
    , @NamedQuery(name = "PeriodUnit.findByUnit", query = "SELECT p FROM PeriodUnit p WHERE p.unit = :unit")})
public class PeriodUnit implements Serializable {
    /*  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	unit VARCHAR(250) NOT NULL*/
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Basic(optional = false)
    private String unit;
    @OneToMany(mappedBy = "idPeriodUnit")
    private Collection<Transactions> transactionsCollection;

    public PeriodUnit() {
    }

    public PeriodUnit(Integer id) {
        this.id = id;
    }

    public PeriodUnit(Integer id, String unit) {
        Check.checkIsEmpty(unit, "unit");
        if (!unit.matches("[a-zA-Z]+")){
            throw new IllegalArgumentException("unit must contain only letters, ex monthly.");
        }
        this.id = id;
        this.unit = unit;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        Check.checkIsEmpty(unit, "unit");
        if (!unit.matches("[a-zA-Z]+")){
            throw new IllegalArgumentException("unit must contain only letters, ex monthly.");
        }
        this.unit = unit;
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
        if (!(object instanceof PeriodUnit)) {
            return false;
        }
        PeriodUnit other = (PeriodUnit) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.unit;
    }
    
}
