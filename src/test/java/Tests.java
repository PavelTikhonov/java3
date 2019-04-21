import lesson6.MyTests.MainTests;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Tests {
    private MainTests mainTests;
    @Before
    public void init() {
        mainTests = new MainTests();
    }

    @Test
    public void arrConversTest() {
        Assert.assertArrayEquals((new int[]{7, 3, 6, 8}), mainTests.arrayConversion((new int[]{0, 1, 4, 7, 3, 6, 8})));
    }

    @Test(expected = RuntimeException.class)
    public void arrConversTest1() {
        Assert.assertArrayEquals((null), mainTests.arrayConversion((new int[]{0, 1, 3, 7, 4, 6, 8, 5, 4})));
    }

    @Test(expected = RuntimeException.class)
    public void arrConversTest2() {
        Assert.assertArrayEquals((null), mainTests.arrayConversion((new int[]{0, 1, 3, 7, 1, 6, 8, 5, 1})));
    }

    @Test
    public void arrCheckTest() {
        Assert.assertTrue(mainTests.arrayCheck(new int[]{0, 1, 4, 7, 3, 6, 8}));
    }

    @Test
    public void arrCheckTest1() {
        Assert.assertTrue(mainTests.arrayCheck(new int[]{0, 3, 3, 7, 7, 6, 8, 5, 3}));
    }

    @Test
    public void arrCheckTest2() {
        Assert.assertTrue(mainTests.arrayCheck(new int[]{0, 1, 3, 7, 9, 6, 8, 5, 8}));
    }

    @Test
    public void arrCheckTest3() {
        Assert.assertTrue(mainTests.arrayCheck(new int[]{0, 4, 3, 7, 9, 6, 8, 5, 8}));
    }

}
