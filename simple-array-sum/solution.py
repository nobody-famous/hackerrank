#!/bin/python

import os
import sys

#
# Complete the simpleArraySum function below.
#


def simpleArraySum(ar):
    n = 0
    for a in ar:
        n += a
    return n


if __name__ == '__main__':
    ar_count = int(input())

    ar = list(map(int, input().rstrip().split()))

    result = simpleArraySum(ar)

    print(result)
