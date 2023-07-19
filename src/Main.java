import FuzzySearch.BKTree;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        BKTree tree = new BKTree();


        System.out.print("Enter directory to search: ");
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();

        System.out.println("Processing files...");
        try (Stream<Path> stream = Files.walk(Paths.get(s))) {
            List<Path> res = stream.toList();

            for (Path file: res) {
                String fileName = stripExtension(file.getFileName().toString());

                if (Files.isRegularFile(file) && !Files.isHidden(file)) {
                    tree.insert(stripExtension(fileName), file.toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (!Objects.equals(s, "0")) {
            System.out.print("Search for: ");

            s = scanner.nextLine();
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
}