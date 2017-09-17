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
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

import static com.example.user.sunyeolscloset.MainActivity.talkview;

public class CustomView extends View {
    //for clothes item
    Canvas mCanvas;
    Paint mPaint;
    Bitmap mBitmap;
    //for pen
    Paint mPaintforPen;
    ///--item
    Bitmap whiteshbm;
    Matrix whiteshmt;
    Bitmap slipperbm;
    Matrix slippermt;
    Bitmap glassesbm;
    Matrix glassesmt;
    Bitmap hanbokbm;
    Matrix hanbokmt;
    Bitmap blackshobm;
    Matrix blackshomt;
    Bitmap blueshobm;
    Matrix blueshomt;
    Bitmap blackstbm;
    Matrix blackstmt;
    Bitmap blackjbm;
    Matrix blackjmt;
    Bitmap oldjbm;
    Matrix oldjmt;
    Bitmap hairbm;
    Matrix hairmt;

    static ArrayList<ClothesData> items = new ArrayList<ClothesData>();
    static int whatItem = 0;

    //---for pen Gesture
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
        mPaintforPen.setStrokeWidth(5);//처음 펜굵기

        mDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) { //터치제스쳐
                invalidate();
                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).itemVisible == true) {
                        // 모든 아이템 검사하던 것을 보이는 아이템만 검사함으로써 속도문제 개선!!!
                        if (getPoint(items.get(i), e.getX(), e.getY())) {
                            setItemNum(items.get(i).itemID);
                        }
                    }
                }
                return true;
            }
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {//스크롤제스쳐
                MainActivity.stressUp(1);
                if (whatItem != 0) {
                    if (whatItem == 43) { //특이 아이템
                        talkview.setText("적당히해라..");
                        MainActivity.stressUp(10);
                    }
                    getClothesData(whatItem).itemMatrix.postTranslate(-distanceX, -distanceY);//매트릭스를 원하는 위치로 이동한다
                    invalidate();
                }
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
    protected void onSizeChanged(int w, int h, int oldw, int oldh) { //초기 클래스가 실행될 때 한번만 실행 and 더블버퍼링
        super.onSizeChanged(w, h, oldw, oldh);
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas();
        mCanvas.setBitmap(mBitmap);

        whiteshbm = BitmapFactory.decodeResource(getResources(), R.drawable.shirt);
        slipperbm = BitmapFactory.decodeResource(getResources(), R.drawable.slipper);
        glassesbm = BitmapFactory.decodeResource(getResources(), R.drawable.glasses);
        hanbokbm = BitmapFactory.decodeResource(getResources(), R.drawable.hanbok);
        blackshobm = BitmapFactory.decodeResource(getResources(), R.drawable.blackshoes);
        blueshobm = BitmapFactory.decodeResource(getResources(), R.drawable.blueshoes);
        blackstbm = BitmapFactory.decodeResource(getResources(), R.drawable.blackshirt);
        blackjbm = BitmapFactory.decodeResource(getResources(), R.drawable.blackjean);
        oldjbm = BitmapFactory.decodeResource(getResources(), R.drawable.oldjacket);
        hairbm = BitmapFactory.decodeResource(getResources(), R.drawable.hair);

        whiteshmt = new Matrix();
        slippermt = new Matrix();
        hanbokmt = new Matrix();
        glassesmt = new Matrix();
        blackshomt = new Matrix();
        blueshomt = new Matrix();
        blackstmt = new Matrix();
        blackjmt = new Matrix();
        oldjmt = new Matrix();
        hairmt = new Matrix();
        //  bodymtx.postScale(0.5f,0.5f);
        //  bodymtx.postTranslate((mCanvas.getWidth()-bodyBmap.getWidth()/2)/2,(mCanvas.getHeight()-bodyBmap.getHeight()/2)/2); 중심에 출력

        itemInit(); //아이템 초기화

    }

    boolean getPoint(ClothesData clothesData, float x, float y) {
        float[] value = new float[9];
        clothesData.itemMatrix.getValues(value);
        Log.d("myapp", "value[5]: " + value[5] +
                "높이:" + (value[5] + (clothesData.itemBitmap.getHeight() * value[4])));
        Log.d("myapp", "value[2]: " + value[2] +
                "넓이:" + (value[2] + (clothesData.itemBitmap.getWidth() * value[0])));
        //터치한 포인트가 특정 아이템의 높이와 넓이 범위 안이면 true 리턴
        if (value[2] < x && x < value[2] + (clothesData.itemBitmap.getWidth()) * value[0] && value[5] < y
                && y < value[5] + (clothesData.itemBitmap.getHeight() * value[4])) {
            return true;
        }
        return false;
    }

    public static void setItemTrue(int itemNum) {
        getClothesData(itemNum).itemVisible = true;
    } // 아이템의 표시 여부 온오프

    public void setItemNum(int itemNum) { //선택된 아이템 인지
        whatItem = itemNum;
        invalidate();// 변경이 있으니 다시 그리기
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).itemVisible == true) { //표시가 true인 아이템만 그리기
                canvas.drawBitmap(items.get(i).itemBitmap, items.get(i).itemMatrix, mPaint);
            }
        }
        canvas.drawBitmap(mBitmap, 0, 0, null);
        canvas.restore();
    }

    //--for Pen
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
                    mCanvas.drawLine(oldx, oldy, X, Y, mPaintforPen);
                    invalidate();
                    oldx = X;
                    oldy = Y;
                }
            } else if (event.getAction() == MotionEvent.ACTION_UP) { // 손을 땠을때
                if (oldx != -1) {
                    mCanvas.drawLine(oldx, oldy, X, Y, mPaintforPen);
                    invalidate();
                }
                oldx = -1;
                oldy = -1;
            }
            return true;
        }
        boolean consumed = mDetector.onTouchEvent(event);
        return consumed || super.onTouchEvent(event);
    }

    void penColor(int color) {
        mPaintforPen.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DARKEN));
        mPaintforPen.setStrokeWidth(5);
        switch (color) {
            case R.id.blackpen:
                mPaintforPen.setColor(Color.BLACK);
                break;
            case R.id.redpen:
                mPaintforPen.setColor(Color.parseColor("#ef3f36"));
                break;
            case R.id.yellowpen:
                mPaintforPen.setColor(Color.parseColor("#ffd33f"));
                break;
            case R.id.greenpen:
                mPaintforPen.setColor(Color.parseColor("#008a70"));
                break;
            case R.id.bluepen:
                mPaintforPen.setColor(Color.parseColor("#004477"));
                break;
        }
    }

    void eraser() {
        mPaintforPen.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        mPaintforPen.setStrokeWidth(50);
        mPaintforPen.setAntiAlias(true);
        invalidate();
    }

    void additems(Bitmap bitmap, Matrix matrix, int ID, boolean itemVisible) { //아이템을 어레이리스트에 더하기
        ClothesData clothesData = new ClothesData();
        clothesData.itemBitmap = bitmap;
        clothesData.itemMatrix = matrix;
        clothesData.itemID = ID;
        clothesData.itemVisible = itemVisible;
        items.add(clothesData);
    }

    void itemInit() {
        whiteshmt.postTranslate(10, 700);
        slippermt.postTranslate(0, 2900);
        glassesmt.postTranslate(10, 2700);
        blackjmt.postTranslate(10, 1600);
        hanbokmt.postTranslate(0, 1200);
        oldjmt.postTranslate(0, 700);
        blueshomt.postTranslate(0, 3100);
        blackshomt.postTranslate(0, 3000);
        blackstmt.postTranslate(10, 800);
        hairmt.postTranslate(20, 2700);

        additems(slipperbm, slippermt, 10, true);//슬피퍼
        additems(blackshobm, blackshomt, 11, false);//검은구두
        additems(blueshobm, blueshomt, 12, true);//파란운동화
        additems(blackjbm, blackjmt, 20, true);//검은바지
        additems(whiteshbm, whiteshmt, 30, true);//흰셔츠
        additems(blackstbm, blackstmt, 31, false);//검은셔츠
        additems(glassesbm, glassesmt, 40, true);//안경
        additems(hanbokbm, hanbokmt, 41, false);//한복
        additems(oldjbm, oldjmt, 42, false);//옛날교복
        additems(hairbm, hairmt, 43, false);//머리방울

        for (int i = 0; i < items.size(); i++) {
            items.get(i).itemMatrix.postScale(0.5f, 0.5f);//스케일
        }

    }

    static ClothesData getClothesData(int ID) { //Id 숫자에 따른 clothes 객체를 리턴
        for (int i = 0; i < items.size(); i++) {
            if (ID == items.get(i).itemID) {
                return items.get(i);
            }
        }
        return null;
    }
}
