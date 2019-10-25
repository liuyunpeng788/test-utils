package stream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 测试parallelStream() 方法是否可以保证操作结果的顺序性
 * 如 [1,2,3] 使用parallelStream() ,每个值加1后是否为[2,3,4]
 * @author liumch
 * create :  2019/9/12 14:26
 **/

public class ParallelSeqTest {
    public static void main(String[] args) {
//        int[] ints = IntStream.range(1, 10001).parallel().map(x -> x + 1).toArray();
        int size = 10000*100;
        List<Integer> ints = new ArrayList<>(size);
        for (int i = 0; i < size ; i++) {
            ints.add(i);
        }
        List<Integer> res = ints.parallelStream().map(x->x+1).collect(Collectors.toList());
        System.out.println("start:" + res.get(0) + ",end:" + res.get(size-1));
        boolean isSeq = true;
        for (int i = 0; i < res.size() - 1 ; i++) {
            int j = i+1;
            if(res.get(j) != res.get(i) + 1){
                isSeq = false;
                break;
            }
        }
        System.out.println("isSeq: "+ isSeq);
    }
}
