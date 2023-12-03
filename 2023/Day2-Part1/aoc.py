

def main():
    file = open("input", "r")
    result = 0
    while True:
        line = file.readline()
        if not line:
            break
        data = line.split(":")
        game_number = int(data[0].split(" ")[1])
        draws = data[1].split(";")
        success = True
        for draw in draws:
            if not success:
                break
            cubes = draw.split(",")
            for cube in cubes:
                cube_data = cube.split(" ")
                amount = int(cube_data[1])
                cube_color = cube_data[2].replace("\n", "")
                if cube_color == "red" and amount > 12:
                    success = False
                    break
                if cube_color == "green" and amount > 13:
                    success = False
                    break
                if cube_color == "blue" and amount > 14:
                    success = False
                    break
        if success:
            result += game_number

    print(result)

if __name__ == "__main__":
    main()


