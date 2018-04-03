package com.example.sec.listenerobject;

//import ...

public class MainActivity extends Activity{
	Button b1;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		MyListener mL = new MyListener();

		b1 = (Button) findViewById(R.id.b1);
		b1.setOnClickListener(mL);
	}

	class MyListener implements View.OnClickListener{
		
		puiblic void onClick(View v){

			Toast.makeText(MainActivity.this, "리스너 인터페이스 구현하여 이벤트 처리",Toast.LENGTH_SHORT).show();
			
		}
	}
}