package nl.multicode.match;

import jakarta.enterprise.context.ApplicationScoped;

/**
 * Implements Indel Distance (insertions and deletions only).
 */
@ApplicationScoped
public class Indel {

    /**
     * Computes the Indel Distance between two strings.
     *
     * @param src The source string.
     * @param tar The target string.
     * @return The Indel Distance (number of insertions/deletions).
     */
    public int compute(String src, String tar) {
        final int[][] distanceMatrix = new int[src.length() + 1][tar.length() + 1];

        for (int i = 0; i <= src.length(); i++) {
            distanceMatrix[i][0] = i;
        }
        for (int j = 0; j <= tar.length(); j++) {
            distanceMatrix[0][j] = j;
        }

        for (int i = 1; i <= src.length(); i++) {
            for (int j = 1; j <= tar.length(); j++) {
                if (src.charAt(i - 1) == tar.charAt(j - 1)) {
                    distanceMatrix[i][j] = distanceMatrix[i - 1][j - 1]; // No cost for matching chars
                } else {
                    distanceMatrix[i][j] = Math.min(
                            distanceMatrix[i - 1][j] + 1, // Deletion
                            distanceMatrix[i][j - 1] + 1  // Insertion
                    );
                }
            }
        }

        return distanceMatrix[src.length()][tar.length()];
    }

    /**
     * Computes the normalized similarity score between two strings.
     *
     * @param src The source string.
     * @param tar The target string.
     * @return A similarity score between 0 (completely different) and 1 (identical).
     */
    public double computeNormalized(String src, String tar) {
        int distance = compute(src, tar);
        return 1.0 - ((double) distance / Math.max(src.length(), tar.length()));
    }
}
