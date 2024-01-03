package com.github.coderodde.util.combinatorics;

import java.util.Comparator;
import java.util.List;

/**
 * This class implements a comparator for comparing the groups. Note that it 
 * won't accept groups of different sizes and will throw an
 * {@link IllegalArgumentException}.
 * 
 * @param <T> the element type of a group.
 */
public final class GroupComparator<T> implements Comparator<List<T>> {

    private final Comparator<T> elementComparator;
    
    /**
     * Constructs a new group comparator.
     * 
     * @param elementComparator the element comparator. 
     */
    public GroupComparator(Comparator<T> elementComparator) {
        this.elementComparator = elementComparator;
    }
    
    @Override
    public int compare(List<T> group1, List<T> group2) {
        if (group1 == null) {
            if (group2 == null) {
                // Both null:
                return 0;
            } else {
                // group1 null and group2 is non null:
                return -1;
            }
        } else {
            // Here, group1 != null:
            if (group2 == null) {
                return 1;
            }
        }
        
        // Here, both group1 and group2 are not null:
        if (group1.size() != group2.size()) {
            // Size mismatch, throw:
            throw new IllegalArgumentException(
                    "The input groups are of differnt sizes. "
                            + group1.size() 
                            + " vs. " 
                            + group2.size());
        }
        
        // Here, group1 != null and group2 != null and both of the same size:
        for (int i = 0; i < group1.size(); i++) {
            T element1 = group1.get(i);
            T element2 = group2.get(i);
            
            if (element1 == null) {
                if (element2 == null) {
                    return 0;
                } else {
                    return -1;
                }
            } else {
                // Here, element1 != null:
                if (element2 == null) {
                    return 1;
                }
            }
            
            int cmp = elementComparator.compare(element1, element2);
            
            if (cmp != 0) {
                // Found a different spot, return:
                return cmp;
            }
        }
        
        // Here, both group1 and group2 are the same:
        return 0;
    }
}
