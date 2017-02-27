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
public interface GameFielDateChangeEvent {
    public boolean fieldDataChange(int x,int y,int oldValue, int newValue);
  
    
}
