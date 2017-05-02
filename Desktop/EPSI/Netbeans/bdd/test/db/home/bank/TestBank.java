/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.home.bank;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Charlotte
 */
public class TestBank {
    // Bank(Integer id, String name, String bankCode)
    
    @Before
    public void setUp() {
        this.tested = new Bank(1, "foo", "bar");
    }

    // constructeur, setter
    @Test(expected = NullPointerException.class)
    public void TestBank_NameIsNull() {
        new Bank(1, null, "bar");
    }
    @Test(expected = NullPointerException.class)
    public void TestBank_NameIsEmpty() {
        new Bank(1, "", "bar");
    }
    @Test(expected = NullPointerException.class)
    public void TestSetBank_NameIsNull() {
        this.tested = new Bank();
        this.tested.setName(null);
    }
    @Test(expected = NullPointerException.class)
    public void TestSetBank_NameIsEmpty() {
        this.tested = new Bank();
        this.tested.setName("");
    }
    
    @Test(expected = NullPointerException.class)
    public void TestBank_BankCodeIsNull() {
        new Bank(1, "foo", null);
    }
    @Test(expected = NullPointerException.class)
    public void TestBank_BankCodeIsEmpty() {
        new Bank(1, "foo", "");
    }
    @Test(expected = NullPointerException.class)
    public void TestSetBank_BankCodeIsNull() {
        this.tested = new Bank();
        this.tested.setBankCode(null);
    }
    @Test(expected = NullPointerException.class)
    public void TestSetBank_BankCodeIsEmpty() {
        this.tested = new Bank();
        this.tested.setBankCode("");
    }
    
    @Test
    public void testGetId() {
        assertEquals((Integer)1, this.tested.getId());
    }

    @Test
    public void testGetName() {
        assertEquals("foo", this.tested.getName());
    }

    @Test
    public void testGetBankCode() {
        assertEquals("bar", this.tested.getBankCode());
    }
    
    private Bank tested;
}
