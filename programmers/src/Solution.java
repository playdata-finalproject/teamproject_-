import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[][] a = new int[][]{ { 1, 0, 0 }, {1, 1, 0}, {1, 1, 0}, {1, 0, 1}, {1, 1, 0}, {0, 1, 1}};
        solution.solution(a,2);
    }

    public int solution(int[][] needs, int r) {
        int answer = 0;
        List<Integer> perfect = new ArrayList<Integer>();
        List<ArrayList<Integer>> r_need = new ArrayList<>();

        for (int i=0; i< Arrays.stream(needs).count(); i++){
            perfect.add(i);
            r_need.add(new ArrayList<Integer>());
        }


        for (int i=0; i< Arrays.stream(needs).count(); i++){
            for (int j=0; j< Arrays.stream(needs[i]).count();j++){
                if(needs[i][j]==1){
                    r_need.get(i).add(j);
                }
            }
        }

        System.out.println(r_need);

        return answer;
    }
}
