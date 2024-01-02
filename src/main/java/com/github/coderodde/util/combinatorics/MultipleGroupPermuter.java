package com.github.coderodde.util.combinatorics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * This class provides a method for generating permutations over groups of 
 * elements. If the groups are {@code <1, 2>, <3, 4>, <5>}, the all permutations 
 * produced by the method {@link computeGroupPermutations} are:
 * <ul>
 *  <li>{@code <1, 2> <3, 4> <5>}</li>
 *  <li>{@code <1, 2> <4, 3> <5>}</li>
 *  <li>{@code <2, 1> <3, 4> <5>}</li>
 *  <li>{@code <2, 1> <4, 3> <5>}</li>
 * </ul>
 */
public final class MultipleGroupPermuter<T> {

    // The actual group list. Will be modified by the computeGroupPermutations 
    // method.
    private final List<List<T>> data;
    private final List<List<List<T>>> result;
    
    /**
     * Constructs this object with a given group list.
     * 
     * @param groupList the list of groups.
     */
    public MultipleGroupPermuter(List<List<T>> groupList) {
        Objects.requireNonNull(groupList, "The input group list is null.");
        this.data = validateData(groupList);
        this.result = new ArrayList<>(getNumberOfResultPermutations());
    }
    
    /**
     * Returns the list holding all the group permutations. The algorithm under
     * the hood is an extended 
     * <a href="https://en.wikipedia.org/wiki/Heap%27s_algorithm">
     * Heap's algorithm</a>.
     * 
     * @return the list of all the group permutations.
     */
    public List<List<List<T>>> computeGroupPermutations() {
        if (data.isEmpty()) {
            return new ArrayList<>(1);
        }
        
        computeGroupPermutationsImpl(data.get(0).size(), 0);
        return result;
    }
    
    private void computeGroupPermutationsImpl(int n, int listIndex) {
        // if (n == 0) {
        //     computeGroupPermutationsImpl(data.get(listIndex + 1).size(), listIndex + 1);
        //     return;
        // }
        if (listIndex == data.size() - 1) {
            if (n == 1) {
                result.add(deepCopyGroupPermutation());
                return;
            }
            
            if (n % 2 == 0) {
                for (int i = 0; i < n - 1; i++) {
                    computeGroupPermutationsImpl(n - 1, listIndex);
                    Collections.swap(data.get(listIndex), i, n - 1);
                }
            } else {
                for (int i = 0; i < n - 1; i++) {
                    computeGroupPermutationsImpl(n - 1, listIndex);
                    Collections.swap(data.get(listIndex), 0, n - 1);
                }
            }
            
            computeGroupPermutationsImpl(n - 1, listIndex);
        } else {
            // Here, listIndex < data.size() - 1:
            if (n == 1) {
                computeGroupPermutationsImpl(data.get(listIndex + 1).size(), 
                                             listIndex + 1);
                
                return;
            }
            
            if (n % 2 == 0) {
                for (int i = 0; i < n - 1; i++) {
                    computeGroupPermutationsImpl(n - 1, listIndex);
                    Collections.swap(data.get(listIndex), i, n - 1);
                }
            } else {
                for (int i = 0; i < n - 1; i++) {
                    computeGroupPermutationsImpl(n - 1, listIndex);
                    Collections.swap(data.get(listIndex), 0, n - 1);
                }
            }
            
            computeGroupPermutationsImpl(n - 1, listIndex);
        }
    }
    
    private List<List<T>> deepCopyGroupPermutation() {
        List<List<T>> copy = new ArrayList<>(data.size());
        
        for (List<T> group : data) {
            copy.add(new ArrayList<>(group));
        }
        
        return copy;
    }
    
    private int getNumberOfResultPermutations() {
        int numberOfPermutations = 1;
        
        for (List<T> group : data) {
            numberOfPermutations *= factorial(group.size());
        }
        
        return numberOfPermutations;
    }
    
    private static int factorial(int n) {
        switch (n) {
            case 0:
            case 1:
                return 1;
                
            default:
                return n * factorial(n - 1);
        }
    }
    
    private List<List<T>> validateData(List<List<T>> data) {
        int index = 0;
        
        for (List<T> group : data) {
            Objects.requireNonNull(
                    group, 
                    String.format("The group at index %d is null.", index));
            
            index++;
            
            if (group.isEmpty()) {
                throw new IllegalArgumentException(
                        "Objects of " 
                                + getClass() 
                                + " cannot deal with empty groups.");
            }
        }
        
        return data;
    }
}
