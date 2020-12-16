package com.example.bird;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class Game extends View {
  //  private Bitmap box;
    private Bitmap background;


    private Paint score = new Paint();

    private int points;

    private Bitmap life[] = new Bitmap[2];
    private int lifes;

    private int canvasW;
    private int canvasH;

    private Bitmap box[] = new Bitmap[2];
    private int boxX = 10;
    private int boxY;
    private int boxS;

    private boolean touchF = false;

    private int pointX;
    private int pointY;
    private int pointS = 14;
    private Paint pointPaint = new Paint();

    private int starX;
    private int starY;
    private int starS = 16;
    private Paint starPaint = new Paint();

    private Paint barricade = new Paint();
    private int barricadeX;
    private int barricadeLeft;
    private int barricadeY;
    private int barricadeS = 10;
    private int barricadeHeight = 100;
    final MediaPlayer mediaPlayer = MediaPlayer.create(getContext().getApplicationContext(), R.raw.hit1);
    final MediaPlayer mediaPlayer2 = MediaPlayer.create(getContext().getApplicationContext(), R.raw.gothit);
    final MediaPlayer mediaPlayer3 = MediaPlayer.create(getContext().getApplicationContext(), R.raw.point);
   // private SoundPool soundpool;
    //private int hitSound;

    Context myContext;
    DBhelper dBhelper;
    boolean wasAddedDb = false;
    public Game(Context context) {
        super(context);
        myContext = context;
        dBhelper = new DBhelper(context);

        box[0] = BitmapFactory.decodeResource(getResources(),R.drawable.box_right );
        box[1] = BitmapFactory.decodeResource(getResources(),R.drawable.box_right02_ );

        score.setColor(Color.BLUE);
        score.setTextSize(40);
        score.setTypeface(Typeface.DEFAULT_BOLD);
        life[0] = BitmapFactory.decodeResource(getResources(), R.drawable.heart);
        life[1] = BitmapFactory.decodeResource(getResources(), R.drawable.heart_g);
        background = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
        starPaint.setColor(Color.RED);
        pointPaint.setColor(Color.BLACK);
        barricade.setColor(Color.BLACK);

        boxY = 500;
        lifes = 3;
        points = 0;




    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvasW = canvas.getWidth();
        canvasH = canvas.getHeight();
        canvas.drawBitmap(background,0,0,null);

        int minBoxy = box[0].getHeight();
        int maxBoxy = canvasH - box[0].getHeight()* 3;
        boxY += boxS;
        if (boxY < minBoxy){
           boxY = minBoxy;
        }
        if (boxY > maxBoxy){
            boxY = maxBoxy;
        }

        boxS += 3;
        if (touchF) {
            canvas.drawBitmap(box[1],boxX,boxY, null );
            touchF = false;
            mediaPlayer.start();
        }else{
            canvas.drawBitmap(box[0], boxX, boxY, null);
        }


       // canvas.drawBitmap(box,0,0,null);

        canvas.drawText("Your score:"+ points ,20,67,score);
       // canvas.drawBitmap(life[0], 830, 30, null);
       // canvas.drawBitmap(life[0], 890, 30, null);
       // canvas.drawBitmap(life[1], 950, 30, null);
        canvas.drawCircle(pointX,pointY, 10, pointPaint);
        canvas.drawCircle(starX, starY, 15, starPaint);
        canvas.drawRect(barricadeX,barricadeY,barricadeLeft,barricadeHeight, barricade);



        for(int i=0; i<3; i++){
            int x = (int)(750 + life[0].getWidth()*1.5 *i );
            int y = 30;
            if (i< lifes){
                canvas.drawBitmap(life[0], x , y , null);
            } else {
                canvas.drawBitmap(life[1], x, y, null);
            }
        }

        starX -= starS;
        if(hitCheck(starX, starY)){
            starX = starX - 200;
            lifes--;
            mediaPlayer2.start();
            if(lifes == 0){
                menuBack();
                }
        } if (starX < 0){
            starX = canvasW + 200;
            starY = (int) Math.floor(Math.random() *(maxBoxy- minBoxy)) + minBoxy;
            //canvas.drawCircle(starX, starY, 15, starPaint);
        }

        pointX -= pointS;
        if(hitCheck(pointX, pointY))
        {
            points = points + 10;
            mediaPlayer3.start();
            pointX = -120;
        }
        if(pointX <0){
            pointX = canvasW + 20;
            pointY = (int) Math.floor(Math.random() *(maxBoxy- minBoxy)) + minBoxy;
        }

        barricadeX -= barricadeS;
        barricadeLeft = barricadeX + 10;
        if(hitCheckRect(barricadeY, barricadeHeight, barricadeLeft, barricadeX)){
            lifes = lifes - 3;
            if(lifes <= 0){
                menuBack();
            }
        } if (barricadeX < 0){
            barricadeX = canvasW + 200;
            barricadeY = (int) Math.floor(Math.random() *(maxBoxy- minBoxy)) + minBoxy;
        }


    }
    public boolean hitCheck(int x, int y){
        if(boxX<x && x<(boxX + box[0].getWidth() ) && boxY < y && y< (boxY + box[0].getHeight())){
            return true;}
        return false;
    }
    public boolean hitCheckRect(int x, int y, int z, int z2){
        if(boxY<x && boxY + box[0].getHeight()>y && boxX < z && (boxX + box[0].getWidth()> z2)){
            return true;}
        return false;
    }

    public void menuBack(){
        Intent back = new Intent(getContext(), Points.class);
        back.putExtra("Points", points);
        getContext().startActivity(back);
        if(wasAddedDb == false) {
            wasAddedDb = dBhelper.addData(points, "AAAA");
            Toast.makeText(myContext, "dBhelper INSERT", Toast.LENGTH_SHORT).show();
            Log.d("dBhelper", "dBhelper");
        }

       }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction()==MotionEvent.ACTION_DOWN){
            touchF = true;
            boxS = -28;
        }
        return true;
    }
}
