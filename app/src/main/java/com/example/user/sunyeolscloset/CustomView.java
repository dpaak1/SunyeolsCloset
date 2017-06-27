package com.example.user.sunyeolscloset;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.user.sunyeolscloset.MainActivity.talkview;


public class CustomView extends View {
    Canvas mCanvas;
    Paint mPaint;
    Paint mPaintforPen;
    Bitmap mBitmap;
    ///--item
    Bitmap shirtBmap1;
    Matrix shirtmtx1;
    Bitmap slipperbm;
    Matrix slippermt;
    Bitmap glassesbm;
    Matrix glassesmt;
    Bitmap hanbokbm;
    Matrix hanbokmt;
    Bitmap blackshbm;
    Matrix blackshmt;
    Bitmap blueshbm;
    Matrix blueshmt;
    Bitmap blackstbm;
    Matrix blackstmt;
    Bitmap blackjbm;
    Matrix blackjmt;
    Bitmap oldjbm;
    Matrix oldjmt;
    Bitmap hairbm;
    Matrix hairmt;

    static ArrayList<ClothesData> items = new ArrayList<ClothesData>();
    // boolean randomlist[] = new boolean[10];
    static int whatItem = 0;

    //---pen Gesture
    int oldx = -1;
    int oldy = -1;

    GestureDetector mDetector;

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(final Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaintforPen = new Paint();
        mPaintforPen.setStrokeWidth(5);//펜굵기

        mDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                invalidate();
                for (int i = 0; i < items.size(); i++) {
                    if(items.get(i).itemVisible==true){ // 모든 아이템 검사하던걸 보이는아이템만 검사함으로써 속도문제 개선!!! 굿굿
                   if(getPoint(items.get(i), e.getX(), e.getY())){
                       setItemNum(items.get(i).itemID);
                   }}
                }
                return true;
            }
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {//스크롤제스쳐이벤트
                Log.d("myapp", "" + whatItem);
                MainActivity.stressUp(1);
                // /scrollBy((int)distanceX,(int)distanxeY);
                if(whatItem==43){
                    talkview.setText("적당히해라..");
                    MainActivity.stressUp(10);
                }
                if(whatItem!=0){
                getClothesData(whatItem).itemMatrix.postTranslate(-distanceX, -distanceY);//매트릭스를 원하는 위치로 이동한다
                invalidate();}
                return true;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {//더블클릭제스쳐
                talkview.setText("건드리지마ㅡㅡ");
                return true;
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) { //더블버퍼링//초기 클래스가 실행될 때 한번만 실행
        super.onSizeChanged(w, h, oldw, oldh);
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas();
        mCanvas.setBitmap(mBitmap);

   //     bodyBmap = BitmapFactory.decodeResource(getResources(), R.drawable.bodyimg);
        shirtBmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.shirt);
        slipperbm = BitmapFactory.decodeResource(getResources(), R.drawable.slipper);
        glassesbm = BitmapFactory.decodeResource(getResources(), R.drawable.glasses);
        hanbokbm = BitmapFactory.decodeResource(getResources(), R.drawable.hanbok);
       blackshbm =BitmapFactory.decodeResource(getResources(), R.drawable.blackshoes);
        blueshbm = BitmapFactory.decodeResource(getResources(), R.drawable.blueshoes);
        blackstbm =BitmapFactory.decodeResource(getResources(), R.drawable.blackshirt);
       blackjbm = BitmapFactory.decodeResource(getResources(), R.drawable.blackjean);
        oldjbm = BitmapFactory.decodeResource(getResources(), R.drawable.oldjacket);
        hairbm =BitmapFactory.decodeResource(getResources(), R.drawable.hair);

     //   bodymtx = new Matrix();
        shirtmtx1 = new Matrix();
        slippermt = new Matrix();
        hanbokmt = new Matrix();
        glassesmt = new Matrix();
     blackshmt = new Matrix();
        blueshmt = new Matrix();
        blackstmt = new Matrix();
        blackjmt = new Matrix();
        oldjmt = new Matrix();
        hairmt = new Matrix();
        //  bodymtx.postScale(0.5f,0.5f);
        //  bodymtx.postTranslate((mCanvas.getWidth()-bodyBmap.getWidth()/2)/2,(mCanvas.getHeight()-bodyBmap.getHeight()/2)/2); 중심에 출력

        itemInit();

    }
    public static void setItemTrue(int itemNum){
            getClothesData(itemNum).itemVisible = true;
    }

    public void setItemNum(int itemNum) {
        whatItem = itemNum;//펜을위해남김
        invalidate();// 변경이있으니 다시 그리기!!
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // canvas.drawColor(Color.WHITE); // 배경색
        canvas.save();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).itemVisible == true) {
                canvas.drawBitmap(items.get(i).itemBitmap, items.get(i).itemMatrix, mPaint);
            }
        }
        canvas.drawBitmap(mBitmap, 0, 0, null);

        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (whatItem == 9) {
            int X = (int) event.getX();
            int Y = (int) event.getY();

            if (event.getAction() == MotionEvent.ACTION_DOWN) { //터치 했을 때
                oldx = X;
                oldy = Y;
            } else if (event.getAction() == MotionEvent.ACTION_MOVE) { //터치 후 움직였을 때
                if (oldx != -1) {
                    mCanvas.drawLine(oldx, oldy, X, Y, mPaintforPen );
                    invalidate();
                    oldx = X;
                    oldy = Y;
                }
            } else if (event.getAction() == MotionEvent.ACTION_UP) { // 손을 땠을때
                if (oldx != -1) {
                    mCanvas.drawLine(oldx, oldy, X, Y, mPaintforPen);
                    invalidate();
                }
                //  if (check) drawStamp(X, Y);
                oldx = -1;
                oldy = -1;
            }
            return true;
        }
        boolean consumed = mDetector.onTouchEvent(event);
        return consumed || super.onTouchEvent(event);
    }


    void penBlack() {
        mPaintforPen.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DARKEN));
        mPaintforPen .setStrokeWidth(5);
        mPaintforPen .setColor(Color.BLACK);
    }

    void penRed() {
        mPaintforPen.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DARKEN));
        mPaintforPen .setStrokeWidth(5);
        mPaintforPen .setColor(Color.parseColor("#ef3f36"));
    }

    void penyellow() {

        mPaintforPen.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DARKEN));
        mPaintforPen .setStrokeWidth(5);
        mPaintforPen .setColor(Color.parseColor("#ffd33f"));
    }

    void penGreen() {
        mPaintforPen.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DARKEN));
        mPaintforPen .setStrokeWidth(5);
        mPaintforPen .setColor(Color.parseColor("#008a70"));
    }

    void penBlue() {
        mPaintforPen.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DARKEN));
        mPaintforPen .setStrokeWidth(5);
        mPaintforPen .setColor(Color.parseColor("#004477"));
    }

    void eraser() {
        mPaintforPen .setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        mPaintforPen .setStrokeWidth(50);
        mPaintforPen .setAntiAlias(true);
        invalidate();
    }


    boolean getPoint(ClothesData clothesData, float x, float y) {
        float[] value = new float[9];
        clothesData.itemMatrix.getValues(value);
        Log.d("myapp", "value[5]: " + value[5] + "높이:" + (value[5] + (clothesData.itemBitmap.getHeight() * value[4])));
        Log.d("myapp", "value[2]: " + value[2] + "넓이:" + (value[2] + (clothesData.itemBitmap.getWidth() * value[0])));
        if (value[2] < x && x < value[2] + (clothesData.itemBitmap.getWidth()) * value[0] && value[5] < y && y < value[5] + (clothesData.itemBitmap.getHeight() * value[4])) {
            return true;
        }
        return false;
    }


    void additems(Bitmap bitmap, Matrix matrix, int ID, boolean itemVisible) {
        ClothesData clothesData = new ClothesData();
        clothesData.itemBitmap = bitmap;
        clothesData.itemMatrix = matrix;
        clothesData.itemID = ID;
        clothesData.itemVisible = itemVisible;
        items.add(clothesData);
    }

    void itemInit() {
        shirtmtx1.postTranslate(10, 600);
        slippermt.postTranslate(0, 2800);
        glassesmt.postTranslate(10, 2000);
        blackjmt.postTranslate(10, 1500);
        hanbokmt.postTranslate(0, 1000);
        blueshmt.postTranslate(0, 3000);

        additems(slipperbm, slippermt, 10, true);//슬피퍼
        additems(blackshbm,blackshmt, 11, false);//검은구두
        additems(blueshbm,blueshmt, 12, true);//파란운동화
        additems(blackjbm, blackjmt, 20, true);//검은바지
        additems(shirtBmap1, shirtmtx1, 30, true);//흰셔츠
        additems(blackstbm,blackstmt, 31, false);//검은셔츠
        additems(glassesbm, glassesmt, 40, true);//안경
        additems(hanbokbm, hanbokmt, 41, false);//한복
        additems(oldjbm, oldjmt, 42, false);//옛날교복
        additems(hairbm, hairmt, 43, false);//머리방울

        for(int i=0;i<items.size();i++){
            items.get(i).itemMatrix.postScale(0.5f,0.5f);//스케일
        }

    }

    static ClothesData getClothesData(int ID) {
        for (int i = 0; i < items.size(); i++) {
            if(ID==items.get(i).itemID){
            return items.get(i);}
        }
        return null;
    }
}
