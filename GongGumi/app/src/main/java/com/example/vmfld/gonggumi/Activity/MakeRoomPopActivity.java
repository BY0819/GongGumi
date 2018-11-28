package com.example.vmfld.gonggumi.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.vmfld.gonggumi.R;

import org.w3c.dom.Text;

public class MakeRoomPopActivity extends Activity {
    Integer p_secretcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makeroom_pop);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags =WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.7f;
        getWindow().setAttributes(layoutParams);

        getWindow().setLayout((int) (width*0.75), (int) (height*0.25));



        if(getIntent() != null){
            p_secretcode = getIntent().getIntExtra("p_secretcode", 0 );

            Log.e("넘어온 데이터 확인 : ",p_secretcode+"");
        }


        TextView secretcodeTextView = (TextView) findViewById(R.id.txt_secretcode);
        secretcodeTextView.setText(p_secretcode+"");

        Button btn_check_sc = (Button) findViewById(R.id.btn_check_sc);

        btn_check_sc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });

    }
}
