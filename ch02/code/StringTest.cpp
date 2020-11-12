#include<iostream>

int main(int argc, char* argv[]) {
    char str1[] = "hello world";
    char str2[] = "hello world";

    char* str3 = "hello world";
    char* str4 = "hello world";

    if(str1 == str2) {
        std::cout << "str1 and str2 are same." << std::endl;
    } else {
        std::cout << "str1 and str2 are not same." << std::endl;
    }

    if(str3 == str4) {
        std::cout << "str3 and str4 are same." << std::endl;
    } else {
        std::cout << "str3 and str4 are not same." << std::endl;
    }

    return 0;
}