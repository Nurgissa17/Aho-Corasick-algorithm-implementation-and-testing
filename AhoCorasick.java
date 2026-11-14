import java.util.*;
public class AhoCorasick {
    static class Node {
        Map<Character, Node> next = new HashMap<>();
        Node fail;
        List<String> out = new ArrayList<>();
    }
    Node root = new Node();
    void add(String p) {
        Node v = root;
        for (char c : p.toCharArray()) {
            if (!v.next.containsKey(c)) v.next.put(c, new Node());
            v = v.next.get(c);
        }
        v.out.add(p);
    }
    void build() {
        Queue<Node> q = new ArrayDeque<>();
        root.fail = root;
        for (Node v : root.next.values()) {
            v.fail = root;
            q.add(v);
        }
        while (!q.isEmpty()) {
            Node v = q.poll();
            for (var e : v.next.entrySet()) {
                char c = e.getKey();
                Node u = e.getValue();
                Node f = v.fail;
                while (f != root && !f.next.containsKey(c)) f = f.fail;
                u.fail = f.next.getOrDefault(c, root);
                u.out.addAll(u.fail.out);
                q.add(u);
            }
        }
    }
    List<String> search(String text) {
        List<String> res = new ArrayList<>();
        Node v = root;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            while (v != root && !v.next.containsKey(c)) v = v.fail;
            v = v.next.getOrDefault(c, root);
            for (String p : v.out) res.add(p + " at " + (i - p.length() + 1));
        }
        return res;
    }
}