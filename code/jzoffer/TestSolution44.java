package code.jzoffer;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class TestSolution44 {
    Solution44 s44 = new Solution44();

    @Test
    public void testFindNthDigit(){
        assertEquals("test findNthDigit", 1, s44.findNthDigit(1000000000));
    }

}
