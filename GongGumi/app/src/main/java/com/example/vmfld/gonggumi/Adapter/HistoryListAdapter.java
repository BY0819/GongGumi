package com.example.vmfld.gonggumi.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.vmfld.gonggumi.ApiData.HistorySumData;
import com.example.vmfld.gonggumi.ApiData.PublicTabDetailData;
import com.example.vmfld.gonggumi.R;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.List;

public class HistoryListAdapter extends ArrayAdapter {

    private Context context;
    private List<PublicTabDetailData> historyData;

    private DecimalFormat decimalFormat = new DecimalFormat("#,###");

    public HistoryListAdapter(Context context, List<PublicTabDetailData> historySumData) {
        super(context, R.layout.history_model_layout, historySumData);
        this.context = context;
        this.historyData = historySumData;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(R.layout.history_model_layout, parent, false);

        PublicTabDetailData historySumData = historyData.get(position);

        String temp_date = historySumData.getDate();
        String temp = temp_date.substring(5,10).replaceAll("-", "/");
        Log.e("temp_date : ", temp);



        TextView  txt_history_date = (TextView) convertView.findViewById(R.id.txt_history_date);
        txt_history_date.setText("●"+temp);


        TextView  txt_history_price = (TextView) convertView.findViewById(R.id.txt_history_price);
        Integer temp_price_int = historySumData.getUsageprice();
        String temp_price = decimalFormat.format(Double.parseDouble(temp_price_int.toString().replaceAll(",","")));

        txt_history_price.setText(temp_price);

        return convertView;
    }

}
