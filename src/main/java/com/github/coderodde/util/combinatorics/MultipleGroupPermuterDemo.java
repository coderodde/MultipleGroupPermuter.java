package com.github.coderodde.util.combinatorics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class MultipleGroupPermuterDemo {
    
    public static void main(String[] args) {
        List<List<Integer>> data = new ArrayList<>();
        
        data.add(null);
        data.add(Arrays.asList(1, 2, 3));
        data.add(Arrays.asList(4));
        data.add(Arrays.asList());
        data.add(Arrays.asList(5, 6, null, 8));
        data.add(Arrays.asList());
        data.add(null);
        
        List<List<List<Integer>>> groupPemutationList = 
                new MultipleGroupPermuter<>(data).computeGroupPermutations();
        
        GroupComparator<Integer> groupComparator = 
                new GroupComparator<>(Integer::compareTo);
        
        GroupPermutationComparator<Integer> groupPermutationComparator = 
                new GroupPermutationComparator<>(groupComparator);
        
        Collections.sort(groupPemutationList,
                         groupPermutationComparator);
        
        System.out.println(
                convertGroupPemutationsToString(groupPemutationList));
        
        Set<List<List<Integer>>> groupPermutationSet = 
                new HashSet<>(groupPemutationList.size());
        
        for (List<List<Integer>> groupPermutation : groupPemutationList) {
            groupPermutationSet.add(groupPermutation);
        }
        
        System.out.println(
                "Distinct group permutations: " + groupPermutationSet.size());
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

class GroupComparator<T> implements Comparator<List<T>> {

    private final Comparator<T> elementComparator;
    
    GroupComparator(Comparator<T> elementComparator) {
        this.elementComparator = elementComparator;
    }
    
    @Override
    public int compare(List<T> group1, List<T> group2) {
        if (group1 == null) {
            if (group2 == null) {
                return 0;
            } else {
                return -1;
            }
        } else {
            // Here, group1 != null:
            if (group2 == null) {
                return 1;
            }
        }
        
        // Here, group1 != null and group2 != null:
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
                return cmp;
            }
        }
        
        return 0;
    }
}

class GroupPermutationComparator<T> implements Comparator<List<List<T>>> {
    
    private final Comparator<List<T>> groupComparator;
    
    GroupPermutationComparator(Comparator<List<T>> groupComparator) {
        this.groupComparator = groupComparator;
    }
    
    @Override
    public int compare(List<List<T>> groupPermutation1,
                       List<List<T>> groupPermutation2) {
        
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
