<?php

class Entry {
    public int $sourceStart;
    public int $range;
    public int $offset;

    public function valueFor($value): int {
        if ($value < $this->sourceStart)
            return -1;
        if ($value > $this->sourceStart + $this->range)
            return -1;

        return $value + $this->offset;
    }
}

class Table {
    public string $from;
    public string $to;
    public array $data;

    function valueFor($value) : int {
        foreach ($this->data as $entry) {
            $query_result = $entry->valueFor($value);
            if ($query_result != -1) {
                return $query_result;
            }
        }

        return $value;
    }
}

$file = file_get_contents("input");

$data = preg_split("#\n\s*\n#Uis", $file, 2);
$seeds = $data[0];
$seeds = explode(": ", $seeds, 2)[1];
$blocks = $data[1];

$tables = array();

// Parse Tables
$pass = 0;
foreach (preg_split("#\n\s*\n#Uis", $blocks) as $block) {
    $data = explode("\n", $block, 2);

    $header = $data[0];
    $name_data = explode("-", explode(" ", $header)[0]);
    $from = $name_data[0];
    $to = $name_data[2];

    $table = new Table();
    $table->to = $to;
    $table->from = $from;
    $table->data = array();

    $table_data = $data [1];

    $i = 0;
    foreach (explode("\n", $table_data) as $line) {
        $values = explode(" ", $line);
        $entry = new Entry();
        $entry->sourceStart = $values[1];
        $entry->offset = $values[0] - $values[1];
        $entry->range = $values[2];
        $table->data[$i] = $entry;
        $i++;
    }

    $tables[$pass] = $table;
    $pass++;
}

$closest = PHP_INT_MAX;

$seed_values = explode(" ", $seeds);

foreach ($tables as $table) {
    $i = 0;
    foreach ($seed_values as $seed_value) {
        $res = $table->valueFor($seed_value);
        $seed_values[$i] = $res;
        if ($table->to == "location" && $res < $closest)
            $closest = $res;
        $i++;
    }
}

echo $closest;