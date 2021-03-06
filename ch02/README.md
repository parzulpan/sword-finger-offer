# 面试需要的基础知识

## 面试官谈基础知识

编程语言的基础知识、常用的算法和数据结构、常用的设计模式等。

## 编程语言

应熟练掌握一到两种编程语言，比如 C++、Python等。

### C++

对于编程语言，通常有三种类型的题。

**第一种题型**，考察对 C++ 概念的理解，比如关键字、面向对象特性等等。

**Q:** 在 C++ 中，有哪 4 个与类型转换相关的关键字？这些关键字各有什么特点，应该在什么场合下使用？

**A:** 4 个与类型转换相关的关键字分别是：static_cast、const_cast、reinterpret_cast、dynamic_cast。

* static_cast
  * 特点是**静态转换，在编译处理期间**。应用场合是**用于 C++ 内置的基本数据类型之间的转换**。
  * 把子类的指针或者引用转换为基类表示，即上行转换是安全的，把基类的指针或者引用转换为子类表示时，由于没有动态类型检测，即下行转换是不安全的。把 void 类型的指针转换为目标类型的指针也是不安全的。
  * 通常用于相关类型的转换，不能用于不相关类型的转换。
  * 不能把 const 对象转换为 非 const 对象。

```cpp
int m = 10;
double n = static_cast<int> m;
int *q = static_cast<int *>(malloc(100));
```

* const_cast
  * 特点是**去常转换，编译时执行**。应用场合是**将不是const 类型的数据转换成 const 类型的，或者把 const 属性去掉。**
  * 通常用于相同类型的转换，不能用于不同类型的转换。

```cpp
struct A {
    // 非 const 版
    A &test() { return *this; }

    // const 版，通过 const_cast 强行取出 this 的 const 限定后调用非 const 版本，返回值通过隐式类型转换再转换为 const A &
    const A &test() const { return const_cast<A *>(this)->test(); }
};
```

* reinterpret_cast
  * 特点是**重解释类型转换**。应用场合是**将任何的内置类型和指针转换为其他的类型**。
  * 它的原理是对二进制进行重新的解释，要求是转换前后类型所占用内存大小一致，否则将会编译时错误。

```cpp
int m = 10;
double *n = reinterpret_cast<double *>(m);
```

* dynamic_cast
  * 特点是**动态转换**，应用场合是**多态时，基类类型到派生类类型的转换**。
  * 该运算符将 expression 转换成 type_id 类型的对象。type_id 必须是类的指针，类的引用或者空类型的指针。
    * 如果 type_id 是一个指针类型，那么 expression 也必须是一个指针类型，如果 type_id 是一个引用类型，那么 expression 也必须是一个引用类型。
    * 如果 type_id 是一个空类型的指针，在运行的时候，就会检测 expression 的实际类型，结果是一个由 expression 决定的指针类型。
    * 如果 type_id 不是空类型的指针，在运行的时候指向 expression 对象的指针能否可以转换成 type_id 类型的指针。
    * 在运行的时候决定真正的类型，如果向下转换是安全的，就返回一个转换后的指针，若不安全，则返回一个空指针。
    * 主要用于上下行之间的转换，也可以用于类之间的交叉转换。上行转换时和 static_cast 效果一样，下行转换时，具有检测功能，比 static_cast 更安全。

```cpp
A *b = new B;
dynamic_cast<B *>(b);
```

**Q:** 定义一个空的类型，里面没有任何成员变量和成员函数，对该类型求 sizeof，得到的结果是多少？

**A:** 结果为 1。因为当声明该类型的实例时，它在内存中必须有一定的空间，否则就无法使用这些实例。具体占用多少内存，由编译器决定。

**Q:** 如果在该类型中添加一个构造函数和析构函数，再对该类型求 sizeof，得到的结果是多少？

**A:** 结果为 1。调用构造函数和析构函数只需要知道函数的地址即可，而这些函数的地址只与类型相关，而与类型的实例无关。

**Q:** 如果把析构函数标记为虚函数呢？

**A:** 结果为一个指针的所占的内存空间。编译器会为含有虚函数的类型生成一个虚函数表，并在该类型的每一个实例添加一个指向虚函数表的指针。在 32 位中，为 4 个字节，64 位中，为 8 个字节。

```cpp
#include <iostream>

class A {

};

class B {
public:
    B(){}
    ~B(){}
};

class C {
public:
    C(){}
    virtual ~C(){}
};

int main(int argc, char* argv[])
{
    A a;
    B b;
    C c;

    std::cout << sizeof(a) << std::endl;    // 1
    std::cout << sizeof(b) << std::endl;    // 1
    std::cout << sizeof(c) << std::endl;    // 8
    std::cout << sizeof(int *) << std::endl;    // 8

    return 0;
}
```

**第二种题型**，考察对指定代码的分析，比如运行结果、调优策略等。

**Q:** 以下代码的运行结果，编译错误？编译成功，运行时程序崩溃？编译运行正常，输出 10？

```cpp
class A {
private:
    int value;

public:
    A(int n) { value = n; }
    A(A other) { value = other.value; }

    void print() { std::cout << value << std::endl; }
};

int main(int argc, char* argv[]) {
    A a = 10;
    A b = a;
    b.print();

    return 0;
}
```

**A:** 运行结果为编译错误。复制构造函数 `A(A other) { value = other.value; }` 传入的参数是 A 的一个实例，由于是传值参数，所以形参复制到实参会调用复制构造函数，即会造成无休止的递归调用从而栈溢出。可以把复制构造函数修改为 `A(const A &other) { value = other.value; }` ，即改为常量引用。

**第三种题型**，考察对指定代码的编写，比如定义一个类型、实现一个函数等。

**Q:** 如下为类型 CMyString 的声明，请为该类型添加赋值运算符函数。

```cpp
class CMyString {
private:
    char* m_pData;

public:
    CMyString(char* pData = nullptr);
    CMyString(const CMyString& str);
    ~CMyString(void);
};
```

**A:** 定义一个赋值运算符需要注意以下几点：

* 把返回值的类型声明为该类型的引用，并且函数返回值为自身的引用（*this）。只有返回一个引用，才能连续赋值。
* 把传入的参数的类型声明为常量引用。声明为引用能减少调用一次复制构造函数的无谓消耗，函数内不会改变传入的实例的状态，所以应该声明为常量。
* 释放实例自身的内存。如果忘记在分配新内存之前释放自身已有的实例，将会出现内存泄露。
* 判断传入的参数和当前的实例（*this）是不是同一个实例。

```cpp
// 经典的解法

CMyString& CMyString::operator=(const CMyString&str) {
    if(this != &str) {
        delete [] m_pData;
        m_pData = nullptr;

        m_pData = new char[strlen(str.m_pData) + 1];
        strcpy(m_pData, str.m_pData);
    }

    return *this;
}
```

```cpp
// 考虑异常安全性的解法
// 如果内存不足导致 new char 抛出异常，则 m_pData 将会是一个空指针。
// 可以先创建一个临时实例，再交换临时实例和原来的实例。

CMyString& CMyString::operator=(const CMyString& str) {
    if(this != &str) {
        // 临时实例，超出变量作用域会自动调用析构函数释放内存
        CMyString str_temp(str);

        char* p_temp = str_temp.m_pData;
        str_temp.m_pData = m_pData;
        m_pData = p_temp;
    }

    return *this;
}
```

具体可参考：[AssignmentOperator.cpp](code/AssignmentOperator.cpp)

**Q:** 设计一个类，我们只能生成该类的一个实例。

**A:** 定义一个单例类，需要注意以下几点：

* 这个类只能有一个实例。
* 它必须自己创建这个实例。
* 它必须自己向整个系统提供这个实例。

```cpp
// singleton.h

#ifndef __SINGLETON_H__
#define __SINGLETON_H__

#include <iostream>
#include <string>
#include <thread>
#include <chrono>
#include <mutex>
#include <pthread.h>
#include <unistd.h>

// 单线程版本
class Singleton {
public:
    static Singleton* getInstance() {   // 提供外部接口，满足了第三个要点
        if(instance == nullptr) {
            std::cout << "New Singleton" << std::endl;
            instance = new Singleton();
        }
        return instance;
    }
private:
    Singleton(){}   // 构造函数是私有的，即只能在类内部实例化，满足了第二个要点
    static Singleton* instance; // instance是static的，满足了第一个要点
};

Singleton* Singleton::instance = nullptr;


// 线程安全的多线程版本
class SingletonA {
public:
    static SingletonA* getInstance() {
        if(instance == nullptr) {
            m_mutex.lock(); // 添加互斥锁
            // std::lock_guard<std::mutex> lock(m_mutex);
            if(instance == nullptr) {
                std::cout << "New SingletonA" << std::endl;
                instance = new SingletonA();
            }
            m_mutex.unlock();
        }
        return instance;
    }

private:
    SingletonA() {}
    static SingletonA* instance;
    static std::mutex m_mutex;
};

SingletonA* SingletonA::instance = nullptr;
std::mutex SingletonA::m_mutex;

#endif  //__SINGLETON_H__
```

## 数据结构

常见的几种数据结构为数组/向量、字符串、链表、树、栈和队列等等。

### 数组/向量

这是一种最简单的数据结构，它占据着一块连续的内存并按照顺序存储数据。由于数组中的内存是连续的，所以可以根据下标在O(1)时间读/写任何元素。可以用数组实现简单的哈希表：把数组的下标设为哈希表的 key，而下标对应的值设为哈希表的 value。

为了解决数组空间效率不高的问题，在 C++ 中的 STL 中实现了 vector。由于 vector 的扩充机制，每次扩充数组容量时都有大量的额外操作，这对时间性能有负面影响，因此使用动态数组时要尽量减少改变数组容量大小的次数。

在 C/C++ 中，当数组作为函数的形参进行传递时，数组就自动退化为同类型的指针。

**Q:** 找出数组中重复的数字。在一个长度为 n 的数组里的所有数组都在 0～n-1 的范围内。数组中某些数字是重复的，但不知道有几个数字重复了，也不知道每个数字重复了几次。请找出数组中任意一个重复的数字。例如，如果输入长度为 7的数组 {2, 3, 1, 0, 2, 5, 3}，那么对应的输出是重复的数字 2 或者 3。

**A:** 由于长度为 n 的数组中的数字都在 0～n-1 的范围内，所以如果没有重复数字，则数组排序后，数字 i 将出现在 下标为 i 的位置。遍历数组，当到下标为 i 的数字（用 m 表示）时：

* m == i，则跳过；
* m != i，则比较 m 和 下标为 m的数字（用 n 表示）:
  * m == n，则找到一个重复数字；
  * m != n，则交换 m 和 n。

```cpp
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
```

**Q:** 不修改数组找出重复的数字。在一个长度为 n+1 的数组里的所有数组都在 0～n 的范围内。数组中至少有一个数字是重复的。请找出数组中任意一个重复的数字。

**A:** 采用类似二分查找的方法。把从 1～n 的数字从中间的数字 m 分为两部分，前面一半为 1～m，后面一半为 m+1～n。如果 1～m 的数字的数目超过 m，那么这个区间一定有重复数字，否则，另一个区间存在重复数字。依此类推，继续把包含重复的数字区间一分为二。

```cpp
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
```

**Q:** 二维数组中的查找。在一个 n * m 的二维数组中，每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。

```cpp
[
  [1,   4,  7, 11, 15],
  [2,   5,  8, 12, 19],
  [3,   6,  9, 16, 22],
  [10, 13, 14, 17, 24],
  [18, 21, 23, 26, 30]
]
```

**A:** 可以把这个二维数组想像成一个矩阵，可以发现规律：首先选取数组中右上角的数字（或者左下角的数字）。分为以下情况：

* 如果该数字等于查找的数字，则查找过程结束；
* 如果该数字大于查找的数字，则剔除这个数字所在的列；
* 如果该数字小于查找的数字，则剔除这个数字所在的行；

```cpp
class Solution {
public:
    bool findNumberIn2DArray(vector<vector<int>>& matrix, int target) {
        // 边界条件
        if(matrix.size() == 0) {
            return false;
        }

        int rows = matrix.size();
        int cols = matrix[0].size();

        if(rows > 0 && cols > 0) {
            // 选取数组中右上角的数字
            int row = 0;
            int col = cols - 1;

            while(row < rows && col >= 0) {
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
```

### 字符串

C/C++ 中每个字符串都以字符 '\0'作为结尾，所以每个字符都有一个额外字符的开销，稍不留神就会造成字符串的越界。

为了节省内存，C/C++ 把常量字符串放到单独的一个内存区域。当几个指针赋值给相同的常量字符串时，它们实际上会指向相同的内存地址。但是用常量内存初始化数组，情况却有所不同。

**Q:** 下面的代码的输出结果是？

**A:** str1 和 str2 是两个字符串数组，是两个初始地址不同的数组，所以 str1 和 str2 不相等。而 str3 和 str4 是两个指针指向同一个常量字符串。

```cpp
#include<iostream>

int main(int argc, char* argv[]) {
    char str1[] = "hello world";
    char str2[] = "hello world";

    char* str3 = "hello world";
    char* str4 = "hello world";

    if(str1 == str2) {
        std::cout << "str1 and str2 are same." << std::endl;
    } else {
        std::cout << "str1 and str2 are not same." << std::endl;    // 输出
    }

    if(str3 == str4) {
        std::cout << "str3 and str4 are same." << std::endl;    // 输出
    } else {
        std::cout << "str3 and str4 are not same." << std::endl;
    }

    return 0;
}
```

**Q:** 请实现一个函数，把字符串中的每个空格替换成"%20"。例如，输入“We are happy.”，则输出“We%20are%20happy.”。

**A:** 解法一 可以使用遍历添加，用一个字符串存储结果。

```cpp
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
```

```python
class Solution:
    def replaceSpace(self, s: str) -> str:
        res  = []
        for c in s:
            if c == ' ':
                res.append("%20")
            else:
                res.append(c)
        return "".join(res)
```

解法二 可以先统计出空格数量，进行倒序遍历原地修改。

```cpp
#include <iostream>
#include <string>
using namespace std;

class Solution {
public:
    string replaceSpace(string s) {
        int cnt = 0, len = s.size();

        for(char &c: s) {
            if(c == ' ') {
                ++cnt;
            }
        }
        if(cnt != 0) {
            s.resize(len + 2 * cnt);    // 修改 s 长度
        }

        // 倒序遍历原地修改
        for(int i = len - 1, j = s.size() - 1; i < j; --i, --j) {
            if(s[i] != '') {
                s[j] = s[i];
            } else {
                s[j - 2] = '%';
                s[j - 1] = '2';
                s[j] = '0';
                j -= 2;
            }

        }

        return s;
    }
};
```

**Q:** 有两个排序的数组 A1 和 A2，内存在 A1 的末尾有足够多的空余空间容纳 A2。请实现一个函数，把 A2 中的所有数字插入 A1 中，并且所有的数字是排序的。

**A:** 倒序比较 A1 和 A2 的数字，并把较大的数字，复制到 A1 中的合适位置。

```cpp
class Solution {
public:
    void merge(int[] A1, int[] A2, int lenA1, int lenA2) {
        int indexA1 = lenA1 - 1;
        int indexA2 = lenA2 - 1;
        int indexMerged = lenA1 + indexA2 - 1;

        while(indexA1 >= 0 && indexA2 >= 0) {
            if(A1[indexA1] >= A2[indexA2]) {
                A1[indexMerged] = A1[indexA1];
                --indexMerged;
                --indexA1;
            } else {
                A1[indexMerged] = A1[indexA2];
                --indexMerged;
                --indexA2;
            }
        }

        while(indexA1 >= 0) {
            A1[indexMerged] = A1[indexA1];
                --indexMerged;
                --indexA1;
        }

        while(indexA2 >= 0) {
            A1[indexMerged] = A1[indexA2];
                --indexMerged;
                --indexA2;
        }
    }
}
```

**总结:** 在合并数组或者字符串时，如果正序复制需要移动很多次，可以考虑倒序复制，这样可以减少移动的次数，从而提高效率。

### 链表

单链表的节点定义为：

```cpp
// Definition for singly-linked list.
struct ListNode {
    int val;
    ListNode *next;
    ListNode(int x) : val(x), next(NULL) {}
};
```

**Q:** 输入一个链表的头节点，从尾到头反过来打印每个节点的值。

**A:** 解法一 辅助栈法

```cpp
/**
 * Definition for singly-linked list.
 * struct ListNode {
 *     int val;
 *     ListNode *next;
 *     ListNode(int x) : val(x), next(NULL) {}
 * };
 */
class Solution {
public:
    vector<int> reversePrint(ListNode* head) {
        vector<int> res;
        stack<int> s;

        ListNode *p = head;

        while(p) {
            s.push(p->val);
            p = p->next;
        }

        while(!s.empty()) {
            res.push_back(s.top());
            s.pop();
        }

        return res;
    }
};


class Solution {
public:
    vector<int> reversePrint(ListNode* head) {
        vector<int> res;
        ListNode *p = head;

        while(p) {
            res.push_back(p->val);
            p = p->next;
        }

        reverse(res.begin(), res.end());

        return res;
    }
};
```

```python
# Definition for singly-linked list.
# class ListNode:
#     def __init__(self, x):
#         self.val = x
#         self.next = None

class Solution:
    def reversePrint(self, head: ListNode) -> List[int]:
        stack = []
        while head:
            stack.append(head.val)
            head = head.next

        return stack[::-1]
```

解法二 递归法

```cpp
/**
 * Definition for singly-linked list.
 * struct ListNode {
 *     int val;
 *     ListNode *next;
 *     ListNode(int x) : val(x), next(NULL) {}
 * };
 */
class Solution {
public:
    vector<int> reversePrint(ListNode* head) {
        if(!head) {
            return {};
        }

        vector<int> res = reversePrint(head->next);
        res.push_back(head->val);

        return res;
    }
};
```

```python
# Definition for singly-linked list.
# class ListNode:
#     def __init__(self, x):
#         self.val = x
#         self.next = None

class Solution:
    def reversePrint(self, head: ListNode) -> List[int]:
        return self.reversePrint(head.next) + [head.val] if head else []
```

### 树

树的常用几种遍历方式：

* 前序遍历：先访问根节点，再访问左子节点，最后访问右子节点。
* 中序遍历：先访问左子节点，再访问根节点，最后访问右子节点。
* 后序遍历：先访问左子节点，再访问右子节点，最后访问根节点。
* 宽度优先遍历：先访问树的第一层节点，再访问树的第二层节点...一直到访问到最下面一层节点。

在二叉搜索树中，左子节点总是小于或等于二根节点，而右子节点总是大于或等于根节点。可以平均在 O(logn) 的时间内根据数值在二叉搜索树中找到一个节点。

二叉树的一个特例是堆，最大堆中根节点的值最大，最小堆中根节点的值最小。

二叉树另一个特例是红黑树，它把树中的节点定义为红、黑两种颜色，并通过规则确保从根节点到叶节点的最长路径的长度不超过最短路径的两倍。

**Q:** 输入某二叉树的前序遍历和中序遍历的结果，请重建该二叉树。假设输入的前序遍历和中序遍历的结果中都不含重复的数字。例如，给出前序遍历 preorder = [3,9,20,15,7] 和 中序遍历 inorder = [9,3,15,20,7]，则返回如下的二叉树：

```cpp
    3
   / \
  9  20
    /  \
   15   7
```

限制：0 <= 节点个数 <= 5000

**A:** 若前序遍历或中序遍历或两者序列长度不同，则返回空。前序遍历的第一个节点即为根节点，在中序遍历中找到根节点的位置，分别得到左右子树的前序和中序遍历，然后递归重建即可。

```cpp
class Solution {
public:
    TreeNode *buildTree(vector<int> &preorder, vector<int> &inorder) {
        int lenP = preorder.size(), lenI = inorder.size();
        if(lenP == 0 || lenI == 0 || lenP != lenI) {
            return nullptr;
        }

        vector<int> preorderL, preorderR, inorderL, inorderR;
        int rootIndex = -1;
        TreeNode *root = new TreeNode(preorder[0]); // 根节点

        // 在中序遍历中找到根节点的位置
        for(int i = 0; i < lenP; ++i) {
            if(preorder[0] == inorder[i]) {
                rootIndex = i;
                break;
            }
        }

        // 分别得到左右子树的前序和中序遍历
        for(int i = 0; i < rootIndex; ++i) {
            preorderL.push_back(preorder[i+1]);
            inorderL.push_back(inorder[i]);
        }
        for(int i = rootIndex + 1; i < lenP; ++i) {
            preorderR.push_back(preorder[i]);
            inorderR.push_back(inorder[i]);
        }

        // 递归重建
        root->left = buildTree(preorderL, inorderL);
        root->right = buildTree(preorderR, inorderR);

        return root;
    }
};
```

```python
# Definition for a binary tree node.
# class TreeNode:
#     def __init__(self, x):
#         self.val = x
#         self.left = None
#         self.right = None

class Solution:
    def buildTree(self, preorder: List[int], inorder: List[int]) -> TreeNode:
        lenP = len(preorder)
        lenI = len(inorder)
        if lenP == 0 or lenI == 0 or lenP != lenI:
            return None

        preorderL, preorderR, inorderL, inorderR = [], [], [], []
        rootIndex = -1
        root = TreeNode(preorder[0])

        # 在中序遍历中找到根节点的位置
        for index, val in enumerate(inorder):
            if val == preorder[0]:
                rootIndex = index
                break

        # 分别得到左右子树的前序和中序遍历
        i = 0
        while i < rootIndex:
            preorderL.append(preorder[i+1])
            inorderL.append(inorder[i])
            i = i + 1
        i = rootIndex + 1
        while i < lenP:
            preorderR.append(preorder[i])
            inorderR.append(inorder[i])
            i = i + 1

        # 递归重建
        root.left = self.buildTree(preorderL, inorderL)
        root.right = self.buildTree(preorderR, inorderR)

        return root
```

### 栈和队列

栈在计算机领域被广泛使用，比如操作系统会给每个线程创建一个栈用来存储函数时各个函数的参数、返回地址和临时变量等。它的特点是先进后出。

队列的特点是先进先出。

**Q:** 面试题 9：用两个栈实现队列。用两个栈使用一个队列。队列的声明如下，请实现它的两个函数 appendTail 和 deleteHead，分别完成在队列尾部插入节点和队列头部删除节点的功能。

```cpp
template <typename T> class CQueue {
public:
    CQueue(void);
    ~CQueue(void);

    void appendTail(const T& node);
    T deleteHead();

private:
    stack<T> stack1;
    stack<T> stack2;
};
```

**A:** 用一个栈来中转即可。

```cpp
class CQueue {
    stack<int> stack1,stack2;
public:
    CQueue() {

    }

    void appendTail(int value) {
        stack1.push(value);
    }

    int deleteHead() {
        if(stack2.empty()) {
            while(!stack1.empty()) {
                stack2.push(stack1.top());
                stack1.pop();
            }
        }

        if(stack2.empty()) {
            return -1;
        }

        int head = stack2.top();
        stack2.pop();

        return head;
    }
};

/**
 * Your CQueue object will be instantiated and called as such:
 * CQueue* obj = new CQueue();
 * obj->appendTail(value);
 * int param_2 = obj->deleteHead();
 */
```

```python
class CQueue:

    def __init__(self):
        self.A, self.B = [], []

    def appendTail(self, value: int) -> None:
        self.A.append(value)

    def deleteHead(self) -> int:
        if self.B:
            return self.B.pop()

        if not self.A:
            return -1

        while self.A:
            self.B.append(self.A.pop())

        return self.B.pop()



# Your CQueue object will be instantiated and called as such:
# obj = CQueue()
# obj.appendTail(value)
# param_2 = obj.deleteHead()
```

## 算法和数据操作

很多算法都可以用**递归和循环**两种不同的方式实现。通常基于递归的实现方法代码会比较简洁，但是性能不如基于循环的实现方法。

**排序和查找算法**是重点中的重点，特别是二分查找、归并排序和快速排序。

如果面试题要求在二维数组（可能为迷宫或者棋盘等）上搜索路径，那么可以尝试**回溯法**。通常回溯法很适合用递归的代码实现，或者用栈来模拟递归的过程。

如果面试题要求某个问题的最优解，并且该问题可以分为多个子问题，那么可以尝试**动态规划**。

如果面试题在分解子问题的时候存在某个特殊的选择，如果采用这个特殊的选择将一定能得到最优解，那么可以尝试**贪婪算法**。

**位运算**可以看成一种特殊的算法，即把数字表示成二进制之后对 0 和 1 的操作，主要有 与、或、异或、左移、右移等五种位运算。

### 递归和循环

**Q:** 10- I. 斐波那契数列。写一个函数，输入 n ，求斐波那契（Fibonacci）数列的第 n 项。斐波那契数列的定义如下：

```cpp
F(0) = 0,
F(1) = 1,
F(N) = F(N - 1) + F(N - 2), 其中 N > 1.
```

答案需要取模 1e9+7（1000000007），如计算初始结果为：1000000008，请返回 1。

**A:**

```cpp

```

```python

```

**Q:**

**A:**

```cpp

```

```python

```

### 查找和排序

**Q:**

**A:**

```cpp

```

```python

```

**Q:**

**A:**

```cpp

```

```python

```

### 回溯法

**Q:**

**A:** 

**Q:** 

**A:** 

### 动态规划和贪婪算法

**Q:**

**A:** 

**Q:** 

**A:** 

### 位运算

**Q:**

**A:** 

**Q:** 

**A:** 

## 本章小结
