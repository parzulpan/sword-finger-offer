#include <iostream>
#include <string>
using namespace std;

class Solution {
public:
    string replaceSpace(string s) {
        string result;  // 存储替换后的结果

        for(auto &c : s) {  // 遍历目标字符串
            if(c == ' ') {
                result += "%20";
            } else {
                result += c;
            }
        }

        return result;
    }
};