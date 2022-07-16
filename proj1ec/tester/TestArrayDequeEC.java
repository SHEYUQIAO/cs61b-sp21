package tester;

import static org.junit.Assert.*;
import org.junit.Test;
import student.StudentArrayDeque;
import edu.princeton.cs.introcs.StdRandom;

public class TestArrayDequeEC {

    @Test
    public void testDeque() {
        StudentArrayDeque<Integer> st = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> ad = new ArrayDequeSolution<>();

        int n = 5000;
        String str = "\n";
        for (int i = 0; i < n; i++) {
            int opt = StdRandom.uniform(0, 4);
            if (opt == 0) {
                //add first
                int num = StdRandom.uniform(0, 100);
                st.addFirst(num);
                ad.addFirst(num);
                str += "addFirst(" + num + ")\n";
            } else if (opt == 1) {
                //addLast
                int sum =StdRandom.uniform(0, 100);
                st.addLast(sum);
                ad.addLast(sum);
                str += "addLast(" + sum + ")\n";
            }

            if (st.isEmpty()) {
                continue;
            }

            if (opt == 2) {
                //removeFirst
                str += "removeFirst()\n";
                assertEquals(str, ad.removeFirst(), st.removeFirst());
            } else if (opt == 3) {
                //removeLast
                str += "removeLast()\n";
                assertEquals(str, ad.removeLast(), st.removeLast());
            }
        }
    }
}
