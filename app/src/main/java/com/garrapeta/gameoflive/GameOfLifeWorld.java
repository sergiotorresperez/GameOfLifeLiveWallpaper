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

    public int getLivingNeighbours(int x, int y) {
        int count = 0;

        // left up
        if (isAlive(getLeft(x), getUp(y))) {
            count++;
        }

        // up
        if (isAlive(x, getUp(y))) {
            count++;
        }

        // right up
        if (isAlive(getRight(x), getUp(y))) {
            count++;
        }

        // right
        if (isAlive(getRight(x), y)) {
            count++;
        }

        // right down
        if (isAlive(getRight(x), getDown(y))) {
            count++;
        }

        // down
        if (isAlive(x, getDown(y))) {
            count++;
        }

        // left down
        if (isAlive(getLeft(x), getDown(y))) {
            count++;
        }

        // left
        if (isAlive(getLeft(x), y)) {
            count++;
        }


        return count;
    }

    private int getLeft(int x) {
        int cols = getCols();
        return (cols + x - 1) % cols;
    }

    private int getUp(int y) {
        int rows = getRows();
        return (rows + y - 1) % rows;
    }

    private int getRight(int x) {
        int cols = getCols();
        return (x + 1) % cols;
    }

    private int getDown(int y) {
        int rows = getRows();
        return (y + 1) % rows;
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


}
