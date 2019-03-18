package com.wuyazhou.learn.listview.MultiplexException;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.wuyazhou.learn.listview.R;
import com.wuyazhou.learn.listview.bean.BrandItemInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 吴亚洲
 * @date 2018.7.7
 * @function
 */
public class MultiplexListPagerView extends FrameLayout implements View.OnClickListener {
    private Context mContext = null;
    private LinearLayout mLayout;
    private ListView mLeftListView,mRightListView;

    private ExceptionListViewAdapter mLeftListViewAdapter;
    private RightListViewAdapter mRightListViewAdapter;

    private int number = 50;

    private List mLeftDataList = new ArrayList<BrandItemInfo>();
    private List mRightDataList = new ArrayList<BrandItemInfo>();

    public MultiplexListPagerView(Context context) {
        super(context);
        mContext = context;
        initView();
        initData();
    }

    public MultiplexListPagerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
        initData();
    }

    public MultiplexListPagerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
        initData();
    }

    public void initView() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mLayout = (LinearLayout) inflater.inflate(R.layout.pager_multiplex_layout, null);
        addView(mLayout);

        mLeftListView = mLayout.findViewById(R.id.list_exception);
        mRightListView = mLayout.findViewById(R.id.list_right);
    }

    private void initData(){
        for (int i=0;i<number;i++){
            mLeftDataList.add(new BrandItemInfo("测试："+i,false));
        }
        mLeftListViewAdapter = new ExceptionListViewAdapter(mContext,mLeftDataList);
        mLeftListView.setAdapter(mLeftListViewAdapter);


        for (int i=0;i<number;i++){
            mRightDataList.add(new BrandItemInfo("测试："+i,false));
        }
        mRightListViewAdapter = new RightListViewAdapter(mContext,mRightDataList);
        mRightListView.setAdapter(mRightListViewAdapter);
    }

    @Override
    public void onClick(View v) {

    }
}
