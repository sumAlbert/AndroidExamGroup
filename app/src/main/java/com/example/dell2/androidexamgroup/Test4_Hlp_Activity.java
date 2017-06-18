package com.example.dell2.androidexamgroup;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;

import java.util.ArrayList;
import java.util.List;

import collector.BaseActivity;
import overlay.PoiOverlay;
import util.AMapUtil;
import util.ToastUtil;

/**
 * Created by Administrator on 2017/6/17.
 */

public class Test4_Hlp_Activity extends BaseActivity implements View.OnClickListener,
        PoiSearch.OnPoiSearchListener ,Inputtips.InputtipsListener , TextWatcher {
    private ImageView back_button;
//    private AndroidExamGroup app;
    private ImageView search_button;
    private EditText location,etResult;
    private RelativeLayout button_verify;
    private MapView mMapView = null;
    private AMap aMap;
    private AutoCompleteTextView searchText;// 输入搜索关键字
    private String keyWord = "";// 要输入的poi搜索关键字
    private ProgressDialog progDialog = null;// 搜索时进度条
    private PoiResult poiResult; // poi返回的结果
    private int currentPage = 0;// 当前页面，从0开始计数
    private PoiSearch.Query query;// Poi查询条件类
    private PoiSearch poiSearch;// POI搜索
    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;



    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.activity_test4_hlp);
        mMapView = (MapView) findViewById(R.id.mymap);
        mMapView.onCreate(savedInstanceState);// 此方法必须重写
        if (null == mLocationClient) {
            mLocationClient = new AMapLocationClient(this.getApplicationContext());
        }

        mLocationOption = new AMapLocationClientOption();
        // 使用连续
        mLocationOption.setOnceLocation(false);
        mLocationOption.setLocationCacheEnable(false);
        // 每10秒定位一次
        mLocationOption.setInterval(10 * 1000);
        // 地址信息
        mLocationOption.setNeedAddress(true);
        mLocationClient.setLocationOption(mLocationOption);
       init();
    }

    /**
     * 点击搜索按钮
     */
    public void searchButton() {
        keyWord = AMapUtil.checkEditText(searchText);
        if ("".equals(keyWord)) {
            ToastUtil.show(Test4_Hlp_Activity.this, "请输入搜索关键字");
            return;
        } else {
            doSearchQuery();
        }
    }
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }

    //    private void Location() {
//    }
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    private void init() {

        if (aMap == null) {
            aMap = mMapView.getMap();
            setUpMap();
        }
         /*实例化*/
        etResult = (EditText) findViewById(R.id.result);
        back_button = (ImageView) findViewById(R.id.back_button);
        search_button = (ImageView) findViewById(R.id.search_button);
        location = (EditText) findViewById(R.id.location);
        back_button.setOnClickListener(this);
        search_button.setOnClickListener(this);

        AMapLocationListener locationListener = new AMapLocationListener() {
            public void onLocationChanged(AMapLocation amapLocation) {
                if (amapLocation != null) {
                    if (amapLocation.getErrorCode() == 0) {
                        //定位成功回调信息，设置相关消息
                        amapLocation.getLatitude();//获取纬度
                        amapLocation.getLongitude();//获取经度
                        amapLocation.getAddress();

                        etResult.setText("当前位置经度:" + amapLocation.getLongitude() + "、纬度:"
                                + amapLocation.getLatitude() + "的地址为：\n" + amapLocation.getAddress());
                    } else {
                        //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                        Log.e("AmapError", "location Error, ErrCode:"
                                + amapLocation.getErrorCode() + ", errInfo:"
                                + amapLocation.getErrorInfo());
                    }
                    Log.d("mLocationClient", String.valueOf(amapLocation.getLatitude()));
                }


            }
        };
        mLocationClient.setLocationListener(locationListener);
        mLocationClient.startLocation();

    }


    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，
        // 定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
       myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style

        ImageView searButton = (ImageView) findViewById(R.id.search_button);//
        searButton.setOnClickListener(this);
        searchText = (AutoCompleteTextView) findViewById(R.id.location);
        searchText.addTextChangedListener(this);// 添加文本输入框监听事件
    }

    /**
     * 显示进度框
     */
    private void showProgressDialog() {
        if (progDialog == null)
            progDialog = new ProgressDialog(this);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setIndeterminate(false);
        progDialog.setCancelable(false);
        progDialog.setMessage("正在搜索:\n" + keyWord);
        progDialog.show();
    }

    /**
     * 隐藏进度框
     */
    private void dissmissProgressDialog() {
        if (progDialog != null) {
            progDialog.dismiss();
        }
    }

    /**
     * 开始进行poi搜索
     */
    protected void doSearchQuery() {
        showProgressDialog();// 显示进度框
        currentPage = 0;
        query = new PoiSearch.Query(keyWord, "", "");// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(10);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);// 设置查第一页

        poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();

    }


    @Override
    public void onPoiSearched(PoiResult result, int rCode) {
        dissmissProgressDialog();// 隐藏对话框
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getQuery() != null) {// 搜索poi的结果
                if (result.getQuery().equals(query)) {// 是否是同一条
                    poiResult = result;
                    // 取得搜索到的poiitems有多少页
                    List<PoiItem> poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                    List<SuggestionCity> suggestionCities = poiResult
                            .getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息

                    if (poiItems != null && poiItems.size() > 0) {
                        aMap.clear();// 清理之前的图标
                        PoiOverlay poiOverlay = new PoiOverlay(aMap, poiItems);
                        poiOverlay.removeFromMap();
                        poiOverlay.addToMap();
                        poiOverlay.zoomToSpan();
                    } else if (suggestionCities != null
                            && suggestionCities.size() > 0) {
                        showSuggestCity(suggestionCities);
                    } else {
                        ToastUtil.show(Test4_Hlp_Activity.this,
                                R.string.no_result);
                    }
                }
            } else {
                ToastUtil.show(Test4_Hlp_Activity.this,
                        R.string.no_result);
            }
        } else {
            ToastUtil.showerror(this, rCode);
        }

    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    /**
     * poi没有搜索到数据，返回一些推荐城市的信息
     */
    private void showSuggestCity(List<SuggestionCity> cities) {
        String infomation = "推荐城市\n";
        for (int i = 0; i < cities.size(); i++) {
            infomation += "城市名称:" + cities.get(i).getCityName() + "城市区号:"
                    + cities.get(i).getCityCode() + "城市编码:"
                    + cities.get(i).getAdCode() + "\n";
        }
        ToastUtil.show(Test4_Hlp_Activity.this, infomation);

    }

    @Override
    public void onGetInputtips(List<Tip> tipList, int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {// 正确返回
            List<String> listString = new ArrayList<String>();
            for (int i = 0; i < tipList.size(); i++) {
                listString.add(tipList.get(i).getName());
            }
            ArrayAdapter<String> aAdapter = new ArrayAdapter<String>(
                    getApplicationContext(),
                    R.layout.route_input, listString);
            searchText.setAdapter(aAdapter);
            aAdapter.notifyDataSetChanged();
        } else {
            ToastUtil.showerror(this, rCode);
        }

    }




    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String newText = s.toString().trim();
        if (!AMapUtil.IsEmptyOrNullString(newText)) {
            InputtipsQuery inputquery = new InputtipsQuery(newText, "");
            Inputtips inputTips = new Inputtips(Test4_Hlp_Activity.this, inputquery);
            inputTips.setInputtipsListener(this);
            inputTips.requestInputtipsAsyn();
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_button:
                finish();
                break;

        case R.id.search_button:
        if (location.getText().toString() != null && !location.getText().toString().equals("")) {
            searchButton();
        } else
            Toast.makeText(Test4_Hlp_Activity.this, "地址不能为空", Toast.LENGTH_SHORT).show();
        break;
        default:
        break;
    }
    }

    public static void actionStart(Context context) {
        Intent intent=new Intent(context,Test4_Hlp_Activity.class);//
        context.startActivity(intent);
    }
}
