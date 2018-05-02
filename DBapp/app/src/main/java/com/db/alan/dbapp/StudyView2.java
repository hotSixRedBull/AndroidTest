package com.db.alan.dbapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGestureListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Display;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;


public class StudyView2 extends SurfaceView implements Callback {
    static int soundOk = 1;
    int questionNumber = 0;
    int numberOfQuestion = 9;
    int solvingOver = 0;
    int btnResultConfirmOk = 0;
    int[] ox = {};

    int textSize = 49;

    int answerButton = 0;
    int answerUser = 0;

    int starIng = 0;
    int starIndex = 0;
    int starX, starY;

    int oNumber;
    int xNumber;

    int submenuOk = 0;
    int submenuOk2 = 0;

    double rand;
    int btnPressed = 0;

    String[] wordForDelete = {"", "", "", "", ""};
    String wordToDelete = "";

    static StudyThread mThread;
    SurfaceHolder mHolder;
    static Context mContext;
    FileTable mFile2;

    MyDBHelper m_helper;

    Cursor cursor;
    int dicOK = 0;
    int movePosition = 0;

    MyButton2 btnSetting;
    MyButton2 btnExit;

    MyButton2 btnNum1;
    MyButton2 btnNum2;
    MyButton2 btnNum3;
    MyButton2 btnNum4;
    MyButton2 btnNum5;
    MyButton2 btnNextQuestion;

    MyButton2 btn10Question;
    MyButton2 btn20Question;
    MyButton2 btn25Question;
    MyButton2 btn50Question;
    MyButton2 btn100Question;

    MyButton2 btnMenuClose;

    MyButton2 btnResultConfirm;
    MyButton2 btnExplain;

    int explainPressed = 0;

    MyButton2 btnOx;

    MyButton2 btnLeftArrow;
    MyButton2 btnRightArrow;
    MyButton2 btnClose2;

    MyButton2 btnDeleteDb;
    MyButton2 exitButton;

    int btnNum1Count = 0;
    int btnNum2Count = 0;
    int btnNum3Count = 0;
    int btnNum4Count = 0;

    static int width, height;
    int level;
    int score = 0;

    Bitmap chilpan;

    Bitmap answerx;
    Bitmap answero;

    Bitmap explain;

    Bitmap star[] = new Bitmap[4];

    Bitmap testtitle;

    Bitmap screenPink;

    static SoundPool sdPool;
    static int dingdongdaeng, taeng;

    // 20180422 from here
    public StudyView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        mHolder = holder;
        mContext = context;
        mThread = new StudyThread(holder, context);

        initAll();
        makeQuestion(level);
        setFocusable(true);
    }

    private void initAll() {
        m_helper = new DBHelper(mContext, "test.db", null, 1);
        Display display = ((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();

        mFile2 = new FileTable();

        btnNum1 = new MyButton2(width/14, height/3, 7);
        btnNum2 = new MyButton2(btnNum1.x,btnNum1.y+btnNum1.h*2+8,8);
        btnNum3 = new MyButton2(btnNum2.x,btnNum2.y+btnNum2.h*2+8, 9);
        btnNum4 = new MyButton2(btnNum3.x,btnNum3.y+btnNum3.h*2+8, 10);
        btnNum5 = new MyButton2(btnNum4.x,btnNum4.y+btnNum4.h*2+8, 11);

        btnSetting = new MyButton2(width - btnNum1.w*8, btnNum1.h, 14);
        btnExit = new MyButton2(width - btnSetting.w*3, btnSetting.y, 5);

        testtitle = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.testtitle);
        testtitle = Bitmap.createScaledBitmap(testtitle,btnSetting.w*6,btnSetting.h*2,true);

        btnNextQuestion = new MyButton2(width*2/3, height/2, 12);

        btnLeftArrow = new MyButton1(btnPrevious.x, height - btnNext.h * 2, 26);
        btnRightArrow = new MyButton1(btnPrevious.x + 150, height - btnNext.h * 2, 27);

        btn10Question= new MyButton2(width/10,height/2,5);
        btn20Question = new MyButton2(btn10Question.x+btn100Question.w*2,btn10Question.y,6);
        btn25Question = new MyButton2(btn20Question.x+btn20Question.w*4,btn10Question.y,7);
        btn50Question = new MyButton2(btn25Question.x+btn25Question.w*6,btn10Question.y,8);
        btn100Question = new MyButton2(btn100Question.x+btn100Question.w*8,btn10Question.y,9);

        btnMenuClose = new MyButton2(width/2+btnSetting.w*3,height-btnSetting.h*3,24);

        btnResultConfirm = new MyButton2(width/2+btnSetting.w,height-btnSetting.h*2,25);

        btnExplain = new MyButton2(width/40,height*7/10,31);

        btnOx = new MyButton2(width/4,btnMenuClose.y,32);

        btnLeftArrow = new MyButton2(btnNum1.w, height-btnNum1.h*3,26);
        btnRightArrow = new MyButton2(btnLeftArrow.x+btnLeftArrow.w*3,height-btnNum1.h*3, 27);

        btnClose2 = new MyButton2(width-btnNum1.w*3,height-btnNum1.h,28);

        btnDeleteDb = new MyButton2(width*9/10,3,30);

        exitButton = new MyButton2(10,600,4);

        int xxx = width / 6;
        answerx = BitmapFactory.decodeResource(mContext.getResources(), android.R.drawable.answerx);
        answerx = Bitmap.createScaledBitmap(answerx, xxx, xxx, true);
        answero = BitmapFactory.decodeResource(mContext.getResources(), android.R.drawable.answero);
        answero = Bitmap.createScaledBitmap(answero, xxx, xxx, true);
        explain = BitmapFactory.decodeResource(mContext.getResources(), android.R.drawable.explain);
        //explain = Bitmap.createScaledBitmap(explain, width / 11, height / 7, true);

        chilpan = BitmapFactory.decodeResource(mContext.getResources(), android.R.drawable.screen1);
        chilpan = Bitmap.createScaledBitmap(chilpan, width, height, true);

        screenPink = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.subscreen00);
        screenPink = Bitmap.createScaledBitmap(screenPink,width * 2, height-height/5-btnNum1.h/4,true);

        for (int i = 0; i < 4; i++) {
            star[i] = BitmapFactory.decodeResource(mContext.getResources(), android.R.drawable.circlewhite);
            star[i] = Bitmap.createScaledBitmap(star[i], btnNum1.w * 2 + i * 2, btnNum1.w * 2 + i * 2, true)
        }

        sdPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        dingdongdaeng = sdPool.load(mContext, android.R.raw.dingdongdang, 1);
        taeng = sdPool.load(mContext, android.R.raw.taeng, 2);
        btn10Question.pressed = 1;
    }

    public void makeQuestion(int x) {
        mFile2.loadFile2(x);
        SQLiteDatabase db = m_helper.getWritableDatabase();
        db.delete("test",null,null);
        db.close();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mThread.start();
        }catch (Exception e){
            RestartGame();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    public static void RestartGame() {
        mThread.StopThread();
        mThread = null;
        mThread = new StudyThread(mHolder,mContext);
        mThread.start();
    }

    class StudyThread extends Thread {
        boolean canRun = true;
        boolean isWait = false;
        int loop;
        Paint paint = new Paint();
        Paint paint2 = new Paint();
        Paint paint3 = new Paint();
        Paint paint4 = new Paint();
        Paint paint5   = new Paint();

        public StudyThread(SurfaceHolder holder, Context context) {
            paint.setColor(Color.WHITE);
            paint.setAntiAlias(true);
            paint.setTextSize(textSize);
            paint.setTypeface(Typeface.create("", Typeface.BOLD));
            paint2.setColor(Color.BLUE);
            paint2.setAntiAlias(true);
            paint2.setTypeface(Typeface.create("", Typeface.BOLD));
            paint3.setAlpha(100);
            paint4.setColor(Color.WHITE);
            paint5.setColor(Color.RED);
        }

        public void DrawAll(Canvas canvas) {
            canvas.drawBitmap(chilpan, 0, 0, null);

            textSizeChanging = (int) (width * 58 / 1280);
            textSizeChanging2 = (int) (width * 40 / 1280);

            paint.setTextSize(width / 23);
            paint2.setTextSize(width / 40);
            paint3.setTextSize(width / 34);

            if (submenuOk == 0 && submenuOk2 == 0) {
                if (questionNumber > numberOfQuestion)
                    questionNumber = numberOfQuestion;
                if (questionNumber < 0) questionNumber = 0;

                canvas.drawText(FileSplit0.questionNum[questionNumber][0], btnNext.x + btnNum1.w, btnNext.y + btnNext.h * 3, paint);
                canvas.drawText(FileSplit0.questionNum[questionNumber][1], btnNext.x + btnNum1.w * 3, btnNext.y + btnNext.h * 3, paint);

                canvas.drawText(FileSplit0.questionNum[questionNumber][2], btnNext.x + btnNum1.w * 6, btnNext.y + btnNext.w * 3 / 2, paint);
                canvas.drawText(FileSplit0.questionNum[questionNumber][3], btnNext.x + btnNum1.w * 6, btnNext.y + btnNext.w * 3 / 2, paint);
                canvas.drawText(FileSplit0.questionNum[questionNumber][4], btnNext.x + btnNum1.w * 6, btnNext.y + btnNext.w * 3 / 2, paint);
                canvas.drawText(FileSplit0.questionNum[questionNumber][5], btnNext.x + btnNum1.w * 6, btnNext.y + btnNext.w * 3 / 2, paint);
            }

            if (submenuOk == 0 && submenuOk2 == 0) {
                canvas.drawBitmap(btnNum1.button_img, btnNum1.x, btnNum1.y, null);
                canvas.drawBitmap(btnNum1.button_img, btnNum1.x, btnNum1.y, null);
                canvas.drawBitmap(btnNum1.button_img, btnNum1.x, btnNum1.y, null);
                canvas.drawBitmap(btnNum1.button_img, btnNum1.x, btnNum1.y, null);

                canvas.drawBitmap(btnKakaoQSending.button_img, btnKakaoQSending.x, btnKakaoQSending.y, null);
            }

            if (submenuOk == 0 && submenuOk2 == 0) {
                canvas.drawBitmap(btnNext.button_img, btnNext.x, btnNext.y, null);
                canvas.drawBitmap(btnPrevious.button_img, btnPrevious.x, btnPrevious.y, null);
                canvas.drawBitmap(btnWordSelection.button_img, btnWordSelection.x, btnWordSelection.y, null);
                canvas.drawBitmap(btnMyNote.button_img, btnMyNote.x, btnMyNote.y, null);
                canvas.drawBitmap(btnExit.button_img, btnExit.x, btnExit.y, null);
                canvas.drawBitmap(btnRandom.button_img, btnRandom.x, btnRandom.y, null);
            }

            if (submenuOk == 1) {
                canvas.drawBitmap(chilpan, 0, 0, null);
                canvas.drawText("각 서브메뉴에는 100개의 단어가 있습니다.", btnNext.x + 100, btnNext.h * 2, paint);
                canvas.drawBitmap(btnClose.button_img, btnClose.x, btnClose.y, null);
                canvas.drawBitmap(btnSub1.button_img, btnSub1.x, btnSub1.y, null);
                canvas.drawBitmap(btnSub2.button_img, btnSub1.x, btnSub1.y, null);
                canvas.drawBitmap(btnSub3.button_img, btnSub1.x, btnSub1.y, null);
                canvas.drawBitmap(btnSub4.button_img, btnSub1.x, btnSub1.y, null);
                canvas.drawBitmap(btnSub5.button_img, btnSub1.x, btnSub1.y, null);
                canvas.drawBitmap(btnSub6.button_img, btnSub1.x, btnSub1.y, null);
                canvas.drawBitmap(btnSub7.button_img, btnSub1.x, btnSub1.y, null);
                canvas.drawBitmap(btnSub8.button_img, btnSub1.x, btnSub1.y, null);
            }

            if (submenuOk == 0 && submenuOk2 == 0) {
                canvas.drawText(whichSubject, width / 2, btnExit.h, paint2);
                canvas.drawText("맞은 개수: " + Integer.toString(oNumber) + " 개 틀린 개수: " + Integer.toString(xNumber) + "개", width / 2, btnExit.h * 2, paint2);
            }

            if (answerButton == 1 && dicOK == 0) {
                int sss = 0;
                sss = Integer.parseInt(FileSplit0.questionNum[questionNumber][6].trim());

                if (sss == answerUser) {
                    canvas.drawBitmap(answero, btnNum1.x + 280, btnNum1.y + 20, null);
                } else {
                    canvas.drawBitmap(answerx, btnNum1.x + 280, btnNum1.y + 20, null);
                }
                canvas.drawBitmap(btnNextQuestion.button_img, btnNextQuestion.x, btnNextQuestion.y, null);
                canvas.drawBitmap(btnSolveAgain.button_img, btnSolveAgain.x, btnSolveAgain.y, null);

                if (answerButton == 1 && wordSave == 1) {
                    canvas.drawBitmap(btnWordSave.button_img, btnWordSave.x, btnWordSave.y, null);
                }

            }

            if (starIng == 1) {
                starIndex += 1;
                if (starIndex >= 15) {
                    starIng = 0;
                    starIndex = 0;
                } else
                    canvas.drawBitmap(star[starIndex / 4], starX - starIndex / 4, starY - starIndex / 4, null);
            }

            if (btnPressed == 1) {
                btnPreCount++;
                btnNextCount++;
                btnSelectCount++;
                btnMyNoteCount++;
                btnRanCount++;

                btnNum1Count++;
                btnNum2Count++;
                btnNum3Count++;
                btnNum4Count++;
            }

            if (btnPreCount == 15) {
                btnPreCount = 0;
                btnPrevious.button_released();
            }

            if (btnNextCount == 15) {
                btnNextCount = 0;
                btnNext.button_released();
            }

            if (btnSelectCount == 15) {
                btnSelectCount = 0;
                btnWordSelection.button_released();
            }

            if (btnMyNoteCount == 15) {
                btnMyNoteCount = 0;
                btnMyNote.button_released();
            }

            if (btnRanCount == 15) {
                btnRanCount = 0;
                btnRandom.button_released();
            }

            if (btnNum1Count == 15) {
                btnNum1Count = 0;
                btnNum1.button_released();
            }

            if (btnNum2Count == 15) {
                btnNum2Count = 0;
                btnNum2.button_released();
            }

            if (btnNum3Count == 15) {
                btnNum3Count = 0;
                btnNum3.button_released();
            }

            if (btnNum4Count == 15) {
                btnNum4Count = 0;
                btnNum4.button_released();
            }

            if (dicOK == 1) {
                canvas.drawBitmap(chilpan, 0, 0, null);
                SQLiteDatabase db = m_helper.getReadableDatabase();

                cursor = db.query("englishWordTable", null, null, null, null, null, null);

                int numofdb = cursor.getCount();
                if (movePosition > numofdb) movePosition -= 5;
                else if (movePosition == numofdb) movePosition -= 5;

                if (movePosition <= 0) movePosition = 0;

                for (int i = 0; i < 5; i++) {
                    if (cursor.moveToPosition(movePosition + i) == false)
                        break;
                    canvas.drawText((movePosition + i + 1) + "" + cursor.getString(1) + " : " + cursor.getString(2), btnExit.w * 3, btnExit.h * 4 + btnExit.w * 3 / 2 * i, paint);
                    wordForDelete[i] = cursor.getString(1);
                }

                canvas.drawBitmap(btnLeftArrow.button_img, btnLeftArrow.x, btnLeftArrow.y, null);
                canvas.drawBitmap(btnRightArrow.button_img, btnRightArrow.x, btnRightArrow.y, null);
                canvas.drawBitmap(btnClose.button_img, btnClose.x, btnClose.y, null);

                int imsy;
                int x = 0;
                for (int i = 0; i < 5; i++) {
                    canvas.drawText("저장된 단어 수: " + Integer.toString(numofdb), 100, 100, paint2);
                    imsy = 0;
                    if (numofdb == 0) {
                        canvas.drawText("단어가 없습니다!", 70, 180 + 90 * i, paint);
                        break;
                    }

                    canvas.drawBitmap(btnForDictionary[i].button_img, btnForDictionary[i].x, btnForDictionary[i].y + btnExit.h / 3, null);

                    x = (numofdb - 1) / 5;
                    if ((movePosition) / 5 < x) imsy = 1;
                    else imsy = 0;

                    if (imsy == 0) {
                        if (numofdb % 5 == 4 && i == 3) break;
                        if (numofdb % 5 == 3 && i == 2) break;
                        if (numofdb % 5 == 2 && i == 1) break;
                        if (numofdb % 5 == 1 && i == 0) break;
                    }
                }
                cursor.close();
                db.close();
            }
        }

        public void run() {
            Canvas canvas = null;
            while (canRun) {
                canvas = mHolder.lockCanvas();
                try {
                    synchronized (mHolder) {
                        DrawAll(canvas);
                    }
                } finally {
                    if (canvas != null) {
                        mHolder.unlockCanvasAndPost(canvas);
                    }
                }

                synchronized (this) {
                    if (isWait) {
                        try {
                            wait();
                        } catch (Exception e) {
                            // nothing
                        }
                    }
                }
            }
        }

        public void StopThread() {
            canRun = false;
            synchronized (this) {
                this.notify();
            }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = 0, y = 0;

        synchronized (mHolder) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                x = (int) event.getX();
                y = (int) event.getY();
            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {

            } else if (event.getAction() == MotionEvent.ACTION_UP) {

            }
        }

        if (x > btnNext.x && x < (btnNext.x + btnNext.w * 2) && y > btnNext.y && y < (btnNext.y + btnNext.h * 2)) {
            questionNumber -= 1;
            answerButton = 0;
            btnPreCount = 0;
            btnPressed = 1;
            btnNext.btn_press();
            submenuOk = 0;
            dicOK = 0;
        }

        if (x > btnPrevious.x && x < (btnPrevious.x + btnPrevious.w * 2) && y > btnPrevious.y && y < (btnPrevious.y + btnPrevious.h * 2)) {
            questionNumber += 1;
            answerButton = 0;
            btnPreCount = 0;
            btnPressed = 1;
            btnPrevious.btn_press();
            submenuOk = 0;
            dicOK = 0;
        }

        if (x > btnWordSelection.x && x < (btnWordSelection.x + btnWordSelection.w * 2) && y > btnWordSelection.y && y < (btnWordSelection.y + btnWordSelection.h * 2)) {
            questionNumber = 0;
            answerButton = 0;
            btnSelectCount = 0;
            btnPressed = 1;
            submenuOk = 1;
            dicOK = 0;
            whatStudy = 0;
            submenuOk2 = 0;
        }

        if (x > btnMyNote.x && x < (btnMyNote.x + btnMyNote.w * 2) && y > btnMyNote.y && y < (btnMyNote.y + btnMyNote.h * 2)) {
            btnMyNote.btn_press();
            btnMyNoteCount = 0;
            btnPressed = 1;
            dicOK = 1;
            submenuOk = 0;
        }

        if (dicOK != 1) {
            if (x > btnExit.x && x < (btnExit.x + btnExit.w * 2) && y > btnExit.y && y < (btnExit.y + btnExit.h * 2)) {
                btnPressed = 1;
                System.exit(0);
                submenuOk = 0;
                dicOK = 0;
            }
        }

        if (x > btnRandom.x && x < (btnRandom.x + btnRandom.w * 2) && y > btnRandom.y && y < (btnRandom.y + btnRandom.h * 2)) {
            rand = Math.random();
            questionNumber = (int) ((rand * (numberOfQuestion + 2)));
            answerButton = 0;
            btnRanCount = 0;
            btnPressed = 1;
            btnRandom.btn_press();
            submenuOk = 0;
            dicOK = 0;
        }

        if (answerButton == 0) {
            if (dicOK == 0 && submenuOk == 0 && submenuOk2 == 0) {
                if (x > btnNum1.x && x < (btnNum1.x + btnNum1.w * 2) && y > btnNum1.y && y < (btnNum1.y + btnNum1.h * 2)) {
                    int sss = 0;
                    submenuOk = 0;
                    dicOK = 0;
                    answerUser = 1;
                    btnNum1.btn_press();
                    answerButton = 1;
                    wordSave = 1;
                    btnNum1Count = 0;
                    btnPressed = 1;
                    starIng = 1;
                    starX = btnNum1.x;
                    starY = btnNum1.y;

                    sss = Integer.parseInt(FileSplit0.questionNum[questionNumber][6].trim());

                    if (sss == answerUser) {
                        if (soundOk == 1)
                            StudyView.sdPool.play(StudyView.dingdongdaeng, 1, 1, 9, 0, 1);
                        oNumber++;
                    } else {
                        if (soundOk == 1)
                            StudyView.sdPool.play(StudyView.taeng, 1, 1, 9, 0, 1);
                        xNumber++;
                    }
                }
            }

            if (dicOK == 0 && submenuOk == 0 && submenuOk2 == 0) {
                if (x > btnNum2.x && x < (btnNum2.x + btnNum2.w * 2) && y > btnNum2.y && y < (btnNum2.y + btnNum2.h * 2)) {
                    int sss = 0;
                    submenuOk = 0;
                    dicOK = 0;
                    answerUser = 2;
                    btnNum2.btn_press();
                    answerButton = 1;
                    wordSave = 1;
                    btnNum2Count = 0;
                    btnPressed = 1;
                    starIng = 1;
                    starX = btnNum2.x;
                    starY = btnNum2.y;

                    sss = Integer.parseInt(FileSplit0.questionNum[questionNumber][6].trim());

                    if (sss == answerUser) {
                        if (soundOk == 1)
                            StudyView.sdPool.play(StudyView.dingdongdaeng, 1, 1, 9, 0, 1);
                        oNumber++;
                    } else {
                        if (soundOk == 1)
                            StudyView.sdPool.play(StudyView.taeng, 1, 1, 9, 0, 1);
                        xNumber++;
                    }
                }
            }

            if (dicOK == 0 && submenuOk == 0 && submenuOk2 == 0) {
                if (x > btnNum3.x && x < (btnNum3.x + btnNum3.w * 2) && y > btnNum3.y && y < (btnNum3.y + btnNum3.h * 2)) {
                    int sss = 0;
                    submenuOk = 0;
                    dicOK = 0;
                    answerUser = 3;
                    btnNum3.btn_press();
                    answerButton = 1;
                    wordSave = 1;
                    btnNum3Count = 0;
                    btnPressed = 1;
                    starIng = 1;
                    starX = btnNum3.x;
                    starY = btnNum3.y;

                    sss = Integer.parseInt(FileSplit0.questionNum[questionNumber][6].trim());

                    if (sss == answerUser) {
                        if (soundOk == 1)
                            StudyView.sdPool.play(StudyView.dingdongdaeng, 1, 1, 9, 0, 1);
                        oNumber++;
                    } else {
                        if (soundOk == 1)
                            StudyView.sdPool.play(StudyView.taeng, 1, 1, 9, 0, 1);
                        xNumber++;
                    }
                }
            }

            if (dicOK == 0 && submenuOk == 0 && submenuOk2 == 0) {
                if (x > btnNum4.x && x < (btnNum4.x + btnNum4.w * 2) && y > btnNum4.y && y < (btnNum4.y + btnNum4.h * 2)) {
                    int sss = 0;
                    submenuOk = 0;
                    dicOK = 0;
                    answerUser = 4;
                    btnNum4.btn_press();
                    answerButton = 1;
                    wordSave = 1;
                    btnNum4Count = 0;
                    btnPressed = 1;
                    starIng = 1;
                    starX = btnNum4.x;
                    starY = btnNum4.y;

                    sss = Integer.parseInt(FileSplit0.questionNum[questionNumber][6].trim());

                    if (sss == answerUser) {
                        if (soundOk == 1)
                            StudyView.sdPool.play(StudyView.dingdongdaeng, 1, 1, 9, 0, 1);
                        oNumber++;
                    } else {
                        if (soundOk == 1)
                            StudyView.sdPool.play(StudyView.taeng, 1, 1, 9, 0, 1);
                        xNumber++;
                    }
                }
            }
        }
        if (answerButton == 1) {
            if (x > btnNextQuestion.x && x < (btnNextQuestion.x + btnNextQuestion.w * 2) && y > btnNextQuestion.y && y < (btnNextQuestion.y + btnNextQuestion.h * 2)) {
                btnNextQuestion.btn_press();
                questionNumber += 1;
                answerButton = 0;
                submenuOk = 0;
            }
        }

        if (answerButton == 1) {
            if (x > btnSolveAgain.x && x < (btnSolveAgain.x + btnSolveAgain.w * 2) && y > btnSolveAgain.y && y < (btnSolveAgain.y + btnSolveAgain.h * 2)) {
                btnSolveAgain.btn_press();
                answerButton = 0;
                submenuOk = 0;
            }
        }

        if (answerButton == 1 && wordSave == 1) {
            if (x > btnWordSave.x && x < (btnWordSave.x + btnWordSave.w * 2) && y > btnWordSave.y && y < (btnWordSave.y + btnWordSave.h * 2)) {
                btnWordSave.btn_press();
                submenuOk = 0;

                int sss = Integer.parseInt(FileSplit0.questionNum[questionNumber][6].trim());

                dicOK = 0;
                SQLiteDatabase db = m_helper.getWritableDatabase();
                String sql = String.format("INSERT INTO englishWordTable VALUES(NULL, '%s', '%s');",
                        FileSplit0.questionNum[questionNumber][sss + 1],
                        FileSplit0.questionNum[questionNumber][1]);
                db.execSQL(sql);
                db.close();

                Toast toast = Toast.makeText(mContext, "단어가 저장되었습니다.", Toast.LENGTH_SHORT);
                toast.show();
            }
        }

        if (submenuOk == 0 && answerButton == 0) {
            if (x > btnKakaoQSending.x && x < (btnKakaoQSending.x + btnKakaoQSending.w * 2) && y > btnKakaoQSending.y && y < (btnKakaoQSending.y + btnKakaoQSending.h * 2)) {
                controlButton();
            }
        }

        if (submenuOk == 1) {
            if (x > btnSub1.x && x < (btnSub1.x + btnSub1.w * 2) && y > btnSub1.y && y < (btnSub1.y + btnSub1.h * 2)) {
                btnSub1.btn_press();
                submenuOk = 0;
                whichSubject = "선택단어 1";
                int subNumber = 1;
                makeQuestion(subNumber);
            }
        }

        if (submenuOk == 1) {
            if (x > btnSub2.x && x < (btnSub2.x + btnSub2.w * 2) && y > btnSub2.y && y < (btnSub2.y + btnSub2.h * 2)) {
                btnSub2.btn_press();
                submenuOk = 0;
                whichSubject = "선택단어 2";
                int subNumber = 2;
                makeQuestion(subNumber);
            }
        }

        if (submenuOk == 1) {
            if (x > btnSub3.x && x < (btnSub3.x + btnSub3.w * 2) && y > btnSub3.y && y < (btnSub3.y + btnSub3.h * 2)) {
                btnSub3.btn_press();
                submenuOk = 0;
                whichSubject = "선택단어 3";
                int subNumber = 3;
                makeQuestion(subNumber);
            }
        }

        if (submenuOk == 1) {
            if (x > btnSub4.x && x < (btnSub4.x + btnSub4.w * 2) && y > btnSub4.y && y < (btnSub4.y + btnSub4.h * 2)) {
                btnSub4.btn_press();
                submenuOk = 0;
                whichSubject = "선택단어 4";
                int subNumber = 4;
                makeQuestion(subNumber);
            }
        }

        if (submenuOk == 1) {
            if (x > btnSub5.x && x < (btnSub5.x + btnSub5.w * 2) && y > btnSub5.y && y < (btnSub5.y + btnSub5.h * 2)) {
                btnSub5.btn_press();
                submenuOk = 0;
                whichSubject = "선택단어 5";
                int subNumber = 5;
                makeQuestion(subNumber);
            }
        }

        if (submenuOk == 1) {
            if (x > btnSub6.x && x < (btnSub6.x + btnSub6.w * 2) && y > btnSub6.y && y < (btnSub6.y + btnSub6.h * 2)) {
                btnSub6.btn_press();
                submenuOk = 0;
                whichSubject = "선택단어 6";
                int subNumber = 6;
                makeQuestion(subNumber);
            }
        }

        if (submenuOk == 1) {
            if (x > btnSub7.x && x < (btnSub7.x + btnSub7.w * 2) && y > btnSub7.y && y < (btnSub7.y + btnSub7.h * 2)) {
                btnSub7.btn_press();
                submenuOk = 0;
                whichSubject = "선택단어 7";
                int subNumber = 7;
                makeQuestion(subNumber);
            }
        }

        if (submenuOk == 1) {
            if (x > btnSub8.x && x < (btnSub8.x + btnSub8.w * 2) && y > btnSub8.y && y < (btnSub8.y + btnSub8.h * 2)) {
                btnSub8.btn_press();
                submenuOk = 0;
                whichSubject = "선택단어 8";
                int subNumber = 8;
                makeQuestion(subNumber);
            }
        }

        if (dicOK == 1) {
            if (x > btnLeftArrow.x && x < (btnLeftArrow.x + btnLeftArrow.w * 2) && y > btnLeftArrow.y && y < (btnLeftArrow.y + btnLeftArrow.h * 2)) {
                btnLeftArrow.btn_press();
                submenuOk = 0;
                dicOK = 1;
                movePosition -= 5;
                if (movePosition < 0) movePosition = 0;
            }
        }

        if (dicOK == 1) {
            if (x > btnRightArrow.x && x < (btnRightArrow.x + btnRightArrow.w * 2) && y > btnRightArrow.y && y < (btnRightArrow.y + btnRightArrow.h * 2)) {
                btnRightArrow.btn_press();
                submenuOk = 0;
                movePosition += 5;
            }
        }

        if (dicOK == 1 || submenuOk == 1 || submenuOk2 == 1) {
            if (x > btnClose.x && x < (btnClose.x + btnClose.w * 2) && y > btnClose.y && y < (btnClose.y + btnClose.h * 2)) {
                try {
                    Thread.sleep(120);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                btnClose.btn_press();
                submenuOk = 0;
                submenuOk2 = 0;
                dicOK = 0;
            }
        }

        if (dicOK == 1) {
            if (x > btnForDictionary[0].x && x < (btnForDictionary[0].x + btnForDictionary[0].w * 2)
                    && y > btnForDictionary[0].y && y < (btnForDictionary[0].y + btnForDictionary[0].h * 2)) {
                if (wordForDelete[0] != null) wordToDelete = wordForDelete[0];
            }

            if (x > btnForDictionary[1].x && x < (btnForDictionary[1].x + btnForDictionary[1].w * 2)
                    && y > btnForDictionary[1].y && y < (btnForDictionary[1].y + btnForDictionary[1].h * 2)) {
                if (wordForDelete[1] != null) wordToDelete = wordForDelete[1];
            }

            if (x > btnForDictionary[2].x && x < (btnForDictionary[2].x + btnForDictionary[2].w * 2)
                    && y > btnForDictionary[2].y && y < (btnForDictionary[2].y + btnForDictionary[2].h * 2)) {
                if (wordForDelete[2] != null) wordToDelete = wordForDelete[2];
            }

            if (x > btnForDictionary[3].x && x < (btnForDictionary[3].x + btnForDictionary[3].w * 2)
                    && y > btnForDictionary[3].y && y < (btnForDictionary[3].y + btnForDictionary[3].h * 2)) {
                if (wordForDelete[3] != null) wordToDelete = wordForDelete[3];
            }

            if (x > btnForDictionary[4].x && x < (btnForDictionary[4].x + btnForDictionary[4].w * 2)
                    && y > btnForDictionary[4].y && y < (btnForDictionary[4].y + btnForDictionary[4].h * 2)) {
                if (wordForDelete[4] != null) wordToDelete = wordForDelete[4];
            }

            SQLiteDatabase db = m_helper.getWritableDatabase();
            String sql = String.format("DELETE FROM englishWordTable WHERE eWord = '%s'", wordToDelete);
            db.execSQL(sql);
            try {
                Thread.sleep(130);
            } catch (InterruptedException e) {
            }
            db.close();
        }
        return gestureScanner.onTouchEvent(event);
    }

    public void controlButton() {
        ArrayList<Map<String, String>> metaInfoArray = new ArrayList<Map<String, String>>();
        Map<String, String> metaInfoAndroid = new Hashtable<String, String>();
        metaInfoAndroid.put("os", "android");
        metaInfoAndroid.put("devicetype", "phone");
        metaInfoAndroid.put("installurl", "m");
        metaInfoAndroid.put("executeurl", "csc");

        Map<String, String> metaInfoIOS = new Hashtable<String, String>();
        metaInfoIOS.put("os", "ios");
        metaInfoIOS.put("devicetype", "phone");
        metaInfoIOS.put("installurl", "your iOS app install url");
        metaInfoIOS.put("executeurl", "");
        metaInfoArray.add(metaInfoAndroid);
        metaInfoArray.add(metaInfoIOS);

        KaKaoLink kakaoLink = KaKaoLink.getLink(mContext);
        if (!kakaoLink.isAvailableIntent()) {
            return;
        }
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "친구야 영어단어문제를 풀어보렴~" + '\n');
            intent.putExtra(Intent.EXTRA_TEXT, "(문제) " + FileSplit0.questionNum[questionNumber][1] + "?" + '\n'
                    + "1번" + FileSplit0.questionNum[questionNumber][2] + " \n"
                    + "2번" + FileSplit0.questionNum[questionNumber][3] + " \n"
                    + "3번" + FileSplit0.questionNum[questionNumber][4] + " \n"
                    + "4번" + FileSplit0.questionNum[questionNumber][5]);
            intent.setPackage("con.kakao.talk");
            mContext.startActivity(intent);
        } catch (Exception e) {

        }
        return;
    }

    public void controlButton2() {
        ArrayList<Map<String, String>> metaInfoArray = new ArrayList<Map<String, String>>();
        Map<String, String> metaInfoAndroid = new Hashtable<String, String>();
        metaInfoAndroid.put("os", "android");
        metaInfoAndroid.put("devicetype", "phone");
        metaInfoAndroid.put("installurl", "m");
        metaInfoAndroid.put("executeurl", "csc");

        Map<String, String> metaInfoIOS = new Hashtable<String, String>();
        metaInfoIOS.put("os", "ios");
        metaInfoIOS.put("devicetype", "phone");
        metaInfoIOS.put("installurl", "your iOS app install url");
        metaInfoIOS.put("executeurl", "");
        metaInfoArray.add(metaInfoAndroid);
        metaInfoArray.add(metaInfoIOS);

        KaKaoLink kakaoLink = KaKaoLink.getLink(mContext);
        if (!kakaoLink.isAvailableIntent()) {
            return;
        }
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "정답을 보내요" + '\n');
            intent.putExtra(Intent.EXTRA_TEXT, "(정답) " + FileSplit0.questionNum[questionNumber][1] + "?" + '\n'
                    + "" + FileSplit0.questionNum[questionNumber][6] + " \n"
                    + "" + FileSplit0.questionNum[questionNumber][7] + " \n"
                    + "" + FileSplit0.questionNum[questionNumber][8]);
            intent.setPackage("con.kakao.talk");
            mContext.startActivity(intent);
        } catch (Exception e) {

        }
        return;
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
                    break;
            }
        }
        return false;
    }

    class MyDBHelper extends SQLiteOpenHelper {
        public MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE englishWordTable (_id INTEGER PRIMARY KEY AUTOINCREMENT," + "eWord TEXT, kWord TEXT);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS person");
            onCreate(db);
        }
    }

    @Override
    public void onGesture(GestureOverlayView arg0, MotionEvent arg1) {

    }

    @Override
    public void onGestureCancelled(GestureOverlayView arg0, MotionEvent arg1) {

    }

    @Override
    public void onGestureEnded(GestureOverlayView arg0, MotionEvent arg1) {

    }

    @Override
    public void onGestureStarted(GestureOverlayView arg0, MotionEvent arg1) {

    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        try {
            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                return false;
            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                questionNumber += 1;
                answerButton = 0;
            } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                questionNumber -= 1;
                answerButton = 0;
            } else if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {

            } else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {

            }
        } catch (Exception e) {

        }
        return true;
    }


}
