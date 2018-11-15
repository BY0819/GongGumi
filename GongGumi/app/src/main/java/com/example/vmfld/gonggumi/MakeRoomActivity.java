package com.example.vmfld.gonggumi;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MakeRoomActivity extends AppCompatActivity {
    public int num_mem = 0;

   // Button btn_add_mem = (Button)findViewById(R.id.btn_add_mem);
    //Button btn_sub_mem = (Button)findViewById(R.id.btn_sub_mem);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_room);
        Toolbar toolbar = (Toolbar) findViewById(R.id.makeroom_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false); // 커스텀 타이틀 사용으로 실제 타이틀 삭제
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼 생성



        findViewById(R.id.btn_sub_mem).setOnClickListener(btnClickListener);
        findViewById(R.id.btn_add_mem).setOnClickListener(btnClickListener);
    }

    private Button.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final TextView txt_num_mem = (TextView)findViewById(R.id.txt_num_mem);

           /* switch (v.getId()){
                case R.id.btn_add_mem :
                    num_mem += 1;
                    txt_num_mem.setText(""+num_mem);
                    break;

                case R.id.btn_sub_mem :
                    num_mem -= 1;
                    txt_num_mem.setText(""+num_mem);
                    break;

            }*/

           if (v.getId() == R.id.btn_add_mem)
           {
               num_mem += 1;
               txt_num_mem.setText(""+num_mem);
           }
           else if (v.getId() == R.id.btn_sub_mem  && num_mem == 0)
           {
               Toast.makeText(MakeRoomActivity.this, "0 이하는 불가능합니다.", Toast.LENGTH_SHORT).show();
           }
           else if (v.getId() == R.id.btn_sub_mem)
           {
               num_mem -= 1;
               txt_num_mem.setText(""+num_mem);
           }

        }
    };

    @Override
    public void onBackPressed() {
        Toast.makeText(this,"방 만들기를 취소하셨습니다.", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                Toast.makeText(this,"방 만들기를 취소하셨습니다.", Toast.LENGTH_SHORT).show();
                finish();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }


}
