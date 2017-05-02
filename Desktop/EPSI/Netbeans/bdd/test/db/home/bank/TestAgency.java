package db.home.bank;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestAgency {
    // Agency(Integer id, String agencyName, String agencyCode)
    
    @Before
    public void setUp() {
        this.tested = new Agency(1, "foo", "12345");
    }

    // constructeur, setter
    @Test(expected = NullPointerException.class)
    public void testAgency_AgencyNameIsNull(){
        new Agency(1, null, "12345");
    }
    @Test(expected = IllegalArgumentException.class)
    public void testAgency_AgencyNameIsEmpty(){
        new Agency(1, "", "12345");
    }
    @Test(expected = NullPointerException.class)
    public void testSetAgency_AgencyNameIsNull(){
        this.tested = new Agency();
        this.tested.setAgencyName(null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testSetAgency_AgencyNameIsEmpty(){
        this.tested = new Agency();
        this.tested.setAgencyName("");
    }
    
    @Test(expected = NullPointerException.class)
    public void testAgency_AgencyCodeIsNull(){
        new Agency(1, "foo", null); 
    }
    @Test(expected = IllegalArgumentException.class)
    public void testAgency_AgencyCodeIsEmpty(){
        new Agency(1, "foo", ""); 
    }
    @Test(expected = IllegalArgumentException.class)
    public void testAgency_AgencyCodeIsInvalid(){
        new Agency(1, "foo", "12bar"); 
    }
    @Test(expected = IllegalArgumentException.class)
    public void testAgency_AgencyCodeLengthIsInvalid(){
        new Agency(1, "foo", "1234"); 
    }
    @Test(expected = NullPointerException.class)
    public void testSetAgency_AgencyCodeIsNull(){
        this.tested = new Agency();
        this.tested.setAgencyCode(null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testSetAgency_AgencyCodeIsEmpty(){
        this.tested = new Agency();
        this.tested.setAgencyCode("");
    }
    @Test(expected = IllegalArgumentException.class)
    public void testSetAgency_AgencyCodeIsInvalid(){
        this.tested = new Agency();
        this.tested.setAgencyCode("12bar");
    }
    @Test(expected = IllegalArgumentException.class)
    public void testSetAgency_AgencyCodeLengthIsInvalid(){
        this.tested = new Agency();
        this.tested.setAgencyCode("1234");
    }
    
    // getter
    @Test
    public void testGetId(){
        assertEquals((Integer)1, this.tested.getId());
    }
    @Test
    public void testGetAgencyName(){
        assertEquals("foo", this.tested.getAgencyName());
    }
    
    @Test
    public void testGetAgencyCode(){
        assertEquals("12345", this.tested.getAgencyCode());
    }
    
    private Agency tested;
}
