#include <stdio.h>
#include <stdlib.h>

#define ARRAY_SIZE(arr) (sizeof(arr) / sizeof((arr)[0]))

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
        	printf("%s\n", line);
		size_t first = -1, last = -1; 
		for (size_t i = 0; i < lineLengh - 1; i++) {
			if (line[i] >= '0' && line[i] <= '9') {
				if(first == -1) {
					first = line[i] - 0x30;
					last = line[i] - 0x30;
				}
				else {
					last = line[i] - 0x30;
				}
			}
		}

		size_t val = 10 * first + last;
		sum += val;
		printf("%u\n", val);
    	}

	printf("Summ: %u", sum);
    
    	if(line)
        	free(line);

	fclose(file);

	return 0;
}
