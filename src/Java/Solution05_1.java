import java.util.Arrays;

/**
 * 有两个排序的数组A1和A2，内存在A1的末尾有足够的空余空间容纳A2。请写一个函数，把A2的所有数字插入A1中，并且所有的数字是排序的。
 */

public class Solution05_1 {
    public static void main(String[] args) {
        int[] A1 = new int[100];
        A1[0] = 1;
        A1[1] = 3;
        A1[2] = 5;
        A1[3] = 65;
        A1[4] = 436;
        int[] A2 = {4, 40, 99, 214, 5432, 532515};
        Solution05_1 s = new Solution05_1();
        int[] A3 = s.merge(A1, A2, 5, A2.length);
        System.out.println(Arrays.toString(A3));
    }

    /**
     * 从尾到头比较数字
     */
    public int[] merge(int[] A1, int[] A2, int lenA1, int lenA2) {
        int indexA1 = lenA1 - 1;
        int indexA2 = lenA2 - 1;
        int indexM = indexA1 + indexA2 + 1;
        
        while (indexA1 >=0 && indexA2 >=0) {
            if (A1[indexA1] >= A2[indexA2]) {
                A1[indexM] = A1[indexA1];
                --indexM;
                --indexA1;
            } else {
                A1[indexM] = A2[indexA2];
                --indexM;
                --indexA2;
            }
        }

        while (indexA1 >= 0) {
            A1[indexM] = A1[indexA1];
            --indexM;
            --indexA1;
        }

        while (indexA2 >= 0) {
            A1[indexM] = A2[indexA2];
            --indexM;
            --indexA2;
        }

        return A1;
    }
}