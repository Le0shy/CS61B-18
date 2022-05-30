import org.junit.Test;

import static org.junit.Assert.*;

public class TestPalindrome {
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();
    static OffByOne comparator = new OffByOne();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        assertFalse(palindrome.isPalindrome("cat"));
        assertTrue(palindrome.isPalindrome("toohottohoot"));
        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome("noon"));

        assertTrue(palindrome.isPalindrome("flake", comparator));
        assertFalse(palindrome.isPalindrome("noon", comparator));
        assertTrue(palindrome.isPalindrome("a", comparator));
        assertTrue(palindrome.isPalindrome("", comparator));
    }
}
