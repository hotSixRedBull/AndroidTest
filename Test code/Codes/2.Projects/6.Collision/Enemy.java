package com.tutorial.hotsixredbull.mygumi;

public class Enemy {
    int x,y;
    int enemySpeed=15;
    int dir;

    Enemy(int x,int y,int dir){
        this.x = x;
        this.y = y;
        this.dir = dir;
    }
    public void move(){
        if(dir==0)
            x-=enemySpeed;
        else
            x+=enemySpeed;
    }
}
