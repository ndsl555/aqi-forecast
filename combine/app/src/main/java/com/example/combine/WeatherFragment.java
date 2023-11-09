package com.example.combine;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import java.util.List;

public class WeatherFragment extends Fragment {
    private RecyclerView recycler_view;
    private MyAdapter adapter;
    private ArrayList<String> sites = new ArrayList<>();
    private ArrayList<String> start_end = new ArrayList<>();
    private ArrayList<String> stuation = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        //ProgressDialog dialog = ProgressDialog.show(getActivity(), "讀取中", "請稍候", true);
        recycler_view = view.findViewById(R.id.country_recyclerView);
        recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler_view.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        String catchData = "https://opendata.cwb.gov.tw/api/v1/rest/datastore/F-D0047-091?Authorization=rdec-key-123-45678-011121314";

        new Thread(() -> {
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
                JSONObject obj_site = arr_zero.getJSONObject(0);
                JSONArray arr_location = obj_site.getJSONArray("location");
                System.out.println(arr_location.length());

                for (int i = 0; i < arr_location.length(); i++) {
                    JSONObject obj_location = arr_location.getJSONObject(i);
                    String sitename = obj_location.getString("locationName");
                    JSONArray arr_weatherElement = obj_location.getJSONArray("weatherElement");
                    JSONObject obj_combine = arr_weatherElement.getJSONObject(10);
                    JSONArray arr_time = obj_combine.getJSONArray("time");
                    sites.add(sitename);
                    for (int j = 0; j < arr_time.length(); j++) {
                        JSONObject aa = arr_time.getJSONObject(j);
                        String tt = aa.getString("startTime").substring(5) + " ~ " + aa.getString("endTime").substring(5);
                        JSONArray arr_element = aa.getJSONArray("elementValue");
                        JSONObject obj_value = arr_element.getJSONObject(0);
                        String vv = obj_value.getString("value");
                        start_end.add(tt.replaceAll("-", "/"));
                        stuation.add(vv);
                    }
                }

                //dialog.dismiss();
                getActivity().runOnUiThread(() -> {
                    adapter = new MyAdapter(sites);
                    recycler_view.setAdapter(adapter);
                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }).start();

        return view;
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private List<String> mData;

        MyAdapter(List<String> data) {
            mData = data;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private TextView txtItem;

            ViewHolder(View itemView) {
                super(itemView);
                txtItem = itemView.findViewById(R.id.txtItem);
            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), ForecastActivity.class);
                    Bundle extras = new Bundle();
                    extras.putStringArrayList("se", start_end);
                    extras.putStringArrayList("su", stuation);
                    extras.putString("site", sites.get(position));
                    extras.putInt("pos", holder.getAdapterPosition());
                    intent.putExtras(extras);
                    view.getContext().startActivity(intent);
                }
            });
            holder.txtItem.setText(mData.get(position)+"\t\t\t\t\t\t\t\t\t\t\t\t點擊");
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }
}
