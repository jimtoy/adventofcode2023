package day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CubeConundrum {

    public static void main(String[] args) throws IOException {

        String[] colors = {"blue", "red", "green"};
        int maxRed = 12;
        int maxGreen = 13;
        int maxBlue = 14;
        List<String> lines = Files.readAllLines(Paths.get("src/day2/input"));

        Map<Integer, Map> gameMap = new HashMap<>();

        for (String line : lines) {
            Map<String, Integer> highColors = new HashMap<>();
            int gameDelmimitor = line.indexOf(":");
            String gameNumber = line.substring(0, gameDelmimitor).replaceAll("Game ", "");
            String gamesDetail = (line.substring(gameDelmimitor + 1));
            String[] games = gamesDetail.split(";");
            for (String game : games) {
                String[] cubes = game.split(",");
                for (String cube : cubes) {
                    for (String color : colors) {
                        if (cube.contains(color)) {
                            int pickedValue = Integer.parseInt(cube.replace(color, "").trim());
                            int storedValue = 0;
                            if (highColors.get(color) != null) {
                                storedValue = highColors.get(color).intValue();
                            }
                            if (storedValue < pickedValue) {
                                highColors.put(color, pickedValue);
                            }
                        }
                    }

                }
            }
            gameMap.put(Integer.parseInt(gameNumber), highColors);
        }

        int gameSum = 0;
        for (Map.Entry<Integer, Map> game : gameMap.entrySet()) {

            int gameKey = game.getKey();

            int red = Integer.parseInt(game.getValue().get("red").toString());
            int blue = Integer.parseInt(game.getValue().get("blue").toString());
            int green = Integer.parseInt(game.getValue().get("green").toString());

            if (red <= maxRed && blue <= maxBlue && green <= maxGreen) {
                gameSum += gameKey;
            } else {
                System.out.println("Game impossible " + gameKey);
                System.out.println(game);
            }

        }

        System.out.println(gameSum);
    }
}
