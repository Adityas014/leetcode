import java.util.*;

class Solution {

    static class Node {
        long count;   // how many numbers
        long wavSum;  // total waviness sum

        Node(long count, long wavSum) {
            this.count = count;
            this.wavSum = wavSum;
        }
    }

    private char[] digits;
    private Node[][][][] memo;
    private boolean[][][][] seen;

    public long totalWaviness(long num1, long num2) {
        return solve(num2) - solve(num1 - 1);
    }

    private long solve(long n) {
        if (n < 0) return 0;

        digits = Long.toString(n).toCharArray();

        int len = digits.length;

        // pos, started, prev2(-1..9 => 0..10), prev1(-1..9 => 0..10)
        memo = new Node[len + 1][2][11][11];
        seen = new boolean[len + 1][2][11][11];

        return dfs(0, true, false, -1, -1).wavSum;
    }

    private Node dfs(int pos, boolean tight, boolean started, int prev2, int prev1) {
        if (pos == digits.length) {
            return new Node(1, 0);
        }

        int s = started ? 1 : 0;
        int p2 = prev2 + 1;
        int p1 = prev1 + 1;

        if (!tight && seen[pos][s][p2][p1]) {
            return memo[pos][s][p2][p1];
        }

        int limit = tight ? digits[pos] - '0' : 9;

        long totalCount = 0;
        long totalWav = 0;

        for (int d = 0; d <= limit; d++) {
            boolean nextTight = tight && (d == limit);

            if (!started && d == 0) {
                Node child = dfs(pos + 1, nextTight, false, -1, -1);

                totalCount += child.count;
                totalWav += child.wavSum;
            } else {
                int add = 0;

                // When we place digit d, prev1 becomes an interior digit.
                if (prev2 != -1) {
                    boolean peak = prev1 > prev2 && prev1 > d;
                    boolean valley = prev1 < prev2 && prev1 < d;

                    if (peak || valley) add = 1;
                }

                int newPrev2;
                int newPrev1;

                if (!started) {
                    // first digit of the number
                    newPrev2 = -1;
                    newPrev1 = d;
                } else if (prev2 == -1) {
                    // second digit
                    newPrev2 = prev1;
                    newPrev1 = d;
                } else {
                    // third or later digit
                    newPrev2 = prev1;
                    newPrev1 = d;
                }

                Node child = dfs(pos + 1, nextTight, true, newPrev2, newPrev1);

                totalCount += child.count;
                totalWav += child.wavSum + child.count * add;
            }
        }

        Node res = new Node(totalCount, totalWav);

        if (!tight) {
            seen[pos][s][p2][p1] = true;
            memo[pos][s][p2][p1] = res;
        }

        return res;
    }
}