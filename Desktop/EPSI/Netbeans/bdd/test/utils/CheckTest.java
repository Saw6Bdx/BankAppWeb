/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;


import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Guest
 */
public class CheckTest {
    
    public CheckTest() {
    }
    
    @Before
    public void setUp() {
    }

    @Test
    public void testCheckIsNull_String_String() {
        //Check.checkIsNull(null,"foobar");
    }

    @Test
    public void testCheckIsEmpty() {
        Check.checkIsEmpty("", "name");
    }

    @Test
    public void testCheckIsNull_Date_String() {
        System.out.println("checkIsNull");
        Date field = null;
        String name = "";
        Check.checkIsNull(field, name);
        fail("The test case is a prototype.");
    }

    @Test
    public void testCheckLetters() {
        Check.checkLetters("Abc123", "name");
    }

    @Test
    public void testCheckPhoneNumber() {
        Check.checkPhoneNumber("061234AB12");
    }

    @Test
    public void testCheckEmail() {
        System.out.println("checkEmail");
        String email = "";
        Check.checkEmail(email);
        fail("The test case is a prototype.");
    }

    @Test
    public void testCheckDate() {
        System.out.println("checkDate");
        String date = "";
        Check.checkDate(date);
        fail("The test case is a prototype.");
    }
    
}
