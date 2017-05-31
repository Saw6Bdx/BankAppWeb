/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import utils.Check;
import utils.DateUtils;
import utils.Valid;

/**
 *
 * @author Mary, Nicolas ?
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Holder.findAll", query = "SELECT h FROM Holder h")
    , @NamedQuery(name = "Holder.findById", query = "SELECT h FROM Holder h WHERE h.id = :id")
    , @NamedQuery(name = "Holder.findByName", query = "SELECT h FROM Holder h WHERE h.name = :name")
    , @NamedQuery(name = "Holder.findByFirstname", query = "SELECT h FROM Holder h WHERE h.firstname = :firstname")
    , @NamedQuery(name = "Holder.findByPhone", query = "SELECT h FROM Holder h WHERE h.phone = :phone")
    , @NamedQuery(name = "Holder.findByLogin", query = "SELECT h FROM Holder h WHERE h.login = :login")
    , @NamedQuery(name = "Holder.findByPassword", query = "SELECT h FROM Holder h WHERE h.password = :password")})
public class Holder implements Serializable {
    /*  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(250) NOT NULL,
	firstname VARCHAR(250) NOT NULL,
        birthday DATE,
	phone VARCHAR(250),
	login VARCHAR(250) NOT NULL,
	password VARCHAR(250) NOT NULL,
	idAddress INT NOT NULL,*/
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Basic(optional = false)
    private String name;
    @Basic(optional = false)
    private String firstname;
    @Temporal(TemporalType.DATE)
    private Date birthday;
    private String phone;
    @Basic(optional = false)
    private String login;
    @Basic(optional = false)
    private String password;
    @ManyToMany(mappedBy = "holderCollection")
    private Collection<Account> accountCollection;
    @JoinColumn(name = "idAddress", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Address idAddress;

    public Holder() {
    }

    public Holder(Integer id) {
        this.id = id;
    }

    public Holder(Integer id, String name, String firstname, String login, String password) {
        Check.checkIsEmpty(name,"name");
        Check.checkIsEmpty(firstname,"firstname");
        Check.checkIsEmpty(login,"login");
        Check.checkIsEmpty(password,"password");

        this.id = id;
        this.name = name;
        this.firstname = firstname;
        this.login = login;
        this.password = password;
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
        Check.checkIsEmpty(name,"name");
        this.name = name;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public void setFirstname(String firstname) {
        Check.checkIsEmpty(firstname,"firstname");
        this.firstname = firstname;
    }
    
    public String getLogin() {
        return this.login;
    }
    
    public void setLogin(String login) {
        Check.checkIsEmpty(login,"login");
        this.login = login;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        Check.checkIsEmpty(password,"password");
        this.password = password;
    }

    public Date getBirthday() {
        return this.birthday;
    }

    public void setBirthday(Date birthday) {
        Check.checkIsNull(birthday,"birthday");
        if (birthday.getTime() > DateUtils.today().getTime()) {
           throw new IllegalArgumentException("birthday cannot be in the future.");
        }
        this.birthday = birthday;
    }
    
    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        Check.checkIsNull(phone,"phone");
        if(!phone.matches("[0-9]+") || phone.length() < 10){
            throw new IllegalArgumentException("phone must contain at least 10 digits.");
        }
        this.phone = phone;
    }

    @XmlTransient
    public Collection<Account> getAccountCollection() {
        return this.accountCollection;
    }

    public void setAccountCollection(Collection<Account> accountCollection) {
        this.accountCollection = accountCollection;
    }

    public Address getIdAddress() {
        return this.idAddress;
    }

    public void setIdAddress(Address idAddress) {
        this.idAddress = idAddress;
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
        if (!(object instanceof Holder)) {
            return false;
        }
        Holder other = (Holder) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.name + " " + this.firstname;
    }
}
