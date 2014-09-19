package com.garrapeta.gameoflive;


public class GameOfLifeWorld {


    private boolean[][] matrix;

    public GameOfLifeWorld() {
        // TODO: fix this
        matrix = new boolean[1][1];
    }

    public void createMatrix(int cols, int rows) {
        matrix = new boolean[cols][rows];
        init();
    }

    private void init() {
        int cols = getCols();
        int rows = getRows();

        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                if (i % 2 == 0 && j % 3 == 0) {
                    setAlive(i, j, true);
                }
            }
        }
    }

    public int getCols() {
        return matrix.length;
    }

    public int getRows() {
        return matrix[0].length;
    }

    public boolean isAlive(int x, int y) {
        return matrix[x][y];
    }

    private void setAlive(int x, int y, boolean alive) {
        matrix[x][y] = alive;
    }

    public void onCellClicked(int x, int y) {
        setAlive(x, y, !isAlive(x, y));
    }

    public void step() {
        int cols = getCols();
        int rows = getRows();

        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                setAlive(i, j, !isAlive(i, j));
            }
        }
    }

    public int getAliveNeighbours(int x, int y) {
        int count = 0;

        int cols = getCols();
        int rows = getRows();

//        if (isAlive(x, y)) {
//            count++;
//        }

//        //left
//        if (isAlive((x - 1) % rows , y)) {
//            count++;
//        }

        //up
        if (isAlive(x , (y + 1) % cols)) {
            count++;
        }
//
//        //right
//        if (isAlive((x + 1) % rows , y)) {
//            count++;
//        }

//        //down
//        if (isAlive(x , (y - 1) % cols)) {
//            count++;
//        }

        return count;
    }
}
