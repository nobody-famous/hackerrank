import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class Solution {

  // Complete the morganAndString function below.
  static String morganAndString(String aStr, String bStr) {
    StringBuilder out = new StringBuilder();
    Tokenizer a = new Tokenizer(aStr);
    Tokenizer b = new Tokenizer(bStr);

    while (true) {
      if (a.isEmpty()) {
        drain(out, b);
        break;
      }
      if (b.isEmpty()) {
        drain(out, a);
        break;
      }

      getChunk(out, a, b.peek());
      getChunk(out, b, a.peek());
      if (a.peek() == b.peek()) {
        ties(out, a, b);
      }
    }

    return out.toString();
  }

  static private void ties(StringBuilder out, Tokenizer a, Tokenizer b) {
    int ch = a.peek();
    int cursor = 1;

    if (a.isValid(cursor) && b.isValid(cursor)) {
      while (a.peek(cursor) == b.peek(cursor)) {
        if (a.peek(cursor) > ch) {
          break;
        }
        cursor += 1;
        if (!a.isValid(cursor) || !b.isValid(cursor)) {
          break;
        }
      }
    }

    if (!a.isValid(cursor)) {
      out.append(b.pop());
    } else if (!b.isValid(cursor)) {
      out.append(a.pop());
    } else if (a.peek(cursor) == b.peek(cursor)) {
      String chunk = a.chunk(cursor);
      a.seek(cursor);
      b.seek(cursor);
      out.append(chunk).append(chunk);
    } else if (a.peek(cursor) < b.peek(cursor)) {
      String chunk = a.chunk(cursor);
      a.seek(cursor);
      out.append(chunk);
    } else if (a.peek(cursor) > b.peek(cursor)) {
      String chunk = a.chunk(cursor);
      b.seek(cursor);
      out.append(chunk);
    }
  }

  static private void drain(StringBuilder builder, Tokenizer t) {
    while (!t.isEmpty()) {
      builder.append(t.pop());
    }
  }

  static private void getChunk(StringBuilder out, Tokenizer t, char ch) {
    int cursor = 0;

    while ((t.peek(cursor) != 0) && (t.peek(cursor) < ch)) {
      cursor += 1;
    }

    out.append(t.chunk(cursor));
    t.seek(cursor);
  }

  private static class Tokenizer {
    public Tokenizer(String str) {
      this.str = str;
      this.cursor = 0;
    }

    public boolean isValid(int offset) {
      return ((this.cursor + offset) >= 0) && ((this.cursor + offset) < str.length());
    }

    public char peek() {
      return peek(0);
    }

    public char peek(int offset) {
      int ndx = this.cursor + offset;
      return (ndx < str.length()) ? this.str.charAt(ndx) : 0;
    }

    public boolean isEmpty() {
      return this.cursor >= this.str.length();
    }

    public char pop() {
      char ch = 0;

      if (!isEmpty()) {
        ch = this.str.charAt(this.cursor);
        this.cursor += 1;
      }

      return ch;
    }

    public String chunk(int size) {
      return str.substring(this.cursor, this.cursor + size);
    }

    public void seek(int size) {
      this.cursor += size;
    }

    public String toString() {
      return str.substring(cursor);
    }

    private String str;
    private int cursor;
  }

  private static final Scanner scanner = new Scanner(System.in);

  public static void main(String[] args) throws IOException {
    int t = scanner.nextInt();
    scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

    for (int tItr = 0; tItr < t; tItr++) {
      String a = scanner.nextLine();

      String b = scanner.nextLine();

      String result = morganAndString(a, b);

      System.out.println(result);
    }

    scanner.close();
  }
}
