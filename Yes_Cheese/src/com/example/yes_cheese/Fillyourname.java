package com.example.yes_cheese;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Toast;
import android.widget.Button;

public class Fillyourname extends Activity {
	 static public String user1 ;
	 static public String user2 ;
	 static public int entertype;
	 static public Fillyourname instance;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		Bundle bundle = this.getIntent().getExtras();
		user1 = bundle.getString("user1").toString();
		user2 = bundle.getString("user2").toString();
		entertype = bundle.getInt("entertype");
		instance = Fillyourname.this;
		if(user1.length()==0)
			user1="玩家一";
		if(user2.length()==0)
			user2 = "玩家二";
	}
	
	@Override 
	public boolean onKeyDown(int keyCode,KeyEvent event)
	{
		
		if(keyCode==KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0)
		{
			int [][]chess= new int[100][100];
			chess = StepList.chess;
			for(int i = 0;i<10;i++)
			{
				for(int j = 0;j<10;j++)
				{
					if(chess[i][j]!=0)
					{
						dialog();
						return true;
					}
				}
			}
			Fillyourname.this.finish();
		}
		return true;
	}
	
	private void dialog() {
		AlertDialog.Builder builder = new Builder(Fillyourname.this); 
        builder.setMessage("确定要退出吗?"); 
        builder.setTitle("提示"); 
        builder.setPositiveButton("确认", 
                new android.content.DialogInterface.OnClickListener() { 
                    @Override
                    public void onClick(DialogInterface dialog, int which) { 
                        dialog.dismiss(); 
                        Fillyourname.this.finish(); 
                    } 
                }); 
        builder.setNegativeButton("取消", 
                new android.content.DialogInterface.OnClickListener() { 
                    @Override
                    public void onClick(DialogInterface dialog, int which) { 
                        dialog.dismiss(); 
                    } 
                }); 
        builder.create().show(); 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
