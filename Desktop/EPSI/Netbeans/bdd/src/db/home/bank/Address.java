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
    @NamedQuery(name = "Address.findAll", query = "SELECT a FROM Address a")
    , @NamedQuery(name = "Address.findById", query = "SELECT a FROM Address a WHERE a.id = :id")
    , @NamedQuery(name = "Address.findByLine1", query = "SELECT a FROM Address a WHERE a.line1 = :line1")
    , @NamedQuery(name = "Address.findByLine2", query = "SELECT a FROM Address a WHERE a.line2 = :line2")})
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Basic(optional = false)
    private String line1;
    private String line2;
    @JoinColumn(name = "idPostcode", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Postcode idPostcode;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAddress")
    private Collection<Agency> agencyCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAddress")
    private Collection<Holder> holderCollection;

    public Address() {
    }

    public Address(Integer id) {
        this.id = id;
    }

    public Address(Integer id, String line1) {
        this.id = id;
        this.line1 = line1;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public Postcode getIdPostcode() {
        return idPostcode;
    }

    public void setIdPostcode(Postcode idPostcode) {
        this.idPostcode = idPostcode;
    }

    @XmlTransient
    public Collection<Agency> getAgencyCollection() {
        return agencyCollection;
    }

    public void setAgencyCollection(Collection<Agency> agencyCollection) {
        this.agencyCollection = agencyCollection;
    }

    @XmlTransient
    public Collection<Holder> getHolderCollection() {
        return holderCollection;
    }

    public void setHolderCollection(Collection<Holder> holderCollection) {
        this.holderCollection = holderCollection;
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
        if (!(object instanceof Address)) {
            return false;
        }
        Address other = (Address) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.home.bank.Address[ id=" + id + " ]";
    }
    
}
