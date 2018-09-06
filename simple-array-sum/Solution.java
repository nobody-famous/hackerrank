import java.io.BufferedReader;
import java.io.InputStreamReader;

/***********************************************************************
 * Solution to the Simple Array Sum challenge
 ***********************************************************************/
public class Solution {
    public static int simpleArraySum(int[] array) {
        int sum = 0;

        for (int ndx = 0; ndx < array.length; ndx += 1) {
            sum += array[ndx];
        }

        return sum;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int length = Integer.parseInt(reader.readLine());
        int[] array = new int[length];

        String line = reader.readLine();
        String[] split = line.split(" ");
        for (int ndx = 0; ndx < length; ndx += 1) {
            array[ndx] = Integer.parseInt(split[ndx]);
        }

        System.out.println(simpleArraySum(array));
    }
}
