/*

可以把这个二维数组想像成一个矩阵，可以发现规律：首先选取数组中右上角的数字（或者左下角的数字）。分为以下情况：

* 如果该数字等于查找的数字，则查找过程结束；
* 如果该数字大于查找的数字，则剔除这个数字所在的列；
* 如果该数字小于查找的数字，则剔除这个数字所在的行；

*/

#include <iostream>
#include <vector>
using namespace std;

class Solution {
public:
    bool findNumberIn2DArray(vector< vector<int> >& matrix, int target) {
        // 边界条件
        if(matrix.size() == 0) {
            return false;
        }

        int rows = matrix.size();
        int cols = matrix[0].size();
        
        if(rows > 0 && cols >= 0) {
            // 选取数组中右上角的数字
            int row = 0;
            int col = cols - 1;

            while(row < rows && col > 0) {
                if(matrix[row][col] == target) {
                    return true;
                } else if(matrix[row][col] > target) {
                    --col;
                } else {
                    ++row;
                }
            }
        }

        return false;
    }
};

int main(int argc, char* argv[]) {
    Solution s;
    vector< vector<int> > matrix{
        {1,   4,  7, 11, 15},
        {2,   5,  8, 12, 19},
        {3,   6,  9, 16, 22},
        {10, 13, 14, 17, 24},
        {18, 21, 23, 26, 30}};
    std::cout << s.findNumberIn2DArray(matrix, 5) << std::endl;

    return 0;

}