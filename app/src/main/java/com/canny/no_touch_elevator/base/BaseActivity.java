package com.canny.no_touch_elevator.base;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.canny.no_touch_elevator.R;


public abstract class BaseActivity extends AppCompatActivity{

    RelativeLayout mRootBaseView;//根布局
    View mStateLayout;//include的state_layout
    //View mStateLayout;//include的state_layout
    LinearLayout ll_page_state_error;//stateLayout网络错误的布局
    //LinearLayout ll_page_state_empty;//stateLayout无数据的布局
    Button btnFresh;//网络错误重新加载的布局

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        initBaseView();
        setWindow();
        setContentView(getLayoutId());
        initData();
        getData();
    }

    //这里控件初始化没用ButterKnife 会报错  原因暂时没找到
    public void initBaseView() {
        mRootBaseView = (RelativeLayout) findViewById(R.id.activity_base_root);
       // titleView = findViewById(R.id.activity_base_title_bar);
        //tvLeft = (TextView) findViewById(R.id.title_bart_tv_left);
        //tvMiddle = (TextView) findViewById(R.id.title_bart_tv_middle);
        mStateLayout = findViewById(R.id.activity_base_state_layout);
        btnFresh = (Button) findViewById(R.id.btn_fresh);
        //ll_page_state_empty = (LinearLayout) findViewById(R.id.state_layout_empty);
        ll_page_state_error = (LinearLayout) findViewById(R.id.state_layout_error);
    }

    /**
     * 必须重写setContentView注意不能够添加这行代码 目的将当前界面的布局添加到BaseActivity中去
     * super.setContentView(R.layout.activity_base);
     */
    @Override
    public void setContentView(int layoutResID) {
        View view = getLayoutInflater().inflate(layoutResID, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        if (null != mRootBaseView) {
            mRootBaseView.addView(view, lp);
        }
    }

    /**
     * 切换页面的布局
     * @param pageState 页面状态 NORMAL  EMPTY  ERROR
     */
    public void changePageState(PageState pageState) {
        switch (pageState) {
            case NORMAL:
                if (mStateLayout.getVisibility() == View.VISIBLE) {
                    mStateLayout.setVisibility(View.GONE);
                }
                break;
            case ERROR:
                if (mStateLayout.getVisibility() == View.GONE) {
                    mStateLayout.setVisibility(View.VISIBLE);
                    ll_page_state_error.setVisibility(View.VISIBLE);
                    btnFresh.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            reloadInterface.reloadClickListener();
                        }
                    });
                   // ll_page_state_empty.setVisibility(View.GONE);
                }
                break;
            case EMPTY:
                if (mStateLayout.getVisibility() == View.GONE) {
                    mStateLayout.setVisibility(View.VISIBLE);
                    ll_page_state_error.setVisibility(View.GONE);
                   // ll_page_state_empty.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    //网络错误重新加载的接口
    public ReloadInterface reloadInterface;

    public void setReloadInterface(ReloadInterface reloadInterface) {
        this.reloadInterface = reloadInterface;
    }

    public interface ReloadInterface {
        void reloadClickListener();
    }

    public enum PageState {
        /**
         * 数据内容显示正常
         */
        NORMAL,
        /**
         * 数据为空
         */
        EMPTY,
        /**
         * 数据加载失败
         */
        ERROR
    }


    protected abstract int getLayoutId();
    public abstract void initData();

    protected abstract void getData();
    protected  void setWindow(){
    }
}
