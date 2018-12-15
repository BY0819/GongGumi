package com.example.vmfld.gonggumi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.vmfld.gonggumi.ApiData.DivideData;
import com.example.vmfld.gonggumi.ApiData.DividePersonalSumData;
import com.example.vmfld.gonggumi.R;

import java.util.ArrayList;

public class DivideListAdapter extends BaseAdapter {
    private ArrayList<DivideData> divideDataList = new ArrayList<>();

    @Override
    public int getCount() {
        return divideDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return divideDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Context context = parent.getContext();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.content_divide,parent,false);
        }


        TextView str_i_name = (TextView) convertView.findViewById(R.id.str_i_name);
        TextView str_i_publicprice = (TextView) convertView.findViewById(R.id.str_i_publicprice);
        TextView str_i_personalprice = (TextView) convertView.findViewById(R.id.str_i_personalprice);
        TextView str_i_personal = (TextView) convertView.findViewById(R.id.str_i_personal);
        TextView str_i_leftprice = (TextView) convertView.findViewById(R.id.str_i_leftprice);

        DivideData divideData = (DivideData) getItem(position);

        str_i_name.setText(divideData.getName());
        str_i_publicprice.setText(divideData.getstrPublicused());
        if(divideData.getPersonalused() != 0) {
            str_i_personal.setText("개인사용액");
            str_i_personalprice.setText(divideData.getstrPersonalused());
        }
        else{
            str_i_personal.setText("");
            str_i_personalprice.setText("");
        }

        str_i_leftprice.setText(divideData.getstrGetpermoney());


        return convertView;


    }

    public void addItem(String result, Integer publicUsed, Integer totalLeft, DividePersonalSumData[] dividePSDList, int i){
        DivideData divideData = new DivideData(result,publicUsed,totalLeft,dividePSDList,i);

        divideDataList.add(divideData);
    }
}
