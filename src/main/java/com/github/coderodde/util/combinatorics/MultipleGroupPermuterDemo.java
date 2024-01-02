package com.github.coderodde.util.combinatorics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class MultipleGroupPermuterDemo {
    
    public static void main(String[] args) {
        List<List<Integer>> data = new ArrayList<>();
        
        data.add(Arrays.asList(1, 2, 3));
        data.add(Arrays.asList(4));
        data.add(Arrays.asList());
        data.add(Arrays.asList(5, 6));
        data.add(Arrays.asList());
        
        List<List<List<Integer>>> groupPemutationList = 
                new MultipleGroupPermuter<>(data).computeGroupPermutations();
        
        System.out.println(
                convertGroupPemutationsToString(groupPemutationList));
    }
    
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
}
