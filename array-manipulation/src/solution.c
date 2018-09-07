#include <stdio.h>
#include <stdlib.h>

void read_deltas(int *deltas, int num_elems, int num_ops)
{
    int start, end, value;

    for (int loop = 0; loop < num_ops; loop += 1)
    {
        scanf("%d %d %d", &start, &end, &value);

        deltas[start] += value;
        if ((end + 1) <= num_elems)
        {
            deltas[end + 1] -= value;
        }
    }
}

long get_max(int *deltas, int num_elems)
{
    long max = 0;
    long count = 0;

    for (int ndx = 1; ndx <= num_elems; ndx += 1)
    {
        count += deltas[ndx];
        if ((max == 0) || (count > max))
        {
            max = count;
        }
    }

    return max;
}

int main(int argc, char **argv)
{
    int num_elems, num_ops;
    int *deltas;

    scanf("%d %d\n", &num_elems, &num_ops);

    deltas = (int *)calloc(num_elems + 1, sizeof(int));
    read_deltas(deltas, num_elems, num_ops);
    printf("%ld\n", get_max(deltas, num_elems));
}
