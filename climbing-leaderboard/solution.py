import os


def climbLeaderboard(leaderboard, scores):
    ranks = []
    lbRanks = [0 for x in range(len(leaderboard))]

    lbRanks[0] = 1
    curRank = 1
    curScore = leaderboard[0]
    for i in range(1, len(leaderboard)):
        if leaderboard[i] < curScore:
            curRank += 1
            curScore = leaderboard[i]
        lbRanks[i] = curRank

    lbNdx = len(leaderboard) - 1

    scoresNdx = 0
    while scores[scoresNdx] < leaderboard[lbNdx]:
        ranks.append(lbRanks[lbNdx] + 1)
        scoresNdx += 1
        if scoresNdx >= len(scores):
            break

    while scoresNdx < len(scores):
        score = scores[scoresNdx]
        if score > leaderboard[0]:
            ranks.append(1)
        else:
            while score > leaderboard[lbNdx]:
                lbNdx -= 1

            rank = lbRanks[lbNdx] if score == leaderboard[lbNdx] else lbRanks[lbNdx] + 1
            ranks.append(rank)
        scoresNdx += 1

    return ranks


numPlayers = int(input())
leaderboard = list(map(int, input().rstrip().split(' ')))
numGames = int(input())
scores = list(map(int, input().rstrip().split(' ')))

ranks = climbLeaderboard(leaderboard, scores)

for r in ranks:
    print(r)
