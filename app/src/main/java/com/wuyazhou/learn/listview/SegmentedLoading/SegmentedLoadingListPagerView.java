package com.wuyazhou.learn.listview.SegmentedLoading;

import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
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
public class SegmentedLoadingListPagerView extends FrameLayout implements View.OnClickListener {
    private Context mContext = null;
    private LinearLayout mLayout;
    private ListView mListView;

    private int mMaxPage = 10;
    private int mNumber = 20;
    private boolean mLoadFinish = true;
    private View mLoadProgress;

    private List mDataList = new ArrayList<BrandItemInfo>();
    private SegmentListViewAdapter mSegmentListViewAdapter;

    public SegmentedLoadingListPagerView(Context context) {
        super(context);
        mContext = context;
        initView();
        initData();
    }

    public SegmentedLoadingListPagerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
        initData();
    }

    public SegmentedLoadingListPagerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
        initData();
    }

    public void initView() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mLayout = (LinearLayout) inflater.inflate(R.layout.pager_segment_layout, null);
        addView(mLayout);

        mLoadProgress = inflater.inflate(R.layout.item_progress_layout,null);
        mListView = mLayout.findViewById(R.id.list);

        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState){
                    case SCROLL_STATE_FLING:
                        break;
                    case SCROLL_STATE_IDLE:
                        break;
                    case SCROLL_STATE_TOUCH_SCROLL:
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //得到mListView最后一项的id
                int lastItemId = mListView.getLastVisiblePosition();
                //滑动到最后一项时重新做加载操作
                if((lastItemId+1)==totalItemCount) {
                    /**
                     * 计算当前页，因为每一页只加载十条数据，所以总共加载的数据除以每一页的数据的个数
                     * 如果余数为零则当前页为currentPage=totalItemCount/mNumber；
                     * 如果不能整除则当前页为(int)(totalItemCount/mNumber)+1;
                     * 下一页则是当前页加1
                     */
                    int currentPage = totalItemCount% mNumber;
                    if(currentPage == 0) {
                        currentPage = totalItemCount/ mNumber;
                    } else {
                        currentPage = (int)(totalItemCount/ mNumber) + 1;
                    }
                    int nextPage = currentPage + 1;
                    if(totalItemCount>0) {
                        //判断当前页是否超过最大页，以及上一页的数据是否加载完成
                        if(nextPage<= mMaxPage && mLoadFinish) {
                            //添加页脚视图
                            mListView.addFooterView(mLoadProgress);
                            mLoadFinish = false;

                            AsyncTaskLoadData asynctask = new AsyncTaskLoadData(totalItemCount);
                            asynctask.execute();
                        }
                    }

                }

            }
        });
    }

    private void initData(){
        for (int i = 0; i< mNumber; i++){
            mDataList.add(new BrandItemInfo("测试："+i,false));
        }

        mSegmentListViewAdapter = new SegmentListViewAdapter(mContext,mDataList);
        mListView.setAdapter(mSegmentListViewAdapter);
    }

    private final class AsyncTaskLoadData extends AsyncTask<Object, Object, Object> {
        private int count;
        public AsyncTaskLoadData(int totalItemCount) {
            this.count=totalItemCount;
        }

        @Override
        protected Object doInBackground(Object... params) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i=count;i<count +mNumber;i++){
                mDataList.add(new BrandItemInfo("测试："+i,false));
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object result)
        {
            mSegmentListViewAdapter.notifyDataSetChanged();

            mLoadFinish = true;
            if(mListView.getFooterViewsCount()!=0) {
                mListView.removeFooterView(mLoadProgress);
            }
            super.onPostExecute(result);
        }


    }

    @Override
    public void onClick(View v) {

    }
}
