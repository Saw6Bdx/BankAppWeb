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
 * @author Mary
 */
public class TestTransactions {
    // Transactions(Integer id, String label, double amount, Date date, Date endDate)
    
    @Before
    public void setUp() {
        Calendar cal = new GregorianCalendar(2017, Calendar.APRIL, 11, 15, 29, 15);
        this.tested = new Transactions(1, "foo", 2.0, cal.getTime(), cal.getTime());
        // 2,"bar","hebdo","CB"
    }
    
    // Constructeur, setter
    @Test(expected = NullPointerException.class)
    public void testTransactions_LabelIsNull() {
        new Transactions(1, null, 2.0, new Date(0), new Date(0));
    }
    @Test(expected = IllegalArgumentException.class)
    public void testTransactions_LabelIsEmpty() {
        new Transactions(1, "", 2.0, new Date(0), new Date(0));
    }
    @Test(expected = NullPointerException.class)
    public void testSetTransactions_LabelIsNull() {
        this.tested.setLabel(null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testSetTransactions_LabelIsEmpty() {
        this.tested.setLabel("");
    }
    
    @Test(expected = NullPointerException.class)
    public void testTransactions_DateIsNull() {
        new Transactions(1, "foo", 2.0, null, new Date(0));
    }
    @Test(expected = NullPointerException.class)
    public void testSetTransactions_DateIsNull() {
        this.tested.setDate(null);
    }
    
    @Test(expected = NullPointerException.class)
    public void testTransactions_EndDateIsNull() {
        new Transactions(1, "foo", 2.0, new Date(0), null);
    }
    @Test(expected = NullPointerException.class)
    public void testSetTransactions_EndDateIsNull() {
        this.tested.setEndDate(null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testSetTransactions_DayNbIsInvalid(){
        this.tested.setDayNb(31);
    }
    
    @Test(expected = NullPointerException.class)
    public void testTransactions_CommentIsNull() {
        this.tested.setComment(null);
    }
    
    // getter
    @Test
    public void testGetId() {
        assertEquals((Integer)1,this.tested.getId());
    }
    @Test
    public void testGetLabel() {
        assertEquals("foo",this.tested.getLabel());
    }
    @Test
    public void testGetAmount() {
        assertEquals(2.0,this.tested.getAmount(),0.0);
    }
    @Test
    public void testGetDate() {
        assertEquals(
            new GregorianCalendar(2017, Calendar.APRIL, 11, 15, 29, 15).getTime(),
            this.tested.getDate()
        );
    }
    @Test
    public void testGetEndDate() {
        assertEquals(
            new GregorianCalendar(2017, Calendar.APRIL, 11, 15, 29, 15).getTime(),
            this.tested.getEndDate()
        );
    }
    @Test
    public void testGetDayNb() {
        this.tested.setDayNb(2);
        assertEquals((Integer)2,this.tested.getDayNb());
    }
    @Test
    public void testGetComment() {
        this.tested.setComment("bar");
        assertEquals("bar",this.tested.getComment());
    }
    
    private Transactions tested;
}
