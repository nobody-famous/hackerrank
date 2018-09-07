import math
import os
import random
import re
import sys

# Complete the nonDivisibleSubset function below.


def nonDivisibleSubset(k, S):
    d = []
    for x in range(k):
        d.append([])

    for i in range(len(S)):
        d[S[i] % k].append(S[i])

    count = 1 if len(d[0]) > 0 else 0

    divSet = [(x, k-x) for x in range(1, k//2 + 1)]

    for i, j in divSet:
        if i != j:
            if len(d[i]) > len(d[j]):
                count += len(d[i])
            else:
                count += len(d[j])
        else:
            if len(d[i]) > 0:
                count += 1

    return count


if __name__ == '__main__':
    nk = input().split()

    n = int(nk[0])

    k = int(nk[1])

    S = list(map(int, input().rstrip().split()))

    result = nonDivisibleSubset(k, S)

    print(result)
