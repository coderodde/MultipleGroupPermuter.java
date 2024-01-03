package com.github.coderodde.util.combinatorics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;

public final class MultipleGroupPermuterTest {
    
    @Test(expected = NullPointerException.class)
    public void throwsNullPointerExceptionOnNullData() {
        new MultipleGroupPermuter<>(null);
    }
    
    @Test
    public void returnsOneNullArray() {
        List<List<Integer>> data = new ArrayList<>();
        
        List<Integer> group = new ArrayList<>();
        group.add(null);
        data.add(group);
        
        List<List<List<Integer>>> result = 
                new MultipleGroupPermuter<>(data)
                        .computeGroupPermutations();
        
        assertEquals(1, result.size());
        
        List<List<Integer>> permutation = result.get(0);
        
        assertNull(permutation.get(0).get(0));
    }
    
    @Test
    public void returnsTwoNullArrays() {
        List<List<Integer>> data = new ArrayList<>();
        
        List<Integer> group1 = new ArrayList<>();
        List<Integer> group2 = new ArrayList<>();
        
        group1.add(null);
        group2.add(null);
        
        data.add(group1);
        data.add(group2);
        
        List<List<List<Integer>>> result = 
                new MultipleGroupPermuter<>(data)
                        .computeGroupPermutations();
        
        // Number of permutations must be 2:
        assertEquals(1, result.size());
        
        // First permutation:
        List<List<Integer>> permutation = result.get(0);
        
        assertNull(permutation.get(0).get(0));
        assertNull(permutation.get(1).get(0));
    }
    
    @Test
    public void uniqueGroupPermutations() {
        List<List<Integer>> data = new ArrayList<>();
        List<Integer> group = Arrays.asList(1, null, null);
        data.add(group);
        
        List<List<List<Integer>>> groupPermutations = 
                new MultipleGroupPermuter<>(data).computeGroupPermutations();
        
        Set<List<List<Integer>>> uniqueGroupPermutations = new HashSet<>();
        
        for (List<List<Integer>> groupPermutation : groupPermutations) {
            uniqueGroupPermutations.add(groupPermutation);
        }
        
        assertEquals(3, uniqueGroupPermutations.size());
        
        List<List<Integer>>[] uniqueGroupPermutationArray = new List[3];
        uniqueGroupPermutationArray = 
                uniqueGroupPermutations.toArray(uniqueGroupPermutationArray);
        
        assertEquals(3, uniqueGroupPermutationArray.length);
    }
}
