package com.tutorial.hotsixredbull.mygumi;

public class AnswerBalloon {
    int x,y;
    int speed;

    AnswerBalloon(int x,int y,int speed){
        this.x = x;
        this.y = y;
        this.speed =speed;
    }

    public void move(){
        y+=speed;
    }
}
