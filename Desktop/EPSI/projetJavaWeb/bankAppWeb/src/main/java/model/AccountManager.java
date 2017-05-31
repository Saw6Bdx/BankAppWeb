/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
import utils.DateUtils;

import java.io.Serializable;
import java.util.Calendar;
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
 * @author Charlotte
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

    /*  name VARCHAR(250) NOT NULL,
	firstName VARCHAR(250) NOT NULL,
	phone VARCHAR(250),
	email VARCHAR(250),
	assignementDate DATE NOT NULL,
        idAgency INT NOT NULL,*/
    
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
        // levée d'exception nécessaire pour les id auto-incrémentés ?
        this.id = id;
    }

    public AccountManager(Integer id, String name, String firstName, Date assignementDate) {
        if(name.isEmpty()) {
            throw new IllegalArgumentException("The name cannot be empty.");
        }
        if(firstName.isEmpty()) {
            throw new IllegalArgumentException("The firstName cannot be empty.");
        }
        if(assignementDate == null) {
            throw new NullPointerException("The assignementDate cannot be null.");
        }
        if(assignementDate.getTime()>DateUtils.today().getTime()) {
            throw new IllegalArgumentException("assignementDate cannot be in the future.");
        }
        
        this.id = id;
        this.name = name;
        this.firstName = firstName;
        this.assignementDate = assignementDate;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        // levée d'exception nécessaire pour les id auto-incrémentés ?
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        if(name.isEmpty()) {
            throw new IllegalArgumentException("The name cannot be empty.");
        }
        this.name = name;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        if(firstName.isEmpty()) {
            throw new IllegalArgumentException("The firstName cannot be empty.");
        }
        this.firstName = firstName;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        if(phone == null) {
            throw new NullPointerException("The phone cannot be null.");
        }
        if(!phone.matches("[0-9]+")) {
            throw new IllegalArgumentException("The phone number must contain only numbers.");
        }
        this.phone = phone;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        if(email == null) {
            throw new NullPointerException("The email cannot be null.");
        }
        if(!email.matches(".*@.*")) {
            throw new IllegalArgumentException("The email address must contain an @.");
        }
        this.email = email;
    }

    public Date getAssignementDate() {
        return this.assignementDate;
    }

    public void setAssignementDate(Date assignementDate) {
        if(assignementDate == null) {
            throw new NullPointerException("The assignementDate cannot be null.");
        }
        if(assignementDate.getTime()>DateUtils.today().getTime()) {
            throw new IllegalArgumentException("assignementDate cannot be in the future.");
        }
        this.assignementDate = assignementDate;
    }

    public Agency getIdAgency() {
        return this.idAgency;
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
        return (this.name + " " + this.firstName);
    }
    
}
