package jfxui;

import db.home.bank.Account;
import db.home.bank.AccountType;
import db.home.bank.Address;
import db.home.bank.Agency;
import db.home.bank.Bank;
import db.home.bank.CountryCode;
import db.home.bank.Postcode;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

/**
 *  
 * @author Mary
 */
public class NewAccountWindowController extends ControllerBase {
    
    private Agency agency;
    private Bank bank;
    private Address address;
    private Postcode postCode;
    private Account account;
    private AccountType accountType;
    private CountryCode countryCode;
        
    private int flagHolder;
    
    @Override 
    public void initialize(Mediator mediator) {
        
    }
    
    public Bank getBank() {
        return this.bank;
    }
    
    public void setBank(Bank bank) {
        this.bank = bank;
    }
    
    public Address getAddress() {
        return this.address;
    }
    
    public void setAddress(Address address) {
        this.address = address;
    }
    
    public Postcode getPostcode() {
        return this.postCode;
    }
    
    public void setPostcode(Postcode postCode) {
        this.postCode = postCode;
    }
    
    public Account getAccount() {
        return this.account;
    }
    
    public void setAccount(Account account) {
        this.account = account;
    }

    public AccountType getAccountType() {
        return this.accountType;
    }
    
    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public CountryCode getCountryCode() {
        return this.countryCode;
    }
    
    public void setCountryCode(CountryCode countryCode) {
        this.countryCode = countryCode;
    }
    
    public Agency getAgency() {
        return this.agency;
    }
    
    public void setAgency(Agency agency) {
        this.agency = agency;
    }
    
    
    public int idAccountType(String str) {
        
        int id = 0;
        
        try {
            EntityManager em = getMediator().createEntityManager();
            TypedQuery<AccountType> qAccountType = em.createNamedQuery("AccountType.findAll", AccountType.class);
            List<AccountType> accountTypeList = qAccountType.getResultList();
            
            for ( int i = 0 ; i < accountTypeList.size() ; i++ ) {
                if ( str.equals(accountTypeList.get(id).getType()) ) {
                    id = i;
                }
            }
            
            em.close();
        } catch (PersistenceException e) {
            
        }
        return id;
    }
    
    
    public int idCountryCode(String str) {
        int id = 1;
        try {
            EntityManager em = getMediator().createEntityManager();
            TypedQuery<CountryCode> qCountryCode = em.createNamedQuery("CountryCode.findAll", CountryCode.class);
            List<CountryCode> countryCodeList = qCountryCode.getResultList();
            
            for ( int i = 0 ; i < countryCodeList.size() ; i++ ) {
                if ( str.equals(countryCodeList.get(id).getCode()) ) {
                    id = i;
                }
            }
            
            em.close();
        } catch (PersistenceException e) {
            
        }
        return id;
    }
    
    
    public int idBank(String str) {
        int id = 0;
        try {
            EntityManager em = getMediator().createEntityManager();
            TypedQuery<Bank> qBank = em.createNamedQuery("Bank.findAll", Bank.class);
            List<Bank> bankList = qBank.getResultList();
            
            for ( int i = 0 ; i < bankList.size() ; i++ ) {
                if ( str.equals(bankList.get(id).getName()) ) {
                    id = i;
                }
            }
            
            em.close();
        } catch (PersistenceException e) {
            
        }
        return id;
    }
    
    public void setFlagHolder(int flagHolder) {
        this.flagHolder = flagHolder;
    }
    
    public int getFlagHolder() {
        return this.flagHolder;
    }
    
}