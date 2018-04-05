package com.tutorial.hotsixredbull.mygumi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.AttributeSet;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Random;
import java.util.jar.Attributes;

public class MySurface extends SurfaceView implements SurfaceHolder.Callback {
    MyThread mThread;
    SurfaceHolder mHolder;
    Context mContext;

    Bitmap basket;
    int basket_x, basket_y;
    int basketWidth,basketHeight;
    Bitmap leftKey,rightKey;
    int leftKey_x,rightKey_x;
    int leftKey_y,rightKey_y;
    int width,height;
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
    int resultShow_x,resultShow_y;

    int menuOk = 1;
    Bitmap menuButton;
    int menuButton_x,menuButton_y;
    Bitmap helpButton;
    int helpButton_x,helpButton_y;
    Bitmap closeButton;
    int closeButton_x,closeButton_y;
    Bitmap applyButton;
    int applyButton_x,applyButton_y;

    Bitmap plusicon,minusicon,multiicon;
    int plusicon_x,plusicon_y;
    int minusicon_x,minusicon_y;
    int multiicon_x,multiicon_y;

    Bitmap time30,time60,time120;
    int time30_x,time30_y;
    int time60_x,time60_y;
    int time120_x,time120_y;

    Bitmap level1,level2,level3;
    int level1_x,level1_y;
    int level2_x,level2_y;
    int level3_x,level3_y;

    Bitmap scoreImage;
    int scoreImage_x,scoreImage_y;
    int score_count;
    int scoreImageOk;
    int count = 3000;

    int level =1;
    int timeValue=1;
    int operator;

    ArrayList<Balloon> balloon;
    Bitmap menuTitle;

    int number1,number2;
    int answer;
    int[] wrongNumber = new int[5];

    SoundPool sPool;
    int dingdongdang,taeng;
    public MySurface(Context context, AttributeSet attrs){
        super(context,this);
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        mThread = new MyThread(holder,context);
        InitApp();
        setFocusable(true);
    }

    private void InitApp(){
        Display display = ((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;

        basket_speed = width/40;
        balloon_speed = width/140;

        balloon = new ArrayList<Balloon>();
        button_width=width/6;
        basket = BitmapFactory.decodeResource(getResources(),R.drawable.baskethand);
        int x  = width/4;
        int y = x;
        basket = Bitmap.createScaledBitmap(basket,x,y,true);
        basketWidth = basket.getWidth();
        basketHeight = basket.getHeight();
        basket_x = width*1/9;
        basket_y = height*6/9 + button_width/2;

        leftKey = BitmapFactory.decodeResource(getResources(),R.drawable.leftarrow);
        leftKey_x = button_width/2;
        leftKey_y = height-button_width*2;
        button_width = width/6;
        leftKey = Bitmap.createScaledBitmap(leftKey,button_width,button_width,true);

        rightKey = BitmapFactory.decodeResource(getResources(),R.drawable.rightarrow);
        rightKey_x = width-button_width-button_width/2;
        rightKey_y = height-button_width*2;
        rightKey = Bitmap.createScaledBitmap(rightKey,button_width,button_width,true);

        balloonimg = BitmapFactory.decodeResource(getResources(),R.drawable.coin);
        balloonimg = Bitmap.createScaledBitmap(balloonimg,button_width,button_width*5/4,true);
        balloonWidth = balloonimg.getWidth();
        balloonHeight = balloonimg.getHeight();

        menuTitle = BitmapFactory.decodeResource(getResources(),R.drawable.menutitle);
        menuTitle = Bitmap.createScaledBitmap(menuTitle,width*3/5,height/7,true);

        Random r1 = new Random();
        int xx = r1.nextInt(width);
        answerBalloon = new AnswerBalloon(x,0,balloon_speed);

        menuButton = BitmapFactory.decodeResource(getResources(),R.drawable.menubutton);
        menuButton = Bitmap.createScaledBitmap(menuButton,button_width,button_width,true);
        menuButton_x = width - button_width*5/4;
        menuButton_y = height/30;

        helpButton = BitmapFactory.decodeResource(getResources(),R.drawable.helpButton);
        helpButton = Bitmap.createScaledBitmap(helpButton,button_width,button_width,true);
        helpButton_x = width - button_width*5/4;
        helpButton_y = height/8;

        plusicon = BitmapFactory.decodeResource(getResources(),R.drawable.plusicon);
        plusicon = Bitmap.createScaledBitmap(plusicon,button_width,button_width,true);
        plusicon_x = button_width;
        plusicon_y = height/5;

        minusicon = BitmapFactory.decodeResource(getResources(),R.drawable.minusicon);
        minusicon = Bitmap.createScaledBitmap(minusicon,button_width,button_width,true);
        minusicon_x = button_width*9/4;
        minusicon_y = height/5;

        multiicon = BitmapFactory.decodeResource(getResources(),R.drawable.multiicon);
        multiicon = Bitmap.createScaledBitmap(multiicon,button_width,button_width,true);
        multiicon_x = button_width*7/2;
        multiicon_y = height/5;

        level1 = BitmapFactory.decodeResource(getResources(),R.drawable.level1);
        level1 = Bitmap.createScaledBitmap(level1,button_width,button_width,true);
        level1_x = plusicon_x;
        level1_y = plusicon_y+button_width*2;

        level2 = BitmapFactory.decodeResource(getResources(),R.drawable.level2);
        level2 = Bitmap.createScaledBitmap(level2,button_width,button_width,true);
        level2_x = minusicon_x;
        level2_y = plusicon_y+button_width*2;

        level3 = BitmapFactory.decodeResource(getResources(),R.drawable.level3);
        level3 = Bitmap.createScaledBitmap(level3,button_width,button_width,true);
        level3_x = multiicon_x;
        level3_y = plusicon_y+button_width*2;

        time30 = BitmapFactory.decodeResource(getResources(),R.drawable.time30);
        time30 = Bitmap.createScaledBitmap(time30,button_width,button_width,true);
        time30_x = plusicon_x;
        time30_y = plusicon_y+button_width*4;

        time60 = BitmapFactory.decodeResource(getResources(),R.drawable.time60);
        time60 = Bitmap.createScaledBitmap(time60,button_width,button_width,true);
        time60_x = minusicon_x;
        time60_y = plusicon_y+button_width*4;

        time120 = BitmapFactory.decodeResource(getResources(),R.drawable.time120);
        time120 = Bitmap.createScaledBitmap(time120,button_width,button_width,true);
        time120_x = multiicon_x;
        time120_y = plusicon_y+button_width*4;

        applyButton = BitmapFactory.decodeResource(getResources(),R.drawable.applybutton);
        applyButton = Bitmap.createScaledBitmap(applyButton,button_width,button_width,true);
        applyButton_x = width/4 - button_width/2;
        applyButton_y = plusicon_y+button_width*13/2;

        closeButton = BitmapFactory.decodeResource(getResources(),R.drawable.closebutton);
        closeButton = Bitmap.createScaledBitmap(closeButton,button_width,button_width,true);
        closeButton_x = width*2/3 - button_width/2;
        closeButton_y = plusicon_y+button_width*13/2;

        scoreImage = BitmapFactory.decodeResource(getResources(),R.drawable.scoreImage);
        scoreImage = Bitmap.createScaledBitmap(scoreImage,button_width,button_width,true);

        resultShow = BitmapFactory.decodeResource(getResources(),R.drawable.resultShow);
        resultShow = Bitmap.createScaledBitmap(resultShow,width*2/3,height/3,true);
        resultShow_x = button_width;
        resultShow_y = button_width/2;

        sPool = new SoundPool(10, AudioManager.STREAM_MUSIC,0);
        dingdongdang = sPool.load(mContext,R.raw.dingdongdang,1);
        taeng = sPool.load(mContext,R.raw.taeng,2);
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
        public  MyThread(SurfaceHolder holder, Context context){
            mHolder = holder;
            mContext = context;
            makeQuestion();
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
    }
}
