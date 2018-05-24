package com.grabarski.mateusz;

/**
 * Created by Mateusz Grabarski on 21.05.2018.
 */
public class Main {

    public static void main(String[] args) {
        Maze maze = new Maze("maze");

        System.out.println(maze.toString());

        new MazeSolver(maze).solve();
    }
}