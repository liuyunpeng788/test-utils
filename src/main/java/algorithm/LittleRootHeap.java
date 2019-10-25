package algorithm;

/**
 * 小根堆算法实现
 * @author: liumch
 * @create: 2019/7/8 17:33
 **/

public class LittleRootHeap {
    private final static int CAPACITY = 10;
    private static int size = 0 ;
    int[] arr = new int[CAPACITY];

    public static void insert(int[] arr, int elem){
        if(arr.length >= CAPACITY){
            System.out.println("堆已满");
            return;
        }
        arr[size++] = elem;
        modifyHeap(arr,size);

    }

    /**
     * 调整堆
     * 从最后一个根节点开始，依次调整每一颗子树
     * @param arr 元素列表
     * @param end 最后一个元素的位置
     */
    public static void modifyHeap(int[] arr, int end){

        int left ;
         for(int i = (end-1)/2;i>=0 ;i--){
            left = 2*i +1;
            //左孩子节点索引小于数组的长度（说明有右孩子），且左孩子小于父节点
            if(left < end && arr[left] > arr[left+1]){
                //left 指向右孩子
                left++;
            }
            if(arr[left] < arr[i]){
                /**
                 *  交换父子节点的值
                 */

                swap(arr,i,left);
            }

        }

    }




    /**
     * 交换i,j 的位置
     * @param arr
     * @param i
     * @param j
     */
    private static void swap(int[] arr,int i,int j){
        arr[i] = arr[i] ^ arr[j];
        arr[j] = arr[i] ^ arr[j];
        arr[i] = arr[j] ^ arr[i];
    }

    public static void main(String[] args) {

        String s1 = "ab" + "cd";

        String s2 = "abc" + "d";

        System.out.println("s1==s2:" + s1==s2);

        int[] arr = {3,6,4,2,9,7,8,20,17,5,11,30,23,31,19,27};

        for (int i = arr.length -1; i >1 ; i--) {
            modifyHeap(arr,i);
            swap(arr,0,i);
        }
        for (int i = 0; i <arr.length ; i++) {
            System.out.print(arr[i] + ",");
        }

    }
}
