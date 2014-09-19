package com.garrapeta.gameoflive;


public class GameOfLifeWorld {


    private boolean[][] matrix;

    public void createMatrix(int cols, int rows) {
        matrix = new boolean[cols][rows];
    }

    public int getCols() {
        return matrix.length;
    }

    public int getRows() {
        return matrix[0].length;
    }
}
