package com.example.sec.buttonlistener;

//import ...

public class MainActivity extends Activity{
	Button b1,b2;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		b1 = (Button) findViewById(R.id.b1);
		b2 = (Button) findViewById(R.id.b2);
		b1.setOnClickListener(new View.OnClickListener() {
			@Override
			puiblic void onClick(View v){
				Toast.makeText(MainActivity.this, "seoul",Toast.LENGTH_SHORT).show();
			}
		});
		b2.setOnClickListener(new View.OnClickListener() {
			@Override
			puiblic void onClick(View v){
				Toast.makeText(MainActivity.this, "london",Toast.LENGTH_SHORT).show();
			}
		});
	}
}