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

import static com.example.user.sunyeolscloset.MainActivity.talkview;


public class CustomView extends View {
    Canvas mCanvas;
    Paint mPaint;
    Bitmap mBitmap;

    Bitmap bodyBmap;
    Bitmap shirtBmap1;
    Matrix bodymtx;
    Matrix shirtmtx1;
    Bitmap slipperbm;
    Matrix slippermt;
    Bitmap glassesbm;
    Bitmap hanbokbm;
    Matrix hanbokmt;
    Matrix glassesmt;
    boolean randomlist[] = new boolean[10];

    static int whatItem = 0;

    int oldx = -1;
    int oldy = -1;

    GestureDetector mDetector;

    public CustomView(Context context) {
        super(context);
    }
    public CustomView(final Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mPaint = new Paint();
        mPaint.setStrokeWidth(5);
        for(int i = 0; i< randomlist.length ; i++) {
            randomlist[i]=false;
        }

        mDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onDown(MotionEvent e){
                if(getPoint(shirtmtx1,shirtBmap1,e.getX(),e.getY())) {
                    setItemNum(2);
                } else if(getPoint(slippermt,slipperbm,e.getX(),e.getY())){
                    setItemNum(4);
                }
                return true;
            }
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {//스크롤제스쳐이벤트
                Log.d("myapp",""+whatItem);
                MainActivity.stressUp();
                // /scrollBy((int)distanceX,(int)distanxeY);
                if(whatItem==1){
                   // 대사함수//몸 :건들지마, 이상한 옷 : 그걸 입으라고?
                }
                switch (whatItem){
                case 1:
                    bodymtx.postTranslate(-distanceX, -distanceY);//매트릭스를 원하는 위치로 이동한다
                    invalidate();
                    break;
                case 2:
                    shirtmtx1.postTranslate(-distanceX, -distanceY);
                    invalidate();
                    break;
                case 4:
                        slippermt.postTranslate(-distanceX, -distanceY);
                        invalidate();
                        break;
                case 5:
                    glassesmt.postTranslate(-distanceX, -distanceY);
                    invalidate();
                    break;
                case 6:
                    hanbokmt.postTranslate(-distanceX, -distanceY);
                    invalidate();
                    break;
                }
                return true;
            }
            @Override
            public boolean onDoubleTap(MotionEvent e) {//더블클릭제스쳐
                //matrix.postScale(1.5f, 1.5f);
               // invalidate();
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

        bodyBmap = BitmapFactory.decodeResource(getResources(), R.drawable.bodyimg);
        shirtBmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.shirt);
        slipperbm = BitmapFactory.decodeResource(getResources(), R.drawable.slipper);
        glassesbm = BitmapFactory.decodeResource(getResources(), R.drawable.glasses);
        hanbokbm = BitmapFactory.decodeResource(getResources(), R.drawable.hanbok);
        bodymtx = new Matrix();
        shirtmtx1 = new Matrix();
        slippermt = new Matrix();
        hanbokmt = new Matrix();
        glassesmt = new Matrix();
        bodymtx.reset();
        shirtmtx1.reset();
        slippermt.reset();
        hanbokmt.reset();
        glassesmt.reset();
      //  bodymtx.postScale(0.5f,0.5f);
      //  bodymtx.postTranslate((mCanvas.getWidth()-bodyBmap.getWidth()/2)/2,(mCanvas.getHeight()-bodyBmap.getHeight()/2)/2); 중심에 출력
        shirtmtx1.postScale(0.5f,0.5f);
        shirtmtx1.postTranslate(10,300);
        slippermt.postScale(0.5f,0.5f);
        slippermt.postTranslate(0,1300);
        hanbokmt.postScale(0.5f,0.5f);
        glassesmt.postScale(0.5f,0.5f);
        glassesmt.postTranslate(10,500);

    }

    public void setItemNum(int itemNum){
        whatItem = itemNum;
        invalidate();// 변경이있으니 다시 그리기!!
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // canvas.drawColor(Color.WHITE); // 배경색
        canvas.save();
        // canvas.drawBitmap(bodyBmap, bodymtx, mPaint);
        canvas.drawBitmap(shirtBmap1, shirtmtx1, mPaint);
        canvas.drawBitmap(slipperbm, slippermt, mPaint);
        canvas.drawBitmap(mBitmap, 0, 0, null);
        if(randomlist[0]){
            canvas.drawBitmap(glassesbm, glassesmt, mPaint);
        }
        if(randomlist[1]){
            canvas.drawBitmap(glassesbm, glassesmt, mPaint);
        }
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(whatItem==3) {
            int X = (int) event.getX();
            int Y = (int) event.getY();

            if (event.getAction() == MotionEvent.ACTION_DOWN) { //터치 했을 때
                oldx = X;
                oldy = Y;
            } else if (event.getAction() == MotionEvent.ACTION_MOVE) { //터치 후 움직였을 때
                if (oldx != -1) {
                    mCanvas.drawLine(oldx, oldy, X, Y, mPaint);
                    invalidate();
                    oldx = X;
                    oldy = Y;
                }
            } else if (event.getAction() == MotionEvent.ACTION_UP) { // 손을 땠을때
                if (oldx != -1) {
                    mCanvas.drawLine(oldx, oldy, X, Y, mPaint);
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



    void penBlack(){
        mPaint.setColor(Color.BLACK);
    }
   void penRed(){
        mPaint.setColor(Color.parseColor("#ef3f36"));
   }
    void penyellow(){
        mPaint.setColor(Color.parseColor("#ffd33f"));
    }
   void penGreen(){
        mPaint.setColor(Color.parseColor("#008a70"));
    }
    void penBlue(){
         mPaint.setColor(Color.parseColor("#004477"));
     }
  void eraser() {
      mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
      mPaint.setStrokeWidth(30);
      mPaint.setAntiAlias(true);
      invalidate();
  }
    void remover(){
      //  mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
     //   mCanvas.drawBitmap();
        mCanvas.setBitmap(mBitmap);
        invalidate();
    }

    boolean getPoint(Matrix matrix, Bitmap bitmap, float x, float y){
        float[] value = new float[9];
        matrix.getValues(value);
        Log.d("myapp", "value[5]: " + value[5] + "높이:" + (value[5] + (bitmap.getHeight() * value[4])));
        Log.d("myapp", "value[2]: " + value[2] + "넓이:" + (value[2] + (bitmap.getWidth() * value[0])));
        if(value[2] < x && x < value[2]+(bitmap.getWidth())*value[0] && value[5] < y && y < value[5]+(bitmap.getHeight()*value[4]) ){
            return true;
        }
        return false;
    }


}
