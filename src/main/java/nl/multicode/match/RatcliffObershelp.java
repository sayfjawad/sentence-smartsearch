package nl.multicode.match;

import jakarta.enterprise.context.ApplicationScoped;

/**
 * RatcliffObershelp implementeert de Ratcliff-Obershelp gelijkenis.
 * <p>
 * De gelijkenis wordt berekend als:
 * <pre>
 *     similarity = (2 * som van overeenkomende substrings) / (length(src) + length(tar))
 * </pre>
 */
@ApplicationScoped
public class RatcliffObershelp {

    /**
     * Berekent de Ratcliff-Obershelp gelijkenis tussen twee strings.
     *
     * @param src De bronstring.
     * @param tar De doelstring.
     * @return De gelijkenisscore in het bereik [0,1].
     */
    public double sim(String src, String tar) {
        if (src.equalsIgnoreCase(tar)) {
            return 1.0;
        }
        if (src.isEmpty() || tar.isEmpty()) {
            return 0.0;
        }

        int matchScore = substringMatches(src, tar);
        return (2.0 * matchScore) / (src.length() + tar.length());
    }

    /**
     * Vindt de som van alle overeenkomende substringlengtes recursief.
     *
     * @param src De bronstring.
     * @param tar De doelstring.
     * @return De som van substring matchlengtes.
     */
    private int substringMatches(String src, String tar) {
        LcsMatch lcsMatch = findLcs(src, tar);
        if (lcsMatch.length == 0) {
            return 0;
        }

        return lcsMatch.length
                + substringMatches(src.substring(0, lcsMatch.srcStart), tar.substring(0, lcsMatch.tarStart))
                + substringMatches(src.substring(lcsMatch.srcStart + lcsMatch.length), tar.substring(lcsMatch.tarStart + lcsMatch.length));
    }

    /**
     * Vindt de langste gemeenschappelijke substring (LCS) tussen twee strings.
     *
     * @param src De bronstring.
     * @param tar De doelstring.
     * @return De LCS matchdata (startposities en lengte).
     */
    private LcsMatch findLcs(String src, String tar) {
        int[][] dp = new int[src.length() + 1][tar.length() + 1];
        int maxLength = 0;
        int srcStart = 0;
        int tarStart = 0;

        for (int i = 1; i <= src.length(); i++) {
            for (int j = 1; j <= tar.length(); j++) {
                if (src.charAt(i - 1) == tar.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                    if (dp[i][j] > maxLength) {
                        maxLength = dp[i][j];
                        srcStart = i - maxLength;
                        tarStart = j - maxLength;
                    }
                }
            }
        }
        return new LcsMatch(srcStart, tarStart, maxLength);
    }

    /**
     * Helper klasse om LCS matchdata op te slaan.
     */
    private static class LcsMatch {
        final int srcStart;
        final int tarStart;
        final int length;

        LcsMatch(int srcStart, int tarStart, int length) {
            this.srcStart = srcStart;
            this.tarStart = tarStart;
            this.length = length;
        }
    }
}
