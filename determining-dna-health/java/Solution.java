import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class Solution {

    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws Exception {
        String line;

        line = reader.readLine();
        int n = Integer.parseInt(line);

        String[] genes = new String[n];

        line = reader.readLine();
        String[] genesItems = line.split(" ");

        for (int i = 0; i < n; i++) {
            String genesItem = genesItems[i];
            genes[i] = genesItem;
        }

        int[] health = new int[n];

        line = reader.readLine();
        String[] healthItems = line.split(" ");

        for (int i = 0; i < n; i++) {
            int healthItem = Integer.parseInt(healthItems[i]);
            health[i] = healthItem;
        }

        line = reader.readLine();
        int s = Integer.parseInt(line);

        long max = -1;
        long min = -1;
        Node tree = buildTree(genes, health);
        for (int sItr = 0; sItr < s; sItr++) {
            line = reader.readLine();
            String[] firstLastd = line.split(" ");

            int first = Integer.parseInt(firstLastd[0]);

            int last = Integer.parseInt(firstLastd[1]);

            String d = firstLastd[2];

            long value = getHealth(tree, d, first, last);
            if ((min == -1) || (value < min)) {
                min = value;
            }
            if ((max == -1) || (value > max)) {
                max = value;
            }
        }

        System.out.printf("%d %d", min, max);
        System.out.println();
        reader.close();
    }

    private static class Node {
        int health = 0;
        int index = -1;
        Map<Character, Node> kids = new HashMap<>();
        Map<Integer, Integer> healthMap = new HashMap<>();
    }

    private static long getHealth(Node tree, String strand, int first, int last) {
        long value = 0;

        for (int strNdx = 0; strNdx < strand.length(); strNdx += 1) {
            value += getHealth(tree, strand, strNdx, first, last);
        }

        return value;
    }

    private static long getHealth(Node tree, String strand, int start, int first, int last) {
        long value = 0;

        Node n = tree;
        for (int ndx = start; ndx < strand.length(); ndx += 1) {
            char ch = strand.charAt(ndx);
            n = n.kids.get(ch);
            if (n == null) {
                break;
            }

            for (int geneNdx : n.healthMap.keySet()) {
                if ((geneNdx >= first) && (geneNdx <= last)) {
                    value += n.healthMap.get(geneNdx);
                }
            }
        }

        return value;
    }

    private static Node buildTree(String[] genes, int[] health) {
        Node root = new Node();

        for (int geneNdx = 0; geneNdx < genes.length; geneNdx += 1) {
            Node node = root;
            String gene = genes[geneNdx];
            char[] chars = gene.toCharArray();

            for (int ndx = 0; ndx < chars.length; ndx += 1) {
                char ch = chars[ndx];
                Node next = node.kids.get(ch);

                if (next == null) {
                    next = new Node();
                    node.kids.put(ch, next);
                }

                node = next;
                if (ndx >= chars.length - 1) {
                    node.index = geneNdx;
                    node.health += health[geneNdx];
                    node.healthMap.put(geneNdx, health[geneNdx]);
                }
            }
        }

        return root;
    }
}
