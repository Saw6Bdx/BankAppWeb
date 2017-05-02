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
 * @author Mary
 */
public class TestAddress {
    // Address(Integer id, String line1)
    
    @Before
    public void setup() { // appelée avant chaque test
        this.tested = new Address(1, "foo");
    }
   
    // Constructeur, Setter
    @Test(expected = NullPointerException.class)
    public void testAddress_Line1IsNull() {
        new Address(1, null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testAddress_Line1IsEmpty() {
        new Address(1, "");
    }
    @Test(expected = NullPointerException.class)
    public void testSetAddress_Line1IsNull() {
        this.tested = new Address();
        this.tested.setLine1(null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testSetAddress_Line1IsEmpty() {
        this.tested = new Address();
        this.tested.setLine1("");
    }
    
    @Test(expected = NullPointerException.class)
    public void testSetAddress_Line2IsNull() {
        this.tested = new Address(1, "foo");
        this.tested.setLine2(null);
    }
   
    // Getter
    @Test
    public void testGetId() {
        assertEquals((Integer)1,this.tested.getId());
    }
    
    @Test
    public void testGetLine1() {
        assertEquals("foo",this.tested.getLine1());
    }
    
    @Test
    public void testGetLine2() {
        this.tested.setLine2("bar");
        assertEquals("bar",this.tested.getLine2());
    }
    
    private Address tested;
}
