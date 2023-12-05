<?php

function parseMapping(string $line): array {
    return explode(' ', trim($line));
}

function convertRange(array $range, array $mappings): array {
    $resultRanges = [];
    list($min, $max) = $range;

    foreach ($mappings as $mapping) {
        list($destStart, $sourceStart, $length) = $mapping;
        $sourceEnd = $sourceStart + $length;

        if ($min < $sourceEnd && $max >= $sourceStart) {
            $mappedMin = max($min, $sourceStart);
            $mappedMax = min($max, $sourceEnd - 1);

            $resultRanges[] = [$destStart + ($mappedMin - $sourceStart), $destStart + ($mappedMax - $sourceStart)];
        }
    }

    if (empty($resultRanges))
        $resultRanges[] = $range;

    return $resultRanges;
}

function parseSeedRanges(array $lines, int &$index): array {
    $seedRangesLine = explode(' ', substr(trim($lines[$index++]), 7));
    $ranges = [];
    for ($i = 0; $i < count($seedRangesLine); $i += 2) {
        $start = (int)$seedRangesLine[$i];
        $length = (int)$seedRangesLine[$i + 1];
        $ranges[] = [$start, $start + $length - 1];
    }
    return $ranges;
}

function parseMappings(array $lines, int &$index): array {
    $maps = [];
    while ($index < count($lines)) {
        if (trim($lines[$index]) === "") {
            $index++;
            continue;
        }
        if (str_contains($lines[$index], 'map:'))
            $maps[] = [];
        else
            $maps[count($maps) - 1][] = parseMapping($lines[$index]);
        $index++;
    }
    return $maps;
}

function processRangesThroughMappings(array $ranges, array $maps): ?int {
    foreach ($maps as $map) {
        $newRanges = [];
        foreach ($ranges as $range) {
            $convertedRanges = convertRange($range, $map);
            $newRanges = array_merge($newRanges, $convertedRanges);
        }
        $ranges = $newRanges;
    }

    $lowestLocation = PHP_INT_MAX;
    foreach ($ranges as $range)
        $lowestLocation = min($lowestLocation, $range[0], $range[1]);

    return $lowestLocation === PHP_INT_MAX ? null : $lowestLocation;
}

function processSeeds(string $filePath): ?int {
    $lines = file($filePath, FILE_IGNORE_NEW_LINES);
    $index = 0;

    $ranges = parseSeedRanges($lines, $index);
    $maps = parseMappings($lines, $index);

    return processRangesThroughMappings($ranges, $maps);
}

echo processSeeds("input");