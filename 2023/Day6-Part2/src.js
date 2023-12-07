"use strict";

const fs = require("fs");
const path = require("path");

let fileContent = fs.readFileSync(path.join(__dirname, "input")).toString();

let lines = fileContent.split("\n");
let timeLine = lines[0].split(/\s+/).filter(Boolean).slice(1);
let distanceLine = lines[1].split(/\s+/).filter(Boolean).slice(1);

let timeArray = timeLine.map(Number);
let distanceArray = distanceLine.map(Number);

console.log(timeArray);
console.log(distanceArray);

let result = 1;

for (let i = 0; i < timeArray.length; i++) {
    let time = timeArray[i];
    let distance = distanceArray[i];
    let winOptions = 0;
    for (let j = 0; j < time; j++) {
        if (j * (time - j) > distance)
            winOptions++;
    }
    console.log(winOptions);
    result *= winOptions;
}

console.log(result);