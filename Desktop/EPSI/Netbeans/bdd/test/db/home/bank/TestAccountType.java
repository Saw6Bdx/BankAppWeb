/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.home.bank;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Charlotte
 */
public class TestAccountType {
    // AccountType(Integer id, String type)

    @Test(expected = NullPointerException.class)
    public void TestAccountType_TypeIsNull(){
        new AccountType(1, null);
    }
            
    @Test(expected = IllegalArgumentException.class)
    public void TestAccountType_TypeIsEmpty(){
        new AccountType(1, "");
    }
    
    @Test(expected = NullPointerException.class)
    public void TestSetAccountType_TypeIsNull(){
        this.tested = new AccountType();
        this.tested.setType(null);
    }
            
    @Test(expected = IllegalArgumentException.class)
    public void TestSetAccountType_TypeIsEmpty(){
        this.tested = new AccountType();
        this.tested.setType("");
    }
    
    @Test
    public void testGetId(){
        this.tested = new AccountType(1, "foo");
        assertEquals((Integer)1, this.tested.getId());
    }

    @Test
    public void testGetType() {
        this.tested = new AccountType(1, "foo");
        assertEquals("foo", this.tested.getType());
    }
    
    private AccountType tested;
}
