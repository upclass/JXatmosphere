package net.univr.pushi.jxatmosphere.feature;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.interfaces.CallBackUtil;
import net.univr.pushi.jxatmosphere.interfaces.Picdispath;
import net.univr.pushi.jxatmosphere.utils.PicUtils;

import java.util.ArrayList;
import java.util.List;


public class PicDealActivity extends Activity implements View.OnTouchListener {

    private float lastX[] = {0, 0};//用来记录上一次两个触点的横坐标
    private float lastY[] = {0, 0};//用来记录上一次两个触点的纵坐标

    private float windowWidth, windowHeight;//当前窗口的宽和高
    private float imageHeight, imageWidth;//imageview中图片的宽高（注意：不是imageview的宽和高）

    Context mContext;
    private static Matrix currentMatrix = new Matrix();//保存当前窗口显示的矩阵
    private Matrix touchMatrix, mmatrix;
    private boolean flag = false;//用来标记是否进行过移动前的首次点击
    private float moveLastX, moveLastY;//进行移动时用来记录上一个触点的坐标

    private static float max_scale = 4f;//缩放的最大值
    private static float min_scale = 1f;//缩放的最小值
    private ImageView image;
    List<String> urls = new ArrayList<>();
    String url;
    String pack;
    private Bitmap bitmap;
    Boolean isMove = false;
    List<Object> object;
    private long startTime = 0;
    private long endTime = 0;




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_deal);
        mContext = this;
        image = findViewById(R.id.image);
        CallBackUtil.doDispatchDarken();
        initView();
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        windowWidth = windowManager.getDefaultDisplay().getWidth();
        windowHeight = windowManager.getDefaultDisplay().getHeight();
        center();
    }


    /*
* 开始时将图片居中显示
*
*
* */
    private void center() {

        //获取imageview中图片的实际高度
        BitmapDrawable drawable = (BitmapDrawable) (image).getDrawable();
        if (drawable != null)
            bitmap = drawable.getBitmap();
        if (bitmap != null) {
            imageHeight = bitmap.getHeight();
            imageWidth = bitmap.getWidth();

            //变换矩阵，使其移动到屏幕中央
            Matrix matrix = new Matrix();
            matrix.postTranslate(windowWidth / 2 - imageWidth / 2, windowHeight / 2 - imageHeight / 2);
            //保存到currentMatrix
            if (currentMatrix != null) {
                currentMatrix.set(matrix);
                image.setImageMatrix(matrix);
            } else {
                currentMatrix = new Matrix();
                currentMatrix.set(matrix);
                image.setImageMatrix(matrix);
            }

        }

    }


    public void initView() {
        urls = getIntent().getStringArrayListExtra("urls");
        url = getIntent().getStringExtra("url");
        pack = getIntent().getStringExtra("pack");
        CallBackUtil.setPicdispath(new Picdispath() {
            @Override
            public void onDispatchPic(int position) {
                bitmap = PicUtils.readLocalImage(urls.get(position), pack, PicDealActivity.this);
                if (bitmap != null) {
                    image.setImageBitmap(bitmap);
                }
//                center();
            }
        });

        bitmap = PicUtils.readLocalImage(url, pack, PicDealActivity.this);
        if (bitmap != null) {
            image.setImageBitmap(bitmap);
            image.setOnTouchListener(this);
        }

    }


    @Override
    public boolean onTouch(View v, MotionEvent motionEvent) {

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            // 在这里解释一下，在程序中我们将单点控制移动和双点控制缩放区分开（但是双点也是可以
            // 控制移动的)flag 的作用很简单，主要是用在单点移动时判断是否此次点击是否将要移动（不好描述，请读者自行细想一下）
            // 否则容易与双点操作混乱在一起，给用户带来较差的用户体验
            case MotionEvent.ACTION_DOWN://第一个触点按下，将第一次的坐标保存下来
                startTime = System.currentTimeMillis();
                lastX[0] = motionEvent.getX(0);
                lastY[0] = motionEvent.getY(0);
                moveLastX = motionEvent.getX();
                moveLastY = motionEvent.getY();
                flag = true;//第一次点击，说明有可能要进行单点移动，flag设为true

                break;
            case MotionEvent.ACTION_POINTER_DOWN://第二个触点按下，保存下来
                lastX[1] = motionEvent.getX(1);
                lastY[1] = motionEvent.getY(1);
                flag = false;//第二次点击，说明要进行双点操作,而不是单点移动，所以设为false
                break;
            case MotionEvent.ACTION_MOVE:
                isMove = true;
                //计算上一次触点间的距离
                float lastDistance = getDistance(lastX[0], lastY[0], lastX[1], lastY[1]);

                //如果有两个触点，进行放缩操作
                if (motionEvent.getPointerCount() == 2) {
                    //得到当前触点之间的距离
                    float currentDistance = getDistance(motionEvent.getX(0), motionEvent.getY(0), motionEvent.getX(1), motionEvent.getY(1));
                    touchMatrix = new Matrix();
                    //矩阵初始化为当前矩阵
                    touchMatrix.set(currentMatrix);

                    float pp[] = new float[9];
                    touchMatrix.getValues(pp);
                    float leftPosition = pp[2];//图片左边的位置
                    float upPostion = pp[5];//图片顶部的位置
                    /*
                    * 缩放之前对图片进行平移，将缩放中心平移到将要缩放的位置
                    * */

                    float l = (motionEvent.getX(0) + motionEvent.getX(1)) / 2 - leftPosition;
                    float t = (motionEvent.getY(0) + motionEvent.getY(1)) / 2 - upPostion;

                    touchMatrix.postTranslate(-(currentDistance / lastDistance - 1) * l,
                            -(currentDistance / lastDistance - 1) * t);
                    float p[] = new float[9];
                    touchMatrix.getValues(p);
                    //根据判断当前缩放的大小来判断是否达到缩放边界
                    if (p[0] * currentDistance / lastDistance < min_scale || p[0] * currentDistance / lastDistance > max_scale) {
                        //超过边界值时，设置为先前记录的矩阵
                        touchMatrix.set(mmatrix);
                        image.setImageMatrix(touchMatrix);
                    } else {
                        //图像缩放
                        touchMatrix.preScale(currentDistance / lastDistance, currentDistance / lastDistance);
                        //根据两个触点移动的距离实现位移（双触点平移）
                        float movex = (motionEvent.getX(0) - lastX[0] + motionEvent.getX(1) - lastX[1]) / 2;
                        float movey = (motionEvent.getY(0) - lastY[0] + motionEvent.getY(1) - lastY[1]) / 2;
                        touchMatrix.postTranslate(movex, movey);
                        //保存最后的矩阵，当缩放超过边界值时就设置为此矩阵
                        mmatrix = touchMatrix;
                        image.setImageMatrix(touchMatrix);
                    }

                } else {
                    if (flag) {
                        //只有一个触点时进行位移
                        Matrix tmp = new Matrix();//临时矩阵用来判断此次平移是否会导致平移越界
                        tmp.set(currentMatrix);
                        tmp.postTranslate(-moveLastX + motionEvent.getX(0), -moveLastY + motionEvent.getY(0));

                        if (!isTranslateOver(tmp)) {
                            //如果不越界就进行平移
                            touchMatrix = new Matrix();
                            touchMatrix.set(currentMatrix);
                            touchMatrix.postTranslate(-moveLastX + motionEvent.getX(0), -moveLastY + motionEvent.getY(0));
                            image.setImageMatrix(touchMatrix);
                        } else {
                            //如果会越界就保存当前位置，并且不进行矩阵变换
                            currentMatrix = touchMatrix;
                            moveLastX = motionEvent.getX(0);
                            moveLastY = motionEvent.getY(0);
                            image.setImageMatrix(touchMatrix);
                        }

                    }
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                object=new ArrayList<>();
                object.add(new Object());
                object.add(new Object());
            case MotionEvent.ACTION_UP:
                endTime = System.currentTimeMillis();
                currentMatrix = touchMatrix;
                moveLastX = motionEvent.getX(0);
                moveLastY = motionEvent.getY(0);
                flag = false;
                //松开手时，保存当前矩阵，此时的位置保存下来
                //flag设为控制
                if(object!=null&&object.size()!=0)object.remove(0);
                else{
                    if(isMove==true){
                        if ((endTime - startTime) > 0.1 * 1000L) {
                          ;
                        }else{
                            finish();
                        }
                    }else{
                        finish();
                    }
                }
                isMove=false;
                break;
        }

        return true;
    }

    //得到两点之间的距离
    private float getDistance(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }

    //判断平移是否越界
    private boolean isTranslateOver(Matrix matrix) {
        float p[] = new float[9];
        matrix.getValues(p);
        float leftPosition = p[2];
        float rightPosition = (p[2] + imageWidth * p[0]);

        float upPostion = p[5];
        float downPostion = p[5] + imageHeight * p[0];

        float leftSide, rightSide, upSide, downSide;
        leftSide = windowWidth / 4;
        rightSide = windowWidth / 4 * 3;
        upSide = windowHeight / 4;
        downSide = windowHeight / 4 * 3;
        return (leftPosition > rightSide || rightPosition < leftSide || upPostion > downSide || downPostion < upSide);
    }


    @Override
    public void finish() {
        super.finish();
        CallBackUtil.picdispath = null;
        CallBackUtil.doDispatchLight();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bitmap != null) {
            bitmap.recycle();
        }
    }

    public Bitmap setImgSize(Bitmap bm, int newWidth, int newHeight) {
        // 获得图片的宽高.
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例.
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数.
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片.
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }

}
