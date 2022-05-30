public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> deque = new ArrayDeque<>();
        for (int i = 0; i < word.length(); i++) {
            deque.addLast(word.charAt(i));
        }
        return deque;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> deque = new ArrayDeque<>();
        for (int i = 0; i < word.length(); i += 1) {
            deque.addLast(word.charAt(i));
        }
        return isPalindromeHelper(deque);
    }

    private boolean isPalindromeHelper(Deque deque) {
        if (deque.size() == 0 || deque.size() == 1) {
            return true;
        } else {
            if (deque.removeLast() == deque.removeFirst()) {
                return isPalindromeHelper(deque);
            } else {
                return false;
            }
        }
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> deque = new ArrayDeque<>();
        for (int i = 0; i < word.length(); i += 1) {
            deque.addLast(word.charAt(i));
        }
        return isPalindromeHelper(deque, cc);
    }

    private boolean isPalindromeHelper(Deque deque, CharacterComparator comparator) {
        if (deque.size() == 0 || deque.size() == 1) {
            return true;
        } else {
            if (comparator.equalChars((char)deque.removeFirst(),(char)deque.removeLast())) {
                return isPalindromeHelper(deque,comparator);
            } else {
                return false;
            }
        }
    }
}
