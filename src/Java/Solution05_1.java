package Java;

import java.util.Arrays;

/**
 * @author parzulpan

 * 有两个排序的数组A1和A2，内存在A1的末尾有足够的空余空间容纳A2。
 * 请写一个函数，把A2的所有数字插入A1中，并且所有的数字是排序的。
 */

public class Solution05_1 {
    public static void main(String[] args) {
        int[] a1 = new int[100];
        a1[0] = 1;
        a1[1] = 3;
        a1[2] = 5;
        a1[3] = 65;
        a1[4] = 436;
        int[] a2 = {4, 40, 99, 214, 5432, 532515};
        int[] a3 = Solution05_1.merge(a1, a2, 5, a2.length);
        System.out.println(Arrays.toString(a3));
    }

    /**
     * 从尾到头比较数字
     */
    public static int[] merge(int[] a1, int[] a2, int lenA1, int lenA2) {
        int indexA1 = lenA1 - 1;
        int indexA2 = lenA2 - 1;
        int indexM = indexA1 + indexA2 + 1;
        
        while (indexA1 >=0 && indexA2 >=0) {
            if (a1[indexA1] >= a2[indexA2]) {
                a1[indexM] = a1[indexA1];
                --indexM;
                --indexA1;
            } else {
                a1[indexM] = a2[indexA2];
                --indexM;
                --indexA2;
            }
        }

        while (indexA1 >= 0) {
            a1[indexM] = a1[indexA1];
            --indexM;
            --indexA1;
        }

        while (indexA2 >= 0) {
            a1[indexM] = a2[indexA2];
            --indexM;
            --indexA2;
        }

        return a1;
    }
}