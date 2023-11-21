package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(10);
        for (int i = 0; i < 10; i +=1){
            arb.enqueue(0);
        }
        // arb.enqueue(0);
        arb.dequeue();
        arb.enqueue(9);
        arb.dequeue();
        arb.enqueue(15);
        arb.dequeue();
        arb.enqueue(31);
        arb.dequeue();
        for(int x : arb) {
            System.out.println(x);
        }
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
