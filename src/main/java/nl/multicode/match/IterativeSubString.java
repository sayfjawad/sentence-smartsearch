package nl.multicode.match;

import jakarta.enterprise.context.ApplicationScoped;

/**
 * Implements Iterative-SubString (I-Sub) similarity based on Stoilos (2005).
 */
@ApplicationScoped
public class IterativeSubString {

    private final double hamacher;
    private final boolean normalizeStrings;

    public IterativeSubString() {
        this(0.6, false);
    }

    public IterativeSubString(double hamacher, boolean normalizeStrings) {
        this.hamacher = hamacher;
        this.normalizeStrings = normalizeStrings;
    }

    /**
     * Computes the I-Sub similarity between two strings.
     *
     * @param src The source string.
     * @param tar The target string.
     * @return The similarity score in the range [0,1].
     */
    public double sim(String src, String tar) {
        return (corr(src, tar) + 1.0) / 2.0;
    }

    /**
     * Computes the I-Sub correlation between two strings.
     *
     * @param src The source string.
     * @param tar The target string.
     * @return The correlation value (which may be negative).
     */
    private double corr(String src, String tar) {
        src = preprocess(src);
        tar = preprocess(tar);

        int srcLen = src.length();
        int tarLen = tar.length();
        if (srcLen == 0 || tarLen == 0) return -1.0;

        int common = computeCommonCharacters(src, tar);
        double commonality = (2.0 * common) / (srcLen + tarLen);
        double winklerBonus = computeWinklerBonus(src, tar, commonality);
        double dissimilarity = computeDissimilarity(srcLen, tarLen, common);

        return commonality - dissimilarity + winklerBonus;
    }

    private String preprocess(String str) {
        if (!normalizeStrings) return str;
        return str.toLowerCase().replaceAll("[._ ]", "");
    }

    private int computeCommonCharacters(String src, String tar) {
        int common = 0;
        int bestMatch = 2;
        String tempSrc = src, tempTar = tar;

        while (!tempSrc.isEmpty() && !tempTar.isEmpty() && bestMatch > 0) {
            bestMatch = 0;
            int srcLength = tempSrc.length(), tarLength = tempTar.length();
            int startSrc = 0, startTar = 0, endSrc = 0, endTar = 0;

            for (int i = 0; i < srcLength && srcLength - i > bestMatch; i++) {
                for (int j = 0; j < tarLength && tarLength - j > bestMatch; j++) {
                    int k = i;
                    while (j < tarLength && tempSrc.charAt(k) != tempTar.charAt(j)) j++;
                    if (j < tarLength) {
                        int p = j;
                        j++;
                        k++;
                        while (j < tarLength && k < srcLength && tempSrc.charAt(k) == tempTar.charAt(j)) {
                            j++;
                            k++;
                        }
                        if (k - i > bestMatch) {
                            bestMatch = k - i;
                            startSrc = i;
                            endSrc = k;
                            startTar = p;
                            endTar = j;
                        }
                    }
                }
            }

            tempSrc = tempSrc.substring(0, startSrc) + tempSrc.substring(endSrc);
            tempTar = tempTar.substring(0, startTar) + tempTar.substring(endTar);
            if (bestMatch > 2) common += bestMatch;
        }

        return common;
    }

    private double computeDissimilarity(int srcLen, int tarLen, int common) {
        double unmatchedSrc = Math.max(srcLen - common, 0) / (double) srcLen;
        double unmatchedTar = Math.max(tarLen - common, 0) / (double) tarLen;
        double unmatchedProd = unmatchedSrc * unmatchedTar;

        return unmatchedProd / (hamacher + (1 - hamacher) * (unmatchedSrc + unmatchedTar - unmatchedProd));
    }

    private double computeWinklerBonus(String src, String tar, double commonality) {
        int prefix = 0;
        int minLen = Math.min(src.length(), tar.length());
        for (int i = 0; i < minLen; i++) {
            if (src.charAt(i) != tar.charAt(i)) break;
            prefix++;
        }
        return Math.min(4.0, prefix) * 0.1 * (1.0 - commonality);
    }
}
