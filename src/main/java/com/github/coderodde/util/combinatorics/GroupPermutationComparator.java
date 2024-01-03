package com.github.coderodde.util.combinatorics;

import java.util.Comparator;
import java.util.List;

/**
 * This class implements a comparator comparing group permutations It accepts 
 * only group permutations of the same size. If the aforementioned invariant is
 * broken, this comparator throws {@link IllegalArgumentException}.
 * 
 * @param <T> the element type of groups. 
 */
public final class GroupPermutationComparator<T> 
        implements Comparator<List<List<T>>> {
    
    private final Comparator<List<T>> groupComparator;
    
    /**
     * Constructs a new group permutation comparator.
     * 
     * @param groupComparator the group comparator. 
     */
    public GroupPermutationComparator(Comparator<List<T>> groupComparator) {
        this.groupComparator = groupComparator;
    }
    
    @Override
    public int compare(List<List<T>> groupPermutation1,
                       List<List<T>> groupPermutation2) {
        if (groupPermutation1.size() != groupPermutation2.size()) {
            throw new IllegalArgumentException(
                    "The input group permutations are of different size. " 
                            + groupPermutation1.size()
                            + " vs. "
                            + groupPermutation2.size());
        }
        
        for (int i = 0; i < groupPermutation1.size(); i++) {
            List<T> group1 = groupPermutation1.get(i);
            List<T> group2 = groupPermutation2.get(i);
            
            int cmp = groupComparator.compare(group1, group2);
            
            if (cmp != 0) {
                return cmp;
            }
        }
        
        return 0;
    }
}
