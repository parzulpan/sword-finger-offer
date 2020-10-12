# 面试需要的基础知识

## 面试官谈基础知识

## 编程语言

### C++

对于编程语言，通常有三种类型的题。

**第一种题型**，考察对 C++ 概念的理解，比如关键字、面向对象特性等等。

Q: 在 C++ 中，有哪 4 个与类型转换相关的关键字？这些关键字各有什么特点，应该在什么场合下使用？

> A: 

Q: 定义一个空的类型，里面没有任何成员变量和成员函数，对该类型求 sizeof，得到的结果是多少？

> A: 

Q: 如果在该类型中添加一个构造函数和析构函数，再对该类型求 sizeof，得到的结果是多少？

> A: 

Q: 如果把析构函数标记为虚函数呢？

> A: 

**第二种题型**，考察对指定代码的分析，比如运行结果、调优策略等。

Q: 以下代码的运行结果，编译错误？编译成功，运行时程序崩溃？编译运行正常，输出 10？

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

> A: 

**第三种题型**，考察对指定代码的编写，比如定义一个类型、实现一个函数等。

Q: 如下为类型 CMyString 的声明，请为该类型添加赋值运算符函数。

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

> A: 

## 数据结构

## 算法和数据操作

## 本章小结
