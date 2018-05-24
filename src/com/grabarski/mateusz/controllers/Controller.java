package com.grabarski.mateusz.controllers;

import com.grabarski.mateusz.Maze;
import com.grabarski.mateusz.MazeSolver;
import com.grabarski.mateusz.Point;
import com.grabarski.mateusz.PointType;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.List;

/**
 * Created by Mateusz Grabarski on 24.05.2018.
 */
public class Controller {

    @FXML
    private GridPane mazeGridPane;
    @FXML
    private Button up;
    @FXML
    private Button down;
    @FXML
    private Button left;
    @FXML
    private Button right;
    @FXML
    private Button resolve;

    private Maze maze;

    public void initialize() {
        maze = new Maze("maze");

        loadMaze();

        right.setOnAction(event -> moveRight());
        left.setOnAction(event -> moveLeft());
        up.setOnAction(event -> moveUp());
        down.setOnAction(event -> moveDown());
        resolve.setOnAction(event -> {
            MazeSolver mazeSolver = new MazeSolver(maze);

            List<Point> points = mazeSolver.solve();

            new Thread(() ->
                    points.forEach(s -> {
                        maze.setCurrentPoint(s);
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Platform.runLater(() -> loadMaze());
                    })).start();
        });
    }

    private void loadMaze() {
        PointType[][] mazePoints = maze.getPoints();

        for (int y = 0; y < mazePoints.length; y++) {
            PointType[] row = mazePoints[y];

            for (int x = 0; x < row.length; x++) {
                Button button = new Button();

                PointType currentPoint = mazePoints[y][x];

                switch (currentPoint) {
                    case START:
                        button.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
                        break;
                    case END:
                        button.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
                        break;
                    case WALL:
                        button.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
                        break;
                    case PATH:
                        button.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                        break;
                }

                mazeGridPane.add(button, x, y);
            }
        }

        loadCurrentPosition();
    }

    private void loadCurrentPosition() {
        Point startPoint = maze.getStartPoint();

        Button startBtn = new Button();
        startBtn.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));

        mazeGridPane.add(startBtn, startPoint.getX(), startPoint.getY());
    }

    private void moveRight() {
        tryMove(new Point(maze.getStartPoint().getX() + 1, maze.getStartPoint().getY()));
    }

    private void moveLeft() {
        tryMove(new Point(maze.getStartPoint().getX() - 1, maze.getStartPoint().getY()));
    }

    private void moveDown() {
        tryMove(new Point(maze.getStartPoint().getX(), maze.getStartPoint().getY() + 1));
    }

    private void moveUp() {
        tryMove(new Point(maze.getStartPoint().getX(), maze.getStartPoint().getY() - 1));
    }

    private void tryMove(Point checkingPoint) {
        PointType checkingPointType = maze.getPointTypeAt(checkingPoint);

        if (checkingPointType == PointType.PATH || checkingPointType == PointType.END) {
            maze.setCurrentPoint(checkingPoint);
        }

        loadMaze();
    }
}