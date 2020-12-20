#include<iostream>
#include<vector>
using namespace std;

class Solution {
public:
    int findRepeatNumber(vector<int>& nums) {
        int len = nums.size();
        int temp = 0;
        for (int i = 0; i < len; ++i) {
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
};

int main() {
    auto s = new Solution();
    vector<int> v = {2, 3, 1, 0, 2, 5, 3};
    cout << s->findRepeatNumber(v) << endl;
    delete s;
}