package main.textfiller;

import java.util.*;

/**
 * A ternary-search-tree implementation of a text-autocompletion
 * trie, a simplified version of some autocomplete software.
 * @author SARRON_TADESSE
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
        return getSize(root);
    }

    public boolean empty () {
        if (size == 0) {
        	return true;
        }else {
        	return false;
        }
    }
    
    public void add (String toAdd) {
    	if (!this.contains(normalizeTerm(toAdd))) {
             this.root = this.add(this.root, normalizeTerm(toAdd), 0);
        }
    }
    
    public boolean contains (String word) {
    	return this.contains(this.root, normalizeTerm(word));
    }
    
    public String textFill (String word) {
    	return this.textFill(this.root, normalizeTerm(word), "");
    }
    
    public List<String> getSortedList() {
       ArrayList<String> sortedList = new ArrayList<>();
       if (this.empty()) {
    	   return sortedList;
       }
       this.getSortedList(this.root, "", sortedList);
       return sortedList;
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
 // Question for office hours: What if root is not empty?? We compare the first character of the new word with that of root and
 //decide where is goes
	
    /**
     * @param node is the current node
     * @return the size of the node
     */
    private int getSize(TTNode node) {
    	return size;
    }
    /**
     * 
     * @param node is the current node
     * @param prefix is the prefix to search for
     * @param other a string that contains the other parts of the word
     * @return string with prefix as its prefix
     */
    private String textFill(TTNode node, String prefix, String other) {
        if (node == null) {
            return null;
        }
        int compare = compareChars(prefix.charAt(0), node.letter);
        if (compare == 0) {
            if (prefix.length() == 1) {
                return suffix(node, other);
            }
            other += node.letter;
            return textFill(node.mid, prefix.substring(1), other);
        } 
        else if (compare > 0) {
            return textFill(node.right, prefix, other);
        } 
        else {
            return textFill(node.left, prefix, other);
        }
    }
    /**
     * Collects the suffixes
     * @param node is the current node
     * @param word is a string with the already collected letters
     * @return String with the collected letters
     */
    private String suffix(TTNode node, String word) {
        word += node.letter;
        return node.wordEnd ? word : suffix(node.mid, word);
    }
    
    /**
     * Adds new word 
     * @param toAdd is the new word to be added
     * @param queue is the order of where to add the new term 
     */
    public void add(String toAdd, int queue) {
        toAdd = normalizeTerm(toAdd);
        if (!this.contains(toAdd)) {
            this.root = this.add(this.root, toAdd, queue);
        }
    }
    
    /**
     * Adds a word to our tree
     * @param node is the current node
     * @param toAdd is the term to be added
     * @param queue is the order of where to add the new term
     * @return the new node with the added word
     */
    private TTNode add(TTNode node, String toAdd, int queue) {
        if (node == null) {
            return toAddSuffix(node, toAdd, queue);
        }
        int compare = compareChars(toAdd.charAt(0), node.letter);
        if (compare == 0) {
            if (node.queue < queue) {
                node.queue = queue;
            }
            if (toAdd.length() == 1 && !node.wordEnd) {
                this.size++;
                node.wordEnd = true;
                return node;
            }
            node.mid = add(node.mid, toAdd.substring(1), queue);
        } else if (compare > 0) {
            node.right = add(node.right, toAdd, queue);
        } else {
            node.left = add(node.left, toAdd, queue);
        }
        return node;
    }


    /**
     * Searches tree for prefix
     * @param node the current node
     * @param word the prefix to search 
     * @param other the  second part of the word. 
     * @return the word that contains word as its prefix. 
     */
    private String fullWord(TTNode node, String word, String other) {
        if (node == null) {
            return null;
        }
        int compare = compareChars(word.charAt(0), node.letter);
        if (compare < 0) {
            return fullWord(node.left, word, other);
        } else if (compare > 0) {
            return fullWord(node.right, word, other);
        } else {
            other += node.letter;
            if (word.length() == 1) {
                return node.mid == null ? other : suffix(node.mid, other, node.queue);
            }
            return fullWord(node.mid, word.substring(1), other);
        }
    }
 
    
    public String returnFullword(String word) {
        return this.fullWord(this.root, normalizeTerm(word), "");
    }

    
    /**
     * Collects the suffix 
     * @param node the current node
     * @param word the String that contains already collected letters
     * @return a String that contains term with highest priority
     */
    private String suffix(TTNode node, String word, int queue) {
        if (node.queue > queue) {
            queue = node.queue;
        }
        if (node.left != null) {
            word = suffix(node.left, word, queue);
            if (node.left.queue >= queue) {
                return word;
            }
        }
        if (node.right != null) {
            word = suffix(node.right, word, queue);
            if (node.right.queue >= queue) {
                return word;
            }
        }
        if (node.queue < queue) {
            return word;
        }
        if (node.mid != null) {
            return suffix(node.mid, word + node.letter, queue);
        }
        return word + node.letter;
    }
    
    
   /**
    *  Adds the suffix following the middle path
    * @param node is the current node
    * @param suffix is the new suffix we want to add
    * @param queue is the order of where to add the new term
    * @return the node with the new suffix
    */
    private TTNode toAddSuffix(TTNode node, String suffix, int queue) {
        if (suffix.length() <= 0) {
            return null;
        }
        node = new TTNode(suffix.charAt(0), suffix.length() == 1, queue);
        if (suffix.length() == 1) {
            this.size++;
        } else {
            node.mid = toAddSuffix(node.mid, suffix.substring(1), queue);
        }
        return node;
    }
    
    
    private boolean contains(TTNode node, String word) {
        if (node == null) {
            return false;
        }
        int compare = compareChars(word.charAt(0), node.letter);
        if (compare == 0) {
        	if (word.length() == 1) {
                return node.wordEnd;
        	}
        	return contains(node.mid, word.substring(1));
        } 
        else if (compare > 0) {
            return contains(node.right, word);
        } 
        else {
            return contains(node.left, word);
        }
    }
    
    
    /**
     * Adds all terms into an array list
     * @param node is the current node
     * @param word is the current word
     * @param collect is a list of all the collected words
     */
    private void getSortedList(TTNode node, String word, ArrayList<String> collect) {
        if (node.left != null) {
            getSortedList(node.left, word, collect);
        }
        if (node.wordEnd) {
            collect.add(word + node.letter);
        }
        if (node.mid != null) {
            getSortedList(node.mid, word + node.letter, collect);
        }
        if (node.right != null) {
            getSortedList(node.right, word, collect);
        }
    }
    

    
    
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
        
       // public TTNode middle;
		boolean wordEnd;
        char letter;
        TTNode left, mid, right;
        int queue;
        
        
        /**
         * Constructs a new TTNode containing the given character
         * and whether or not it represents a word-end, which can
         * then be added to the existing tree.
         * @param letter Letter to store at this node
         * @param wordEnd Whether or not this is a word-ending letter
         */
        TTNode (char letter, boolean wordEnd, int queue) {
            this.letter  = letter;
            this.wordEnd = wordEnd;
            this.queue = queue;
        }
        
    }
    
}
