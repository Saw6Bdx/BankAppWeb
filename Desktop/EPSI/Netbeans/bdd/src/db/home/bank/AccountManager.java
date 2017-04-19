/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.home.bank;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Guest
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccountManager.findAll", query = "SELECT a FROM AccountManager a")
    , @NamedQuery(name = "AccountManager.findById", query = "SELECT a FROM AccountManager a WHERE a.id = :id")
    , @NamedQuery(name = "AccountManager.findByName", query = "SELECT a FROM AccountManager a WHERE a.name = :name")
    , @NamedQuery(name = "AccountManager.findByFirstName", query = "SELECT a FROM AccountManager a WHERE a.firstName = :firstName")
    , @NamedQuery(name = "AccountManager.findByPhone", query = "SELECT a FROM AccountManager a WHERE a.phone = :phone")
    , @NamedQuery(name = "AccountManager.findByEmail", query = "SELECT a FROM AccountManager a WHERE a.email = :email")
    , @NamedQuery(name = "AccountManager.findByAssignementDate", query = "SELECT a FROM AccountManager a WHERE a.assignementDate = :assignementDate")})
public class AccountManager implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Basic(optional = false)
    private String name;
    @Basic(optional = false)
    private String firstName;
    private String phone;
    private String email;
    @Basic(optional = false)
    @Temporal(TemporalType.DATE)
    private Date assignementDate;
    @JoinColumn(name = "idAgency", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Agency idAgency;

    public AccountManager() {
    }

    public AccountManager(Integer id) {
        this.id = id;
    }

    public AccountManager(Integer id, String name, String firstName, Date assignementDate) {
        this.id = id;
        this.name = name;
        this.firstName = firstName;
        this.assignementDate = assignementDate;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getAssignementDate() {
        return assignementDate;
    }

    public void setAssignementDate(Date assignementDate) {
        this.assignementDate = assignementDate;
    }

    public Agency getIdAgency() {
        return idAgency;
    }

    public void setIdAgency(Agency idAgency) {
        this.idAgency = idAgency;
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
        if (!(object instanceof AccountManager)) {
            return false;
        }
        AccountManager other = (AccountManager) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.home.bank.AccountManager[ id=" + id + " ]";
    }
    
}
