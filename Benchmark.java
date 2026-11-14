import java.io.*;
import java.util.*;
public class Benchmark {
    static String make(Random r, int n) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < n; i++) s.append((char) ('a' + r.nextInt(6)));
        return s.toString();
    }
    static double avg(double[] t) {
        double sum = 0;
        for (double x : t) sum += x;
        return sum / t.length;
    }
    static void save(PrintWriter f, int p, int n, String name, double[] t, int m) {
        f.printf(Locale.US, "%d,%d,%s", p, n, name);
        for (double x : t) f.printf(Locale.US, ",%.3f", x);
        f.printf(Locale.US, ",%.3f,%d%n", avg(t), m);
    }
    public static void main(String[] args) throws IOException {
        TestCases.main(new String[0]);
        int[] counts = {10, 100, 1000};
        int[] lengths = {1000, 10000, 100000};
        try (PrintWriter f = new PrintWriter("results.csv")) {
            f.println("patterns,text_length,algorithm,run1_ms,run2_ms,run3_ms,run4_ms,run5_ms,average_ms,matches");
            System.out.println("patterns | text length | Aho-Corasick avg (ms) | KMP avg (ms) | matches");
            for (int p : counts) {
                for (int n : lengths) {
                    Random r = new Random(42 + p + n);
                    String text = make(r, n);
                    String[] ps = new String[p];
                    for (int i = 0; i < p; i++) ps[i] = make(r, 4 + r.nextInt(5));
                    double[] a = new double[5];
                    double[] k = new double[5];
                    int matches = 0;
                    for (int i = 0; i < 5; i++) {
                        long start = System.nanoTime();
                        AhoCorasick ac = new AhoCorasick();
                        for (String s : ps) ac.add(s);
                        ac.build();
                        List<String> x = ac.search(text);
                        a[i] = (System.nanoTime() - start) / 1000000.0;
                        start = System.nanoTime();
                        List<String> y = new ArrayList<>();
                        for (String s : ps) y.addAll(KMP.search(text, s));
                        k[i] = (System.nanoTime() - start) / 1000000.0;
                        Collections.sort(x);
                        Collections.sort(y);
                        if (!x.equals(y)) throw new AssertionError("Aho-Corasick and KMP results differ");
                        matches = x.size();
                    }
                    save(f, p, n, "Aho-Corasick", a, matches);
                    save(f, p, n, "KMP", k, matches);
                    System.out.printf(Locale.US, "%8d | %11d | %16.3f | %12.3f | %d%n",
                            p, n, avg(a), avg(k), matches);
                }
            }
        }
        System.out.println("results saved to results.csv");
    }
}