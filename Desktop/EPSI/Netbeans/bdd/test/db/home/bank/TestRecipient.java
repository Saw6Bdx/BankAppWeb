/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.home.bank;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Charlotte
 */
public class TestRecipient {
    // Recipient(Integer id, String name)
    
    @Before
    public void setUp() {
        this.tested = new Recipient(1, "foo");
    }
    
    // constructeur, setter
    @Test(expected = NullPointerException.class)
    public void testRecipient_NameIsNull() {
        new Recipient(1, null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testRecipient_NameIsEmpty() {
        new Recipient(1, "");
    }
    @Test(expected = NullPointerException.class)
    public void testSetRecipient_NameIsNull() {
        this.tested.setName(null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testSetRecipient_NameIsEmpty() {
        this.tested.setName("");
    }
    
    @Test(expected = NullPointerException.class)
    public void testSetRecipient_IbanIsNull() {
        this.tested.setIban(null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testSetRecipient_IbanIsInvalid() {
        this.tested.setIban("12345678901");
    }

    // getter
    @Test
    public void testGetId() {
        assertEquals((Integer)1, this.tested.getId());
    }
    @Test
    public void testGetName() {
        assertEquals("foo", this.tested.getName());
    }
    @Test
    public void testGetIban() {
        this.tested.setIban("FR00000000000");
        assertEquals("FR00000000000", this.tested.getIban());
    }
    
    private Recipient tested;
}
