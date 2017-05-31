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

/**
 *
 * @author Mary, Nicolas ?
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CountryCode.findAll", query = "SELECT c FROM CountryCode c")
    , @NamedQuery(name = "CountryCode.findById", query = "SELECT c FROM CountryCode c WHERE c.id = :id")
    , @NamedQuery(name = "CountryCode.findByCode", query = "SELECT c FROM CountryCode c WHERE c.code = :code")})
public class CountryCode implements Serializable {

    /*  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	code CHAR(2) NOT NULL*/
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Basic(optional = false)
    private String code;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCountryCode")
    private Collection<Account> accountCollection;

    public CountryCode() {
    }

    public CountryCode(Integer id) {
        this.id = id;
    }

    public CountryCode(Integer id, String code) {
        if(code.isEmpty()){
            throw new IllegalArgumentException("code can't be empty");
        }
        this.id = id;
        this.code = code;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        if(code.isEmpty()){
            throw new IllegalArgumentException("code can't be empty");
        }
        this.code = code;
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
        if (!(object instanceof CountryCode)) {
            return false;
        }
        CountryCode other = (CountryCode) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.code;
    }
    
}
