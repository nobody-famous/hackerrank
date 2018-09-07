import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Solution {
    public static int countPairs(long[] elems) {
        int[] counts = new int[elems.length];
        int result = 0;

        for (int ndx = 1; ndx < elems.length; ndx += 1) {
            int count = 0;
            long revMax = elems[ndx];
            for (int rev = ndx - 1; rev >= 0; rev -= 1) {
                if (elems[rev] > revMax) {
                    revMax = elems[rev];
                }

                if ((elems[rev] * elems[ndx]) <= revMax) {
                    count += 1;
                }
            }
            counts[ndx] = counts[ndx - 1] + count;
            result = counts[ndx];
        }

        return result;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Integer.parseInt(reader.readLine());
        String[] strElems = reader.readLine().split(" ");
        long[] elems = new long[strElems.length];

        for (int i = 0; i < strElems.length; i += 1) {
            elems[i] = Integer.parseInt(strElems[i]);
        }

        System.out.println(Solution.countPairs(elems));
    }
}
