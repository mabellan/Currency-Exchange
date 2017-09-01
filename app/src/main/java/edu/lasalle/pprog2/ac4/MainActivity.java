package edu.lasalle.pprog2.ac4;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import edu.lasalle.pprog2.ac4.adapter.MoneyChangeAdapter;
import edu.lasalle.pprog2.ac4.database.MoneyDataBase;
import edu.lasalle.pprog2.ac4.model.CanviMoneda;

public class MainActivity extends AppCompatActivity {

    private MoneyChangeAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    private ArrayList<CanviMoneda> monedesArrayList;
    private ListView listView;
    private MoneyDataBase moneyDataBase;
    private RequestQueue mRequestQueue;
    private TextView horaActualitzacio;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        monedesArrayList = new ArrayList<>(3);
        moneyDataBase = new MoneyDataBase(this);

        for (int i = 1; i < 4; i++) {
            monedesArrayList.add(moneyDataBase.getCurrency(i));
        }



        horaActualitzacio = (TextView) findViewById(R.id.hora_actualitzacio);
        horaActualitzacio.setText(R.string.no_act);

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);

        listView = (ListView) findViewById(R.id.listview);
        adapter = new MoneyChangeAdapter(this,monedesArrayList, moneyDataBase);
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                doPetition();
                refreshLayout.setRefreshing(false);
            }

        });
    }

    //metodo que se encagarga de hacer la petición al webservice y guardar los valores obtenidos.
    private void doPetition(){
        String tag_json_obj = "json_obj_req";

        String url = "http://api.coindesk.com/v1/bpi/currentprice.json";

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage(getString(R.string.loading));
        pDialog.show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //obtengo los JSONObject de cada moneda.
                            String last_update = response.getJSONObject("time").getString("updated");
                            JSONObject usd = response.getJSONObject("bpi").getJSONObject("USD");
                            JSONObject gbp = response.getJSONObject("bpi").getJSONObject("GBP");
                            JSONObject eur = response.getJSONObject("bpi").getJSONObject("EUR");


                            //guardo los valores en una moneda
                            CanviMoneda cm = new CanviMoneda();
                            cm.setMoneda(usd.getString("code"));
                            cm.setDiners(usd.getDouble("rate_float"));
                            cm.setLastUpdate(last_update);

                            CanviMoneda cm2 = new CanviMoneda();
                            cm2.setMoneda(gbp.getString("code"));
                            cm2.setDiners(gbp.getDouble("rate_float"));
                            cm2.setLastUpdate(last_update);


                            CanviMoneda cm3 = new CanviMoneda();
                            cm3.setMoneda(eur.getString("code"));
                            cm3.setDiners(eur.getDouble("rate_float"));
                            cm3.setLastUpdate(last_update);


                            //diferencia de moneda respecto la anterior.
                            cm.setDiferencia(monedesArrayList.get(0).getDiners() -
                                    cm.getDiners());

                            cm2.setDiferencia(monedesArrayList.get(1).getDiners() -
                                    cm2.getDiners());

                            cm3.setDiferencia(monedesArrayList.get(2).getDiners() -
                                    cm3.getDiners());

                            horaActualitzacio.setText(cm.getLastUpdate());


                            monedesArrayList.set(0,cm);
                            monedesArrayList.set(1,cm2);
                            monedesArrayList.set(2,cm3);


                            adapter.notifyDataSetChanged();


                            pDialog.hide();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("MainActivity", "Error: " + error.getMessage());
                pDialog.hide();
            }
        });

        addToRequestQueue(jsonObjReq, tag_json_obj);
    }
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? "MainActivity" : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }



}
