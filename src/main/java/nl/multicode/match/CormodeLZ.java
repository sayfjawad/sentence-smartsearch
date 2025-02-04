package nl.multicode.match;

import jakarta.enterprise.context.ApplicationScoped;

/**
 * Implements Cormode's LZ distance for text similarity detection.
 *
 * <p>
 * The algorithm processes the source string in blocks by checking whether an expanding
 * substring (from a current position) is present in the target string or in the portion
 * of the source already seen. When the substring is no longer found, an edit is recorded,
 * the position is advanced, and the span is reset.
 * </p>
 */
@ApplicationScoped
public class CormodeLZ {

    /**
     * Computes the absolute Cormode LZ distance between two strings.
     *
     * @param src the source string
     * @param tar the target string
     * @return the absolute distance
     */
    public double computeAbsoluteDistance(String src, String tar) {
        int edits = 0;
        int pos = 0;
        int span = 1;
        int srcLen = src.length();

        while (pos + span <= srcLen) {
            String substring = src.substring(pos, pos + span);

            if (tar.contains(substring) || src.substring(0, pos).contains(substring)) {
                span++;
                if (pos + span > srcLen) break;
            } else {
                edits++;
                pos += Math.max(1, span - 1);
                span = 1;
            }
        }

        return 1 + edits;
    }

    /**
     * Computes the normalized Cormode LZ distance between two strings.
     *
     * @param src the source string
     * @param tar the target string
     * @return the normalized distance
     */
    public double computeNormalizedDistance(String src, String tar) {
        double absDistance = computeAbsoluteDistance(src, tar) - 1;
        return (absDistance == 0) ? 0.0 : absDistance / src.length();
    }
}
