package com.wuyazhou.learn.listview.listdemo;

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

import java.util.List;


/**
 * @author  陈伟飞
 * 时间： 2018/7/26.
 */
public class ListViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mList = null;

    public ListViewAdapter(Context context, List<String> list) {
        this.mContext = context;
        this.mList = list;
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
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
        String entity = mList.get(position);
        Resources resources = mContext.getResources();

        if (convertView == null){
            convertView = View.inflate(mContext, R.layout.list_item_layout, null);
            holderView = new ViewHolder();

            holderView.textView = (TextView) convertView.findViewById(R.id.message);

            convertView.setTag(holderView);
        }else {
            holderView = (ViewHolder) convertView.getTag();
        }

        holderView.textView.setTextColor(resources.getColor(R.color.green));
        holderView.textView.setText(Html.fromHtml( "<font color=#97CB60>"+ mList.get(position)+"</font> "+ "<font color=#FFFFFF>"+ mList.get(position)+"</font>"));

        return convertView;
    }

    public final class ViewHolder {
        TextView textView;
    }

}
