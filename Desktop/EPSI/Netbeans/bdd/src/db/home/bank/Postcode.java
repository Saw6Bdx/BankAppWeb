/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.home.bank;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Guest
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Postcode.findAll", query = "SELECT p FROM Postcode p")
    , @NamedQuery(name = "Postcode.findById", query = "SELECT p FROM Postcode p WHERE p.id = :id")
    , @NamedQuery(name = "Postcode.findByPostcode", query = "SELECT p FROM Postcode p WHERE p.postcode = :postcode")
    , @NamedQuery(name = "Postcode.findByCity", query = "SELECT p FROM Postcode p WHERE p.city = :city")})
public class Postcode implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Basic(optional = false)
    private int postcode;
    @Basic(optional = false)
    private String city;

    public Postcode() {
    }

    public Postcode(Integer id) {
        this.id = id;
    }

    public Postcode(Integer id, int postcode, String city) {
        this.id = id;
        this.postcode = postcode;
        this.city = city;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getPostcode() {
        return postcode;
    }

    public void setPostcode(int postcode) {
        this.postcode = postcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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
        //return this.id;
        return "db.home.bank.Postcode[ id=" + id + " ]";
    }
    
}
