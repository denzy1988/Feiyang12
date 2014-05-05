package com.qihoo.feiyang.contact;import java.util.ArrayList;import com.qihoo.feiyang.R;import com.qihoo.feiyang.util.GlobalsUtil;import android.app.Activity;import android.app.ProgressDialog;import android.content.Context;import android.content.Intent;import android.database.Cursor;import android.os.Bundle;import android.os.Handler;import android.os.Message;import android.provider.ContactsContract;import android.telephony.SmsManager;import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import android.widget.AdapterView;import android.widget.AdapterView.OnItemClickListener;import android.widget.BaseAdapter;import android.widget.Button;import android.widget.CheckBox;import android.widget.ImageView;import android.widget.ListView;import android.widget.TextView;import android.widget.Toast;public class ContactActivity extends Activity {    private int rowselect=0;        private ArrayList<Boolean> checklist=new ArrayList<Boolean>();		private Handler handler=null;	private  ProgressDialog pd=null;		@Override	protected void onCreate(Bundle savedInstanceState) {		// TODO Auto-generated method stub		super.onCreate(savedInstanceState);				setContentView(R.layout.contact);		handler=new Handler(){			@Override			public void handleMessage(Message msg) {				// TODO Auto-generated method stub				super.handleMessage(msg);				if(msg.what==0x1234 && GlobalsUtil.contactNames!=null){										if(pd!=null){						pd.dismiss();					}										for(int i=0;i<GlobalsUtil.contactNames.size();i++)						checklist.add(false);										ListView content=(ListView) findViewById(R.id.contactLV);										//SimpleAdapter listItemAdapter = new SimpleAdapter(ContactActivity.this,listitem, R.layout.contactlistitem, new String[] {"contactlistitem_avatar","contactlistitem_name", "contactlistitem_phone","contactlistitem_cloud"}, new int[] {R.id.contactlistitem_avatar,R.id.contactlistitem_name,R.id.contactlistitem_phone,R.id.contactlistitem_cloud});					MyAdapter listItemAdapter= new MyAdapter(ContactActivity.this);					content.setAdapter(listItemAdapter);										content.setOnItemClickListener(new OnItemClickListener() {						@Override						public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {							// TODO Auto-generated method stub							rowselect=arg2;							System.out.println(GlobalsUtil.contactNames.get(rowselect)+" selected");																					if(GlobalsUtil.contactIds.get(rowselect)==0){								return;							}														Intent intent=new Intent(ContactActivity.this,ContactDetailActivity.class);														intent.putExtra("contact_select", rowselect);							intent.putExtra("contact_detail", getContactDetail(GlobalsUtil.contactIds.get(rowselect)));														startActivity(intent);						}					});				}			}		};								if(GlobalsUtil.contactGot){			handler.sendEmptyMessage(0x1234);					}else{						pd=ProgressDialog.show(this, null, "正在拼命读取通讯录，请等待...");						Thread thread=new Thread(new Runnable() {								@Override				public void run() {					// TODO Auto-generated method stub					while(!GlobalsUtil.contactGot);										handler.sendEmptyMessage(0x1234);				}			});			thread.start();					}			}	private ArrayList<String> getContactDetail(Long contactID){				ArrayList<String> contactDetail=new ArrayList<String>();				Cursor phones=getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"="+contactID, null,null);		int i=0;		while(phones.moveToNext()){			i++;			String number=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));			contactDetail.add("phone" + i +": " + number);		}		phones.close();				Cursor emails=getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"="+contactID, null,null);		i=0;		while(emails.moveToNext()){			i++;			String email=emails.getString(emails.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));			contactDetail.add("email" + i +": " + email);		}		emails.close();				for(i=0;i<contactDetail.size();i++){			System.out.println(contactDetail.get(i));		}				return contactDetail;	}		private class MyAdapter extends BaseAdapter{				private LayoutInflater inflater;				public MyAdapter(Context context){			this.inflater = LayoutInflater.from(context);		}		@Override		public int getCount() {			// TODO Auto-generated method stub			return GlobalsUtil.contactNames.size();		}		@Override		public Object getItem(int position) {			// TODO Auto-generated method stub			return null;		}		@Override		public long getItemId(int position) {			// TODO Auto-generated method stub			return position;		}		@Override		public View getView(int position, View convertView, ViewGroup parent) {			// TODO Auto-generated method stub						if(convertView==null){				convertView = inflater.inflate(R.layout.contactlistitem, null);			}						ImageView avatar = (ImageView) convertView.findViewById(R.id.contactlistitem_avatar);			TextView name = (TextView) convertView.findViewById(R.id.contactlistitem_name);			TextView phone = (TextView) convertView.findViewById(R.id.contactlistitem_phone);			CheckBox select = (CheckBox) convertView.findViewById(R.id.contactlistitem_select);			avatar.setImageBitmap(GlobalsUtil.contactAvatars.get(position));			name.setText(GlobalsUtil.contactNames.get(position));			phone.setText(GlobalsUtil.contactPhones.get(position));			select.setChecked(checklist.get(position));						select.setTag(position);						select.setOnClickListener(new View.OnClickListener(){				@Override				public void onClick(View v) {					// TODO Auto-generated method stub					rowselect=(Integer) v.getTag();					System.out.println("contact list item "+rowselect+" btn click" );										CheckBox cb= (CheckBox) v;										checklist.set(rowselect,cb.isChecked());									}							});			return convertView;		}			}		public void onClickOfBackward(View source) {				finish();			}		public void onClickOfSendCard(View source){				int sends=0;		for(int i=0;i<checklist.size();i++){			if(checklist.get(i)){								SmsManager smsManager = SmsManager.getDefault();				String smsContent=GlobalsUtil.nickName+"的名片：\n";				for(int j=0;j<GlobalsUtil.cardinfo.length;j++){					smsContent=smsContent+GlobalsUtil.cardinfo[j]+"：";					smsContent=smsContent+GlobalsUtil.phoneinfo[j]+"\n";				}								String phoneNum=GlobalsUtil.contactPhones.get(i);				if(phoneNum!=null){					if(phoneNum.length()>70)						phoneNum=phoneNum.substring(0, 65);										sends++;										smsManager.sendTextMessage(phoneNum, null, smsContent, null, null);				}                		        			}		}				if(sends>0)			Toast.makeText(this, "发送成功", 50).show();		else			Toast.makeText(this, "未选择发送目标", 50).show();	}}