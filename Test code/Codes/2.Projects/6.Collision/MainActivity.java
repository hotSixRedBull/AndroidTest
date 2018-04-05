package com.tutorial.hotsixredbull.mygumi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
    Bitmap smiley;
    int smiley_x,smiley_y;
    int smileyWidth;
    Bitmap leftKey,rightKey;
    int leftKey_x,rightKey_x;
    int leftKey_y,rightKey_y;
    int width,height;
    int score;
    int button_width;

    Bitmap missileButton;
    int missileButton_x, missileButton_y;
    int missileWidth;
    int missile_middle;
    Bitmap missile;
    Bitmap enemyImage;

    int count;
    ArrayList<MyMissile> myM;
    ArrayList<Enemy> enemy;
    Bitmap screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));

        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();

        myM = new ArrayList<MyMissile>();
        enemy = new ArrayList<Enemy>();

        smiley = BitmapFactory.decodeResource(getResources(),R.drawable.smiley);
        int x  = width/8;
        int y = x;
        smiley = Bitmap.createScaledBitmap(smiley,x,y,true);
        smileyWidth = smiley.getWidth();
        smiley_x = width*1/9;
        smiley_y = width*7/9;

        leftKey = BitmapFactory.decodeResource(getResources(),R.drawable.left);
        leftKey_x = width*5/9;
        leftKey_y = width*8/9;
        button_width = width/6;
        leftKey = Bitmap.createScaledBitmap(leftKey,button_width,button_width,true);

        rightKey = BitmapFactory.decodeResource(getResources(),R.drawable.right);
        rightKey_x = width*7/9;
        rightKey_y = width*8/9;
        rightKey = Bitmap.createScaledBitmap(rightKey,button_width,button_width,true);

        missileButton = BitmapFactory.decodeResource(getResources(),R.drawable.missilebtn);
        missileButton = Bitmap.createScaledBitmap(missileButton,button_width,button_width,true);
        missileButton_x = width*1/11;
        missileButton_y = width*8/9;

        missile = BitmapFactory.decodeResource(getResources(),R.drawable.missile);
        missile = Bitmap.createScaledBitmap(missile,button_width/4,button_width/4,true);
        missileWidth = missile.getWidth();

        enemyImage = BitmapFactory.decodeResource(getResources(),R.drawable.enemy);
        enemyImage = Bitmap.createScaledBitmap(enemyImage,button_width,button_width,true);
        screen = BitmapFactory.decodeResource(getResources(),R.drawable.screen);
        screen = Bitmap.createScaledBitmap(screen,width,height,true);

    }

    class MyView extends View {
        MyView(Context context) {
            super(context);
            setBackgroundColor(Color.BLUE);
            gHandler.sendEmptyMessageDelayed(0, 50);
        }

        @Override
        synchronized public void onDraw(Canvas canvas) {
            Random r1 = new Random();
            int x = r1.nextInt(width);

            if (enemy.size() < 5)
                enemy.add(new Enemy(x, -100, 1));

            Paint p1 = new Paint();
            p1.setColor(Color.RED);
            p1.setTextSize(50);
            canvas.drawBitmap(screen, 0, 0, p1);
            canvas.drawText(Integer.toString(count), 0, 300, p1);
            canvas.drawText("점수 : " + Integer.toString(score), 0, 200, p1);
            canvas.drawBitmap(smiley, smiley_x, smiley_y, p1);
            canvas.drawBitmap(leftKey, leftKey_x, leftKey_y, p1);
            canvas.drawBitmap(rightKey, rightKey_x, rightKey_y, p1);
            canvas.drawBitmap(missileButton, missileButton_x, missileButton_y, p1);
            for (Enemy tmp : enemy)
                canvas.drawBitmap(enemyImage, tmp.x, tmp.y, p1);
            for (MyMissile tmp : myM)
                canvas.drawBitmap(missile, tmp.x, tmp.y, p1);

            moveMissile();
            moveEnemy();
            checkCollision();
            count++;
        }

        public void moveMissile() {
            for (int i = myM.size() - 1; i >= 0; i--) {
                myM.get(i).move();
            }
            for (int i = myM.size() - 1; i >= 0; i--) {
                if (myM.get(i).y < 0) myM.remove(i);
            }
        }

        public void moveEnemy() {
            for (int i = enemy.size() - 1; i >= 0; i--)
                enemy.get(i).move();
            for (int i = enemy.size() - 1; i >= 0; i--)
                if (enemy.get(i).y < 0) enemy.remove(i);
        }

        public void checkCollision() {
            for (int i = enemy.size() - 1; i >= 0; i--) {
                for (int j = myM.size() - 1; j >= 0; j--) {
                    if (myM.get(j).x + missile_middle > enemy.get(i).x
                            && myM.get(j).x + missile_middle < enemy.get(i).x + button_width
                            && myM.get(j).y > enemy.get(i).y
                            && myM.get(j).y < enemy.get(i).y + button_width) {
                        enemy.remove(i);
                        myM.get(j).y = -30;
                        score += 10;
                    }
                }
            }
        }

        Handler gHandler = new Handler() {
            public void handleMessage(Message msg) {
                invalidate();
                gHandler.sendEmptyMessageDelayed(0, 1000);
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
                smiley_x -= 20;
            if ((x > rightKey_x) && (x < rightKey_x + button_width) && (y > rightKey_y) && (y < leftKey_y + button_width))
                smiley_x += 20;
            if (event.getAction() == MotionEvent.ACTION_DOWN)
                if ((x > missileButton_x) && (x < missileButton_x + button_width) && (y > missileButton_y) && (y < missileButton_y + button_width)) {
                    if (myM.size() < 1) {
                        myM.add(new MyMissile(smiley_x + smileyWidth / 2 - missileWidth / 2, smiley_y));
                    }

                }
            return true;
        }

    }
}
