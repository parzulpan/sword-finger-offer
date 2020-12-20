from __future__ import annotations
from typing import List

class Solution:
    def findRepeatNumber(self, nums: List[int]) -> int:
        size = len(nums)
        temp = 0
        for index, value in enumerate(nums):
            if value != index:
                if value == nums[value]:
                    return value
                else:
                    temp = value
                    value = nums[temp]
                    nums[temp] = temp
        return -1

if __name__ == "__main__":
    s = Solution()
    print(s.findRepeatNumber([2, 3, 1, 0, 2, 5, 3]))
