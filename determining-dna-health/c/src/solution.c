#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define BUF_SIZE 256

typedef struct NumberNode
{
    int value;
    struct NumberNode *kids[10];
} NumberNode;

typedef struct TreeNode
{
    int *indices;
    int indicesNdx;
    size_t allocSize;
    struct TreeNode *kids[26];
    long value;
} Node;

NumberNode *numberTree = NULL;
int numGenes;
int *health;

char *readLine()
{
    size_t alloc_length = 1024;
    size_t data_length = 0;
    char *data = malloc(alloc_length);

    while (1)
    {
        char *cursor = data + data_length;
        char *line = fgets(cursor, alloc_length - data_length, stdin);

        if (!line)
        {
            break;
        }

        data_length += strlen(cursor);

        if (data_length < alloc_length - 1 || data[data_length - 1] == '\n')
        {
            break;
        }

        size_t new_length = alloc_length << 1;
        data = realloc(data, new_length);

        if (!data)
        {
            break;
        }

        alloc_length = new_length;
    }

    if (data[data_length - 1] == '\n')
    {
        data[data_length - 1] = '\0';
    }

    data = realloc(data, data_length);

    return data;
}

int readNumber()
{
    NumberNode *root = (numberTree == NULL)
                           ? (NumberNode *)calloc(1, sizeof(NumberNode))
                           : numberTree;
    int ch;
    int value;

    NumberNode *n = root;
    while (1)
    {
        ch = fgetc(stdin);
        if ((feof(stdin)) || (ch < '0') || (ch > '9'))
        {
            break;
        }

        int hash = ch - '0';
        if (n->kids[hash] == NULL)
        {
            n->kids[hash] = (NumberNode *)calloc(1, sizeof(NumberNode));
            n->kids[hash]->value = (n->value * 10) + hash;
        }
        n = n->kids[hash];
        value = n->value;
    }

    numberTree = root;
    return value;
}

char **split_string(char *str)
{
    char **splits = NULL;
    char *token = strtok(str, " ");

    int spaces = 0;

    while (token)
    {
        splits = realloc(splits, sizeof(char *) * ++spaces);
        if (!splits)
        {
            return splits;
        }

        splits[spaces - 1] = token;

        token = strtok(NULL, " ");
    }

    return splits;
}

Node *createNode()
{
    Node *n = (Node *)calloc(1, sizeof(Node));
    return n;
}

void addIndex(Node *n, int index)
{
    if (n->indicesNdx >= n->allocSize)
    {
        n->allocSize *= 2;
        n->indices = (int *)realloc(n->indices, n->allocSize * sizeof(int));
    }

    n->indices[n->indicesNdx] = index;
    n->indicesNdx += 1;
}

Node *buildTree()
{
    Node *root = createNode();
    int ch;
    int index;

    Node *n = root;
    index = 0;
    while (1)
    {
        ch = fgetc(stdin);
        if (feof(stdin))
        {
            break;
        }

        if ((ch >= 'a') && (ch <= 'z'))
        {
            int hash = ch - 'a';
            if (n->kids[hash] == NULL)
            {
                n->kids[hash] = createNode();
            }
            n = n->kids[hash];
        }
        else
        {
            if (n->indices == NULL)
            {
                n->allocSize = BUF_SIZE;
                n->indices = (int *)calloc(n->allocSize, sizeof(int));
            }
            addIndex(n, index);

            n = root;
            index += 1;
            if (ch == ' ')
            {
                continue;
            }
            else
            {
                break;
            }
        }
    }

    return root;
}

int *buildHealth(int size)
{
    int *health = (int *)calloc(size, sizeof(int));

    for (int index = 0; index < size; index += 1)
    {
        health[index] = readNumber();
    }

    return health;
}

void freeTree(Node *root)
{
    for (int ndx = 0; ndx < 26; ndx += 1)
    {
        if (root->kids[ndx] != NULL)
        {
            freeTree(root->kids[ndx]);
        }
    }

    if (root->indices != NULL)
    {
        free(root->indices);
    }
    free(root);
}

long nodeValue(Node *n, int first, int last)
{
    long value = 0;

    for (int ndx = 0; ndx < n->indicesNdx; ndx += 1)
    {
        if ((n->indices[ndx] >= first) && (n->indices[ndx] <= last))
        {
            value += health[n->indices[ndx]];
        }
    }

    return value;
}

long calculateHealthOffset(Node *tree, char *strand, int offset, int length, int first, int last)
{
    long value = 0;

    Node *n = tree;
    for (int ndx = offset; ndx < length; ndx += 1)
    {
        int hash = strand[ndx] - 'a';
        if (n->kids[hash] == NULL)
        {
            break;
        }
        n = n->kids[hash];
        value += nodeValue(n, first, last);
    }

    return value;
}

long calculateHealth(Node *tree, char *strand, int first, int last)
{
    long health = 0;
    int length = strlen(strand);

    for (int ndx = 0; ndx < length; ndx += 1)
    {
        health += calculateHealthOffset(tree, strand, ndx, length, first, last);
    }

    return health;
}

int main()
{
    numGenes = readNumber();
    Node *root = buildTree();
    health = buildHealth(numGenes);
    int s = readNumber();

    long min = -1;
    long max = -1;
    for (int i = 0; i < s; i += 1)
    {
        int first = readNumber();
        int last = readNumber();
        char *strand = readLine();

        long value = calculateHealth(root, strand, first, last);
        free(strand);
        if ((min == -1) || (value < min))
        {
            min = value;
        }
        if ((max == -1) || (value > max))
        {
            max = value;
        }
    }

    printf("%ld %ld\n", min, max);

    freeTree(root);
    free(health);
}
