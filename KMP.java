import java.util.*;
public class KMP {
    static List<String> search(String text, String p) {
        List<String> res = new ArrayList<>();
        int[] lps = new int[p.length()];
        for (int i = 1, j = 0; i < p.length();) {
            if (p.charAt(i) == p.charAt(j)) lps[i++] = ++j;
            else if (j > 0) j = lps[j - 1];
            else i++;
        }
        for (int i = 0, j = 0; i < text.length();) {
            if (text.charAt(i) == p.charAt(j)) {
                i++;
                j++;
                if (j == p.length()) {
                    res.add(p + " at " + (i - j));
                    j = lps[j - 1];
                }
            } else if (j > 0) j = lps[j - 1];
            else i++;
        }
        return res;
    }
}