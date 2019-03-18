package com.wuyazhou.learn.listview.ImageLoadList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.LruCache;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author wuyzh
 * */
public class ImageLoader {
    private ListView mListView;
    private List<ItemInfos.Item> mDataItems;
    private LruCache<String, Bitmap> mImageCaches;
    private Set<LoadImageAsyncTask> mAsyncTask;

    public ImageLoader(ListView listView, List<ItemInfos.Item> list){
        this.mListView = listView;
        this.mDataItems = list;
        mAsyncTask = new HashSet<>();
        int cacheSize = (int) Runtime.getRuntime().maxMemory()/4;
        mImageCaches = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }
    public void loadImages(int start, int end){
        for (int i = start; i < end; i++){
            String url = mDataItems.get(i).picSmall;
            Bitmap bitmap = getBitmapFormCache(url);
            if (bitmap == null){
                //当bitmap为空时，由AsyncTask进行加载，并在onPostExecute()方法中setImageBitmap
                LoadImageAsyncTask task = new LoadImageAsyncTask(url);
                task.execute(url);
                mAsyncTask.add(task);
            }else {
                ImageView imageView = (ImageView) mListView.findViewWithTag(url);
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    public void showImage(ImageView imageView,String url){
        Bitmap bitmap = getBitmapFormCache(url);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        }else {
            imageView.setImageBitmap(null);
        }
    }

    public void addBitmapToCache(String url,Bitmap bitmap){
        if (getBitmapFormCache(url) == null){
            mImageCaches.put(url, bitmap);
        }
    }

    public Bitmap getBitmapFormCache(String url){
        return mImageCaches.get(url);
    }

    public void cancelAllTasks(){
        if (mAsyncTask != null){
            for (LoadImageAsyncTask task : mAsyncTask){
                task.cancel(false);
            }
        }
    }

    //参数1：启动任务输入的参数，参数2：后台任务执行的百分比，参数3，后台执行任务的返回方法
    private class LoadImageAsyncTask extends AsyncTask<String, Void, Bitmap> {

        private String mUrl;

        public LoadImageAsyncTask(String stringUrl) {
            mUrl = stringUrl;
        }
        @Override
        protected Bitmap doInBackground(String... params) {
            String url = params[0];
            Bitmap bitmap = getBitmapFormURL(url);
            if (bitmap != null){
                //将bitmap添加到缓存
                addBitmapToCache(url,bitmap);
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            //根据url从listView中找到对应的ImageView
            ImageView imageView = (ImageView) mListView.findViewWithTag(mUrl);
            if (imageView != null && bitmap != null){
                imageView.setImageBitmap(bitmap);
            }
            mAsyncTask.remove(this);
        }
    }

    public Bitmap getBitmapFormURL(String urlString) {
        Bitmap bitmap;
        InputStream inputStream = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //得到图片的数据流
            inputStream = new BufferedInputStream(conn.getInputStream());
            //根据数据流来解析出图片的bitmap
            bitmap = BitmapFactory.decodeStream(inputStream);
            conn.disconnect();
            return bitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

}
