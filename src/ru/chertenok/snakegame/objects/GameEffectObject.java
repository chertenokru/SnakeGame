/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.chertenok.snakegame.objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;

/**
 *
 * @author 13-ый
 */
public class GameEffectObject implements ActionListener{
    private int x;
    private int y;
    private int type;
    private int scroll=0;
    private int timeScroll=100;
    private int maxScroll=20;
    private Timer timer;
    private List<GameEffectComplitEvent> compliteListener = new ArrayList<GameEffectComplitEvent>();
    public void addCompliteListener(GameEffectComplitEvent e)
    {
        compliteListener.add(e);
    }
    
    public static final int EO_POPUPINFO = 1;
    public static final int EO_STOP = 2;
    public static final int EO_LEVEL =3;
    private boolean b;

    public GameEffectObject(int x, int y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;
        timer = new Timer(timeScroll, this);
        timer.start();
   }

   public GameEffectObject(int x, int y, int type,GameEffectComplitEvent onCompliteListener)
   {
          this(x, y, type);
                
       
        addCompliteListener(onCompliteListener);
              
       
   }
  
    public void draw(Graphics g)
    {
         g.setFont(new Font("Tahome", Font.BOLD, 15));
        if (type == EO_POPUPINFO)
        {
        if (b) g.setColor(Color.red); else g.setColor(Color.yellow);
        b=!b;
        g.drawString("+1 яблоко", x+scroll, y-scroll);
        g.drawString("+5 опыт", x+10+scroll, y-10-scroll);
        } else if (type == EO_STOP)
        {
            g.setColor(Color.red);
            g.drawOval(x+scroll, y-scroll, 5, 5);
        }
               
       else if (type == EO_LEVEL)
        {
             b=!b;
             g.setFont(new Font(Font.MONOSPACED,Font.BOLD,50));
        g.drawString("+1 level ", x+scroll, y-scroll);
       
        }
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
       if (scroll<=maxScroll) scroll++; else 
       {
           timer.stop();
           timer.removeActionListener(this);           
           timer=null;
           for(GameEffectComplitEvent ec:compliteListener)
           {
               ec.complite(this);              
           }
           compliteListener.clear();
       }
    }
    
    
}
