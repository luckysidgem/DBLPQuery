package dblp.controller;

import java.util.*;
/** StringMatcher Class to match the querries to the database .Implements Fuzzy string algo.
*/
public class StringMatcher
{
	public static int THRESHOLD = 10;
    /**calculateLevenshteinDistance calculates the sum of the number of places the strings don't match(kinda)
    *\param[a] and \param[b] are the two strings to be compared
    */
    private static int calculateLevenshteinDistance(String a, String b) {
        b = b.toLowerCase();
        a = a.toLowerCase();
        int i,j;
        int[] costs = new int[b.length() + 1];
        for (i = 0; i < costs.length; i++)
            costs[i] = i;
        for (i = 1; i <= a.length(); i++)
        {
            int nw = i - 1;
            costs[0] = i;
            for (j = 1; j <= b.length(); j++)
            {
                int temp = Math.min(a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1, 1 + Math.min(costs[j], costs[j - 1]));
                nw = costs[j];
                costs[j] = temp;
            }
        }
        return costs[b.length()];
    }
    /**calculates the longest common substriing between the two strings
    *\param[a] and \param[b] are the two strings to be compared
    */
    public static int findLCS(String a, String b){
      int n = b.length();
      int m = a.length();
      b = b.toLowerCase();
      a = a.toLowerCase();
      int max = 0;
     
      int[][] store = new int[m][n];
     
      for(int i=0; i<m; i++){
        for(int j=0; j<n; j++){
          if(a.charAt(i) == b.charAt(j)){
            if(i == 0 || j == 0)
              store[i][j] =1 ;
            else
              store[i][j] = store[i-1][j-1]+1;
            
     
            if(max < store[i][j])
              max = store[i][j];
          }
     
        }
      }
     
      return max;
    }
    /**The funtion which actually does the comarison using the earlier two functions
        *\param[a] and \param[b] are the two strings to be compared
    */
    public static double compare(String A, String B) {
      if(A.equalsIgnoreCase(B))
        return 0;
      else if(B.length() >= A.length() && B.substring(0,A.length()).equalsIgnoreCase(A))
          return 0.1;
      else if(findLCS(A, B) == A.length())
        return 0.2;
      else	{
        int count = 0;
        List<String> listA = new LinkedList<>(Arrays.asList(A.split(" ")));
        for(String word1 : listA)
        {
            if(findLCS(word1, B) == word1.length())
              count++;
        }
        if(count == listA.size())
        	return 0.3;
        else 
        	return (calculateLevenshteinDistance(A, B)*100)/A.length();
      }
    }
    
}

