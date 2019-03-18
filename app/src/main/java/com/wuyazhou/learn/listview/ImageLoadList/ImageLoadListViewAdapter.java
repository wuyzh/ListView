package com.wuyazhou.learn.listview.ImageLoadList;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuyazhou.learn.listview.R;

import java.util.List;


/**
 * @author  吴亚洲
 */
public class ImageLoadListViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<ItemInfos.Item> mList = null;
    private ImageLoader mImageLoader;

    public ImageLoadListViewAdapter(Context context, List<ItemInfos.Item> list,ImageLoader imageLoader) {
        this.mContext = context;
        this.mList = list;
        this.mImageLoader = imageLoader;
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public ItemInfos.Item getItem(int position) {
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
        ItemInfos.Item entity = mList.get(position);

        if (convertView == null){
            convertView = View.inflate(mContext, R.layout.item_image_layout, null);
            holderView = new ViewHolder();
            holderView.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
            holderView.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            holderView.tvContent = (TextView) convertView.findViewById(R.id.tv_content);
            convertView.setTag(holderView);
        }else {
            holderView = (ViewHolder) convertView.getTag();
        }
        holderView.ivIcon.setTag(entity.picSmall);

        mImageLoader.showImage(holderView.ivIcon,entity.picSmall);
        holderView.tvTitle.setText(entity.name);
        holderView.tvContent.setText(entity.description);
        return convertView;
    }

    public final class ViewHolder {
        private TextView tvTitle, tvContent;
        private ImageView ivIcon;
    }

}
