package com.lx.imagedemo;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {
    private ImageView mImageView;
    private GridLayout mGridLayout;
    private Button btn_ok;
    private Button btn_reset;
    private SeekBar sb_baohe,sb_huidu,sb_sediao;
    private Bitmap mBitmap,imageViewBitmap;

    private EditText[] mEditTexts = new EditText[20];
    private float [] mcolorMatrix = new float[20];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();


    }

    private void initView(){
        btn_ok = (Button)findViewById(R.id.okbtn);
        btn_reset = (Button) findViewById(R.id.resetbtn);
        mGridLayout = (GridLayout)findViewById(R.id.girdlayout);
        mImageView = (ImageView)findViewById(R.id.imageView);
        sb_baohe = (SeekBar)findViewById(R.id.seekbar_baohe);
        sb_huidu = (SeekBar)findViewById(R.id.seekbar_huidu);
        sb_sediao = (SeekBar)findViewById(R.id.seekbar_sediao);

        mGridLayout.post(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<20;i++){
                    EditText editText = new EditText(MainActivity.this);
                    editText.setGravity(Gravity.CENTER);
                    if(i%6==0)
                        editText.setText("1");
                    else editText.setText("0");
                    mEditTexts[i] = editText;
                    mGridLayout.addView(editText,mGridLayout.getWidth()/5,
                            mGridLayout.getHeight()/4);
            }
        }
        });

        Resources r = MainActivity.this.getResources();
        mBitmap = BitmapFactory.decodeResource(r,R.mipmap.timg);

        btn_ok.setOnClickListener(btnclickListener);
        btn_reset.setOnClickListener(btnclickListener);
        sb_sediao.setOnSeekBarChangeListener(seekbarChangeListener);
        sb_huidu.setOnSeekBarChangeListener(seekbarChangeListener);
        sb_baohe.setOnSeekBarChangeListener(seekbarChangeListener);


    }
    View.OnClickListener btnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.okbtn:
                    ColorMatrix colorMatrix = new ColorMatrix(getColorMatrix());
                    mImageView.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
                    break;
                case R.id.resetbtn:

                    ColorMatrix colorMatrix1 = new ColorMatrix(resetColorMatrix());
                    mImageView.setColorFilter(new ColorMatrixColorFilter(colorMatrix1));
                    sb_baohe.setProgress(10);
                    sb_huidu.setProgress(10);
                    sb_sediao.setProgress(180);
                    break;
                    default:
            }
        }
    };

    SeekBar.OnSeekBarChangeListener seekbarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            ColorMatrix colorMatrix = new ColorMatrix(getColorMatrix());
            colorMatrix = new ColorMatrix(getColorMatrix());
            switch (seekBar.getId()){
                case R.id.seekbar_baohe:
                    colorMatrix.setSaturation(i/10.0f);
                    mImageView.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
                    break;
                case R.id.seekbar_huidu:
                    float lum = i/20.0f;
                    colorMatrix.setScale(lum,lum,lum,1);
                    mImageView.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
                    break;
                case R.id.seekbar_sediao:
                    float hue = i/20.0f;
                    colorMatrix.setRotate(0,hue);
                    colorMatrix.setRotate(1,hue);
                    colorMatrix.setRotate(2,hue);
                    mImageView.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
                    break;
                default:
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private float[] getColorMatrix(){
        for(int i=0;i<20;i++){
            mcolorMatrix[i] = Float.parseFloat(mEditTexts[i].getText().toString());
        }
        return mcolorMatrix;
    }

    private float[] resetColorMatrix(){
        for(int i=0;i<20;i++){
            if(i%6==0) {
                mcolorMatrix[i] = 1;
                mEditTexts[i].setText("1");
            }
            else
            {
                mcolorMatrix[i] = 0;
                mEditTexts[i].setText("0");
            }
        }

        return mcolorMatrix;
    }



}
