'use strict';

const fs = require('fs');

process.stdin.resume();
process.stdin.setEncoding('utf-8');

let inputString = '';
let currentLine = 0;

process.stdin.on('data', inputStdin => {
  inputString += inputStdin;
});

process.stdin.on('end', _ => {
  inputString = inputString.replace(/\s*$/, '')
    .split('\n')
    .map(str => str.replace(/\s*$/, ''));

  main();
});

function readLine() {
  return inputString[currentLine++];
}

function mergeSort(arr, temp, left, right) {
  let mid = 0;
  let invCount = 0;

  if (right > left) {
    mid = Math.floor((right + left) / 2);

    invCount = mergeSort(arr, temp, left, mid);
    invCount += mergeSort(arr, temp, mid + 1, right);

    invCount += merge(arr, temp, left, mid + 1, right);
  }

  return invCount;
}

function merge(arr, temp, left, mid, right) {
  let i = left;
  let j = mid;
  let k = left;
  let invCount = 0;

  while ((i <= mid - 1) && (j <= right)) {
    if (arr[i] <= arr[j]) {
      temp[k] = arr[i];
      k += 1;
      i += 1;
    } else {
      temp[k] = arr[j];
      k += 1;
      j += 1;
      invCount += (mid - i);

    }
  }

  while (i <= mid - 1) {
    temp[k] = arr[i];
    k += 1;
    i += 1;
  }

  while (j <= right) {
    temp[k] = arr[j];
    k += 1;
    j += 1;
  }

  for (i = left; i <= right; i += 1) {
    arr[i] = temp[i];
  }

  return invCount;
}

// Complete the insertionSort function below.
function insertionSort(arr) {
  let temp = [];
  for (let i = 0; i < arr.length; i += 1) {
    temp[i] = 0;
  }
  return mergeSort(arr, temp, 0, arr.length - 1);
}

function main() {
  const t = parseInt(readLine(), 10);

  for (let tItr = 0; tItr < t; tItr++) {
    const n = parseInt(readLine(), 10);

    const arr = readLine().split(' ').map(arrTemp => parseInt(arrTemp, 10));

    let result = insertionSort(arr);

    process.stdout.write(result + "\n");
  }
}
