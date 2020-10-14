/* 

采用类似二分查找的方法。
把从 1～n 的数字从中间的数字 m 分为两部分，前面一半为 1～m，后面一半为 m+1～n。
如果 1～m 的数字的数目超过 m，那么这个区间一定有重复数字，否则，另一个区间存在重复数字。
依此类推，继续把包含重复的数字区间一分为二。

*/

#include <iostream>
#include <vector>
using namespace std;

class Solution {
public:
    int findRepeatNumberNoEdit(vector<int>& nums) {
        int nums_size = nums.size();
        int start = 1;
        int end = nums_size - 1;
        
        while(end >= start) {
            int mid = ((end - start) >> 1) + start;
            int cnt = getNumberCount(nums, nums_size, start, mid);
            
            if(end == start) {
                if(cnt > 1) {
                    return start;
                } else {
                    break;
                }
            }

            if(cnt > (mid - start + 1)) {
                end = mid;
            } else {
                start = mid + 1;
            }
        }

        return -1;
    }

    int getNumberCount(vector<int>& nums, int nums_size, int start, int end) {
        int cnt = 0;

        for(const &n: nums) {
            if(n >= start && n <=  end) {
                ++cnt;
            }
        }

        return cnt;
    }
};