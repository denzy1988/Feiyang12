package com.qihoo.feiyang.picture;import java.util.List;import android.content.Context;import android.content.Intent;import android.graphics.Bitmap;import android.os.Bundle;import android.view.LayoutInflater;import android.view.View;import android.widget.ImageView;import android.widget.LinearLayout;import android.widget.TextView;import com.qihoo.feiyang.R;import com.qihoo.feiyang.util.FileUtil;/** * 主界面的thumb的界面的添加和事件的绑定 * @author zhangshixin * */public class MainPictureViewAdd implements IViewAddAndEventSet {	public View addViewAndAddEvenet(final Context context, int position, List<Bundle> list) {		Bundle data = list.get(position);		final String fileNid = data.getString("nid");		final String name = data.getString("name");		Bitmap map = FileUtil.loadBitmapFromCache(FileUtil.getThumbPicName(fileNid));				String inflater=Context.LAYOUT_INFLATER_SERVICE;		LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(inflater);		LinearLayout linearLayout = (LinearLayout) layoutInflater.inflate(R.layout.photo_main_list_item, null);		ImageView imageView = (ImageView) linearLayout.findViewById(R.id.pict_thumb);		TextView textView = (TextView) linearLayout.findViewById(R.id.pict_dirname);		imageView.setImageBitmap(map);		textView.setText(name);				linearLayout.setOnClickListener(new View.OnClickListener() {			public void onClick(View v) {				Intent intent = new Intent(context, DirectoryPictureActivity.class);				intent.putExtra("nid", fileNid);				context.startActivity(intent);			}		});				return linearLayout;	}}