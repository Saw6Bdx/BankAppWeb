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
public class TestTransactionType {
    // TransactionType(Integer id, String type)
    
    @Before
    public void setUp() {
        this.tested = new TransactionType(1, "foo");
    }

    // constructeur, setter
    @Test(expected = NullPointerException.class)
    public void TestTransactionType_TypeIsNull() {
        new TransactionType(1, null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void TestTransactionType_TypeIsEmpty() {
        new TransactionType(1, "");
    }
    @Test(expected = IllegalArgumentException.class)
    public void TestTransactionType_TypeIsInvalid() {
        new TransactionType(1, "123");
    }
    @Test(expected = NullPointerException.class)
    public void TestSetTransactionType_TypeIsNull() {
        this.tested.setType(null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void TestSetTransactionType_TypeIsEmpty() {
        this.tested.setType("");
    }
    @Test(expected = IllegalArgumentException.class)
    public void TestSetTransactionType_TypeIsInvalid() {
        this.tested.setType("123");
    }
    
    // getter
    @Test
    public void testGetId() {
        assertEquals((Integer)1, this.tested.getId());
    }
    @Test
    public void testGetType() {
        assertEquals("foo", this.tested.getType());
    }

    private TransactionType tested;
}
