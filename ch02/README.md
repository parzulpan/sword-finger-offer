# 面试需要的基础知识

## 面试官谈基础知识

## 编程语言

### C++

对于编程语言，通常有三种类型的题。

**第一种题型**，考察对 C++ 概念的理解，比如关键字、面向对象特性等等。

Q: 在 C++ 中，有哪 4 个与类型转换相关的关键字？这些关键字各有什么特点，应该在什么场合下使用？

A: 4 个与类型转换相关的关键字分别是：static_cast、const_cast、reinterpret_cast、dynamic_cast。

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

Q: 定义一个空的类型，里面没有任何成员变量和成员函数，对该类型求 sizeof，得到的结果是多少？

A: 结果为 1。因为当声明该类型的实例时，它在内存中必须有一定的空间，否则就无法使用这些实例。具体占用多少内存，由编译器决定。

Q: 如果在该类型中添加一个构造函数和析构函数，再对该类型求 sizeof，得到的结果是多少？

A: 结果为 1。调用构造函数和析构函数只需要知道函数的地址即可，而这些函数的地址只与类型相关，而与类型的实例无关。

Q: 如果把析构函数标记为虚函数呢？

A: 结果为一个指针的所占的内存空间。编译器会为含有虚函数的类型生成一个虚函数表，并在该类型的每一个实例添加一个指向虚函数表的指针。在 32 位中，为 4 个字节，64 位中，为 8 个字节。

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

A: 运行结果为编译错误。复制构造函数 `A(A other) { value = other.value; }` 传入的参数是 A 的一个实例，由于是传值参数，所以形参复制到实参会调用复制构造函数，即会造成无休止的递归调用从而栈溢出。可以把复制构造函数修改为 `A(const A &other) { value = other.value; }` ，即改为常量引用。

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

A: 定义一个赋值运算符需要注意以下几点：

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

## 数据结构

## 算法和数据操作

## 本章小结
