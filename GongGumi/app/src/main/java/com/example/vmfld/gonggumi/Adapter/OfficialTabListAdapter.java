package com.example.vmfld.gonggumi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import com.example.vmfld.gonggumi.R;

import java.util.List;

public class OfficialTabListAdapter {

    /*Integer[] ToolbarBackgrounds  = {R.drawable.roomt1, R.drawable.roomt2, R.drawable.roomt3, R.drawable.roomt4,
                                                                  R.drawable.roomt5, R.drawable.roomt6, R.drawable.roomt7, R.drawable.roomt8,};

    private Context context;
    private List<OfficialTabData> officialTab;
    private List<OfficialTabSumData> officialTabSum;
    private List<OfficialTabDetailData> officialTabDetail;



    public OfficialTabListAdapter(Context context, List<OfficialTabData> officialTabData,
                                  List<OfficialTabSumData> officialTabSumData, List<OfficialTabDetailData> officialTabDetailData) {
        super(context, R.layout.list_model_layout, officialTabData);
        this.context = context;
        this.officialTab = officialTabData;
        this.officialTabSum = officialTabSumData;
        this.officialTabDetail = officialTabDetailData;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(R.layout.list_model_layout, parent, false);

        OfficialTabData officialTabData = officialTab.get(position);
        OfficialTabSumData officialTabSumData = officialTabSum.get(position);
        OfficialTabDetailData officialTabDetailData = officialTabDetail.get(position);

        int img_index = (int) (Math.random()*8);
        int res = ToolbarBackgrounds[img_index];

        LinearLayout toolbar_linearlayout = (LinearLayout)convertView.findViewById(R.id.toolbar_linearlayout);
        toolbar_linearlayout.setBackgroundResource(res);


        // List model만 적용시키는것임
        // 지금 문제는 detail이 하나가 아니라는 부분.
        // 이걸 해결하기 위해서 생각을 해보야함 




        return convertView;
    }*/
}
