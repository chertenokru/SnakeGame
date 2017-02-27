/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.chertenok.snakegame.objects;

/**
 *
 * @author 13-ый
 */
public class Apple {
    public int posX;
    public int posY;
    public int width;
    public int height;
    
    public Apple(int width,int height)
    {
      this.width=width;
      this.height=height;
      //setNewPosition();
    }
    
    public void setNewPosition()
            {
                posX=(int)(Math.random()*(width-1));
                posY=(int)(Math.random()*(height-1));
                
            }
    
}
