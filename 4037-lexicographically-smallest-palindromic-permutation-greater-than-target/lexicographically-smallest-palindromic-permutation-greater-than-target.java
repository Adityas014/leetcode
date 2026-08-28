class Solution {
    public String lexPalindromicPermutation(String s, String target) {
        // --------------------------------------------------
        // STEP 1: Count frequency of every character
        // --------------------------------------------------
        int[] characterFrequency = new int[26];
        for (char currentCharacter : s.toCharArray()) {
            int characterIndex = currentCharacter - 'a';
            characterFrequency[characterIndex]++;
        }

        // --------------------------------------------------
        // STEP 2: Check whether a palindrome is possible
        // --------------------------------------------------
        int oddFrequencyCount = 0;
        char middleCharacter = 0;
        for (int characterIndex = 0; characterIndex < 26; characterIndex++) {
            if (characterFrequency[characterIndex] % 2 == 1) {
                oddFrequencyCount++;
                middleCharacter = (char) ('a' + characterIndex);
            }
        }
        // More than one odd frequency means palindrome is impossible.
        if (oddFrequencyCount > 1) {
            return "";
        }

        // --------------------------------------------------
        // STEP 3: Keep only half of every frequency
        // --------------------------------------------------
        for (int characterIndex = 0; characterIndex < 26; characterIndex++) {
            characterFrequency[characterIndex] /= 2;
        }

        int stringLength = s.length();
        int halfLength = stringLength / 2;
        char[] leftHalf = new char[halfLength];

        // --------------------------------------------------
        // STEP 4: Build the left half using backtracking
        // --------------------------------------------------
        if (buildLeftHalf(0, false, target, characterFrequency, leftHalf,
                middleCharacter, stringLength)) {
            // --------------------------------------------------
            // STEP 5: Construct the final palindrome
            // --------------------------------------------------
            String leftPart = new String(leftHalf);
            String rightPart = new StringBuilder(leftPart).reverse().toString();
            String palindrome = leftPart;
            if (stringLength % 2 == 1) {
                palindrome += middleCharacter;
            }
            palindrome += rightPart;
            return palindrome;
        }
        return "";
    }

    private boolean buildLeftHalf(int currentPosition, boolean alreadyGreaterThanTarget,
            String target, int[] characterFrequency, char[] leftHalf,
            char middleCharacter, int stringLength) {
        if (currentPosition == leftHalf.length) {
            String leftPart = new String(leftHalf);
            String rightPart = new StringBuilder(leftPart).reverse().toString();
            String palindrome = leftPart;
            if (stringLength % 2 == 1) {
                palindrome += middleCharacter;
            }
            palindrome += rightPart;
            return palindrome.compareTo(target) > 0;
        }

        char firstCharacterToTry;
        if (alreadyGreaterThanTarget) {
            firstCharacterToTry = 'a';
        } else {
            firstCharacterToTry = target.charAt(currentPosition);
        }

        for (char currentCharacter = firstCharacterToTry;
                currentCharacter <= 'z'; currentCharacter++) {
            int characterIndex = currentCharacter - 'a';
            if (characterFrequency[characterIndex] == 0) {
                continue;
            }
            leftHalf[currentPosition] = currentCharacter;
            characterFrequency[characterIndex]--;
            boolean nowGreaterThanTarget = alreadyGreaterThanTarget
                    || currentCharacter > target.charAt(currentPosition);
            if (buildLeftHalf(currentPosition + 1, nowGreaterThanTarget, target,
                    characterFrequency, leftHalf, middleCharacter, stringLength)) {
                return true;
            }
            characterFrequency[characterIndex]++;
        }
        return false;
    }
}