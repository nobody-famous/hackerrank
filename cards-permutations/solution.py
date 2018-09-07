#!/bin/python

import math
import os
import random
import re
import sys

# Complete the solve function below.
MAXN = 3e5 + 5
mod = int(1e9 + 7)
inv2 = (mod + 1) >> 1


c = []
pre = []
fac = []
N = 0
count = 0
lex = 0


def add(a, b):
    a += b
    return a - mod if a >= mod else a


def pop(a, b):
    a -= b
    return a + mod if a < 0 else a


def mul(a, b):
    return (a * b) % mod


def lowbit(i):
    return i & (-i)


def update(o, v):
    i = o + 1
    while i <= N:
        c[i] += v
        i += lowbit(i)


def calc(o):
    sum = 0
    i = o + 1
    while i >= 1:
        sum += c[i]
        i -= lowbit(i)
    return sum


def prepare():
    global count, lex

    for i in range(1, N + 1):
        a[i] -= 1
        count += 1 if (a[i] == -1) else 0
        if a[i] >= 0:
            pre[a[i]] = 1

    fac[0] = 1
    for i in range(1, N + 1):
        fac[i] = mul(i, fac[i - 1])
    for i in range(1, N):
        pre[i] += pre[i - 1]

    lex = mul(mul(N, pop(N, 1)), inv2)
    for i in range(1, N + 1):
        if a[i] != -1:
            lex = pop(lex, a[i])


def cal2(n):
    return mul(mul(n, pop(n, 1)), inv2)


def solve(x):
    global count, lex

    prepare()

    cur = 0
    ans = 0
    for i in range(1, N + 1):
        if a[i] != -1:
            sum = mul(fac[count], a[i] - calc(a[i]))
            if count >= 1:
                sum = pop(sum, mul(fac[count - 1],
                                   mul(cur, a[i] + 1 - pre[a[i]])))
            sum = mul(sum, fac[N - i])
            ans = add(ans, sum)
            update(a[i], 1)
            lex = pop(lex, pop(N - 1 - a[i], pop(pre[N-1], pre[a[i]])))
        else:
            sum = mul(lex, fac[count - 1])
            if count >= 2:
                sum = pop(sum, mul(fac[count - 2], mul(cur, cal2(count))))
            sum = mul(sum, fac[N - i])
            ans = add(ans, sum)
            cur += 1
    return add(ans, fac[count])


if __name__ == '__main__':
    N = int(raw_input())

    a = map(int, raw_input().rstrip().split())
    a.insert(0, 0)

    for i in range(len(a)):
        pre.append(0)
        fac.append(0)
        c.append(0)

    result = solve(a)

    print(result)
