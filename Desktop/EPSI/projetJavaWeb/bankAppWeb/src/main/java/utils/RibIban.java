package utils;

import model.Account;
import model.Agency;
import model.Bank;
import model.CountryCode;
import java.math.BigInteger;

/**
 * Generation and calculus of Rib and IBAN keys
 *
 * @author Nicolas
 */

public class RibIban {
    public static String generateRib(Agency agency, Account account, Bank bank){
        String Rib = BankIdentity(agency, account, bank) + generateRibKey(agency, account, bank); 
        return Rib;
    }
    
    public static String generateIban(Agency agency, Account account, Bank bank, CountryCode countryCode){
        String Iban = countryCode.getCode() + generateIbanKey(agency, account, bank, countryCode) + BankIdentity(agency, account, bank) + generateRibKey(agency, account, bank);
        return Iban;
    }
    
    private static String BankIdentity(Agency agency, Account account, Bank bank){
        String BankIdentity = bank.getBankCode() + agency.getAgencyCode() + account.getNumber();
        return BankIdentity;
    }
    
    private static int generateRibKey(Agency agency, Account account, Bank bank) {
        int RibKey = 97 - ((89 * Integer.parseInt(bank.getBankCode()) + 15 * Integer.parseInt(agency.getAgencyCode()) + 3 * Integer.parseInt(account.getNumber())) % 97);
        return RibKey;
    }
    
    private static Integer generateIbanKey(Agency agency, Account account, Bank bank, CountryCode countryCode) {
        Integer cc1 = Character.getNumericValue(countryCode.getCode().charAt(0));//get the numeric value of 1st letter of countryCode
        Integer cc2 = Character.getNumericValue(countryCode.getCode().charAt(1));//get the numeric value of 2nd letter of countryCode
        String cc1s = cc1.toString();
        String cc2s = cc2.toString();
        BigInteger bankIdentity = new BigInteger(generateRib(agency, account, bank) + cc1s + cc2s + "00");//too big for Long.parseLong
        BigInteger modulo = new BigInteger("97");      
        int IbanKey = 98 - (bankIdentity.mod(modulo)).intValue();
        return IbanKey;
    }
}