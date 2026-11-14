import java.util.*;
public class TestCases {
    static void check(String text, String[] ps, String... expected) {
        AhoCorasick ac = new AhoCorasick();
        List<String> kmp = new ArrayList<>();
        for (String p : ps) {
            ac.add(p);
            kmp.addAll(KMP.search(text, p));
        }
        ac.build();
        List<String> res = ac.search(text);
        List<String> want = new ArrayList<>(Arrays.asList(expected));
        Collections.sort(res);
        Collections.sort(kmp);
        Collections.sort(want);
        if (!res.equals(want) || !kmp.equals(want)) throw new AssertionError("wrong matches");
    }
    public static void main(String[] args) {
        check("ushers", new String[]{"he", "she", "his", "hers"},
                "she at 1", "he at 2", "hers at 2");
        check("aaaa", new String[]{"a", "aa", "aaa"},
                "a at 0", "a at 1", "a at 2", "a at 3",
                "aa at 0", "aa at 1", "aa at 2", "aaa at 0", "aaa at 1");
        check("ababa", new String[]{"aba", "bab"},
                "aba at 0", "bab at 1", "aba at 2");
        check("fish", new String[]{"cat", "dog"});
        check("", new String[]{"a", "abc"});
        check("banana", new String[]{"ana", "na"},
                "ana at 1", "na at 2", "ana at 3", "na at 4");
        System.out.println("all 6 tests passed.");
    }
}