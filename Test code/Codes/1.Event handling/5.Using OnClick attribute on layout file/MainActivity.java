package com.example.sec.buttonxml;

//import ...

public class MainActivity extends Activity{
	Button b1,b2;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void buttonListener1(View v){
		Toast.makeText(getApplicationContext(),"Seoul",Toast.LENGTH_LONG).show();
	}

	public void buttonListener2(View v){
		Toast.makeText(getApplicationContext(),"London",Toast.LENGTH_LONG).show();
	}	
}