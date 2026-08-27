class Solution {
    public String lexGreaterPermutation(String s, String target) {
        int n = s.length();
        int[] freq = new int[26];
        for (char c : s.toCharArray()) freq[c - 'a']++;

        // m = length of the longest prefix of target we can match exactly
        int m = 0;
        int[] f = freq.clone();
        while (m < n && f[target.charAt(m) - 'a'] > 0) {
            f[target.charAt(m) - 'a']--;
            m++;
        }

        // Try to bump at the rightmost position i (from m down to 0).
        // Keep target[0..i-1] as prefix, place smallest char > target[i],
        // then append remaining chars in sorted order.
        for (int i = m; i >= 0; i--) {
            // Remaining pool after consuming target[0..i-1]
            int[] avail = freq.clone();
            boolean ok = true;
            for (int j = 0; j < i; j++) {
                char c = target.charAt(j);
                if (avail[c - 'a'] <= 0) { ok = false; break; }
                avail[c - 'a']--;
            }
            if (!ok) continue;

            // i == n means we matched the whole target exactly -> not strictly greater
            if (i == n) continue;

            int cur = target.charAt(i) - 'a';
            // Place the smallest char strictly greater than target[i]
            boolean done = false;
            for (int c = cur + 1; c < 26; c++) {
                if (avail[c] > 0) {
                    avail[c]--;
                    StringBuilder res = new StringBuilder();
                    res.append(target, 0, i);
                    res.append((char) ('a' + c));
                    // Append remaining chars in sorted order (smallest first)
                    for (int k = 0; k < 26; k++) {
                        while (avail[k] > 0) {
                            res.append((char) ('a' + k));
                            avail[k]--;
                        }
                    }
                    return res.toString();
                }
            }
        }
        return "";
    }
}