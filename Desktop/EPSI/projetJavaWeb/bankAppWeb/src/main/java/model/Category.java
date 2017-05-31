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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import utils.Check;

/**
 *
 * @author Mary, Nicolas ?
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Category.findAll", query = "SELECT c FROM Category c")
    , @NamedQuery(name = "Category.findById", query = "SELECT c FROM Category c WHERE c.id = :id")
    , @NamedQuery(name = "Category.findByLabel", query = "SELECT c FROM Category c WHERE c.label = :label")})
public class Category implements Serializable {
    /*  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	label VARCHAR(250) NOT NULL, /!\ oubli -> modifier en NOT NULL dans la bdd
	idLabel INT,*/
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    private String label;
    @OneToMany(mappedBy = "idCategory")
    private Collection<Transactions> transactionsCollection;
    @OneToMany(mappedBy = "idLabel", cascade = CascadeType.PERSIST)
    private Collection<Category> categoryCollection;
    @JoinColumn(name = "idLabel", referencedColumnName = "id")
    @ManyToOne
    private Category idLabel;

    public void put(Category cat1, Category cat2) {
        
    }
    
    public Category() {
    }

    public Category(Integer id) {
        this.id = id;
    }
    
    public Category(Integer id, String label) {
        Check.checkIsEmpty(label, "label");
        this.id = id;
        this.label = label;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        Check.checkIsEmpty(label, "label");
        this.label = label;
    }

    @XmlTransient
    public Collection<Transactions> getTransactionsCollection() {
        return this.transactionsCollection;
    }

    public void setTransactionsCollection(Collection<Transactions> transactionsCollection) {
        this.transactionsCollection = transactionsCollection;
    }

    @XmlTransient
    public Collection<Category> getCategoryCollection() {
        return this.categoryCollection;
    }

    public void setCategoryCollection(Collection<Category> categoryCollection) {
        this.categoryCollection = categoryCollection;
    }

    public Category getIdLabel() {
        return this.idLabel;
    }

    public void setIdLabel(Category idLabel) {
        this.idLabel = idLabel;
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
        if (!(object instanceof Category)) {
            return false;
        }
        Category other = (Category) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.label;
    }
    
}
