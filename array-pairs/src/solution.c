#include <stdio.h>
#include <stdlib.h>

long countPairs(int count, long *elems)
{
    long *counts = (long *)calloc(count, sizeof(long));
    long result = 0;

    for (int ndx = 1; ndx < count; ndx += 1)
    {
        long count = 0;
        long revMax = elems[ndx];

        for (int rev = ndx - 1; rev >= 0; rev -= 1)
        {
            if (elems[rev] > revMax) {
                revMax = elems[rev];
            }

            if (((elems[rev] * elems[ndx])) <= revMax) {
                count += 1;
            }
        }

        counts[ndx] = counts[ndx - 1] + count;
        result = counts[ndx];
    }

    return result;
}

void main(int argc, char const *argv[])
{
    int num_elems;
    long *elems;

    scanf("%d\n", &num_elems);

    elems = (long *)calloc(num_elems, sizeof(long));
    for (int i = 0; i < num_elems; i += 1)
    {
        scanf("%ld", &elems[i]);
    }

    printf("%ld\n", countPairs(num_elems, elems));
}
