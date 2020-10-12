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