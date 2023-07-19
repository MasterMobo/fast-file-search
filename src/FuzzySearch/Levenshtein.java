package FuzzySearch;

public class Levenshtein {
    /* Levenshtein distance (or edit-distance) is the number of operations needed to get from one string to the other.
     * For this implementation we consider three types of operation:
     * - Add: cot -> cots
     * - Subtract: cost -> cot
     * - Substitute: cost -> coat*/

    public static int distRecur(String a, String b) {
        // Classic recursive implementation. Very slow, only meant as a reference.
        // Time complexity: O(3^n)

        if (a.length() == 0) return b.length();
        if (b.length() == 0) return a.length();

        String tailA = a.substring(1);
        String tailB = b.substring(1);

        if (a.charAt(0) == b.charAt(0)) return distRecur(tailA, tailB);

        int add = distRecur(tailA, b);
        int diff = distRecur(a, tailB);
        int sub = distRecur(tailA, tailB);

        return 1 + min(add, diff, sub);

    }

    public static int distFullMatrix(String a, String b){
        // Dynamic Programming approach with 2D matrix. Faster but could use further space optimization.
        // Time Complexity: O(m*n)
        // Space Complexity: O(m*n)

        int[][] lev = new int[a.length() + 1][b.length() + 1];

        for (int i = 1; i <= a.length(); i++) {
            lev[i][0] = i;
        }

        for (int j = 1; j <= b.length(); j++) {
            lev[0][j] = j;
        }


        for (int i = 1; i <= a.length(); i++) {
            for (int j = 1; j <= b.length() ; j++) {
                int subCost = a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1;

                lev[i][j] = min(1 + lev[i-1][j], 1 + lev[i][j-1], lev[i-1][j-1] + subCost);
            }
        }

        return lev[a.length()][b.length()];
    }

    public static int dist(String a, String b){
        // Dynamic Programming approach with 2 arrays. Better space optimization.
        // Time Complexity: O(m*n)
        // Space Complexity: O(m*n)

        int n = a.length();
        int m = b.length();
        int[] prev = new int[m + 1];
        int[] current = new int[m + 1];
        int[] dummy = prev;     // variable for swapping prev and current

        for (int i = 0; i <= m; i++) {
            prev[i] = i;
        }

        for (int i = 1; i <= n; i++) {
            current[0] = i;

            for (int j = 1; j <= m; j++) {
                int add = current[j - 1] + 1;
                int diff = prev[j] + 1;
                int sub = a.charAt(i - 1) == b.charAt(j - 1) ? prev[j - 1] : prev[j - 1] + 1;

                current[j] = min(add, diff, sub);
            }

            prev = current;
            current = dummy;
            dummy = prev;
        }

        return prev[m];
    }

    private static int min(int a, int b, int c) {
        return Math.min(a, Math.min(b, c));
    }
}
