import FuzzySearch.BKTree;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Stream;

public class Main {
    // TODO: Its still throwing AccessDenied Errors
    public static List<Path> getFiles(String path) throws IOException {
        try (Stream<Path> stream = Files.walk(Paths.get(path))){
            return stream.toList();
        }catch (AccessDeniedException e) {
                System.out.println("Access to directory denied");
                return null;
            }
        catch (IOException e) {
                System.out.println("Invalid Directory");
                return null;
            }
        }


    public static void main(String[] args) throws IOException {
        BKTree tree = new BKTree();


        System.out.print("Enter directory to search: ");
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine().trim();
        List<Path> files = getFiles(s);

        while (files == null) {
            System.out.print("Please enter a different directory: ");
            s = scanner.nextLine().trim();
            files = getFiles(s);
        }

        addToTree(tree, files);

        while (true) {
            System.out.print("Search for: ");
            s = scanner.nextLine().trim();
            if (s.equals("!q")) return;

            List<String> res = tree.search(s, 10, 20);
            for (String str: res) {
                System.out.println(str);
            }
        }
    }

    static String stripExtension (String str) {
        // Handle null case specially.
        if (str == null) return null;

        // Get position of last '.'.
        int pos = str.lastIndexOf(".");


        // If there wasn't any '.' just return the string as is.
        if (pos == -1) return str;

        // Otherwise return the string, up to the dot.
        return str.substring(0, pos);
    }

    public static void addToTree(BKTree tree, List<Path> files) throws IOException {
        for (Path file : files) {
            String fileName = stripExtension(file.getFileName().toString());

            if (Files.isRegularFile(file) && !Files.isHidden(file)) {
                tree.insert(stripExtension(fileName), file.toString());
            }
        }
    }
}