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
public class TestCountryCode {
    
    @Test(expected = NullPointerException.class)
    public void TestCountryCode_CodeIsNull() {
        new CountryCode(1, null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void TestCountryCode_CodeIsEmpty() {
        new CountryCode(1, "");
    }
    @Test(expected = NullPointerException.class)
    public void TestSetCountryCode_CodeIsNull() {
        this.tested = new CountryCode();
        this.tested.setCode(null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void TestSetCountryCode_CodeIsEmpty() {
        this.tested = new CountryCode();
        this.tested.setCode("");
    }

    @Test
    public void testGetId() {
        this.tested = new CountryCode(1, "foo");
        assertEquals((Integer)1, this.tested.getId());
    }

    @Test
    public void testGetCode() {
        this.tested = new CountryCode(1, "foo");
        assertEquals("foo", this.tested.getCode());
    }

    private CountryCode tested;
}
