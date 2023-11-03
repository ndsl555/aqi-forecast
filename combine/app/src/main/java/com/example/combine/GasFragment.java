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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;

public class GasFragment extends Fragment {

    private ArrayList<GasData> gasDataList = new ArrayList<>();
    private View view; // 声明一个变量来存储视图
    private int isSizeAdjusted = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_gas, container, false); // 初始化视图


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
                return true;
            case R.id.menu_adjusted:
                Log.d("GasFragment", "Adjusted option selected");
                isSizeAdjusted = 1;
                updateUI();
                return true;
            case R.id.menu_adjusted2:
                Log.d("GasFragment", "Adjusted2 option selected");
                isSizeAdjusted = 2;
                updateUI();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // 更新UI根据isSizeAdjusted标志
    private void updateUI() {
        int size = gasDataList.size();
        ArrayList<GasData> lastGasData = null;

        if (isSizeAdjusted==0) {
            lastGasData = new ArrayList<>(gasDataList.subList(size - 5, size - 1));
        } else if (isSizeAdjusted==1) {
            lastGasData = new ArrayList<>(gasDataList.subList(size - 9, size - 5));
        }
        else {
            lastGasData = new ArrayList<>(gasDataList.subList(1, 4));
        }

        // 初始化 RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.oil_recyclerView);

        // 创建适配器并设置给 RecyclerView
        GasAdapter adapter = new GasAdapter(lastGasData);
        recyclerView.setAdapter(adapter);
    }

    // 处理从网络请求获取的数据
    private void handleDataFromNetwork(Document document) {
        if (document != null) {
            // 提取汽油数据
            Elements liElements = document.select("ul.cont_18 li.row");
            GasData gasData = null;
            for (Element li : liElements) {
                String name = li.select("div.col-4").text().trim();
                String price = li.select("div.col-5 strong").text().trim();
                String unit=li.select("div.col-5 small").text().trim();
                String value = li.select("div.col-3").text().trim();

                // 创建Gasoline对象并添加到ArrayList
                gasData = new GasData(name, price,unit,value);
                gasDataList.add(gasData);
            }
            for (GasData gas :gasDataList) {
                System.out.println(gas.getName()+'\n');
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
