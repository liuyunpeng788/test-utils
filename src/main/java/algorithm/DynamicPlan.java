package algorithm;

/**
 * 动态规划
 * @author: liumch
 * @create: 2019/7/2 11:00
 **/

public class DynamicPlan {

    public static String longestPalindrome(String srcStr){
        if(srcStr.isEmpty()|| srcStr.length() == 1){
            return srcStr;
        }else{
            int len = srcStr.length();
            boolean[][] dp = new boolean[len][len];
            int maxLen = 0;
            int start = 0;
            int end = 0;
            for(int i = len-2; i>=0; i--){
                  dp[i][i] = true;
                for (int j = i+1; j <len  ; j++) {
                    if(i + 1== j-1){
                        dp[i+1][j-1] = true;
                    }
                    if(srcStr.charAt(i) == srcStr.charAt(j) && (Math.abs(i-j) == 1 ||dp[i+1][j-1] ) ){
                        dp[i][j] = true;
                        if(Math.abs(i-j) > maxLen){
                            maxLen = Math.abs(i-j);
                            start = Math.min(i,j);
                            end = Math.max(i,j);
                        }
                    }
                }
            }
            return srcStr.substring(start,end+1);
        }
    }
    public static void main(String[] args) {
        String srcStr = "  ";

        String res = longestPalindrome(srcStr);
        System.out.println("最长回文串：" + res);
    }
}
