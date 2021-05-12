package Java;

import java.util.HashSet;
import java.util.Set;

/**
 * @author parzulpan

【剑指 Offer】03.数组中重复的数字

找出数组中重复的数字。

在一个长度为 n 的数组 nums 里的所有数字都在 0～n-1 的范围内。数组中某些数字是重复的，但不知道有几个数字重复了，也不知道每个数字重复了几次。请找出数组中任意一个重复的数字。

示例 1：
输入：
[2, 3, 1, 0, 2, 5, 3]
输出：2 或 3

限制：
2 <= n <= 100000
 */

public class Solution03 {
    public static void main(final String[] args) {
        final Solution03 solution = new Solution03();
        final int[] nums = new int[] { 2, 3, 1, 0, 2, 5, 3 };
        System.out.println(solution.findRepeatNumber(nums));
        System.out.println(solution.findRepeatNumber2(nums));

    }

    /**
     * 方法一：遍历数组
     * 
     * 遍历数组中的每个元素： * 将该元素加入集合中，判断是否加入成功 * 添加失败，则找到重复数字
     * 
     * 时间复杂度 O(n) 空间复杂度 O(n)
     *
     */
    public int findRepeatNumber(final int[] nums) {
        Set<Integer> set = new HashSet<>();
        int repeat = -1;
        for (final int num : nums) {
            if (!set.add(num)) {
                repeat = num;
                break;
            }
        }
        return repeat;
    }

    /**
     * 方法二：原地置换
     * 注意所有数字都在 0～n-1 的范围内
     */
    public int findRepeatNumber2(final int[] nums) {
        int len = nums.length;
        int temp = 0;

        for (int i = 0; i < len; i++) {
            if (nums[i] != i) {
                if (nums[i] == nums[nums[i]]) {
                    return nums[i];
                } else {
                    temp = nums[i];
                    nums[i] = nums[temp];
                    nums[temp] = temp;
                }
            }
        }

        return -1;
    }
}
