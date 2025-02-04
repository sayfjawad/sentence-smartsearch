package nl.multicode.match;

import jakarta.enterprise.context.ApplicationScoped;

/**
 * Implements Tichy’s edit distance.
 * <p>
 * Tichy’s algorithm locates substrings (blocks) in a source string that can be copied
 * to reconstruct a target string. Only block moves and add operations are allowed.
 * The absolute distance is computed as:
 *
 * <pre>
 *     distance = (# block moves) * moveCost + (# add operations) * addCost
 * </pre>
 *
 * The normalized distance divides the absolute score by (target length × max(cost)).
 */
@ApplicationScoped
public class Tichy {
    private final int moveCost;
    private final int addCost;

    public Tichy() {
        this(1, 1);
    }

    public Tichy(int moveCost, int addCost) {
        this.moveCost = moveCost;
        this.addCost = addCost;
    }

    /**
     * Computes the absolute Tichy distance between two strings.
     *
     * @param src The source string.
     * @param tar The target string.
     * @return The absolute Tichy distance.
     */
    public double distAbs(String src, String tar) {
        if (src.equals(tar)) {
            return 0;
        }

        int moves = 0;
        int adds = 0;
        final int tarLen = tar.length();
        int qPos = 0;

        while (qPos < tarLen) {
            int bestLength = findMaxBlock(src, tar, qPos);
            if (bestLength > 0) {
                moves++;
            } else {
                adds++;
            }
            qPos += Math.max(1, bestLength);
        }
        return moves * moveCost + adds * addCost;
    }

    /**
     * Computes the normalized Tichy distance.
     *
     * @param src The source string.
     * @param tar The target string.
     * @return A similarity score in range [0,1].
     */
    public double dist(String src, String tar) {
        if (src.equals(tar)) {
            return 1.0;
        }
        double score = distAbs(src, tar);
        int maxCost = Math.max(moveCost, addCost);
        return 1.0 - (score / (tar.length() * maxCost)); // Normalize similarity score
    }

    /**
     * Finds the maximum block length matching between src and tar, starting from position qPos in tar.
     *
     * @param src  The source string.
     * @param tar  The target string.
     * @param qPos The current starting index in tar.
     * @return The length of the longest matching block found.
     */
    private int findMaxBlock(String src, String tar, int qPos) {
        int bestLength = 0;
        final int srcLen = src.length();
        final int tarLen = tar.length();

        for (int pCur = 0; pCur < srcLen; pCur++) {
            int lengthCur = 0;

            while (pCur + lengthCur < srcLen &&
                    qPos + lengthCur < tarLen &&
                    src.charAt(pCur + lengthCur) == tar.charAt(qPos + lengthCur)) {
                lengthCur++;
            }
            bestLength = Math.max(bestLength, lengthCur);
        }
        return bestLength;
    }
}
