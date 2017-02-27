/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.chertenok.snakegame.objects;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 13-ый
 */


public class Snake {
    public static final int DIR_RIGHT = 0;
    public static final int DIR_DOWN = 1;
    public static final int DIR_UP = 3;
    public static final int DIR_LEFT = 2;
    public static final int DIR_STOP = 4;

    private int direction = DIR_RIGHT;

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
        if (direction == DIR_STOP) {
            //      gameEffectList.add(new GameEffectObject(snake.snakeX[0]*SCALE, snake.snakeY[0]*SCALE, GameEffectObject.EO_STOP,null));

        }
    }

    public int lenght = 2;
    public int snakeX[];
    public int snakeY[];
    public int width;
    public int height;
    private List<SnakeCheckEvent> listCheckListener = new ArrayList<SnakeCheckEvent>();

    public void addCheckListener(SnakeCheckEvent cl) {
        listCheckListener.add(cl);
    }

    private boolean checkXY(int x, int y) {
        boolean res = true;
        for (SnakeCheckEvent cl : listCheckListener) {
            res = cl.checkNextXY(x, y);
        }
        for (int i = 1; i < lenght; i++)
            if ((snakeX[i] == x) & (snakeY[i] == y)) res = false;
        if (!res) direction = DIR_STOP;
        return res;
    }

    public Snake(int x0, int y0, int x1, int y1, int width, int height) {
        snakeX = new int[width * height];
        snakeY = new int[width * height];
        this.width = width;
        this.height = height;
        snakeX[0] = x0;
        snakeY[0] = y0;
        snakeX[1] = x1;
        snakeY[1] = y1;


    }

    public boolean move() {
        int newX = snakeX[0];
        int newY = snakeY[0];
        if (direction == DIR_RIGHT) newX++;
        if (direction == DIR_DOWN) newY++;
        if (direction == DIR_LEFT) newX--;
        if (direction == DIR_UP) newY--;

        if (newX < 0) newX = width - 1;
        if (newX > width - 1) newX = 0;
        if (newY < 0) newY = height - 1;
        if (newY > height - 1) newY = 0;

        if (!checkXY(newX, newY)) direction = DIR_STOP;

        if (direction == DIR_STOP) return false;
        for (int d = lenght - 1; d > 0; d--) {
            snakeX[d] = snakeX[d - 1];
            snakeY[d] = snakeY[d - 1];

        }
        snakeX[0] = newX;
        snakeY[0] = newY;


        return true;
    }

    public void addLength() {
        lenght++;
        snakeX[lenght - 1] = snakeX[lenght - 2];
        snakeY[lenght - 1] = snakeY[lenght - 2];
    }


}
