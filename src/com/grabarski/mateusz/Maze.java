package com.grabarski.mateusz;

import com.grabarski.mateusz.exceptions.InvalidMazeException;
import com.grabarski.mateusz.exceptions.MazeCreateException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by Mateusz Grabarski on 21.05.2018.
 */
public class Maze {

    private PointType[][] points;
    private Point startPoint;
    private Point endPoint;

    public Maze(String filesName) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filesName));
            readPointsFromFileLines(lines);
        } catch (IOException e) {
            throw new MazeCreateException("Error with read file");
        }
    }

    private void readPointsFromFileLines(List<String> lines) {

        lines.stream()
                .findFirst()
                .orElseThrow(() -> new InvalidMazeException("Brak wierszy w pliku"));

        if (lines.stream()
                .map(s -> s.length())
                .distinct()
                .count() > 1) {
            throw new InvalidMazeException("Wiersze pliku mają różną długość");
        }

        points = new PointType[lines.size()][lines.get(0).length()];

        for (int i = 0; i < lines.size(); i++) {

            String parsingLine = lines.get(i);

            for (int j = 0; j < parsingLine.length(); j++) {

                char letter = parsingLine.charAt(j);

                switch (letter) {
                    case '+':
                    case '-':
                    case '|':
                        points[i][j] = PointType.WALL;
                        break;
                    case 'S':
                        points[i][j] = PointType.START;
                        startPoint = new Point(j, i);
                        break;
                    case 'E':
                        points[i][j] = PointType.END;
                        endPoint = new Point(j, i);
                        break;
                    case ' ':
                        points[i][j] = PointType.PATH;
                        break;
                    default:
                        throw new InvalidMazeException("Niedowzolony znak w pliku");
                }
            }
        }
    }

    public void printWithPoint(Point point) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int y = 0; y < points.length; y++) {
            PointType[] row = points[y];
            for (int x = 0; x < row.length; x++) {
                if (new Point(x, y).equals(point)) {
                    stringBuilder.append('#');
                } else {
                    PointType pointType = row[x];
                    switch (pointType) {
                        case PATH:
                            stringBuilder.append(' ');
                            break;
                        case WALL:
                            stringBuilder.append('+');
                            break;
                        case START:
                            stringBuilder.append('S');
                            break;
                        case END:
                            stringBuilder.append('E');
                            break;
                    }
                }
            }
            stringBuilder.append('\n');
        }
        System.out.println(stringBuilder.toString());
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (PointType[] row : points) {
            for (PointType point : row) {
                switch (point) {
                    case WALL:
                        builder.append("+");
                        break;
                    case START:
                        builder.append("S");
                        break;
                    case END:
                        builder.append("E");
                        break;
                    default:
                        builder.append(" ");
                        break;
                }
            }

            builder.append("\n");
        }

        return builder.toString();
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public PointType[][] getPoints() {
        return points;
    }

    public PointType getPointTypeAt(Point point) {
        try {
            return points[point.getY()][point.getX()];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }
}