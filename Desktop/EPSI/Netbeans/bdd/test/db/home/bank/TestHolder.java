/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.home.bank;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mary, Nicolas ?
 */
public class TestHolder {
    // Holder(Integer id, String name, String firstname, String login, String password)
    
    @Before
    public void setUp() {
        this.tested = new Holder(1, "foo","bar","login","password");
    }
    
    
    // constructeur, setter
    @Test(expected = NullPointerException.class)
    public void testHolder_NameIsNull() {
        new Holder(1, null,"bar","login","password");
    }
    @Test(expected = IllegalArgumentException.class)
    public void testHolder_NameIsEmpty() {
        new Holder(1, "","bar","login","password");
    }
    @Test(expected = NullPointerException.class)
    public void testSetHolder_NameIsNull() {
        this.tested = new Holder();
        this.tested.setName(null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testSetHolder_NameIsEmpty() {
        this.tested = new Holder();
        this.tested.setName("");
    }
    
    @Test(expected = NullPointerException.class)
    public void testHolder_FirstnameIsNull() {
        new Holder(1, "foo", null,"login","password");
    }
    @Test(expected = IllegalArgumentException.class)
    public void testTask_FirstnameIsEmpty() {
        new Holder(1, "foo","","login","password");
    }
    @Test(expected = NullPointerException.class)
    public void testSetHolder_FirstnameIsNull() {
        this.tested = new Holder();
        this.tested.setFirstname(null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testSetHolder_FirstNameIsEmpty() {
        this.tested = new Holder();
        this.tested.setFirstname("");
    }
    
    @Test(expected = NullPointerException.class)
    public void testHolder_LoginIsNull() {
        new Holder(1, "foo", "bar",null,"password");
    }
    @Test(expected = IllegalArgumentException.class)
    public void testHolder_LoginIsEmpty() {
        new Holder(1, "foo","bar","","password");
    }
    @Test(expected = NullPointerException.class)
    public void testSetHolder_LoginIsNull() {
        this.tested = new Holder();
        this.tested.setLogin(null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testSetHolder_LoginIsEmpty() {
        this.tested = new Holder();
        this.tested.setLogin("");
    }
    
    @Test(expected = NullPointerException.class)
    public void testHolder_PasswordIsNull() {
        new Holder(1, "foo", "bar","login",null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testTask_PasswordIsEmpty() {
        new Holder(1, "foo","bar","login","");
    }
    @Test(expected = NullPointerException.class)
    public void testSetHolder_PasswordIsNull() {
        this.tested = new Holder();
        this.tested.setPassword(null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testSetHolder_PasswordIsEmpty() {
        this.tested = new Holder();
        this.tested.setPassword("");
    }
    
    @Test(expected = NullPointerException.class)
    public void testHolder_DateIsNull() {
        this.tested.setBirthday(null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testTask_CreateDateInTheFuture() {
        this.tested.setBirthday(new GregorianCalendar(2050, Calendar.APRIL, 21, 15, 29, 15).getTime());
    }
    
    @Test(expected = NullPointerException.class)
    public void testHolder_PhoneIsNull() {
        this.tested.setPhone(null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testHolder_PhoneIsInvalid() {
        this.tested.setPhone("12345678JH");
    }
    @Test(expected = IllegalArgumentException.class)
    public void testHolder_PhoneLengthIsInvalid() {
        this.tested.setPhone("12345678");
    }
    
    // getter
    @Test
    public void testGetId() {
        assertEquals((Integer)1,this.tested.getId());
    }
    @Test
    public void testGetName() {
        assertEquals("foo",this.tested.getName());
    }
    @Test
    public void testGetFirstName() {
        assertEquals("bar",this.tested.getFirstname());
    }
    @Test
    public void testGetLogin() {
        assertEquals("login",this.tested.getLogin());
    }
    @Test
    public void testGetPassword() {
        assertEquals("password",this.tested.getPassword());
    }
    
    @Test
    public void testGetBirthday() {
        Calendar cal = new GregorianCalendar(2017, Calendar.APRIL, 11, 15, 29, 15);
        this.tested.setBirthday(cal.getTime());
        assertEquals(cal.getTime(), this.tested.getBirthday());
    }
    @Test
    public void testGetPhone() {
        this.tested.setPhone("1234567890");
        assertEquals("1234567890",this.tested.getPhone());
    }
    
    private Holder tested;
}
