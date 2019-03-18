package com.wuyazhou.learn.listview.MultiplexException;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.wuyazhou.learn.listview.R;
import com.wuyazhou.learn.listview.bean.BrandItemInfo;

import java.util.List;

/**
 * @author wuyzh
 * */
public class RightListViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<BrandItemInfo> mList;

    public RightListViewAdapter(Context context, List<BrandItemInfo> list){
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
            viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    BrandItemInfo info = (BrandItemInfo) viewHolder.checkBox.getTag();
                    info.selected = buttonView.isChecked();
                }
            });
            convertView.setTag(viewHolder);
            viewHolder.checkBox.setTag(entity);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.checkBox.setTag(entity);
        }

        viewHolder.textView.setText(Html.fromHtml( "<font color=#97CB60>"+
                entity.brandEnName+"</font> "+ "<font color=#FFFFFF>"+ entity.brandEnName+"</font>"));

        if (position%100 < 3){
            viewHolder.convertView.setBackgroundResource(R.color.green);
        }else {
            viewHolder.convertView.setBackgroundResource(R.color.gray);
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
