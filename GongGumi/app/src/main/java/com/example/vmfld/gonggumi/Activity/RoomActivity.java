package com.example.vmfld.gonggumi.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vmfld.gonggumi.ApiData.EnteredRoomData;
import com.example.vmfld.gonggumi.ApiData.PublicTabData;
import com.example.vmfld.gonggumi.ApiData.PublicTabDetailData;
import com.example.vmfld.gonggumi.R;
import com.example.vmfld.gonggumi.database.UserDbHelper;
import com.example.vmfld.gonggumi.database.UserIdContract;
import com.example.vmfld.gonggumi.interfaces.EnteredRoomApi;
import com.example.vmfld.gonggumi.interfaces.PublicTabApi;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RoomActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, TabLayout.OnTabSelectedListener {

    public static final int REQUEST_CODE_FINISH = 1000;

    Integer[] ToolbarBackgrounds  = {R.drawable.roomt1, R.drawable.roomt2, R.drawable.roomt3, R.drawable.roomt4,
                                                                  R.drawable.roomt5, R.drawable.roomt6, R.drawable.roomt7, R.drawable.roomt8,};

    private DecimalFormat decimalFormat = new DecimalFormat("#,###");

    Integer position = null;
    Integer roomid = null;
    ViewPager viewPager;
    TabLayout tabLayout;

    TextView txt_total_money;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        Toolbar toolbar = (Toolbar) findViewById(R.id.room_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); // 커스텀 타이틀 사용으로 실제 타이틀 삭제

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        viewPager = (ViewPager)findViewById(R.id.mViewpager_ID);
        this.addPages();

         tabLayout = (TabLayout)findViewById(R.id.mTab_ID);
         tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
         tabLayout.setupWithViewPager(viewPager);
         tabLayout.setOnTabSelectedListener(this);


         /** toolbar 배경 설정**/

        int img_index = (int) (Math.random()*8);
        final int res = ToolbarBackgrounds[img_index];

        LinearLayout toolbar_linearlayout = (LinearLayout)findViewById(R.id.toolbar_linearlayout);
        toolbar_linearlayout.setBackgroundResource(res);


        /** 넘어온 데이터 맞는지 확인  -> 보안코드때 사용**/

        if(getIntent() != null){
            position = getIntent().getIntExtra("position", 0);

            Log.e("Main에서 넘어온 position :" ,position+"");
        }


        /**데이터베이스에서 roomid 가져오기 **/

        final UserDbHelper userDbHelper = UserDbHelper.getsInstance(RoomActivity.this);

        Cursor cursor = userDbHelper.getReadableDatabase()
                .query(UserIdContract.RoomDataEntry.TABLE_NAME,
                        null, null, null, null, null, null);

        if(cursor != null && cursor.move(position+1)){
            roomid = cursor.getInt(cursor.getColumnIndexOrThrow(UserIdContract.RoomDataEntry.COLUMN_NAME_ROOM_ID));
        }

        // Room 아이디 제대로 들어왔는지 확인
        Log.e("RA_Room 아이디 : ", roomid+"");


        /**Navigation view Header 내용 변경**/

        NavigationView navigationview = (NavigationView) findViewById(R.id.nav_view);
        navigationview.setNavigationItemSelectedListener(this);

        View nav_header_view = navigationview.getHeaderView(0);

        TextView txt_nav_roomid = (TextView) nav_header_view.findViewById(R.id.txt_nav_roomid);
        txt_nav_roomid.setText("No."+roomid);

        /**Retrofit 통신하기**/

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PublicTabApi.PublicTabURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PublicTabApi publicTabApi  = retrofit.create(PublicTabApi.class);

        Call<PublicTabData> call =  publicTabApi.postRoomid(roomid);

        call.enqueue(new Callback<PublicTabData>() {
            @Override
            public void onResponse(Call<PublicTabData> call, Response<PublicTabData> response) {
                PublicTabData publicTabData = response.body();
                List<PublicTabDetailData> publicTabDetailData = response.body().detail;

                Log.e("RA_통신 값 :", response.code() + "");
               // Log.e("RA_잘 들어왔나 확인 : ", publicTabDetailData.get().getMemo() + "");

                Integer nowTotal = publicTabData.getPublicMoneyprice();
                Log.e("RA_총금액 : ",  nowTotal+"");

                txt_total_money = (TextView)findViewById(R.id.txt_total_money);
                String temp_total =  decimalFormat.format(Double.parseDouble(nowTotal.toString().replaceAll(",","")));
                Log.e("txt_total_money 값", txt_total_money+"");
                txt_total_money.setText(temp_total+"원");


            }

            @Override
            public void onFailure(Call<PublicTabData> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.room, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();



        if (id == R.id.nav_divideMoney) {
            // Handle the camera action
        } else if (id == R.id.nav_history) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_getSC) {

            Intent intent = new Intent(RoomActivity.this, CodeGetActivity.class);
            intent.putExtra("position", position);
            startActivityForResult(intent, REQUEST_CODE_FINISH );

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void addPages()
    {
        //MyPagerAdapter pagerAdapter=new MyPagerAdapter(this.getSupportFragmentManager());
        //pagerAdapter.addFragment(new OfficialMFragment());
        //pagerAdapter.addFragment(new PersonalMFragment());

        //SET ADAPTER TO VP
        //viewPager.setAdapter(pagerAdapter);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
