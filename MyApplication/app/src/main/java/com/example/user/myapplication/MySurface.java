package com.example.user.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Random;

public class MySurface extends SurfaceView implements SurfaceHolder.Callback {
    MyThread mThread;
    SurfaceHolder mHolder;
    Context mContext;

    Bitmap basket;
    int basket_x, basket_y;
    int basketWidth, basketHeight;
    Bitmap leftKey, rightKey;
    int leftKey_x, rightKey_x;
    int leftKey_y, rightKey_y;
    int width, height;
    int score;
    int button_width;
    int basket_speed;
    int balloon_speed;
    int oNumber;
    int xNumber;

    Bitmap balloonimg;
    int balloonWidth;
    int balloonHeight;

    AnswerBalloon answerBalloon;

    Bitmap resultShow;
    int resultShow_x, resultShow_y;

    int menuOk = 1;
    Bitmap menuButton;
    int menuButton_x, menuButton_y;
    Bitmap helpButton;
    int helpButton_x, helpButton_y;
    Bitmap closeButton;
    int closeButton_x, closeButton_y;
    Bitmap applyButton;
    int applyButton_x, applyButton_y;

    Bitmap plusicon, minusicon, multiicon;
    int plusicon_x, plusicon_y;
    int minusicon_x, minusicon_y;
    int multiicon_x, multiicon_y;

    Bitmap time30, time60, time120;
    int time30_x, time30_y;
    int time60_x, time60_y;
    int time120_x, time120_y;

    Bitmap level1, level2, level3;
    int level1_x, level1_y;
    int level2_x, level2_y;
    int level3_x, level3_y;

    Bitmap scoreImage;
    int scoreImage_x, scoreImage_y;
    int score_count;
    int scoreImageOk;
    int count = 3000;

    int level = 1;
    int timeValue = 1;
    int operator;

    ArrayList<Balloon> balloon;
    Bitmap menuTitle;

    int number1, number2;
    int answer;
    int[] wrongNumber = new int[5];

    SoundPool sPool;
    int dingdongdang, taeng;

    public MySurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        mThread = new MyThread(holder, context);
        InitApp();
        setFocusable(true);
    }

    private void InitApp() {
        Display display = ((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;

        basket_speed = width / 40;
        balloon_speed = width / 140;

        balloon = new ArrayList<Balloon>();
        button_width = width / 6;
        basket = BitmapFactory.decodeResource(getResources(), R.drawable.handpoint);
        int x = width / 4;
        int y = x;
        basket = Bitmap.createScaledBitmap(basket, x, y, true);
        basketWidth = basket.getWidth();
        basketHeight = basket.getHeight();
        basket_x = width * 1 / 9;
        basket_y = height * 6 / 9 + button_width / 2;

        leftKey = BitmapFactory.decodeResource(getResources(), R.drawable.leftarrow);
        leftKey_x = button_width / 2;
        leftKey_y = height - button_width * 2;
        button_width = width / 6;
        leftKey = Bitmap.createScaledBitmap(leftKey, button_width, button_width, true);

        rightKey = BitmapFactory.decodeResource(getResources(), R.drawable.rightarrow);
        rightKey_x = width - button_width - button_width / 2;
        rightKey_y = height - button_width * 2;
        rightKey = Bitmap.createScaledBitmap(rightKey, button_width, button_width, true);

        balloonimg = BitmapFactory.decodeResource(getResources(), R.drawable.coin);
        balloonimg = Bitmap.createScaledBitmap(balloonimg, button_width, button_width * 5 / 4, true);
        balloonWidth = balloonimg.getWidth();
        balloonHeight = balloonimg.getHeight();

        menuTitle = BitmapFactory.decodeResource(getResources(), R.drawable.menutitle);
        menuTitle = Bitmap.createScaledBitmap(menuTitle, width * 3 / 5, height / 7, true);

        Random r1 = new Random();
        int xx = r1.nextInt(width);
        answerBalloon = new AnswerBalloon(x, 0, balloon_speed);

        menuButton = BitmapFactory.decodeResource(getResources(), R.drawable.menubutton);
        menuButton = Bitmap.createScaledBitmap(menuButton, button_width, button_width, true);
        menuButton_x = width - button_width * 5 / 4;
        menuButton_y = height / 30;

        helpButton = BitmapFactory.decodeResource(getResources(), R.drawable.help);
        helpButton = Bitmap.createScaledBitmap(helpButton, button_width, button_width, true);
        helpButton_x = width - button_width * 5 / 4;
        helpButton_y = height / 8;

        plusicon = BitmapFactory.decodeResource(getResources(), R.drawable.plusicon);
        plusicon = Bitmap.createScaledBitmap(plusicon, button_width, button_width, true);
        plusicon_x = button_width;
        plusicon_y = height / 5;

        minusicon = BitmapFactory.decodeResource(getResources(), R.drawable.minusicon);
        minusicon = Bitmap.createScaledBitmap(minusicon, button_width, button_width, true);
        minusicon_x = button_width * 9 / 4;
        minusicon_y = height / 5;

        multiicon = BitmapFactory.decodeResource(getResources(), R.drawable.multiicon);
        multiicon = Bitmap.createScaledBitmap(multiicon, button_width, button_width, true);
        multiicon_x = button_width * 7 / 2;
        multiicon_y = height / 5;

        level1 = BitmapFactory.decodeResource(getResources(), R.drawable.level1);
        level1 = Bitmap.createScaledBitmap(level1, button_width, button_width, true);
        level1_x = plusicon_x;
        level1_y = plusicon_y + button_width * 2;

        level2 = BitmapFactory.decodeResource(getResources(), R.drawable.level2);
        level2 = Bitmap.createScaledBitmap(level2, button_width, button_width, true);
        level2_x = minusicon_x;
        level2_y = plusicon_y + button_width * 2;

        level3 = BitmapFactory.decodeResource(getResources(), R.drawable.level3);
        level3 = Bitmap.createScaledBitmap(level3, button_width, button_width, true);
        level3_x = multiicon_x;
        level3_y = plusicon_y + button_width * 2;

        time30 = BitmapFactory.decodeResource(getResources(), R.drawable.time30);
        time30 = Bitmap.createScaledBitmap(time30, button_width, button_width, true);
        time30_x = plusicon_x;
        time30_y = plusicon_y + button_width * 4;

        time60 = BitmapFactory.decodeResource(getResources(), R.drawable.time60);
        time60 = Bitmap.createScaledBitmap(time60, button_width, button_width, true);
        time60_x = minusicon_x;
        time60_y = plusicon_y + button_width * 4;

        time120 = BitmapFactory.decodeResource(getResources(), R.drawable.time120);
        time120 = Bitmap.createScaledBitmap(time120, button_width, button_width, true);
        time120_x = multiicon_x;
        time120_y = plusicon_y + button_width * 4;

        applyButton = BitmapFactory.decodeResource(getResources(), R.drawable.applybutton);
        applyButton = Bitmap.createScaledBitmap(applyButton, button_width, button_width, true);
        applyButton_x = width / 4 - button_width / 2;
        applyButton_y = plusicon_y + button_width * 13 / 2;

        closeButton = BitmapFactory.decodeResource(getResources(), R.drawable.closebutton);
        closeButton = Bitmap.createScaledBitmap(closeButton, button_width, button_width, true);
        closeButton_x = width * 2 / 3 - button_width / 2;
        closeButton_y = plusicon_y + button_width * 13 / 2;

        scoreImage = BitmapFactory.decodeResource(getResources(), R.drawable.score);
        scoreImage = Bitmap.createScaledBitmap(scoreImage, button_width, button_width, true);

        resultShow = BitmapFactory.decodeResource(getResources(), R.drawable.resultshow);
        resultShow = Bitmap.createScaledBitmap(resultShow, width * 2 / 3, height / 3, true);
        resultShow_x = button_width;
        resultShow_y = button_width / 2;

        sPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        dingdongdang = sPool.load(mContext, R.raw.dingdongdang, 1);
        taeng = sPool.load(mContext, R.raw.taeng, 2);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    class MyThread extends Thread {
        public MyThread(SurfaceHolder holder, Context context) {
            mHolder = holder;
            mContext = context;
            makeQuestion();
        }

        public void drawEveryThing(Canvas canvas) {
            if (balloon.size() < 4) {
                Random r1 = new Random();
                int x = r1.nextInt(width - button_width);
                int y = r1.nextInt(height / 4);
                balloon.add(new Balloon(x, -y, balloon_speed));
            }

            Paint p1 = new Paint();
            Paint p2 = new Paint();
            Paint p3 = new Paint();
            Paint p4 = new Paint();

            p1.setColor(Color.WHITE);
            p1.setTextSize(width / 14);

            p2.setColor(Color.WHITE);
            p2.setTextSize(width / 14);
            p2.setAlpha(100);

            p3.setColor(Color.BLUE);
            p3.setTextSize(width / 12);

            p4.setColor(Color.BLACK);
            p4.setTextSize(width / 14);

            Paint pp = new Paint();
            pp.setColor(0xFFFFD9EC);

            canvas.drawRect(0, 0, width, height, pp);
            canvas.drawText("남은 시간 : " + Integer.toString(count / 50), 0, height / 7, p4);
            canvas.drawText("점수 : " + Integer.toString(score), 0, height / 5, p4);

            if (operator == 0) answer = number1 + number2;
            else if (operator == 1) answer = number1 - number2;
            else answer = number1 * number2;

            if (operator == 0)
                canvas.drawText("문제 : " + Integer.toString(number1) + "+" + Integer.toString(number2), 0, height / 13, p3);
            else if (operator == 1)
                canvas.drawText("문제 : " + Integer.toString(number1) + "-" + Integer.toString(number2), 0, height / 13, p3);
            else
                canvas.drawText("문제 : " + Integer.toString(number1) + "*" + Integer.toString(number2), 0, height / 13, p3);

            if (basket_x < 0) basket_x = 0;
            if (basket_x + balloonWidth > width) basket_x = width - basketWidth;

            canvas.drawBitmap(basket, basket_x, basket_y, p1);
            canvas.drawBitmap(leftKey, leftKey_x, leftKey_y, p1);
            canvas.drawBitmap(rightKey, rightKey_x, rightKey_y, p1);

            for (Balloon tmp : balloon)
                canvas.drawBitmap(balloonimg, tmp.x, tmp.y, p1);

            for (int i = balloon.size() - 1; i >= 0; i--)
                canvas.drawText(Integer.toString(wrongNumber[i]), balloon.get(i).x + balloonWidth / 6, balloon.get(i).y + balloonWidth * 2 / 3, p1);

            canvas.drawBitmap(balloonimg, answerBalloon.x, answerBalloon.y, p1);
            canvas.drawText(Integer.toString(answer), answerBalloon.x + balloonWidth / 6, answerBalloon.y + balloonWidth * 2 / 3, p1);

            if (answerBalloon.y > height)
                answerBalloon.y = -50;
            if (menuOk == 0)
                moveBalloon();
            if (menuOk == 0)
                checkCollision();
            if (menuOk == 0)
                count--;

            if (count < 0) {
                menuOk = 3;
                count = timeValue * 1500 + 1500;
                if (timeValue == 2) count = 6000;
            }

            if (menuOk == 0)
                count--;

            if (count < 0) {
                menuOk = 3;
                count = timeValue * 1500 + 1500;
                if (timeValue == 2) count = 6000;
            }
            if (menuOk == 0)
                canvas.drawBitmap(menuButton, menuButton_x, menuButton_y, p1);

            if (menuOk == 1) {
                canvas.drawRect(0, 0, width, height, pp);
                canvas.drawBitmap(menuTitle, 0, 0, p1);
                canvas.drawText("* 문제유형 선택", button_width, plusicon_y - button_width / 4, p4);
                canvas.drawBitmap(helpButton, helpButton_x, helpButton_y, p1);

                if (operator == 0) {
                    canvas.drawBitmap(plusicon, plusicon_x, plusicon_y, p1);
                    canvas.drawBitmap(minusicon, minusicon_x, minusicon_y, p2);
                    canvas.drawBitmap(multiicon, multiicon_x, multiicon_y, p2);
                }
                if (operator == 1) {
                    canvas.drawBitmap(plusicon, plusicon_x, plusicon_y, p2);
                    canvas.drawBitmap(minusicon, minusicon_x, minusicon_y, p1);
                    canvas.drawBitmap(multiicon, multiicon_x, multiicon_y, p2);
                }
                if (operator == 2) {
                    canvas.drawBitmap(plusicon, plusicon_x, plusicon_y, p2);
                    canvas.drawBitmap(minusicon, minusicon_x, minusicon_y, p2);
                    canvas.drawBitmap(multiicon, multiicon_x, multiicon_y, p1);
                }

                canvas.drawText("난이도 선택", button_width, level1_y - button_width / 4, p4);

                if (level == 0) {
                    canvas.drawBitmap(level1, level1_x, level1_y, p1);
                    canvas.drawBitmap(level2, level2_x, level2_y, p2);
                    canvas.drawBitmap(level3, level3_x, level3_y, p2);
                }
                if (level == 1) {
                    canvas.drawBitmap(level1, level1_x, level1_y, p2);
                    canvas.drawBitmap(level2, level2_x, level2_y, p1);
                    canvas.drawBitmap(level3, level3_x, level3_y, p2);
                }
                if (level == 2) {
                    canvas.drawBitmap(level1, level1_x, level1_y, p2);
                    canvas.drawBitmap(level2, level2_x, level2_y, p2);
                    canvas.drawBitmap(level3, level3_x, level3_y, p1);
                }

                if (timeValue == 0) {
                    canvas.drawBitmap(time30, time30_x, time30_y, p1);
                    canvas.drawBitmap(time60, time60_x, time60_y, p2);
                    canvas.drawBitmap(time120, time120_x, time120_y, p2);
                }
                if (timeValue == 1) {
                    canvas.drawBitmap(time30, time30_x, time30_y, p2);
                    canvas.drawBitmap(time60, time60_x, time60_y, p1);
                    canvas.drawBitmap(time120, time120_x, time120_y, p2);
                }
                if (timeValue == 2) {
                    canvas.drawBitmap(time30, time30_x, time30_y, p2);
                    canvas.drawBitmap(time60, time60_x, time60_y, p2);
                    canvas.drawBitmap(time120, time120_x, time120_y, p1);
                }
            }
            if (menuOk == 2) {
                canvas.drawRect(0, 0, width, height, pp);
                canvas.drawText("바구니를 움직여서", width / 20, height / 20, p3);
                canvas.drawText("정답풍선을 받는 게임", width / 20, height / 20, p3);
                canvas.drawText("-hotsixredbull", width / 20, height / 20, p4);
                canvas.drawBitmap(balloonimg, width / 2, height / 2, p1);
                canvas.drawBitmap(basket, width, height * 3 / 4, p1);
                canvas.drawBitmap(closeButton, closeButton_x - button_width, closeButton_y + button_width, p1);
            }
            if (menuOk == 3) {
                canvas.drawRect(0, 0, width, height, pp);
                canvas.drawBitmap(resultShow, resultShow_x, resultShow_y, p1);
                canvas.drawText("바구니를 움직여서", width / 20, height / 20, p4);
                canvas.drawText("정답풍선을 받는 게임", width / 20, height / 20, p4);
                canvas.drawBitmap(closeButton, closeButton_x - button_width, closeButton_y + button_width, p1);
            }
            if (scoreImageOk == 1) {
                score_count += 1;
                if (score_count < 40) {
                    canvas.drawBitmap(scoreImage, scoreImage_x, scoreImage_y, p1);
                } else {
                    score_count = 0;
                    scoreImageOk = 0;
                }
            }
        }

        public void makeQuestion() {
            Random r1 = new Random();
            int x = r1.nextInt(99) + 1;
            number1 = x;
            x = r1.nextInt(99) + 1;
            number2 = x;

            if (operator == 0) answer = number1 + number2;

            if (operator == 1) {
                if (number1 < number2) {
                    int tmp = number1;
                    number1 = number2;
                    number2 = tmp;
                    answer = number1 - number2;
                }
            }

            if (operator == 2) {
                r1 = new Random();

                x = r1.nextInt(9) + 1;
                number1 = x;
                x = r1.nextInt(9) + 1;
                number2 = x;
                answer = number1 * number2;
            }

            if (operator == 2) {
                for (int i = 0; i < 5; i++) {
                    x = r1.nextInt(80) + 1;
                    while (x == answer) {
                        x = r1.nextInt(80) + 1;
                    }
                    wrongNumber[i] = x;
                }
            } else {
                for (int i = 0; i < 5; i++) {
                    x = r1.nextInt(197) + 1;
                    while (x == answer) {
                        x = r1.nextInt(197) + 1;
                    }
                    wrongNumber[i] = x;
                }
            }
        }

        public void moveBalloon() {
            for (int i = balloon.size() - 1; i >= 0; i--) {
                balloon.get(i).y += balloon_speed;
            }

            for (int i = balloon.size() - 1; i >= 0; i--) {
                Random r1 = new Random();
                int xx = r1.nextInt(70) + 100;
                if (balloon.get(i).y > height) balloon.get(i).y = -xx;
            }
            answerBalloon.y += balloon_speed;
        }

        public void checkCollision() {
            for (int i = balloon.size() - 1; i >= 0; i--) {
                if (balloon.get(i).x + balloonWidth / 2 > basket_x && balloon.get(i).x + balloonWidth / 2 < basket_x + basketWidth
                        && balloon.get(i).y + balloonHeight / 2 > basket_y && balloon.get(i).y + balloonHeight / 2 < basket_y + basketHeight) {
                    balloon.remove(i);
                    score -= 10;
                    xNumber += 1;
                    sPool.play(taeng, 1, 1, 9, 0, 1);
                }
            }

            if (answerBalloon.x + balloonWidth / 2 > basket_x && answerBalloon.x + balloonWidth / 2 < basket_x + basketWidth
                    && answerBalloon.y + balloonHeight / 2 > basket_y && answerBalloon.y + balloonHeight / 2 < basket_y + basketHeight) {
                score += 30;
                oNumber += 1;
                makeQuestion();
                Random r1 = new Random();

                int xx = r1.nextInt(width-button_width);
                answerBalloon.x = xx;
                xx = r1.nextInt(300);
                answerBalloon.y = -xx;
                scoreImageOk =1;
                scoreImage_x = basket_x - button_width /2;
                scoreImage_y = basket_y - button_width /2;
                sPool.play(dingdongdang,1,1,9,0,1);
            }
        }

        public void run() {
            Canvas canvas = null;
            while (true) {
                canvas = mHolder.lockCanvas();
                try {
                    synchronized (mHolder) {
                        drawEveryThing(canvas);
                    }
                } finally {
                    if (canvas != null)
                        mHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = 0,y = 0;
        boolean returnValue = false;

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            x = (int) event.getX();
            y = (int) event.getY();
            returnValue = true;

            if((x>leftKey_x) && (x < leftKey_x + button_width) && (y>leftKey_y) && (y<leftKey_y+button_width))
                basket_x -= basket_speed;
            if((x>rightKey_x) && (x < rightKey_x + button_width) && (y>rightKey_y) && (y<rightKey_y+button_width))
                basket_x += basket_speed;
        }
        if((x>leftKey_x) && (x < leftKey_x + button_width) && (y>leftKey_y) && (y<leftKey_y+button_width))
            basket_x -= basket_speed;
        if((x>rightKey_x) && (x < rightKey_x + button_width) && (y>rightKey_y) && (y<rightKey_y+button_width))
            basket_x += basket_speed;

        if(menuOk == 1) {
            if((x>plusicon_x) && (x<plusicon_x + button_width) &&(y>plusicon_y) && (y<plusicon_y+button_width)){
                operator = 0;
                mThread.makeQuestion();
            }
            if((x>minusicon_x) && (x<minusicon_x + button_width) &&(y>minusicon_y) && (y<minusicon_y+button_width)){
                operator = 1;
                mThread.makeQuestion();
            }
            if((x>multiicon_x) && (x<multiicon_x + button_width) &&(y>multiicon_y) && (y<multiicon_y+button_width)){
                operator = 2;
                mThread.makeQuestion();
            }
        }

        if(menuOk == 1) {
            if((x>time30_x) && (x<time30_x + button_width) &&(y>time30_y) && (y<time30_y+button_width)){
                count = 50 * 30 ;
                timeValue = 0;
            }
            if((x>time60_x) && (x<time60_x + button_width) &&(y>time60_y) && (y<time60_y+button_width)){
                count = 50 * 60 ;
                timeValue = 0;
            }
            if((x>time120_x) && (x<time120_x + button_width) &&(y>time120_y) && (y<time120_y+button_width)){
                count = 50 * 120 ;
                timeValue = 0;
            }
        }

        if(menuOk == 1) {
            if((x>level1_x) && (x<level1_x + button_width) &&(y>level1_y) && (y<level1_y+button_width)){
                balloon_speed = width / 200;
                level = 0;
                oNumber = 0;
                xNumber = 0;
                mThread.makeQuestion();
            }
            if((x>level2_x) && (x<level2_x + button_width) &&(y>level2_y) && (y<level2_y+button_width)){
                balloon_speed = width / 120;
                level = 1;
                oNumber = 0;
                xNumber = 0;
                mThread.makeQuestion();
            }
            if((x>level3_x) && (x<level3_x + button_width) &&(y>level3_y) && (y<level3_y+button_width)){
                balloon_speed = width / 70;
                level = 2;
                oNumber = 0;
                xNumber = 0;
                mThread.makeQuestion();
            }
        }

        if(menuOk == 1) {
            if((x>applyButton_x) && (x<applyButton_x + button_width) &&(y>applyButton_y) && (y<applyButton_y+button_width)){
                count = timeValue * 1500 + 1500;
                if(timeValue == 2) count = 6000;
                menuOk = 0;
                oNumber = 0;
                xNumber = 0;
            }
            if((x>closeButton_x) && (x<closeButton_x + button_width) &&(y>closeButton_y) && (y<closeButton_y+button_width)){
                oNumber = 0;
                xNumber = 0;
                System.exit(0);
            }
        }

        if(menuOk == 1){
            if((x>helpButton_x) && (x<helpButton_x + button_width) &&(y>helpButton_y) && (y<helpButton_y+button_width)){
                menuOk = 2;
            }
        }

        if(menuOk == 0) {
            if((x>menuButton_x) && (x<menuButton_x + button_width) &&(y>menuButton_y) && (y<menuButton_y+button_width)){
                menuOk = 1;
            }
        }

        if(menuOk == 2 || menuOk == 3) {
            if ((x > closeButton_x - button_width) && (x < closeButton_x) && (y > closeButton_y + button_width) && (y < closeButton_y + button_width * 2)) {
                if (menuOk == 3) {
                    oNumber = 0;
                    xNumber = 0;
                }
                menuOk = 1;
            }
        }
        return  returnValue;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        synchronized (mHolder) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_DPAD_LEFT:

                    break;
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    break;
                case KeyEvent.KEYCODE_DPAD_UP:

                    break;
                    default:
            }
        }
        return false;
    }
}