package jfxui;

import db.home.bank.Account;
import db.home.bank.AccountType;
import db.home.bank.Address;
import db.home.bank.Agency;
import db.home.bank.Bank;
import db.home.bank.CountryCode;
import db.home.bank.Postcode;

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
    
}