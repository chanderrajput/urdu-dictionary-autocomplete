import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class AutoCompleteDictionaryTrie {
    private Node root;
    private int size;

    public AutoCompleteDictionaryTrie() {
        root = new Node();
        size = 0;
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        if (root == null) {
            throw new IllegalArgumentException("root cannot be null");
        }
        this.root = root;
    }

    public boolean addWord(String word) {
        if (word == null) {
            return false;
        }

        String normalized = word.trim().toLowerCase(Locale.ROOT);

        if (normalized.isEmpty() || isWord(normalized)) {
            return false;
        }

        HashMap<Character, Node> children = root.children;

        for (int i = 0; i < normalized.length(); i++) {
            char c = normalized.charAt(i);
            Node current;

            if (children.containsKey(c)) {
                current = children.get(c);
            } else {
                current = new Node(c);
                children.put(c, current);
            }

            if (i == normalized.length() - 1) {
                current.setendsWord(true);
                size++;
            }

            children = current.children;
        }

        return true;
    }

    public int size() {
        return size;
    }

    public boolean isWord(String s) {
        if (s == null) {
            return false;
        }

        String normalized = s.trim().toLowerCase(Locale.ROOT);
        if (normalized.isEmpty()) {
            return false;
        }

        Node node = searchNode(normalized);
        return node != null && node.endWord();
    }

    public Node searchNode(String str) {
        if (str == null) {
            return null;
        }

        HashMap<Character, Node> children = root.children;
        Node current = root;

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);

            if (!children.containsKey(c)) {
                return null;
            }

            current = children.get(c);
            children = current.children;
        }

        return current;
    }

    /**
     * Returns every complete dictionary word beginning with prefix.
     */
    public List<String> fetchAll(String prefix) {
        if (prefix == null) {
            return Collections.emptyList();
        }

        String normalized = prefix.trim().toLowerCase(Locale.ROOT);
        Node prefixNode = searchNode(normalized);

        if (prefixNode == null) {
            return Collections.emptyList();
        }

        List<String> results = new ArrayList<>();
        collectWords(prefixNode, normalized, results);
        Collections.sort(results);
        return results;
    }

    /**
     * Keeps your original method name and prints the autocomplete results.
     */
    public void FetchAll(String prefix) {
        List<String> matches = fetchAll(prefix);

        if (matches.isEmpty()) {
            System.out.println("word not found");
            return;
        }

        for (String word : matches) {
            System.out.println(word);
        }
    }

    private void collectWords(Node node, String currentWord, List<String> results) {
        if (node.endWord()) {
            results.add(currentWord);
        }

        ArrayList<Character> nextCharacters =
                new ArrayList<>(node.getValidNextCharacter());
        Collections.sort(nextCharacters);

        for (Character c : nextCharacters) {
            Node child = node.getChild(c);
            collectWords(child, currentWord + c, results);
        }
    }
}