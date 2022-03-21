import java.io.*;
import java.util.*;
import static java.lang.Math.*;


public class MainA {
    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;
        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }
        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }
        public int nextInt() {
            return Integer.parseInt(next());
        }
        public long nextLong() {
            return Long.parseLong(next());
        }
        public boolean hasNext() {
            try {
                String string = reader.readLine();
                if (string == null) {
                    return false;
                }
                tokenizer = new StringTokenizer(string);
                return tokenizer.hasMoreTokens();
            } catch (IOException e) {
                return false;
            }
        }
    }
    static InputReader in = new InputReader(System.in);
    static PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
//    static Scanner in = new Scanner(System.in);
    static void ini() {

    }

    static long getFac(int n) {
        if (n ==1 || n == 0)return 1;
        if ((n & 1) == 1) {
            return (n * getFac(n-1)) % MOD;
        }
        long temp = getFac(n >> 1);
        return ((cn2(n)*temp)%MOD *temp)%MOD;
    }

    static long pow(long base, long exp) {
        long res = 1;
        while (exp > 0) {
            if ((exp & 1) == 1) res *= base;
            base *= base;
            exp >>=1;
        }
        return res % MOD;
    }

    static long cn2(long n) {
        long x = (1L << n) + 1;
        long mask = (1L << n) - 1;
        return ((pow(x, n) >> ((n >> 1) * n)) & mask) % MOD;
    }

    static String yes = "YES";
    static String no = "NO";
    static int ipInf = Integer.MAX_VALUE-5;
    static int inInf = Integer.MIN_VALUE+5;
    static long lpInf = Long.MAX_VALUE - 5;
    static long lnInf = Long.MIN_VALUE + 5;

    public static void main(String[] args) {
        int t = 1;
        ini();
        while (t -- > 0) {
            solve();
        }
        out.close();
    }
    static final long MOD = 998244353;

    static void solve() {
        int n = in.nextInt();
        if (n == 1) {
            out.println(1);
            return;
        }
        int[] arr = new int[n+1];
        int a, b;
        for (int i = 0; i < n - 1; i++) {
            a = in.nextInt() ;
            b = in.nextInt() ;
            arr[a]++;
            arr[b]++;
        }
        arr[1]++;

        long res = 1;
        for (int i = 1; i <= n; i++) {
            res = (res * getFac(arr[i]-1)) % MOD;
        }

        out.println(res);
    }

}