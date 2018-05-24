package com.grabarski.mateusz.controllers;

import com.grabarski.mateusz.Maze;
import com.grabarski.mateusz.PointType;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

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
    }
}