import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Solution {
    private static final int MAX_NUMBER = 1000000;
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static int[] precompute() {
        int[] values = new int[MAX_NUMBER];

        values[0] = 0;
        values[1] = 1;

        for (int i = 1; i < MAX_NUMBER; i += 1) {
            if ((values[i] == 0) || (values[i] > (values[i - 1] + 1))) {
                values[i] = (values[i - 1] + 1);
            }

            for (int j = 1; j <= i && (i * j) < MAX_NUMBER; j += 1) {
                int mult = i * j;

                if ((values[mult] == 0) || (values[mult] > (values[i] + 1))) {
                    values[mult] = values[i] + 1;
                }
            }
        }

        return values;
    }

    public static void main(String[] args) throws Exception {
        int numQueries = Integer.parseInt(reader.readLine());

        int[] values = Solution.precompute();

        for (int loop = 0; loop < numQueries; loop += 1) {
            int query = Integer.parseInt(reader.readLine());
            System.out.println(values[query]);
        }
    }
}
