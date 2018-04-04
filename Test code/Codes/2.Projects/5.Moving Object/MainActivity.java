package com.example.hotsixredbull.mygumi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Bitmap smiley;
    int smiley_x,smiley_y;
    Bitmap leftKey,rightKey;
    int leftkey_x,leftkey_y;
    int rightkey_x,rightkey_y;
    int Width,Height;
    int button_width;
    Bitmap screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));
        Display display = ((WindowManager)getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Width = display.getWidth();
        Height = display.getHeight();
        smiley = BitmapFactory.decodeResource(getResources(),R.drawable.smiley);
        int x = Width/8;
        int y = x;
        smiley = Bitmap.createScaledBitmap(smiley,x,y,true);
        smiley_x = Width*1/9;
        smiley_y = Height*6/9;

        leftKey = BitmapFactory.decodeResource(getResources(),R.drawable.left);
        leftkey_x = Width*5/9;
        leftkey_y = Height*7/9;
        button_width = Width/6;

        leftKey = Bitmap.createScaledBitmap(leftKey,button_width,button_width,true);

        rightKey = BitmapFactory.decodeResource(getResources(),R.drawable.right);
        rightkey_x = Width*7/9;
        rightkey_y = Height*7/9;

        rightKey = Bitmap.createScaledBitmap(rightKey,button_width,button_width,true);

        screen = BitmapFactory.decodeResource(getResources(),R.drawable.background);
        screen = Bitmap.createScaledBitmap(screen, Width,Height,true);
    }

    class MyView extends View{
        MyView(Context context){
            super(context);
            setBackgroundColor(Color.BLUE);
        }

        @Override
        public void onDraw(Canvas canvas){
            Paint p1 = new Paint();
            p1.setColor(Color.RED);
            p1.setTextSize(50);
            canvas.drawBitmap(screen,0,0,p1);

            canvas.drawBitmap(smiley,smiley_x,smiley_y,p1);
            canvas.drawBitmap(leftKey,leftkey_x,leftkey_y,p1);
            canvas.drawBitmap(rightKey,rightkey_x,rightkey_y,p1);
            //count++;
        }

        @Override
        public boolean onTouchEvent(MotionEvent event){
            int x=0,y=0;

            if(event.getAction() == MotionEvent.ACTION_DOWN
                    ||event.getAction() == MotionEvent.ACTION_MOVE){
                x = (int) event.getX();
                y = (int) event.getY();
            }

            if((x>leftkey_x)&&(x<leftkey_x+button_width)&&(y>leftkey_y)&&(y<leftkey_y+button_width))
                smiley_x-=20;

            if((x>rightkey_x)&&(x<rightkey_x+button_width)&&(y>rightkey_y)&&(y<rightkey_y+button_width))
                smiley_x+=20;

            invalidate();
            return true;
        }
    }
}
