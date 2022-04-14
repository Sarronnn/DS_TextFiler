package main.textfiller;

import java.util.*;

/**
 * A ternary-search-tree implementation of a text-autocompletion
 * trie, a simplified version of some autocomplete software.
 * @author YOUR_NAME_HERE
 */
public class TernaryTreeTextFiller implements TextFiller {

    // Fields
    // -----------------------------------------------------------
    private TTNode root;
    private int size;
    
    // Constructor
    // -----------------------------------------------------------
    public TernaryTreeTextFiller () {
        this.root = null;
        this.size = 0;
    }
    
    
    // Methods
    // -----------------------------------------------------------
    
    public int size () {
        throw new UnsupportedOperationException();
    }

    public boolean empty () {
        throw new UnsupportedOperationException();
    }
    
    public void add (String toAdd) {
        throw new UnsupportedOperationException();
    }
    
    public boolean contains (String query) {
        throw new UnsupportedOperationException();
    }
    
    public String textFill (String query) {
        throw new UnsupportedOperationException();
    }
    
    public List<String> getSortedList () {
        throw new UnsupportedOperationException();
    }
    
    
    // Private Helper Methods
    // -----------------------------------------------------------
    
    /**
     * Normalizes a term to either add or search for in the tree,
     * since we do not want to allow the addition of either null or
     * empty strings within, including empty spaces at the beginning
     * or end of the string (spaces in the middle are fine, as they
     * allow our tree to also store multi-word phrases).
     * @param s The string to sanitize
     * @return The sanitized version of s
     */
    private String normalizeTerm (String s) {
        // Edge case handling: empty Strings illegal
        if (s == null || s.equals("")) {
            throw new IllegalArgumentException();
        }
        return s.trim().toLowerCase();
    }
    
    /**
     * Given two characters, c1 and c2, determines whether c1 is
     * alphabetically less than, greater than, or equal to c2
     * @param c1 The first character
     * @param c2 The second character
     * @return
     *   - some int less than 0 if c1 is alphabetically less than c2
     *   - 0 if c1 is equal to c2
     *   - some int greater than 0 if c1 is alphabetically greater than c2
     */
    private int compareChars (char c1, char c2) {
        return Character.toLowerCase(c1) - Character.toLowerCase(c2);
    }
    
    // [!] Add your own helper methods here!
    
    

    
    
    // TTNode Internal Storage
    // -----------------------------------------------------------
    
    /**
     * Internal storage of textfiller search terms
     * as represented using a Ternary Tree (TT) with TTNodes
     * [!] Note: these are currently implemented for the base-assignment;
     *     those endeavoring the extra-credit may need to make changes
     *     below (primarily to the fields and constructor)
     */
    private class TTNode {
        
        boolean wordEnd;
        char letter;
        TTNode left, mid, right;
        
        /**
         * Constructs a new TTNode containing the given character
         * and whether or not it represents a word-end, which can
         * then be added to the existing tree.
         * @param letter Letter to store at this node
         * @param wordEnd Whether or not this is a word-ending letter
         */
        TTNode (char letter, boolean wordEnd) {
            this.letter  = letter;
            this.wordEnd = wordEnd;
        }
        
    }
    
}
