package com.wuyazhou.learn.listview.ImageLoadList;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.wuyazhou.learn.listview.R;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author 吴亚洲
 * @date 2018.7.7
 * @function
 */
public class ImageLoadListPagerView extends FrameLayout implements View.OnClickListener, AbsListView.OnScrollListener {
    private Context mContext = null;
    private LinearLayout mLayout;
    private ListView mListView;
    private static final String mUrl = "http://www.imooc.com/api/";
    private int mStart, mEnd;
    private boolean mFirstIn = true;
    private ImageLoader mImageLoader;
    List<ItemInfos.Item> mDataItems;

    public ImageLoadListPagerView(Context context) {
        super(context);
        mContext = context;

        initView();
        initData();
    }

    public ImageLoadListPagerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        initView();
        initData();
    }

    public ImageLoadListPagerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        initView();
        initData();
    }

    public void initView() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mLayout = (LinearLayout) inflater.inflate(R.layout.pager_segment_layout, null);
        addView(mLayout);

        mListView = mLayout.findViewById(R.id.list);
    }

    private void initData(){
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(mUrl).
                addConverterFactory(GsonConverterFactory.create()).
                addCallAdapterFactory(RxJavaCallAdapterFactory.create()).
                build();

        Observable<ItemInfos> observable = retrofit.create(HttpService.class).getItems("4","30");
        observable= observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        Observer observer = new Observer<ItemInfos>() {
            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(Throwable e) {
            }
            @Override
            public void onNext(ItemInfos itemInfos) {
                mDataItems= itemInfos.data;
                mImageLoader = new ImageLoader(mListView,mDataItems);
                ImageLoadListViewAdapter imageLoadListViewAdapter = new ImageLoadListViewAdapter(mContext,mDataItems,mImageLoader);
                mListView.setAdapter(imageLoadListViewAdapter);
                mListView.setOnScrollListener(ImageLoadListPagerView.this);
            }
        };
        observable.subscribe(observer);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState){
            //滑动停止时
            case SCROLL_STATE_IDLE:
                mImageLoader.loadImages(mStart, mEnd);
                break;
            //正在滑动时
            case SCROLL_STATE_TOUCH_SCROLL:
                mImageLoader.cancelAllTasks();
                break;
            //手指抛动时，即手指用力滑动在离开后ListView由于惯性而继续滑动
            case SCROLL_STATE_FLING:
                break;
            default:
                break;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mStart = firstVisibleItem;
        mEnd = firstVisibleItem + visibleItemCount;
        //第一次的时候预加载
        if (mFirstIn && visibleItemCount > 0){
            mImageLoader.loadImages(mStart, mEnd);
            mFirstIn = false;
        }
    }
}
