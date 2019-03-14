package com.wuyazhou.learn.listview.listdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.wuyazhou.learn.listview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 吴亚洲
 * @date 2018.7.7
 * @function
 */
public class ListPagerView extends FrameLayout implements View.OnClickListener {
    private Context mContext = null;
    private RelativeLayout mLayout;
    private ListView mListView;
    private List mList = new ArrayList<String>();
    private ListViewAdapter mListViewAdapter;

    public ListPagerView(Context context) {
        super(context);
        mContext = context;
        initView();
        initData();
    }

    public ListPagerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
        initData();
    }

    public ListPagerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
        initData();
    }

    public void initView() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mLayout = (RelativeLayout) inflater.inflate(R.layout.pager_list_layout, null);

        addView(mLayout);

        View modelFirst  = mLayout.findViewById(R.id.model_button_1);
        modelFirst.setOnClickListener(this);
        View modelSecond  = mLayout.findViewById(R.id.model_button_2);
        modelSecond.setOnClickListener(this);

        mListView = mLayout.findViewById(R.id.list);
    }

    public void initData() {
        mList.add("1");
        mList.add("1");
        mList.add("1");
        mList.add("1");
        mList.add("1");
        mListViewAdapter = new ListViewAdapter(mContext,mList);
        mListView.setAdapter(mListViewAdapter);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.model_button_1) {

        } else if (i == R.id.model_button_2) {

        } else {
        }
    }
}
