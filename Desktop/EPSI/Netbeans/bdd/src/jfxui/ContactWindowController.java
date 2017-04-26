/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jfxui;

import db.home.bank.AccountManager;
import db.home.bank.Address;
import db.home.bank.Agency;
import db.home.bank.Bank;
import db.home.bank.Postcode;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import utils.AlertMessage;

/**
 * FXML Controller class
 *
 * @author Charlotte
 */
public class ContactWindowController extends ControllerBase {
    @FXML private Label labelBank, labelAgency, labelAddress, labelManager, labelPhone, labelEmail;
    
    private String flagAccountType;
    
    @Override
    public void initialize(Mediator mediator) {
    }
    
    public void setFlagAccountType(String flagAccountType) {
        this.flagAccountType = flagAccountType;
    }
    
    public void initContactWindowController(Mediator mediator) {
        try{
            EntityManager em = mediator.createEntityManager();

            TypedQuery<Bank> qBank = em.createQuery("SELECT b.name FROM Bank b JOIN b.agencyCollection ag JOIN ag.accountCollection a WHERE a.id =:acc", Bank.class);
            TypedQuery<Agency> qAgency = em.createQuery("SELECT ag.agencyName FROM Agency ag JOIN ag.accountCollection a WHERE a.id =:acc", Agency.class);
            TypedQuery<Address> qAddress = em.createQuery("SELECT ag.idAddress FROM Agency ag JOIN ag.accountCollection a WHERE a.id =:acc", Address.class);
            TypedQuery<Postcode> qPostcode = em.createQuery("SELECT ad.idPostcode FROM Address ad JOIN ad.agencyCollection ag JOIN ag.accountCollection a WHERE a.id =:acc", Postcode.class);
            TypedQuery<AccountManager> qManagerName = em.createQuery("SELECT am.name FROM AccountManager am JOIN am.idAgency ag JOIN ag.accountCollection a WHERE a.id =:acc", AccountManager.class);
            TypedQuery<AccountManager> qManagerFirstname = em.createQuery("SELECT am.firstName FROM AccountManager am JOIN am.idAgency ag JOIN ag.accountCollection a WHERE a.id =:acc", AccountManager.class);
            TypedQuery<AccountManager> qManagerPhone = em.createQuery("SELECT am.phone FROM AccountManager am JOIN am.idAgency ag JOIN ag.accountCollection a WHERE a.id =:acc", AccountManager.class);
            TypedQuery<AccountManager> qManagerEmail = em.createQuery("SELECT am.email FROM AccountManager am JOIN am.idAgency ag JOIN ag.accountCollection a WHERE a.id =:acc", AccountManager.class);

            int idAccount = 0;
            if (flagAccountType.equals("Current")) {
                idAccount = 1;
            }
            else {
                idAccount = 2;
            }
            
            qBank.setParameter("acc", idAccount);
            qAgency.setParameter("acc", idAccount);
            qAddress.setParameter("acc", idAccount);
            qPostcode.setParameter("acc", idAccount);
            qManagerName.setParameter("acc", idAccount);
            qManagerFirstname.setParameter("acc", idAccount);
            qManagerPhone.setParameter("acc", idAccount);
            qManagerEmail.setParameter("acc", idAccount);


            this.labelBank.setText(("Bank : " + qBank.getResultList()).replace("[", "").replace("]", ""));
            this.labelAgency.setText(("Agency : " + qAgency.getResultList()).replace("[", "").replace("]", ""));  
            this.labelAddress.setText(
                    ("Address : " 
                    + qAddress.getResultList()
                    + "\n                 "
                    + qPostcode.getResultList()).replace("[", "").replace("]", ""));
            this.labelManager.setText(
                    ("Manager : " 
                    + qManagerName.getResultList()
                    + " "
                    + qManagerFirstname.getResultList()).replace("[", "").replace("]", ""));
            this.labelPhone.setText(
                    ("Phone : " 
                    + qManagerPhone.getResultList()).replace("[", "").replace("]", ""));
            this.labelEmail.setText(
                    ("Email : " 
                    + qManagerEmail.getResultList()).replace("[", "").replace("]", ""));
        }
        catch(PersistenceException e){
            AlertMessage.processPersistenceException(e);
        }
    }
    
}