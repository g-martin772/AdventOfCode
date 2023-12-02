#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdint.h>

#define ARRAY_SIZE(arr) (sizeof(arr) / sizeof((arr)[0]))

int getValue(char* line, size_t lineLengh) {
    int first = -1, last = -1;
    if (line[0] >= '0' && line[0] <= '9')
	    return line[0] - 0x30;

	if (lineLengh < 3)
		return -1;

	if (lineLengh >= 3) {
		if (strncmp(line, "one", 3) == 0)
            return 1;
		if (strncmp(line, "two", 3) == 0)
			return 2;
		if (strncmp(line, "six", 3) == 0)
			return 6;
	}

    if (lineLengh >= 4) {
		if (strncmp(line, "four", 4) == 0)
            return 4;
        if (strncmp(line, "five", 4) == 0)
            return 5;
        if (strncmp(line, "nine", 4) == 0)
            return 9;
    }

	if (lineLengh >= 5) {
	    if (strncmp(line, "three", 5) == 0)
            return 3;
        if (strncmp(line, "seven", 5) == 0)
            return 7;
        if (strncmp(line, "eight", 5) == 0)
            return 8;
	}

    return -1;
}

int main(int argc, char** argv) {
	if (argc != 2) {
		printf("Supply input file");
		return 1;
	}

	char* fileName = argv[1];

	FILE* file = fopen(fileName, "r");
	if (file == NULL) {
		printf("Opening file: %f failed.", fileName);
		return -1;
	}
	char* line;
    size_t len;
    ssize_t lineLengh;

	size_t sum;

    	while((lineLengh = getline(&line, &len, file)) != -1) {
        	printf("%s", line);
			int first = -1, last = -1, current = -1;
			for (size_t i = 0; i < lineLengh - 1; i++) {
				if ((current = getValue(line + i, lineLengh - i - 1)) != -1) {
					if(first == -1) {
						first = current;
						last = current;
					}
					else {
						last = current;
					}
				}
			}

			size_t val = 10 * first + last;
			sum += val;
			printf("%u\n\n", val);
    	}

	printf("Summ: %u", sum);
    
    if(line)
        free(line);

	fclose(file);

	return 0;
}
