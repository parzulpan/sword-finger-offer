
/**

【剑指 Offer】11.旋转数组的最小数字

把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。输入一个递增排序的数组的一个旋转，输出旋转数组的最小元素。例如，数组 [3,4,5,1,2] 为 [1,2,3,4,5] 的一个旋转，该数组的最小值为1。  

示例 1：
输入：[3,4,5,1,2]
输出：1
示例 2：
输入：[2,2,2,0,1]
输出：0


 */

public class Solution11 {

    public static void main(String[] args) {
        Solution11 s = new Solution11();
        Solution so = s.new Solution();
        System.out.println( so.minArray(new int[] {3,4,5,1,2}) );
        System.out.println( so.minArray2(new int[] {2,2,2,0,1}) );
    }

    class Solution {
        /**
         * 方法一：暴力法
         * 根据题意，可以分析出，是对两个顺序数组分界点的寻找，而且最小数字是分界右边的数字。
         * 直接遍历一遍。
         * 
         * 时间复杂度 O(n)
         * 空间复杂度 O(1)
         */
        public int minArray(int[] numbers) {
            for (int i = 0; i < numbers.length - 1; ++i) {
                if (numbers[i] > numbers[i + 1]) {
                    return numbers[i + 1];
                }
            }

            return numbers[0];
        }

        /**
         * 方法二：二分查找
         * 根据题意，可以分析出，是对两个顺序数组分界点的寻找，而且最小数字是分界右边的数字。
         * 1. 如果中轴元素小于右边界元素，说明最小数字在中轴元素左边
         * 2. 如果中轴元素大于右边界元素，说明最小数字在中轴元素右边
         * 3. 如果中轴元素等于右边界元素，则左移右边界元素
         * 通过上面的分析，这其实是一种二分查找的变种
         * 
         * 时间复杂度 O(logn)
         * 空间复杂度 O(1)
         */
        public int minArray2(int[] numbers) {
            int low = 0;
            int high = numbers.length - 1;
            while (low < high) {
                int mid = low + (high - low) / 2;
                if (numbers[mid] < numbers[high]) {
                    high = mid;
                } else if (numbers[mid] > numbers[high]) {
                    low = mid + 1;
                } else {
                    high -= 1;
                }
            }

            return numbers[low];
        }
        
    }
    
}