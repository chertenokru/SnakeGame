/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.chertenok.snakegame;

import ru.chertenok.snakegame.objects.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 13-ый
 */
public class SnakeGame extends JPanel implements ActionListener, SnakeCheckEvent, GameEffectComplitEvent {

    public static final int SCALE = 16;
    public static final int WIDTH = 40;
    public static final int HEIGHT = 40;
    public static final int SCOPEWIDTH = 250;
    public static final int BUTTON_WIDTH = 70;
    public static int SPEED = 350;
    public static final int S_WALL = 1;
    public static final int S_NULL = 0;
    public static final int S_APPLE = 2;
    public static final int S_SNAKE = 3;
    public static final int S_SNAKE_TOP = 4;
    public Timer timer;
    public Apple apple;
    public Snake snake;
    public Scope scope;
    public GameFieldDate field;
    //public int[][] stack;
    public int[][] buttonArray = new int[5][2];
    public static int BUTT_LEFT = Snake.DIR_LEFT;
    public static int BUTT_RIGHT = Snake.DIR_RIGHT;
    public static int BUTT_PAUSE = Snake.DIR_STOP;
    public static int BUTT_UP = Snake.DIR_UP;
    public static int BUTT_DOWN = Snake.DIR_DOWN;
    public static int X = 0;
    public static int Y = 1;
    public static int BUTT_X_MARGIN = 20;
    public static int BUTT_Y_MARGIN = 400;
    private List<GameEffectObject> gameEffectList = new ArrayList<GameEffectObject>();


    public SnakeGame() {
        field = new GameFieldDate(WIDTH, HEIGHT);
        scope = new Scope();
        scope.setLevel(1);
        snake = new Snake(WIDTH / 2, HEIGHT / 2, (WIDTH / 2) - 1, HEIGHT / 2, WIDTH, HEIGHT);
        snake.addCheckListener(this);
        buttonArray[BUTT_LEFT][X] = WIDTH * SCALE + BUTT_X_MARGIN;
        buttonArray[BUTT_LEFT][Y] = SCALE + BUTT_Y_MARGIN;
        buttonArray[BUTT_RIGHT][X] = WIDTH * SCALE + BUTT_X_MARGIN + (BUTTON_WIDTH * 2);
        buttonArray[BUTT_RIGHT][Y] = SCALE + BUTT_Y_MARGIN;
        buttonArray[BUTT_PAUSE][X] = WIDTH * SCALE + BUTT_X_MARGIN + (BUTTON_WIDTH);
        buttonArray[BUTT_PAUSE][Y] = SCALE + BUTT_Y_MARGIN;
        buttonArray[BUTT_UP][X] = WIDTH * SCALE + BUTT_X_MARGIN + (BUTTON_WIDTH);
        buttonArray[BUTT_UP][Y] = SCALE + BUTT_Y_MARGIN - (BUTTON_WIDTH);
        buttonArray[BUTT_DOWN][X] = WIDTH * SCALE + BUTT_X_MARGIN + (BUTTON_WIDTH);
        buttonArray[BUTT_DOWN][Y] = SCALE + BUTT_Y_MARGIN + (BUTTON_WIDTH);


        apple = new Apple(WIDTH, HEIGHT);
        loadLevel(scope.getLevel());
        timer = new Timer(SPEED, this);
        this.addKeyListener(new Keyboard());
        this.addMouseListener(new Mouse());
        this.setFocusable(true);
        timer.start();
    }

    public void paint(Graphics g) {
        g.setColor(new Color(5, 50, 10));
        g.fillRect(0, 0, WIDTH * SCALE + SCOPEWIDTH, HEIGHT * SCALE);
        g.setColor(Color.GRAY);

        for (int x = 0; x < WIDTH; x++)
            for (int y = 0; y < HEIGHT; y++) {
                if (field.getField(x, y) == S_NULL) {
                    g.setColor(Color.DARK_GRAY);
                    g.fill3DRect(x * SCALE, y * SCALE, SCALE, SCALE, true);
                }
                if (field.getField(x, y) == S_WALL) {
                    g.setColor(Color.LIGHT_GRAY);
                    g.fill3DRect(x * SCALE, y * SCALE, SCALE, SCALE, true);
                }
                if (field.getField(x, y) == S_APPLE) {
                    g.setColor(Color.red);
                    g.fill3DRect(x * SCALE, y * SCALE, SCALE, SCALE, true);
                }
                if (field.getField(x, y) == S_SNAKE) {
                    g.setColor(Color.orange);
                    g.fill3DRect(x * SCALE, y * SCALE, SCALE, SCALE, true);
                }
                if (field.getField(x, y) == S_SNAKE_TOP) {
                    g.setColor(Color.GREEN);
                    g.fill3DRect(x * SCALE, y * SCALE, SCALE, SCALE, true);
                }

            }

        g.setColor(new Color(255, 0, 0));

        //  g.fillRect(apple.posX*SCALE+1,apple.posY*SCALE+1, SCALE-1, SCALE-1);
        g.setColor(Color.GREEN);
        g.drawString("Очки: " + scope.getScope(), WIDTH * SCALE + 30, SCALE + 20);
        g.drawString("Яблок: " + scope.getAppleCount(), WIDTH * SCALE + 30, SCALE + 60);
        g.drawString("Пробег: " + scope.getMoveCount(), WIDTH * SCALE + 30, SCALE + 100);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Tahome", Font.BOLD, 15));

        g.drawString("Длина: " + snake.lenght, WIDTH * SCALE + 30 + 1, SCALE + 140 + 1);
        g.setColor(Color.GREEN);

        g.drawString("Длина: " + snake.lenght, WIDTH * SCALE + 30, SCALE + 140);
        g.drawString("Скорость: " + (SPEED + 1 - timer.getDelay())
                , WIDTH * SCALE + 30, SCALE + 180);
        // g.drawString("x: "+apple.posX+" y: "+apple.posY,WIDTH*SCALE+30 ,SCALE+220);

        g.setColor(Color.DARK_GRAY);
        for (int i = 0; i < buttonArray.length; i++)
            drawButton(i, g);
        for (GameEffectObject ge : gameEffectList) {
            ge.draw(g);
        }
        //  System.out.println("paint");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        //  snake = new SnakeGame()

        mainFrame.setSize((WIDTH + 2) * SCALE + SCOPEWIDTH, (HEIGHT + 2) * SCALE);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.add(new SnakeGame());

        mainFrame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        drawSnake(false);
        if (snake.move()) scope.move();
        if (field.getField(snake.snakeX[0], snake.snakeY[0]) == S_WALL) {
            //  snake.stop();
            snake.setDirection(snake.DIR_STOP);
        }
        int sp = (SPEED - scope.getScope());
        if (sp > 30)
            timer.setDelay(sp);
        if ((snake.snakeX[0] == apple.posX) & (snake.snakeY[0] == apple.posY)) {

            snake.addLength();
            scope.setLevel(scope.getLevel() + 1);
            loadLevel(scope.getLevel());
            gameEffectList.add(new GameEffectObject(WIDTH / 2 * SCALE, HEIGHT / 2 * SCALE, GameEffectObject.EO_LEVEL, this));
            scope.addApple();
            newApple(false);
            gameEffectList.add(new GameEffectObject(snake.snakeX[0] * SCALE, snake.snakeY[0] * SCALE, GameEffectObject.EO_POPUPINFO, this));


        }
        //apple.setNewPosition();
        drawSnake(true);

        repaint();
        //  System.out.println("timer");
    }

    public void newApple(boolean start) {
        if (!start) field.setField(apple.posX, apple.posY, S_NULL);
        boolean b = true;
        do {
            apple.setNewPosition();
            if (field.getField(apple.posX, apple.posY) != S_NULL) b = false;
            else b = true;
        }
        while (!b);
        field.setField(apple.posX, apple.posY, S_APPLE);
    }

    private void drawSnake(boolean draw) {
        int brush;
        if (draw) brush = S_SNAKE;
        else brush = S_NULL;

        for (int i = 0; i < snake.lenght; i++) {
            field.setField(snake.snakeX[i], snake.snakeY[i], brush);
        }
        if (draw) field.setField(snake.snakeX[0], snake.snakeY[0], S_SNAKE_TOP);
    }


    private void loadLevel(int i) {
        for (int x = 0; x < WIDTH / 2; x++) {
            field.setField(x, 0, S_WALL);
            field.setField(x, HEIGHT - 1, S_WALL);
            field.setField(WIDTH - 1 - x, 0, S_WALL);
            field.setField(WIDTH - 1 - x, HEIGHT - 1, S_WALL);


        }
        for (int y = 0; y < HEIGHT / 2; y++) {
            field.setField(0, y, S_WALL);
            field.setField(WIDTH - 1, y, S_WALL);
            field.setField(0, HEIGHT - 1 - y, S_WALL);
            field.setField(HEIGHT - 1, HEIGHT - 1 - y, S_WALL);


        }
        int size = (int) ((Math.random() * 9) + 1);
        int beg = 0;
        beg = (int) ((Math.random() * HEIGHT) - size + 1);
        for (int x = 0; x < size; x++) {
            field.setField(x + beg, 0, S_NULL);
            field.setField(x + beg, WIDTH - 1, S_NULL);
        }


        size = (int) ((Math.random() * 9) + 1);
        beg = (int) ((Math.random() * WIDTH) - size + 1);
        for (int x = 0; x < size; x++) {
            field.setField(WIDTH - 1, x + beg, S_NULL);
            field.setField(0, x + beg, S_NULL);
        }


        newApple(true);
    }

    @Override
    public boolean checkNextXY(int x, int y) {
        boolean res = true;
        if ((field.getField(x, y) == S_WALL) | (field.getField(x, y) == S_SNAKE)) {
            res = false;
        }
        ;
        return res;

    }

    private void drawButton(int i, Graphics g) {
        int SHIFT = 6;
        g.setColor(Color.DARK_GRAY);
        g.fill3DRect(buttonArray[i][X], buttonArray[i][Y], BUTTON_WIDTH, BUTTON_WIDTH, true);
        if (i == snake.getDirection())
            g.setColor(Color.YELLOW);
        else g.setColor(Color.WHITE);
        Polygon p = new Polygon();
        if (i == BUTT_LEFT) {
            p.addPoint(buttonArray[i][X] + SHIFT, buttonArray[i][Y] + (BUTTON_WIDTH / 2));
            p.addPoint(buttonArray[i][X] + BUTTON_WIDTH - SHIFT, buttonArray[i][Y] + SHIFT);
            p.addPoint(buttonArray[i][X] + BUTTON_WIDTH - SHIFT, buttonArray[i][Y] + BUTTON_WIDTH - SHIFT);
            g.drawPolygon(p);
        }
        if (i == BUTT_RIGHT) {
            p.addPoint(buttonArray[i][X] - SHIFT + BUTTON_WIDTH, buttonArray[i][Y] + (BUTTON_WIDTH / 2));
            p.addPoint(buttonArray[i][X] + SHIFT, buttonArray[i][Y] + SHIFT);
            p.addPoint(buttonArray[i][X] + SHIFT, buttonArray[i][Y] + BUTTON_WIDTH - SHIFT);
            g.drawPolygon(p);
        }
        if (i == BUTT_UP) {
            p.addPoint(buttonArray[i][X] + (BUTTON_WIDTH / 2), buttonArray[i][Y] + SHIFT);
            p.addPoint(buttonArray[i][X] + SHIFT, buttonArray[i][Y] - SHIFT + BUTTON_WIDTH);
            p.addPoint(buttonArray[i][X] - SHIFT + (BUTTON_WIDTH), buttonArray[i][Y] + BUTTON_WIDTH - SHIFT);
            g.drawPolygon(p);
        }
        if (i == BUTT_DOWN) {
            p.addPoint(buttonArray[i][X] + (BUTTON_WIDTH / 2), buttonArray[i][Y] - SHIFT + BUTTON_WIDTH);
            p.addPoint(buttonArray[i][X] + SHIFT, buttonArray[i][Y] + SHIFT);
            p.addPoint(buttonArray[i][X] - SHIFT + (BUTTON_WIDTH), buttonArray[i][Y] + SHIFT);
            g.drawPolygon(p);
        }
        if (i == BUTT_PAUSE) {
            p.addPoint(buttonArray[i][X] + (BUTTON_WIDTH / 4), buttonArray[i][Y] + SHIFT);
            p.addPoint(buttonArray[i][X] + (BUTTON_WIDTH / 4), buttonArray[i][Y] - SHIFT + BUTTON_WIDTH);
            //    p.addPoint(buttonArray[i][X]+SHIFT,buttonArray[i][Y]+SHIFT);
            //   p.addPoint(buttonArray[i][X]-SHIFT+(BUTTON_WIDTH),buttonArray[i][Y]+SHIFT);
            g.drawPolygon(p);
            Polygon p1 = new Polygon();

            p1.addPoint(buttonArray[i][X] + BUTTON_WIDTH - (BUTTON_WIDTH / 4), buttonArray[i][Y] + SHIFT);
            p1.addPoint(buttonArray[i][X] + BUTTON_WIDTH - (BUTTON_WIDTH / 4), buttonArray[i][Y] - SHIFT + BUTTON_WIDTH);
            g.drawPolygon(p1);

        }
    }

    @Override
    public void complite(GameEffectObject o) {
        gameEffectList.remove(o);
    }

    private class Keyboard extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent keyEvent) {
            int key = keyEvent.getKeyCode();
            if ((key == KeyEvent.VK_RIGHT) & (snake.getDirection() != snake.DIR_LEFT))
                snake.setDirection(snake.DIR_RIGHT);
            if ((key == KeyEvent.VK_DOWN) & (snake.getDirection() != snake.DIR_UP)) snake.setDirection(snake.DIR_DOWN);
            if ((key == KeyEvent.VK_LEFT) & (snake.getDirection() != snake.DIR_RIGHT))
                snake.setDirection(snake.DIR_LEFT);
            if ((key == KeyEvent.VK_UP) & (snake.getDirection() != snake.DIR_DOWN)) snake.setDirection(snake.DIR_UP);

        }
    }

    private class Mouse extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println(e.getX());
            if ((e.getX() > (WIDTH * SCALE + 50)) & (e.getX() < (WIDTH * SCALE + 50 + BUTTON_WIDTH)) &
                    (e.getY() > (SCALE + 400) & (e.getY() < (SCALE + 400 + BUTTON_WIDTH))))
                snake.setDirection(Snake.DIR_LEFT);

            if ((e.getX() > (WIDTH * SCALE + 50 + (BUTTON_WIDTH * 2))) & (e.getX() < (WIDTH * SCALE + 50 + (BUTTON_WIDTH * 2) + BUTTON_WIDTH)) &
                    (e.getY() > (SCALE + 400) & (e.getY() < (SCALE + 400 + BUTTON_WIDTH))))
                snake.setDirection(Snake.DIR_RIGHT);
            if ((e.getX() > (WIDTH * SCALE + 50 + (BUTTON_WIDTH))) & (e.getX() < (WIDTH * SCALE + 50 + (BUTTON_WIDTH) + BUTTON_WIDTH)) &
                    (e.getY() > (SCALE + 400) & (e.getY() < (SCALE + 400 + BUTTON_WIDTH))))
                snake.setDirection(Snake.DIR_STOP);

            if ((e.getX() > (WIDTH * SCALE + 50 + (BUTTON_WIDTH))) & (e.getX() < (WIDTH * SCALE + 50 + (BUTTON_WIDTH) + BUTTON_WIDTH)) &
                    (e.getY() > (SCALE + 400 - BUTTON_WIDTH) & (e.getY() < (SCALE + 400)))) {
                snake.setDirection(Snake.DIR_UP);

            }

            if ((e.getX() > (WIDTH * SCALE + 50 + (BUTTON_WIDTH))) & (e.getX() < (WIDTH * SCALE + 50 + (BUTTON_WIDTH) + BUTTON_WIDTH)) &
                    (e.getY() > (SCALE + 400 + BUTTON_WIDTH) & (e.getY() < (SCALE + 400 + (BUTTON_WIDTH * 2)))))
                snake.setDirection(Snake.DIR_DOWN);

            //     g.fill3DRect(WIDTH*SCALE+50, SCALE+400, BUTTON_WIDTH,BUTTON_WIDTH, true);
            //g.fill3DRect(WIDTH*SCALE+50+(BUTTON_WIDTH),SCALE+400 , BUTTON_WIDTH, BUTTON_WIDTH, true);
            //  g.fill3DRect(WIDTH*SCALE+50+(BUTTON_WIDTH*2),SCALE+400 , BUTTON_WIDTH, BUTTON_WIDTH, true);
            ////  g.fill3DRect(WIDTH*SCALE+50+(BUTTON_WIDTH),SCALE+400-(BUTTON_WIDTH) , BUTTON_WIDTH, BUTTON_WIDTH, true);
            ////   g.fill3DRect(WIDTH*SCALE+50+(BUTTON_WIDTH),SCALE+400+(BUTTON_WIDTH) , BUTTON_WIDTH, BUTTON_WIDTH, true);


        }
    }

}
