package db.home.bank;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mary, Nicolas ?
 */
public class TestCategory {
    // Category(Integer id, String label)
    
    @Before
    public void setUp() {
        this.tested = new Category(1, "foo");
    }
    
    // constructeur, setter
    @Test(expected = NullPointerException.class)
    public void testCategory_LabelIsNull(){
        new Category(1, null); 
    }
    @Test(expected = IllegalArgumentException.class)
    public void testCategory_LabelIsEmpty(){
        new Category(1, ""); 
    }
    @Test(expected = NullPointerException.class)
    public void testSetCategory_LabelIsNull(){
        this.tested = new Category();
        this.tested.setLabel(null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testSetCategory_LabelIsEmpty(){
        this.tested = new Category();
        this.tested.setLabel("");
    }
    
    // getter
    @Test
    public void testGetId(){
        assertEquals((Integer)1, this.tested.getId());
    }
    @Test
    public void testGetLabel(){
        assertEquals("foo", this.tested.getLabel());
    }
    
    private Category tested;
}
