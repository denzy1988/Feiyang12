package com.qihoo.feiyang.picture;import java.util.List;import android.app.Activity;import android.graphics.Bitmap;import android.os.Bundle;import android.os.Handler;import android.os.Message;import android.view.View;import android.widget.GridView;import android.widget.TextView;import com.qihoo.feiyang.R;import com.qihoo.feiyang.util.FileUtil;import com.qihoo.yunpan.sdk.android.GetNodeByNidAction;import com.qihoo.yunpan.sdk.android.http.model.YunFile;import com.qihoo.yunpan.sdk.android.http.model.YunFileNode;import com.stay.pull.lib.PullToRefreshBase.OnRefreshListener;import com.stay.pull.lib.PullToRefreshGridView;/** * 需要传入目录的nid和dirname * @author zhangshixin * */public class DirectoryPictureActivity extends Activity {		private PullToRefreshGridView refreshGridView = null;	private GridView gridView = null;	private DownloadPict thread = null;	private Handler handler = null;	private ThumbPictureAdapter adapter = null;	private View backward = null;	private TextView titleName = null;		@Override	protected void onCreate(Bundle savedInstanceState) {		super.onCreate(savedInstanceState);		setContentView(R.layout.photo_main);				refreshGridView = (PullToRefreshGridView) findViewById(R.id.pict_gallery);		gridView = refreshGridView.getRefreshableView();				String nid = getIntent().getStringExtra("nid");		String dirName = getIntent().getStringExtra("dirname");		titleName = (TextView) findViewById(R.id.photo_main_name);		titleName.setText(dirName);				adapter = new ThumbPictureAdapter(this, new DirectoryPictureViewAdd());		gridView.setAdapter(adapter);		refreshGridView.setOnRefreshListener(new OnRefreshListener() {			public void onRefresh() {				System.out.println("dir refresh");			}		});		backward = findViewById(R.id.photo_return);		backward.setOnClickListener(new View.OnClickListener() {			public void onClick(View view) {				DirectoryPictureActivity.this.finish();			}		});				handler = new ThumbHandler(adapter);		thread = new DownloadPict(handler, nid);		thread.start();	}		@Override	protected void onPause() {		if( thread != null) {			thread.setStop();		}		super.onPause();	}		@Override	protected void onDestroy() {		if (thread != null) {			thread.setStop();		}		super.onDestroy();	}}class DownloadPict extends Thread {	private Handler handler = null;	private String nid = null;	private volatile boolean isRun = true;		public DownloadPict(Handler handler, String nid) {		super();		this.handler = handler;		this.nid = nid;		setName(this.getClass().getSimpleName());	}		@Override	public void run() {		List<YunFile> picts = null;		if (nid.equals("0")) {			picts = FileUtil.getYunPicturesFromCloud("/");		} else {			picts=  getPicturesByNid(nid);		}		for (YunFile f : picts) {			Bitmap map = FileUtil.getThumbBitMapIfNecessary(f);			Message msg = handler.obtainMessage();			Bundle data = new Bundle();			data.putString("name", f.name);			data.putString("pid", f.pid);			data.putString("nid", f.nid);			data.putString("preview", f.preview);			data.putString("thumb", f.thumb);			msg.setData(data);			handler.sendMessage(msg);		}	}		private List<YunFile> getPicturesByNid(String nid) {		GetNodeByNidAction action = new GetNodeByNidAction();		YunFileNode node = action.getFileNodeByNid(nid);		List<YunFile> picts =  FileUtil.getYunPicturesFromCloud(node.data.name);		return picts;	}		public void setStop() {		this.isRun = false;	}		public boolean isRun() {		return isRun;	}	}