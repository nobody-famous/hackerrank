import math
import os
import random
import re
import sys

# Complete the queensAttack function below.


def delta(a, b):
    return abs(a - b) - 1


def printGrid(n, r_q, c_q, obstacles):
    grid = [['-' for x in range(n + 1)] for x in range(n + 1)]

    for i in range(n + 1):
        if i == 0:
            continue
        for j in range(n + 1):
            if j == 0:
                continue
            if i == r_q or j == c_q:
                grid[i][j] = '*'
            if delta(r_q, i) == delta(c_q, j):
                grid[i][j] = '*'

    grid[r_q][c_q] = 'Q'
    for ob in obstacles:
        grid[ob[0]][ob[1]] = 'X'

    for i in range(n + 1):
        if i == 0:
            continue
        for j in range(n + 1):
            if j == 0:
                continue
            print(grid[i][j], end='')
        print()


def queensAttack(n, k, r_q, c_q, obstacles):
    # printGrid(n, r_q, c_q, obstacles)

    N = 0
    S = 1
    E = 2
    W = 3
    NW = 4
    NE = 5
    SW = 6
    SE = 7

    names = ['N', 'S', 'E', 'W', 'NW', 'NE', 'SW', 'SE']
    near = [-1, -1, -1, -1, -1, -1, -1, -1]

    for ob in obstacles:
        deltac = delta(ob[1], c_q)
        deltar = delta(ob[0], r_q)

        if ob[0] < r_q:
            if ob[1] == c_q:
                if near[S] == -1 or delta(r_q, ob[0]) < near[S]:
                    near[S] = deltar

            elif delta(ob[0], r_q) == delta(ob[1], c_q):
                value = deltac

                if ob[1] > c_q and (near[SE] == -1 or value < near[SE]):
                    near[SE] = value

                if ob[1] < c_q and (near[SW] == -1 or value < near[SW]):
                    near[SW] = value

        elif ob[0] > r_q:
            if ob[1] == c_q:
                if near[N] == -1 or delta(r_q, ob[0]) < near[N]:
                    near[N] = deltar

            elif delta(ob[0], r_q) == delta(ob[1], c_q):
                value = deltac
                if ob[1] > c_q and (near[NE] == -1 or delta(ob[1], c_q) < near[NE]):
                    near[NE] = value
                if ob[1] < c_q and (near[NW] == -1 or delta(ob[1], c_q) < near[NW]):
                    near[NW] = value

        if ob[1] < c_q:
            if ob[0] == r_q:
                if near[W] == -1 or delta(c_q, ob[1]) < near[W]:
                    near[W] = deltac

        elif ob[1] > c_q:
            if ob[0] == r_q:
                if near[E] == -1 or delta(c_q, ob[1]) < near[E]:
                    near[E] = deltac

    total = 0
    for i in range(len(near)):
        if near[i] != -1:
            # print('near{} {}'.format(names[i], near[i]))
            total += near[i]

    if near[N] == -1:
        # print('N {}'.format(delta(n + 1, r_q)))
        total += delta(n + 1, r_q)

    if near[S] == -1:
        # print('S {}'.format(delta(1, r_q) + 1))
        total += delta(1, r_q) + 1

    if near[E] == -1:
        # print('E {}'.format(delta(n + 1, c_q)))
        total += delta(n + 1, c_q)

    if near[W] == -1:
        # print('W {}'.format(delta(1, c_q) + 1))
        total += delta(1, c_q) + 1

    if near[NE] == -1:
        # print('NE {}'.format(min(delta(n + 1, c_q), delta(n + 1, r_q))))
        total += min(delta(n + 1, c_q), delta(n + 1, r_q))

    if near[NW] == -1:
        # print('NW {}'.format(min(delta(1, c_q) + 1, delta(n + 1, r_q))))
        total += min(delta(1, c_q) + 1, delta(n + 1, r_q))

    if near[SE] == -1:
        # print('SE {}'.format(min(delta(n + 1, c_q), delta(1, r_q))))
        total += min(delta(n + 1, c_q), delta(1, r_q) + 1)

    if near[SW] == -1:
        # print('SW {}'.format(min(delta(1, c_q) + 1, delta(1, r_q) + 1)))
        total += min(delta(1, c_q) + 1, delta(1, r_q) + 1)

    return total


if __name__ == '__main__':
    nk = input().split()

    n = int(nk[0])

    k = int(nk[1])

    r_qC_q = input().split()

    r_q = int(r_qC_q[0])

    c_q = int(r_qC_q[1])

    obstacles = []

    for _ in range(k):
        obstacles.append(list(map(int, input().rstrip().split())))

    result = queensAttack(n, k, r_q, c_q, obstacles)

    print(result)
