

def main():
    file = open("input", "r")
    result = 0
    while True:
        line = file.readline()
        if not line:
            break
        data = line.split(": ")
        game_number = int(data[0].split(" ")[1])
        draws = data[1].split("; ")

        min_red = 0
        min_green = 0
        min_blue = 0

        for draw in draws:
            cubes = draw.split(", ")
            for cube in cubes:
                cube_data = cube.split(" ")
                amount = int(cube_data[0])
                cube_color = cube_data[1].replace("\n", "")
                if cube_color == "red" and amount > min_red:
                    min_red = amount
                if cube_color == "green" and amount > min_green:
                    min_green = amount
                if cube_color == "blue" and amount > min_blue:
                    min_blue = amount
        result += (min_red * min_blue * min_green)

    print(result)

if __name__ == "__main__":
    main()


