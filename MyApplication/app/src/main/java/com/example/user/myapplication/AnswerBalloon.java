package com.example.user.myapplication;

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
