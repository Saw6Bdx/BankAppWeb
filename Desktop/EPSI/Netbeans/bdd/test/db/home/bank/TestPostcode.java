/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.home.bank;

import java.util.Collection;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Charlotte
 */
public class TestPostcode {
    // Postcode(Integer id, int postcode, String city)
    
    @Before
    public void setUp() {
        this.tested = new Postcode(1, 33000, "Bordeaux");
    }

    // constructeur, setter
    /*@Test(expected = NullPointerException.class) // Si postcode modifié en VARCHAR(5) dans bdd
    public void PostcodeTest_PostcodeIsNull() {
        new Postcode(1, null, "Bordeaux");
    }
    @Test(expected = IllegalArgumentException.class)
    public void PostcodeTest_PostcodeIsEmpty() {
        new Postcode(1, "", "Bordeaux");
    }
    @Test(expected = IllegalArgumentException.class)
    public void PostcodeTest_PostcodeIsInvalid() {
        new Postcode(1, "3300K", "Bordeaux");
    }
    @Test(expected = NullPointerException.class)
    public void PostcodeSetTest_PostcodeIsNull() {
        this.tested.setPostcode(null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void PostcodeSetTest_PostcodeIsEmpty() {
        this.tested.setPostcode("");
    }
    @Test(expected = IllegalArgumentException.class)
    public void PostcodeSetTest_PostcodeIsInvalid() {
        this.tested.setPostcode("3300K");
    }*/
    
    @Test(expected = NullPointerException.class) // Si postcode modifié en VARCHAR(5) dans bdd
    public void PostcodeTest_CityIsNull() {
        new Postcode(1, 33000, null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void PostcodeTest_CityIsEmpty() {
        new Postcode(1, 33000, "");
    }
    @Test(expected = IllegalArgumentException.class)
    public void PostcodeTest_CityIsInvalid() {
        new Postcode(1, 33000, "33000");
    }
    @Test(expected = NullPointerException.class)
    public void PostcodeSetTest_CityIsNull() {
        this.tested.setCity(null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void PostcodeSetTest_CityIsEmpty() {
        this.tested.setCity("");
    }
    @Test(expected = IllegalArgumentException.class)
    public void PostcodeSetTest_CityIsInvalid() {
        this.tested.setCity("3300K");
    }
    
    // getter
    @Test
    public void testGetId() {
        assertEquals((Integer)1, this.tested.getId());
    }

    @Test
    public void testGetPostcode() {
        assertEquals(33000, this.tested.getPostcode());
    }

    @Test
    public void testGetCity() {
        assertEquals("Bordeaux", this.tested.getCity());
    }

    private Postcode tested;
}
