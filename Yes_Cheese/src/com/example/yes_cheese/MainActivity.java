package com.example.yes_cheese;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	private String id1;
	private String id2;
	private int entertype;
	private EditText et1;
	private EditText et2;
	private Button enter;
	private Button intel_enter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.fillyourid);
		et1 = (EditText)this.findViewById(R.id.et1);
		et2 = (EditText)this.findViewById(R.id.et2);
		enter = (Button)this.findViewById(R.id.btn1);
		intel_enter = (Button)this.findViewById(R.id.ali);
		enter.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				id1 = et1.getText().toString();
				id2 = et2.getText().toString();
				entertype = 1;
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putString("user1", id1);
				bundle.putString("user2", id2);
				bundle.putInt("entertype", entertype);
				intent.setClass(MainActivity.this, Fillyourname.class);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		intel_enter.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				id1 = et1.getText().toString();
				id2 = et2.getText().toString();
				entertype = 2;
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putString("user1", id1);
				bundle.putString("user2", id2);
				bundle.putInt("entertype", entertype);
				intent.setClass(MainActivity.this, Fillyourname.class);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
