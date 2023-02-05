package com.example.combine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Test3Activity extends AppCompatActivity {
    String TAG = Test3Activity.class.getSimpleName()+"My";
    RecyclerView recycler_view;
    MyAdapter adapter;
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    ArrayList<String> sites = new ArrayList<String>();
    ArrayList<String> start_end = new ArrayList<String>();
    ArrayList<String> stuation = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ProgressDialog dialog = ProgressDialog.show(this,"讀取中"
                ,"請稍候",true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test3);
        String catchData ="https://opendata.cwb.gov.tw/api/v1/rest/datastore/F-D0047-091?Authorization=rdec-key-123-45678-011121314";
        new Thread(()->{
            try {
                JSONObject jsonObject = null;
                URL url = new URL(catchData);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream is = connection.getInputStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(is));
                String line = in.readLine();
                StringBuffer json = new StringBuffer();
                while (line != null) {
                    json.append(line);
                    line = in.readLine();
                }
                jsonObject = new JSONObject(json.toString());
                JSONObject obj_records = jsonObject.getJSONObject("records");
                JSONArray arr_zero = obj_records.getJSONArray("locations");
                JSONObject obj_site= arr_zero.getJSONObject(0);
                JSONArray arr_location = obj_site.getJSONArray("location");

                for(int i=0;i<arr_location.length();i++){
                    JSONObject obj_location= arr_location.getJSONObject(i);
                    String sitename = obj_location.getString("locationName");
                    JSONArray arr_weatherElement = obj_location.getJSONArray("weatherElement");
                    JSONObject obj_combine= arr_weatherElement.getJSONObject(10);
                    JSONArray arr_time = obj_combine.getJSONArray("time");
                    sites.add(sitename);
                    System.out.println(arr_time.length());
                    for(int j=0;j<arr_time.length();j++){
                        JSONObject aa = arr_time.getJSONObject(j);
                        String tt=aa.getString("startTime").substring(5)+" ~ "+aa.getString("endTime").substring(5);
                        JSONArray arr_element = aa.getJSONArray("elementValue");
                        JSONObject obj_value=arr_element.getJSONObject(0);
                        String vv=obj_value.getString("value");
                        start_end.add(tt.replaceAll("-","/"));
                        stuation.add(vv);
                    }
                }

                dialog.dismiss();
                runOnUiThread(()->{

                    // 準備資料，塞50個項目到ArrayList裡
                    // 連結元件
                    recycler_view = (RecyclerView) findViewById(R.id.recyclerView);
                    // 設置RecyclerView為列表型態
                    recycler_view.setLayoutManager(new LinearLayoutManager(this));
                    // 設置格線
                    recycler_view.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

                    // 將資料交給adapter
                    adapter = new MyAdapter(sites);
                    // 設置adapter給recycler_view
                    recycler_view.setAdapter(adapter);
                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        private List<String> mData;

        MyAdapter(List<String> data) {
            mData = data;
        }

        // 建立ViewHolder
        class ViewHolder extends RecyclerView.ViewHolder{
            // 宣告元件
            private TextView txtItem;

            ViewHolder(View itemView) {
                super(itemView);
                txtItem = (TextView) itemView.findViewById(R.id.txtItem);
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // 連結項目布局檔list_item
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);
            return new ViewHolder(view);
        }

        @NonNull

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // 設置txtItem要顯示的內容
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), Test4Activity.class);
                    Bundle extras = new Bundle();
                    extras.putStringArrayList("se",start_end);
                    extras.putStringArrayList("su",stuation);
                    extras.putString("site",sites.get(position));
                    extras.putInt("pos",holder.getAdapterPosition());
                    intent.putExtras(extras);
                    view.getContext().startActivity(intent);
                    //holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
                }
            });
            holder.txtItem.setText(mData.get(position));
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }
}