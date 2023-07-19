package FuzzySearch;

import java.util.HashMap;

public class Node {
    public String name;
    public HashMap<Integer, Node> children;
    public String path;

    public Node(String name, String path) {
        this.name = name;
        this.path = path;
        children = new HashMap<>();
    }

    public void addChild(String name, String path, int dist) {
        children.put(dist, new Node(name, path));
    }

    public Node get(int n) {
        return children.get(n);
    }

    public boolean exists(int n) {
        return children.containsKey(n);
    }
}
