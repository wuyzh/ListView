package com.wuyazhou.learn.listview.MultiplexException;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.wuyazhou.learn.listview.R;
import com.wuyazhou.learn.listview.bean.BrandItemInfo;

import java.util.List;

/**
 * @author wuyzh
 * */
public class ExceptionListViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<BrandItemInfo> mList;

    public ExceptionListViewAdapter(Context context,List<BrandItemInfo> list){
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        BrandItemInfo entity = mList.get(position);
        if (convertView == null){
            convertView = View.inflate(mContext, R.layout.list_item_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.convertView = convertView;
            viewHolder.textView = convertView.findViewById(R.id.message);
            viewHolder.checkBox = convertView.findViewById(R.id.checkbox);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.textView.setText(Html.fromHtml( "<font color=#97CB60>"+
                entity.brandEnName+"</font> "+ "<font color=#FFFFFF>"+ entity.brandEnName+"</font>"));

        if (position%100 < 3){
            viewHolder.convertView.setBackgroundResource(R.color.green);
        }

        viewHolder.checkBox.setChecked(entity.selected);
        return convertView;
    }

    class ViewHolder{
        View convertView;
        TextView textView;
        CheckBox checkBox;
    }
}
