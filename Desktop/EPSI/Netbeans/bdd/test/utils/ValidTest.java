/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mary
 */
public class ValidTest {
    
    public ValidTest() {
    }
    
    @Before
    public void setUp() {
    }

    @Test
    public void testIsValidLetters() {
        assertEquals(false, Valid.isValidOnlyLetters("foo1bar"));
    }

    @Test
    public void testIsValidPhoneNumber() {
        assertEquals(false, Valid.isValidPhoneNumber("061234AA12"));
    }

    @Test
    public void testIsValidEmail() {
        assertEquals(false, Valid.isValidEmail("foobar"));
    }

    @Test
    public void testIsValidDate() {
        assertEquals(false, Valid.isValidDate(""));
    }

    @Test
    public void testIsValidPwd() {
        assertEquals(false, Valid.isValidPwd("foo", "foobar"));
    }
    
}
