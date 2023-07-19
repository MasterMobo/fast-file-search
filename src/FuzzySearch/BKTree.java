package FuzzySearch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class BKTree {
    public Node root;

    public BKTree() {
        root = null;
    }

    public BKTree(String name, String path) {
        root = new Node(name, path);
    }

    public void insert(String name, String path) {
        if (root == null) {
            root = new Node(name, path);
            return;
        }

        Node current = root;
        int dist = Levenshtein.dist(name, current.name);

        if (dist == 0) return;

        while (current.exists(dist)) {
            current = current.get(dist);
            dist = Levenshtein.dist(name, current.name);
        }

        current.addChild(name, path, dist);
    }


    private void search(String s, int tol, ArrayList<String> res, Node node, HashMap<String, Integer> map) {
        int dist = Levenshtein.dist(s, node.name);

        if (dist <= tol) {
            res.add(node.path);
            map.put(node.path, dist);
        };

        int i = dist-tol;
        if(i<0) i=1;

        while(i <= dist + tol){
            if (node.exists(i)) {
                search(s, tol, res, node.get(i), map);
            }
            i++;
        }
    }

    public ArrayList<String> search(String s, int tol) {
        ArrayList<String> res = new ArrayList<>(); // Stores unsorted result
        HashMap<String, Integer> map = new HashMap<>(); // Maps path to levenshtein distance (to be sorted by distance)

        search(s, tol, res, root, map);
        res.sort(Comparator.comparing(map::get));

        return res;
    }
}
