package com.example.vmfld.gonggumi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vmfld.gonggumi.ApiData.PublicInsertData;
import com.example.vmfld.gonggumi.R;

import java.text.DecimalFormat;
import java.util.List;

import static android.view.View.GONE;

public class OfficialTabListAdapter  extends  ArrayAdapter{


    private DecimalFormat decimalFormat = new DecimalFormat("#,###");

    private Context context;
    private List<PublicInsertData> publicInsertData;

    public OfficialTabListAdapter(Context context, List<PublicInsertData> publicInsertData) {
        super(context, R.layout.list_model_layout, publicInsertData);
        this.context = context;
        this.publicInsertData = publicInsertData;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(R.layout.list_model_layout, parent, false);

        TextView txt_list_date = (TextView) convertView.findViewById(R.id.txt_list_date);
        txt_list_date.setText(publicInsertData.get(position).getDate());

        TextView txt_use_name1 = (TextView) convertView.findViewById(R.id.txt_use_name1);
        txt_use_name1.setText(publicInsertData.get(position).getMemo1());

        TextView txt_use_price1 = (TextView) convertView.findViewById(R.id.txt_use_price1);
        Integer price1 = publicInsertData.get(position).getPrice1();
        String temp_price1 =  decimalFormat.format(Double.parseDouble(price1.toString().replaceAll(",","")));
        txt_list_date.setText(publicInsertData.get(position).getDate());


        TextView txt_use_name2 = (TextView) convertView.findViewById(R.id.txt_use_name2);
        if(publicInsertData.get(position).getMemo2().isEmpty()){
            txt_use_name2.setVisibility(GONE);
        }
        txt_use_name2.setText(publicInsertData.get(position).getMemo2());

        TextView txt_use_price2 = (TextView) convertView.findViewById(R.id.txt_use_price2);
        if(publicInsertData.get(position).getPrice2().toString().isEmpty()){
            txt_use_price2.setVisibility(GONE);
        }
        else{
            Integer price2 = publicInsertData.get(position).getPrice2();
            String temp_price2 =  decimalFormat.format(Double.parseDouble(price2.toString().replaceAll(",","")));
            txt_list_date.setText(publicInsertData.get(position).getDate());
        }


        return convertView;
    }

}
