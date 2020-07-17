/*
 * 17/07/2020 13:09:47
 * BinarySearch.java created by Tsvetelin
 */
package com.algo;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * @author Tsvetelin
 *
 */
public interface BinarySearch
{
    
    public static final int ELEMENT_NOT_FOUND_CODE = -1;
    
    /**
     * 
     * Recursive binary search. The list should be sorted.
     * 
     * @param <E>
     * @param el
     * @param list
     * @return the index of the element or -1 if it is not found
     */
    public static < E extends Comparable< E > > int binarySearch_recursive (
        final E el ,
        final List< E > list
    )
    {
        if (
            el == null
                || list == null
                || list.size() == 0
        ) return ELEMENT_NOT_FOUND_CODE;
        
        final int mid = list.size() / 2;
        final E midEl = list.get( mid );
        
        switch ( el.compareTo( midEl ) )
        {
            case 0 :
                return mid;
            case 1 : // this case does not work well
                System.out.println( "The element is in this half: " + list );
                return mid + 1 + binarySearch_recursive(
                    el ,
                    list.subList( mid + 1 , list.size() )
                );
            case -1 :
                return binarySearch_recursive(
                    el ,
                    list.subList( 0 , mid )
                );
            default :
                throw new RuntimeException(
                    "The comparator of the type element does not conform to the comparator specifiactions"
                );
        }
    }
    
    /**
     * 
     * Basic binary search. The list should be sorted.
     * 
     * @param <E>
     * @param el
     * @param list
     * @return the index of the element or -1 if it is not found
     */
    public static < E extends Comparable< E > > int binarySearch_loop (
        final E el ,
        final List< E > list
    )
    {
        int high = list.size();
        int low = 0;
        
        do
        {
            int mid = ( high + low ) / 2;
            E midEl = list.get( mid );
            switch ( el.compareTo( midEl ) )
            {
                case 0 :
                    return mid;
                case 1 :
                    low = mid + 1;
                    break;
                case -1 :
                    high = mid - 1;
                    break;
            }
        } while ( low <= high );
        return ELEMENT_NOT_FOUND_CODE;
    }
    
    public static void main ( String [] args )
    {
        final int minIncl = 1;
        final int maxExcl = 1024;
        List< Integer > list =
            IntStream.range( minIncl , maxExcl ).mapToObj( x -> x ).collect( Collectors.toList() );
        
        for ( int i = minIncl ; i < maxExcl ; i++ )
        {
            System.out
                .println( "Index of " + i + ": " + binarySearch_loop( i , list ) );
            assert binarySearch_loop( i , list ) == i - 1;
        }
        
        /*
         * NOTE: I know asserts are not a good practice in production code and
         * that is the sole purpose of testing frameworks but frankly I don't
         * feel like in a practice situation like this it is too bad
         */
        
        System.out
            .println( "Asserting under lower bound results in not found" );
        assert binarySearch_loop(
            minIncl - 1 ,
            list
        ) == ELEMENT_NOT_FOUND_CODE;
        System.out.println( "Asserting over upper bound results in not found" );
        assert binarySearch_loop( maxExcl , list ) == ELEMENT_NOT_FOUND_CODE;
    }
}
