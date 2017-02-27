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
public class Scope {
    
    private int scope = 0;
    private int moveCount = 0;
    private int level = 0;

    public int getMoveCount() {
        return moveCount;
    }

    public void setMoveCount(int moveCount) {
        this.moveCount = moveCount;
    }
    

    public int getScope() {
        return scope;
    }

    public void setScope(int scope) {
        this.scope = scope;
    }

    public int getAppleCount() {
        return appleCount;
    }

    public void setAppleCount(int appleCount) {
        this.appleCount = appleCount;
    }
    private final int appleScope=5;
    private int appleCount=0;
    
    public Scope()
    {
        scope=0;
        
    }
    
    public int addApple()
    {
        appleCount++;
        scope=scope+appleScope;
        return scope;
    }
    
    public void move(){
        moveCount++;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    }
