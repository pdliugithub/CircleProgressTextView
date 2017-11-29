package com.rossia.life.circleprogresstextview;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.rossia.life.circleprogress.CircleProgressTextView;

public class MainActivity extends AppCompatActivity {

    private static final String KEY_TAG = "TAG";
    private static final int COUNT_DOWN = 360;

    private Button mShowBtn;
    private CircleProgressTextView mCircleProgressTextView;

    private Boolean mShowing;

    private View.OnClickListener mOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if(mShowBtn == v){
                //显示CircleProgressTextView进行展示效果
                mCircleProgressTextView.setVisibility(View.VISIBLE);
                mShowBtn.setVisibility(View.GONE);
                mShowing = true;
                //创建线程进行倒计时
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        int number = 0;
                        while (number < COUNT_DOWN) {
                            //暂停一秒
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            number++;
                            //设置当前进度
                            mCircleProgressTextView.setProgress(number * 1);
                        }
                        //进度加载完毕，进行“跳过”操作
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mShowing = false;
                                mShowBtn.setVisibility(View.VISIBLE);
                                mCircleProgressTextView.setVisibility(View.GONE);
                            }
                        });

                    }
                }).start();
                return;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        mShowBtn = (Button) findViewById(R.id.show_btn);
        mCircleProgressTextView = (CircleProgressTextView) findViewById(R.id.circle_progress_tv);
        mCircleProgressTextView.setVisibility(View.GONE);

        //set listener.
        setClickImpl(mShowBtn);
        setClickImpl(mCircleProgressTextView);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    /**
     * 设置点击监听
     * @param view click view.
     */
    public void setClickImpl(View view){
        if(view == null){
            return;
        }
        view.setOnClickListener(mOnClick);
    }

    @Override
    public void onBackPressed() {
        if(!mShowing){
            super.onBackPressed();
        }
    }
}
