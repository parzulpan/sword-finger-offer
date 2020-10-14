/* 

由于长度为 n 的数组中的数字都在 0～n-1 的范围内，所以如果没有重复数字，则数组排序后，数字 i 将出现在 下标为 i 的位置。遍历数组，当到下标为 i 的数字（用 m 表示）时：

* m == i，则跳过；
* m != i，则比较 m 和 下标为 m的数字（用 n 表示）:
  * m == n，则找到一个重复数字；
  * m != n，则交换 m 和 n。

*/

#include <iostream>
#include <vector>
using namespace std;

class Solution {
public:
    int findRepeatNumber(vector<int>& nums) {
        int nums_size = nums.size();
        int temp = 0;
        // 边界条件
        if(nums_size <= 0) {
            return 0;
        }

        for(int i = 0; i < nums_size; ++i) {
            if(nums[i] != i) {
                if(nums[i] == nums[nums[i]]) {
                    return nums[i];
                } else {
                    temp = nums[i];
                    nums[i] = nums[temp];
                    nums[temp] = temp;
                }
            }
        }

        return 0;
    }
};