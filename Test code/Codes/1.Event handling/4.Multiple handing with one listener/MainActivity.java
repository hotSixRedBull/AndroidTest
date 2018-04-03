package com.example.sec.buttonlistener;

//import ...

public class MainActivity extends Activity{
	Button b1,b2;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button.OnClickListener myClick = new Button.OnClickListener(){

			@Override
			public void onClick(View v){
				switch(c.getId()){
					case R.id.b1:
						Toast.makeText(getApplicationContext(),"Seoul",Toast.LENGTH_LONG).show();
						break;
					case R.id.b2:
						Toast.makeText(getApplicationContext(),"London",Toast.LENGTH_LONG).show();
						break;
				}
			}
		};

		findViewById(R.id.b1).setOnClickListener(myClick);
		findViewById(R.id.b2).setOnClickListener(myClick);

	}
}