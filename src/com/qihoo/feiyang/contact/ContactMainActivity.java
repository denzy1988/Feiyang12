package com.qihoo.feiyang.contact;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import com.qihoo.feiyang.R;
import com.qihoo.feiyang.util.GlobalsUtil;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.view.View;

public class ContactMainActivity extends Activity {
	
	private String changeInfo=null;
	

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.contactmain);
		
		
		
		
	}
	
	
	public void onClickOfcontactmainChangeCheck(View source){
		System.out.println("contact main change info check btn click");
		Intent intent=new Intent(ContactMainActivity.this,ContactChangeInfoCheckActivity.class);

		
		
		startActivity(intent);
	}
	
	
	
	
    public void onClickOfBackward(View source) {
		
		finish();
		
	}
	
	public void onClickOfContactBottomSetting(View source){
		System.out.println("contact main bottom setting btn click");
		
		switch (source.getId()) {
		case R.id.contact_dir:
			System.out.println("setting btn : contact main directory");
			break;
		case R.id.contact_quickshare:
			System.out.println("setting btn : contact main quick share");
			break;
		case R.id.contact_favourite:
			System.out.println("setting btn : contact main favourite");
			break;
		case R.id.contact_setting:
			System.out.println("setting btn : contact main setting");
			break;

		default:
			break;
		}
	}
	
	public void onClickOfcontactmain_CloudContact(View source){
		System.out.println("contact main cloud contact btn click");
		
		Intent intent=new Intent(ContactMainActivity.this,ContactActivity.class);

		
		startActivity(intent);
	}
	
	

}
