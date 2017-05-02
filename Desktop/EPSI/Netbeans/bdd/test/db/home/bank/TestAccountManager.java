
package db.home.bank;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Charlotte
 */
public class TestAccountManager {
    // AccountManager(Integer id, String name, String firstName, Date assignementDate)
    
    @Test(expected = NullPointerException.class)
    public void testAccountManager_NameIsNull() {
        new AccountManager(1, null, "bar", new Date(0));
    }
    @Test(expected = IllegalArgumentException.class)
    public void testAccountManager_NameIsEmpty() {
        new AccountManager(1, "", "bar", new Date(0));
    }
    @Test(expected = NullPointerException.class)
    public void testSetAccountManager_NameIsNull() {
        this.tested = new AccountManager();
        this.tested.setName(null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testSetAccountManager_NameIsEmpty() {
        this.tested = new AccountManager();
        this.tested.setName("");
    }
    
    @Test(expected = NullPointerException.class)
    public void testAccountManager_FirstNameIsNull() {
        new AccountManager(1, "foo", null, new Date(0));
    }
    @Test(expected = IllegalArgumentException.class)
    public void testAccountManager_FirstNameIsEmpty() {
        new AccountManager(1, "foo", "",  new Date(0));
    }
    @Test(expected = NullPointerException.class)
    public void testSetAccountManager_FirstNameIsNull() {
        this.tested = new AccountManager();
        this.tested.setFirstName(null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testSetAccountManager_FirstNameIsEmpty() {
        this.tested = new AccountManager();
        this.tested.setFirstName("");
    }
    
    @Test(expected = NullPointerException.class)
    public void testAccountManager_DateIsNull() {
        new AccountManager(1, "foo", "bar", null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testAccountManager_assignementDateInTheFuture() {
        new AccountManager(1, "foo", "bar", new GregorianCalendar(2017, Calendar.JULY, 10, 12, 31, 15).getTime());
    }
    @Test(expected = NullPointerException.class)
    public void testSetAccountManager_DateIsNull() {
        this.tested = new AccountManager();
        this.tested.setAssignementDate(null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testSetAccountManager_assignementDateInTheFuture() {
        this.tested = new AccountManager();
        this.tested.setAssignementDate(new GregorianCalendar(2050, Calendar.JULY, 10, 12, 31, 15).getTime());
    }

    @Test(expected = NullPointerException.class)
    public void testAccountManager_PhoneIsNull() {
        this.tested = new AccountManager(1, "foo", "bar", new Date(0));
        this.tested.setPhone(null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testAccountManager_PhoneIsInvalid() {
        this.tested = new AccountManager(1, "foo", "bar", new Date(0));
        this.tested.setPhone("00000H0000");
    }
    
    @Test(expected = NullPointerException.class)
    public void testAccountManager_EmailIsNull() {
        this.tested = new AccountManager(1, "foo", "bar", new Date(0));
        this.tested.setEmail(null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testAccountManager_EmailIsInvalid() {
        this.tested = new AccountManager(1, "foo", "bar", new Date(0));
        this.tested.setEmail("foo.bar foobar.fr");
    }
    
    @Test
    public void testGetId(){
        this.tested = new AccountManager(1, "foo", "bar", new Date(0));
        assertEquals((Integer)1, this.tested.getId());
    }
    
    @Test
    public void testGetManager_name() {
        this.tested = new AccountManager(1, "foo", "bar", new Date(0));
        assertEquals("foo", this.tested.getName());
    }

    @Test
    public void testGetManager_firstName() {
        this.tested = new AccountManager(1, "foo", "bar", new Date(0));
        assertEquals("bar", this.tested.getFirstName());
    }

    @Test
    public void testGetManager_phone() {
        this.tested = new AccountManager(1, "foo", "bar", new Date(0));
        this.tested.setPhone("0000000000");
        assertEquals("0000000000", this.tested.getPhone());
    }

    @Test
    public void testGetManager_email() {
        this.tested = new AccountManager(1, "foo", "bar", new Date(0));
        this.tested.setEmail("foo.bar@foobar.fr");
        assertEquals("foo.bar@foobar.fr", this.tested.getEmail());
    }

    @Test
    public void testGetManager_assignementDate() {
        this.tested = new AccountManager(1, "foo", "bar", new GregorianCalendar(2017, Calendar.APRIL, 10, 12, 31, 15).getTime());
        assertEquals(new GregorianCalendar(2017, Calendar.APRIL, 10, 12, 31, 15).getTime(), this.tested.getAssignementDate());
    }
    
    private AccountManager tested;
}
