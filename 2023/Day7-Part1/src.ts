"use strict";

import * as fs from 'fs';
import path from 'path';

const cardValues = {
    'A': 14,
    'K': 13,
    'Q': 12,
    'J': 11,
    'T': 10,
    '9': 9,
    '8': 8,
    '7': 7,
    '6': 6,
    '5': 5,
    '4': 4,
    '3': 3,
    '2': 2
};

function handStrength(hand: string): [number, number[]] {
    // @ts-ignore
    const cards = hand.split('').map(card => cardValues[card]).sort((a, b) => b - a);
    const counts = new Map<number, number>();
    for (const card of cards) {
        counts.set(card, (counts.get(card) || 0) + 1);
    }
    const pairs = Array.from(counts.values()).sort((a, b) => b - a);
    let type = 0;
    if (pairs[0] === 5) type = 7;
    else if (pairs[0] === 4) type = 6;
    else if (pairs[0] === 3 && pairs[1] === 2) type = 5;
    else if (pairs[0] === 3) type = 4;
    else if (pairs[0] === 2 && pairs[1] === 2) type = 3;
    else if (pairs[0] === 2) type = 2;
    else type = 1;
    return [type, cards];
}

let fileContent = fs.readFileSync(path.join(__dirname, "input")).toString();
let lines = fileContent.split("\n");
console.log(lines);

interface Hand {
    hand: string;
    bid: number;
    strength: [number, number[]];
}

let hands: Hand[] = [];

for (let line of lines) {
    let [hand, bid] = line.split(' ');
    let bidNumber = parseInt(bid);
    hands.push({ hand, bid: bidNumber, strength: [0, []]});
}

for (const hand of hands) {
    hand.strength = handStrength(hand.hand);
}

hands.sort((a, b) => {
    if (a.strength[0] !== b.strength[0])
        return b.strength[0] - a.strength[0];
    for (let i = 0; i < 5; i++) {
        if (a.strength[1][i] !== b.strength[1][i])
            return b.strength[1][i] - a.strength[1][i];
    }
    return 0;
});

let totalWinnings = 0;
for (let i = 0; i < hands.length; i++) {
    totalWinnings += hands[i].bid * (i + 1);
}

console.log(hands);
console.log(totalWinnings);