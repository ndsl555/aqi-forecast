package com.example.combine;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.combine.Adapter.OilAdapter;
import com.example.combine.DataClass.OilData;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;

public class OilFragment extends Fragment {

    private ArrayList<OilData> oilDataList = new ArrayList<>();
    private View view; // 声明一个变量来存储视图
    private int isSizeAdjusted = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_oil, container, false); // 初始化视图


        setHasOptionsMenu(true); // 启用选项菜单

        String url = "https://www2.moeaea.gov.tw/oil111/";

        // 创建一个新线程执行网络请求
        Thread networkThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document document = Jsoup.connect(url).get();
                    // 处理从网络请求获取的数据
                    handleDataFromNetwork(document);

                } catch (IOException e) {
                    Log.e("oilFragment", "Error fetching data: " + e.getMessage());
                }
            }
        });

        networkThread.start();

        return view;
    }

    // 创建选项菜单
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.gas_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    // 处理选项菜单项的点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_original:
                Log.d("GasFragment", "Original option selected");
                isSizeAdjusted = 0;
                updateUI();
                Toast.makeText(getActivity(), "加油站週均價", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_adjusted:
                Log.d("GasFragment", "Adjusted option selected");
                isSizeAdjusted = 1;
                Toast.makeText(getActivity(), "中油", Toast.LENGTH_SHORT).show();
                updateUI();
                return true;
            case R.id.menu_adjusted1:
                Log.d("GasFragment", "Adjusted1 option selected");
                isSizeAdjusted = 2;
                updateUI();
                Toast.makeText(getActivity(), "台塑", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_adjusted2:
                Log.d("GasFragment", "Adjusted2 option selected");
                isSizeAdjusted = 3;
                updateUI();
                Toast.makeText(getActivity(), "國際", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // 更新UI根据isSizeAdjusted标志
    private void updateUI() {
        int size = oilDataList.size();
        ArrayList<OilData> lastOilData = null;
        if (isSizeAdjusted==0) {
            lastOilData = new ArrayList<>(oilDataList.subList(16,20));
        }
        else if (isSizeAdjusted==1) {
            lastOilData = new ArrayList<>(oilDataList.subList(8, 12));
        }
        else if (isSizeAdjusted==2) {
            lastOilData = new ArrayList<>(oilDataList.subList(12, 16));
        }
        else {
            lastOilData = new ArrayList<>(oilDataList.subList(0, 3));
        }

        // 初始化 RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.oil_recyclerView);

        // 创建适配器并设置给 RecyclerView
        OilAdapter adapter = new OilAdapter(lastOilData);
        recyclerView.setAdapter(adapter);
    }

    // 处理从网络请求获取的数据
    private void handleDataFromNetwork(Document document) {
        if (document != null) {
            // 提取汽油数据
            Elements liElements = document.select("li.row");

            OilData oilData = null;
            for (Element li : liElements) {
                String Name = li.select("div.col-4").text().trim();  // 获取 “北海布蘭特”
                String price = li.select("div.col-5 strong").text();  // 获取 “83.71”
                String unit = li.select("div.col-5 small").text();  // 获取 “美元/桶”
                String state = li.select("div.col-3 i").text();  // 获取 “north”
                String value = li.select("div.col-3").text().replace(state, "").trim();  // 获取 “2.07”

                //System.out.println("Oil Name: " + Name+"Price: " + price+"Unit: " + unit+"State: " + state+"Value: " + value);
                oilData = new OilData(Name,price,unit,state,value);
                oilDataList.add(oilData);
            }

            for (OilData gas : oilDataList) {
                //System.out.println(gas.getName()+'\n'+gas.getValue());
            }

            // 通过Handler在UI线程上更新UI
            Handler mainHandler = new Handler(Looper.getMainLooper());
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    updateUI();
                }
            });
        }
    }
}
