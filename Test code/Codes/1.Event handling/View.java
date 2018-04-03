package com.example.sec.viewevent;

//import ...

public class MainActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		MyView myView = new MyView(this);
		setContentView(myView);
	}

	class MyView extends View{
		MyView(Context context){
			super(context);
			setBackgroundColor(Color.GREEN);
		}

		public boolean onTouchEvent(MotionEvent event){

			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				Toast.makeText(MainActivity.this, "View 클래스를 상속받아 만든 이벤트 처리",Toast.LENGTH_SHORT).show();
			}
			return false;
		}
	}
}