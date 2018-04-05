package com.tutorial.hotsixredbull.mygumi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Bitmap basket;
    int basket_x, basket_y;
    int basketWidth,basketHeight;
    Bitmap leftKey,rightKey;
    int leftKey_x,rightKey_x;
    int leftKey_y,rightKey_y;
    int width,height;
    int score;
    int button_width;

    Bitmap balloonimg;
    int balloonWidth;
    int balloonHeight;

    AnswerBalloon answerBalloon;
    int count;

    ArrayList<Balloon> balloon;
    Bitmap screen;

    int number1,number2;
    int answer;
    int[] wrongNumber = new int[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));

        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;

        balloon = new ArrayList<Balloon>();

        basket = BitmapFactory.decodeResource(getResources(),R.drawable.baskethand);
        int x  = width/8;
        int y = x;
        basket = Bitmap.createScaledBitmap(basket,x,y,true);
        basketWidth = basket.getWidth();
        basketHeight = basket.getHeight();
        basket_x = width*1/9;
        basket_y = height*6/9;

        leftKey = BitmapFactory.decodeResource(getResources(),R.drawable.leftarrow);
        leftKey_x = width*5/9;
        leftKey_y = height*7/9;
        button_width = width/6;
        leftKey = Bitmap.createScaledBitmap(leftKey,button_width,button_width,true);

        rightKey = BitmapFactory.decodeResource(getResources(),R.drawable.rightarrow);
        rightKey_x = width*7/9;
        rightKey_y = height*7/9;
        rightKey = Bitmap.createScaledBitmap(rightKey,button_width,button_width,true);

        balloonimg = BitmapFactory.decodeResource(getResources(),R.drawable.coin);
        balloonimg = Bitmap.createScaledBitmap(balloonimg,button_width,button_width,true);
        balloonWidth = balloonimg.getWidth();
        balloonHeight = balloonimg.getHeight();

        screen = BitmapFactory.decodeResource(getResources(),R.drawable.map);
        screen = Bitmap.createScaledBitmap(screen,width,height,true);


        Random r1 = new Random();
        int xx = r1.nextInt(width);
        answerBalloon = new AnswerBalloon(x,0,5);

    }

    class MyView extends View {
        MyView(Context context) {
            super(context);
            setBackgroundColor(Color.BLUE);
            gHandler.sendEmptyMessageDelayed(0, 1000);
            makeQuestion();
        }

        @Override
        synchronized public void onDraw(Canvas canvas) {

            if (balloon.size() < 5) {
                Random r1 = new Random();
                int x = r1.nextInt(width);
                int y = r1.nextInt(height / 4);
                balloon.add(new Balloon(x, -y, 5));
            }
            Paint p1 = new Paint();
            p1.setColor(Color.BLACK);
            p1.setTextSize(width/14);
            canvas.drawBitmap(screen, 0, 0, p1);
            canvas.drawText(Integer.toString(count), 0, 200, p1);
            canvas.drawText("문제 : " + Integer.toString(number1)+"+"+Integer.toString(number2), 0, height/6, p1);
            canvas.drawText("점수 : " + Integer.toString(score), 0, 100, p1);
            canvas.drawBitmap(basket, basket_x, basket_y, p1);
            canvas.drawBitmap(leftKey, leftKey_x, leftKey_y, p1);
            canvas.drawBitmap(rightKey, rightKey_x, rightKey_y, p1);
            for (Balloon tmp : balloon)
                canvas.drawBitmap(balloonimg, tmp.x, tmp.y, p1);
            for(int i=balloon.size()-1;i>=0;i--)
                canvas.drawText(Integer.toString(wrongNumber[i]),balloon.get(i).x+balloonWidth/6,balloon.get(i).y+balloonWidth*2/3,p1);

            canvas.drawBitmap(balloonimg,answerBalloon.x,answerBalloon.y,p1);
            canvas.drawText(Integer.toString(answer),answerBalloon.x+balloonWidth/6,answerBalloon.y+balloonWidth*2/3,p1);

            if(answerBalloon.y>height) answerBalloon.y = -50;
            moveBalloon();
            checkCollision();
            count++;
        }

        public void makeQuestion(){
            Random r1 = new Random();
            int x = r1.nextInt(99)+1;
            number1 = x;
            x = r1.nextInt(99)+1;
            number2 = x;
            answer = number1+number2;

            int counter=0;
            for(int i=0;i<5;i++){
                x = r1.nextInt(197)+1;
                while(x==answer){
                    x = r1.nextInt(197)+1;
                }
                wrongNumber[i] = x;
            }

        }

        public void moveBalloon() {
            for (int i = balloon.size() - 1; i >= 0; i--) {
                balloon.get(i).move();
            }
            for (int i = balloon.size() - 1; i >= 0; i--) {
                if (balloon.get(i).y > height) balloon.get(i).y=-100;
            }
            answerBalloon.move();
        }

        public void checkCollision() {
            for (int i = balloon.size() - 1; i >= 0; i--) {
                    if (balloon.get(i).x + balloonHeight > balloon.get(i).x
                            && balloon.get(i).x + balloonHeight < balloon.get(i).x + button_width
                            && balloon.get(i).y > balloon.get(i).y
                            && balloon.get(i).y < balloon.get(i).y + button_width) {
                        balloon.remove(i);
                        balloon.get(i).y = -30;
                        score += 10;
                    }
            }

            if(answerBalloon.x+balloonWidth/2>basket_x&& answerBalloon.x+balloonWidth/2 < basket_x+basketWidth
                    &&answerBalloon.y+balloonHeight/2>basket_y&&answerBalloon.y+balloonHeight/2<basket_y+basketHeight){
                score += 30;
                makeQuestion();
                Random r1 = new Random();
                int xx = r1.nextInt(width-button_width);
                answerBalloon.x = xx;
                xx = r1.nextInt(300);
                answerBalloon.y = -xx;
            }
        }

        Handler gHandler = new Handler() {
            public void handleMessage(Message msg) {
                invalidate();
                gHandler.sendEmptyMessageDelayed(0, 30);
            }
        };

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            int x = 0, y = 0;
            if (event.getAction() == MotionEvent.ACTION_DOWN
                    || event.getAction() == MotionEvent.ACTION_MOVE) {
                x = (int) event.getX();
                y = (int) event.getY();
            }
            if ((x > leftKey_x) && (x < leftKey_x + button_width) && (y > leftKey_y) && (y < leftKey_y + button_width))
                basket_x -= 20;
            if ((x > rightKey_x) && (x < rightKey_x + button_width) && (y > rightKey_y) && (y < leftKey_y + button_width))
                basket_x += 20;
            return true;
        }

    }
}
