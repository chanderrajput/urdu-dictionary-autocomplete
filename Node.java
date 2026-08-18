import java.util.HashMap;
import java.util.Set;

public class Node {
    // Package-private so the trie can access it, matching your original design.
    HashMap<Character, Node> children;
    private char text;
    private boolean isWord;

    public Node() {
        children = new HashMap<>();
        text = ' ';
        isWord = false;
    }

    public Node(char text) {
        this();
        this.text = text;
    }

    public Node getChild(Character c) {
        return children.get(c);
    }

    public Node insert(Character c) {
        if (children.containsKey(c)) {
            return null;
        }

        // FIX: use c, not the current node's text.
        Node next = new Node(c);
        children.put(c, next);
        return next;
    }

    public char getText() {
        return text;
    }

    public void setendsWord(boolean b) {
        isWord = b;
    }

    public boolean endWord() {
        return isWord;
    }

    public Set<Character> getValidNextCharacter() {
        return children.keySet();
    }
}