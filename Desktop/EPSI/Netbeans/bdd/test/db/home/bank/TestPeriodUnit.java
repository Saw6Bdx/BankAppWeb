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
public class TestPeriodUnit {
    // PeriodUnit(Integer id, String unit)
    
    @Before
    public void setUp() {
        this.tested = new PeriodUnit(1, "foo");
    }

    // constructeur, setter
    @Test(expected = NullPointerException.class)
    public void PeriodUnitTest_UnitIsNull() {
        new PeriodUnit(1, null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void PeriodUnitTest_UnitIsEmpty() {
        new PeriodUnit(1, "");
    }
    @Test(expected = IllegalArgumentException.class)
    public void PeriodUnitTest_UnitIsInvalid() {
        new PeriodUnit(1, "7");
    }
    @Test(expected = NullPointerException.class)
    public void PeriodUnitSetTest_UnitIsNull() {
        this.tested = new PeriodUnit();
        this.tested.setUnit(null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void PeriodUnitSetTest_UnitIsEmpty() {
        this.tested = new PeriodUnit();
        this.tested.setUnit("");
    }
    @Test(expected = IllegalArgumentException.class)
    public void PeriodUnitSetTest_UnitIsInvalid() {
        this.tested = new PeriodUnit();
        this.tested.setUnit("7");
    }
    
    // getter
    @Test
    public void testGetId() {
        assertEquals((Integer)1, this.tested.getId());
    }

    @Test
    public void testGetUnit() {
        assertEquals("foo", this.tested.getUnit());
    }
    
    private PeriodUnit tested;
}
