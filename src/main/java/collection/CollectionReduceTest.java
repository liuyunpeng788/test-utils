package collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author: liumch
 * @create: 2019/7/11 15:22
 **/

public class CollectionReduceTest {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1,2,3,5);
        int result = list.stream().reduce(0,(a,b)->a + b);
        System.out.println(result);

        List<Integer> values = new ArrayList<>();
        Optional<Integer> optional = values.stream().filter(x->x.equals(2)).findFirst();
        System.out.println(optional.orElse(-1));
    }
}
