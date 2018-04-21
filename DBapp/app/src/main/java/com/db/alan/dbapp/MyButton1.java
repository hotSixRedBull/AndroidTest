package com.db.alan.dbapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class MyButton1 {
    int x,y;
    int w,h;
    Bitmap button_img;//guessing
    private Bitmap buttonImage[] = new Bitmap[2];
    private int whichPic;
    public MyButton1(int x,int y,int z){
        this.x = x;
        this.y = y;
        this.whichPic = z;

        for(int i=0;i<2;i++) {
            buttonImage[i] = BitmapFactory.decodeResource(StudyView.mContext.getResources(), android.R.drawable.word00+whichPic*2+i);

            if(whichPic<8 || (whichPic>=15 && whichPic <= 22)) {
                int xWidth = StudyView.width/11;
                int yWidth = xWidth;

                buttonImage[i] =Bitmap.createScaledBitmap(buttonImage[i],xWidth,yWidth,true);
            }

            if(whichPic > 6 && whichPic < 12){
                int xWidth = StudyView.width/16;
                int yWidth = xWidth;

                buttonImage[i] =Bitmap.createScaledBitmap(buttonImage[i],xWidth,yWidth,true);
            }

            if(whichPic == 12 || whichPic == 13 || whichPic == 33 || whichPic == 23) {
                int xWidth = StudyView.width/5;
                int yWidth = StudyView.height/7;

                buttonImage[i] =Bitmap.createScaledBitmap(buttonImage[i],xWidth,yWidth,true);
            }

            if(whichPic == 25) {
                int xWidth = StudyView.width/5;
                int yWidth = StudyView.height/7;

                buttonImage[i] =Bitmap.createScaledBitmap(buttonImage[i],xWidth,yWidth,true);
            }

            if(whichPic == 26 || whichPic == 27 || whichPic == 28) {
                int xWidth = StudyView.width/16;
                int yWidth = xWidth;

                buttonImage[i] =Bitmap.createScaledBitmap(buttonImage[i],xWidth,yWidth,true);
            }

            if(whichPic == 29) {
                int xWidth = StudyView.width/16;
                int yWidth = xWidth;

                buttonImage[i] =Bitmap.createScaledBitmap(buttonImage[i],xWidth,yWidth,true);
            }

            w = buttonImage[0].getWidth()/ 2;
            h = buttonImage[0].getHeight()/2;
            button_img = buttonImage[0];
        }


    }
    public boolean button_released(){
        button_img = buttonImage[0];
        return true;
    }

    public boolean btn_press() {
        button_img = buttonImage[1];
        return  true;
    }
}
