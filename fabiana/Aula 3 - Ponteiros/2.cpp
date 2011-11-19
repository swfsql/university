#include <iostream>

// implementar ex0~5

void ex0 (float **nums, int l) // point pra aFloat {1.1, .., 10.10}
{
    int i = -1;
    *nums = new float[l];
    while(++i < l)
        (*nums)[i] = (i+1) + ((i+1)<10 ?
                              (i+1)*.1 :
                              (i+1)*.01 );
}

void ex1 () // point float
{
    // na main()
}

void ex2 (float *nums, int l) // imprimir ex0
{
    for (int i = 0; i < l; ++i)
        std::cout << nums[i] << " ";
}

void ex3 (float **nums, float **p) // ex1 apontar pra ex0[0]
{
    *p = &*nums[0]; //*p = &((*nums)[0]);
}

void ex4 (float *p, int l) // ex2 + indexando ex3
{
    int i = -1;
    while(++i < l)
        std:: cout << *(p + i) << " ";
}

void ex5 (float *nums, float *p) // ex0[3] por '[]' e por '(*)'
{
    std::cout << nums[3] << " e " << *(p+3);
}

int main()
{
    // ex0 - point pra aFloat {1.1, .., 10.10}
    const int TAM = 10;
    float *numeros = 0;
    ex0(&numeros, TAM);

    // ex1 - point float
    float *nPtr = 0;

    // ex2 - imprimir ex0
    ex2(numeros, TAM);

    std::cout << "\n";

    // ex3 - ex1 apontar pra ex0[0]
    //nPtr = &numeros[0];
    ex3(&numeros, &nPtr); // se não for por ref. o endereço não é o mesmo.. eu acho.

    // ex4 - ex2 + indexando ex3
    ex4(nPtr, TAM); // nPtr já aponta pro numeros[0]..

    std::cout << "\n";

    // ex5 - ex0[3] por '[]' e por '(*)'
    ex5(numeros, nPtr);

    return 0;
}
