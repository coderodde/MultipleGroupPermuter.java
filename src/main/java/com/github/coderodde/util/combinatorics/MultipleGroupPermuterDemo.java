package com.github.coderodde.util.combinatorics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class MultipleGroupPermuterDemo {
    
    public static void main(String[] args) {
        List<List<Integer>> data = new ArrayList<>();
        
        data.add(null);
        data.add(Arrays.asList(1, 2, 3));
        data.add(Arrays.asList(4));
        data.add(Arrays.asList());
        data.add(Arrays.asList(null, 6, null, 5));
        data.add(Arrays.asList());
        data.add(null);
        
        // Make sure 'data' remains intact since the permuter modifies the order
        // of elements in the input data:
        List<List<Integer>> dataCopy = copyData(data);
        
        // Compute all the group permutations:
        List<List<List<Integer>>> groupPemutationList = 
                new MultipleGroupPermuter<>(dataCopy)
                        .computeGroupPermutations();
        
        // Put all the group permutations into lexicographic order:
        sort(groupPemutationList);
        
        // Print all the group permutations in lexicographic order:
        System.out.println(
                convertGroupPemutationsToString(groupPemutationList));
        
        // Build the map mapping each unique group permutations to its 
        // multiplicity:
        Map<List<List<Integer>>, Integer> frequencyMap = 
                new HashMap<>(groupPemutationList.size());
        
        for (List<List<Integer>> groupPermutation : groupPemutationList) {
            frequencyMap.put(groupPermutation, 
                             frequencyMap.getOrDefault(
                                     groupPermutation, 
                                     0) + 1);
        }
        
        System.out.println(
                "Distinct group permutations: " + frequencyMap.size());
        
        System.out.println(
                "Total group permutations: " 
                        + countAllPermutations(frequencyMap));
    }
    
    /**
     * Counts the total number of group permutations stored in the 
     * {@code frequencyMap}.
     * 
     * @param <T>          the element type of groups.
     * @param frequencyMap the map mapping each unique group permutation to its
     *                     multiplicity.
     * 
     * @return the total number of group permutations.
     */
    private static <T> int 
        countAllPermutations(Map<List<List<T>>, Integer> frequencyMap) {
            
        int numberOfAllPermutations = 0;
        
        for (Integer count : frequencyMap.values()) {
            numberOfAllPermutations += count;
        }
        
        return numberOfAllPermutations;
    }
        
    /**
     * Computes the string representing the input group permutations.
     * 
     * @param <T>                  the element type of groups.
     * @param groupPermutationList the list of group permutations.
     * @return                     the string describing all the group 
     *                             permutations.
     */   
    private static <T> String 
        convertGroupPemutationsToString(
                List<List<List<T>>> groupPermutationList) {
            
        StringBuilder stringBuilder = new StringBuilder();
        boolean first = true;
        int numberOfGroupPemutations = groupPermutationList.size();
        int maximumLineNumberWidth = 
                Integer.toString(numberOfGroupPemutations).length();
        
        String format = "%" + maximumLineNumberWidth + "d: %s";
        int lineNumber = 1;
        
        for (List<List<T>> groupPermutation : groupPermutationList) {
            if (first) {
                first = false;
            } else {
                stringBuilder.append('\n');
            }
            
            String groupPemutationString = 
                    convertGroupPemutationToString(groupPermutation);
            
            stringBuilder.append(
                    String.format(
                            format,
                            lineNumber++, 
                            groupPemutationString));
        }
        
        return stringBuilder.toString();
    }
        
    /**
     * Returns the string representing the input of a single group permutation.
     * 
     * @param <T>             the element type of groups.
     * @param groupPemutation the input group permutation.
     * @return                the string representing the input group.
     */
    private static <T> String 
        convertGroupPemutationToString(List<List<T>> groupPemutation) {
            
       StringBuilder stringBuilder = new StringBuilder();
       boolean first = true;
       
       for (List<T> group : groupPemutation) {
           if (first) {
               first = false;
           } else {
               stringBuilder.append(' ');
           }
           
           stringBuilder.append(Objects.toString(group));
       }
       
       return stringBuilder.toString();
    }
        
    /**
     * Returns the deep copy of {@code data}. We need this in order to keep the
     * actual data for the permuter intact.
     * 
     * @param <T>  the element type of groups.
     * @param data the data to deep copy.
     * @return     the deep copy of the input data.
     */     
    private static <T> List<List<T>> copyData(List<List<T>> data) {
        List<List<T>> deepCopy = new ArrayList<>(data.size());
        
        for (List<T> list : data) {
            if (list == null) {
                deepCopy.add(null);
            } else {
                deepCopy.add(new ArrayList<>(list));
            }
        }
        
        return deepCopy;
    }
    
    /**
     * Sorts the input group permutation list into lexicographic order. Note
     * that this sort routine deems {@code null} values less than any other
     * non-null integer.
     * 
     * @param groupPermutationList the list of all the group permutations.
     */
    private static void sort(List<List<List<Integer>>> groupPermutationList) {
        
        GroupComparator<Integer> groupComparator = 
                new GroupComparator<>(Integer::compareTo);
        
        GroupPermutationComparator<Integer> groupPermutationComparator = 
                new GroupPermutationComparator<>(groupComparator);
        
        Collections.sort(groupPermutationList, groupPermutationComparator);
    }
}
