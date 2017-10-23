import org.junit.Test;

public class StudentAdvancedTest {

    @Test(timeout=1000)
    public void testGenChip_1_1() {
        Utilities.test("./test/inputs/gen_chip_1_1.in");
    }

    @Test(timeout=1000)
    public void testGenChip_1_2() {
        Utilities.test("./test/inputs/gen_chip_1_2.in");
    }

    @Test(timeout=1000)
    public void testWire5() {
        Utilities.test("./test/inputs/wire5.in");
    }

    @Test(timeout=1000)
    public void testWire8() {
        Utilities.test("./test/inputs/wire8.in");
    }


}
