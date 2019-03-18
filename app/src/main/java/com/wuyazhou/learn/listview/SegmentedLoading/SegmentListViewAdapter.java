package com.wuyazhou.learn.listview.SegmentedLoading;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wuyazhou.learn.listview.R;
import com.wuyazhou.learn.listview.bean.BrandItemInfo;

import java.util.List;


/**
 * @author  吴亚洲
 */
public class SegmentListViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<BrandItemInfo> mList = null;

    public SegmentListViewAdapter(Context context, List<BrandItemInfo> list) {
        this.mContext = context;
        this.mList = list;
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public BrandItemInfo getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holderView;
        //用于判断是否直播来展示不同style
        BrandItemInfo entity = mList.get(position);
        Resources resources = mContext.getResources();

        if (convertView == null){
            convertView = View.inflate(mContext, R.layout.item_segment_layout, null);
            holderView = new ViewHolder();
            holderView.convertView = convertView;
            holderView.textView = (TextView) convertView.findViewById(R.id.message);
            convertView.setTag(holderView);
        }else {
            holderView = (ViewHolder) convertView.getTag();
        }

        holderView.textView.setText(Html.fromHtml( "<font color=#97CB60>"+ entity.brandEnName+"</font> "+ "<font color=#FFFFFF>"+ entity.brandEnName+"</font>"));
        return convertView;
    }

    public final class ViewHolder {
        View convertView;
        TextView textView;
    }

}
