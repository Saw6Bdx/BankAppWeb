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
    @NamedQuery(name = "Postcode.findAll", query = "SELECT p FROM Postcode p")
    , @NamedQuery(name = "Postcode.findById", query = "SELECT p FROM Postcode p WHERE p.id = :id")
    , @NamedQuery(name = "Postcode.findByPostcode", query = "SELECT p FROM Postcode p WHERE p.postcode = :postcode")
    , @NamedQuery(name = "Postcode.findByCity", query = "SELECT p FROM Postcode p WHERE p.city = :city")})
public class Postcode implements Serializable {
    /*  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	postcode INT NOT NULL, /!\ changer en VARCHAR(5) ??? si oui ajouter les exceptions dans le constructeur
	city VARCHAR(250) NOT NULL*/
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Basic(optional = false)
    private int postcode;
    @Basic(optional = false)
    private String city;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPostcode")
    private Collection<Address> addressCollection;

    public Postcode() {
    }

    public Postcode(Integer id) {
        this.id = id;
    }

    public Postcode(Integer id, int postcode, String city) {
        Check.checkIsEmpty(city, "city");
        if (!city.matches("[a-zA-Z]+")){
            throw new IllegalArgumentException("unit must contain only letters, ex monthly.");
        }
        this.id = id;
        this.postcode = postcode;
        this.city = city;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getPostcode() {
        return this.postcode;
    }

    public void setPostcode(int postcode) {
        this.postcode = postcode;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        Check.checkIsEmpty(city, "city");
        if (!city.matches("[a-zA-Z]+")){
            throw new IllegalArgumentException("unit must contain only letters, ex monthly.");
        }
        this.city = city;
    }

    @XmlTransient
    public Collection<Address> getAddressCollection() {
        return this.addressCollection;
    }

    public void setAddressCollection(Collection<Address> addressCollection) {
        this.addressCollection = addressCollection;
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
        if (!(object instanceof Postcode)) {
            return false;
        }
        Postcode other = (Postcode) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return (this.postcode + " " + this.city);
    }
    
}
