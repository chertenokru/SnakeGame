/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.chertenok.snakegame.objects;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author 13-ый
 */
public class GameFieldDate {
    
    public class GameField
    {
       private int [][] field ;
    }
   private int[][] field;
   
  // private GameField
   private int levelCount=1;
  
   private final int WIDTH;
   private  final int HEIGHT;   
   private GameEffectObject e=null;
   private List<GameFielDateChangeEvent> listChangeListener=new ArrayList<GameFielDateChangeEvent>();
   
   public void addGameFieldDataChangeListener(GameFielDateChangeEvent listener )
   {
    listChangeListener.add(listener);
   }

    public int getField(int x,int y) {
        if ((x>=0)&(x<WIDTH)&(y>=0)&(y<HEIGHT))
        {
            return field[x][y];
        }
        else return -999;
    } 
    public boolean setField(int x,int y,int value)
    {
        boolean res=false;
        if ((x>=0)&(x<WIDTH)&(y>=0)&(y<HEIGHT))
        {
            if (setFieldCheckListeners(x, y, field[x][y], value)) 
            {
                field[x][y]=value;
                res=true;            
            }
            
        }
        
        return res;
    }
    
    private boolean setFieldCheckListeners(int x,int y,int oldValue,int newValue)
    {
        boolean res = true;
        for(GameFielDateChangeEvent cl:listChangeListener)
        {
            if (!cl.fieldDataChange(x, y, oldValue, newValue))
            {
                res=false;
            }
        }
        return res;
    }
    
    
    public int getWIDTH() {
        return WIDTH;
    }
    public int getHEIGHT() {
        return HEIGHT;
    }    
   
   
   public GameFieldDate(int width,int height)
   {
       this.WIDTH=width;
       this.HEIGHT=height;
       field=new int[width][height];       
   }
   
}

