package db.home.bank;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mary
 */
public class TestAccount {
    //Account(Integer id, String number, Date creationDate, double firstBalance, double overdraft)
    
    @Test(expected = NullPointerException.class)
    public void testAccount_NumberIsNull(){
        new Account(1, null, new Date(0), 150.0, 200.0); 
    }
    @Test(expected = IllegalArgumentException.class)
    public void testAccount_NumberIsEmpty(){
        new Account(1, "", new Date(0), 150.0, 200.0);
    }
    @Test(expected = NullPointerException.class)
    public void testSetAccount_NumberIsNull(){
        this.tested = new Account();
        this.tested.setNumber(null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testSetAccount_NumberIsEmpty(){
        this.tested = new Account();
        this.tested.setNumber("");
    }
    
    @Test(expected = NullPointerException.class)
    public void testAccount_CreationDateIsNull(){
        new Account(1, "foo", null, 150.0, 200.0); 
    }
    @Test(expected = IllegalArgumentException.class)
    public void testAccount_CreateDateIntheFuture() {
        Calendar cal = new GregorianCalendar(2050, Calendar.APRIL, 13, 12, 31, 15);
        this.tested = new Account(1, "foo", cal.getTime(), 150.0, 200.0);
    }
    @Test(expected = NullPointerException.class)
    public void testSetAccount_CreationDateIsNull(){
        this.tested = new Account();
        this.tested.setCreationDate(null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testSetDate_CreateDateIntheFuture() {
        Calendar cal = new GregorianCalendar(2050, Calendar.APRIL, 13, 12, 31, 15);
        this.tested = new Account();
        this.tested.setCreationDate(cal.getTime());
    }
    
    @Test(expected = NullPointerException.class)
    public void testSetAccount_DescriptionIsNull(){
        this.tested = new Account(1, "foo", new Date(0), 150.0, 200.0);
        this.tested.setDescription(null); 
    }
    
    @Test
    public void testGetId(){
        this.tested = new Account(1, "foo", new Date(0), 150.0, 200.0);
        assertEquals((Integer)1, this.tested.getId());
    }
    
    @Test
    public void testGetNumber(){
        this.tested = new Account(1, "foo", new Date(0), 150.0, 200.0);
        assertEquals("foo", this.tested.getNumber());
    }
    
    @Test
    public void testGetFirstBalance(){
        this.tested = new Account(1, "foo", new Date(0), 150.0, 200.0);
        assertEquals(150.00, this.tested.getFirstBalance(), 0.00);
    }
    
    @Test
    public void testGetOverdraft(){
        this.tested = new Account(1, "foo", new Date(0), 150.0, 200.0);
        assertEquals(200.00, this.tested.getOverdraft(), 0.00);
    }
    
    @Test
    public void testGetDescription(){
        this.tested = new Account(1, "foo", new Date(0), 150.0, 200.0);
        this.tested.setDescription("bar2");
        assertEquals("bar2", this.tested.getDescription());
    }
    
    @Test
    public void testGetInterestRate(){
        this.tested = new Account(1, "foo", new Date(0), 150.0, 200.0);
        this.tested.setInterestRate(0.75);
        assertEquals(0.75, this.tested.getInterestRate(), 0.00);
    }
        
    private Account tested;
}
