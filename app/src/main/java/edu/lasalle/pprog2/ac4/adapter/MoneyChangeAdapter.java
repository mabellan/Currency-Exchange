package edu.lasalle.pprog2.ac4.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

import edu.lasalle.pprog2.ac4.R;
import edu.lasalle.pprog2.ac4.database.MoneyDataBase;
import edu.lasalle.pprog2.ac4.model.CanviMoneda;

/**
 * MoneyChangeAdapter
 *
 * Se encarga de mostrar los datos por la listView correctamente
 */

public class MoneyChangeAdapter extends BaseAdapter implements AdapterView.OnItemClickListener{

    private Context context;
    private ArrayList<CanviMoneda> monedes;
    private MoneyChangeAdapter adapter;
    private MoneyDataBase moneyDataBase;


    public MoneyChangeAdapter(Context context, ArrayList<CanviMoneda> monedes, MoneyDataBase moneyDataBase) {
        this.context = context;
        this.monedes = monedes;
        this.moneyDataBase = moneyDataBase;
    }

    @Override
    public int getCount() {
        return monedes.size();
    }

    @Override
    public Object getItem(int position) {
        return monedes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.money_listview,parent, false);

        CanviMoneda canviMoneda = monedes.get(position);

        //miro si la moneda es USD, GBP o EUR, para así cargar la imagen correctamente y los datos.
        if(canviMoneda.getMoneda().equals("USD")) {
            ImageView imatge = (ImageView) itemView.findViewById(R.id.listview_cell_image);
            InputStream is = context.getResources().openRawResource(R.raw.usa);
            imatge.setImageBitmap(BitmapFactory.decodeStream(is));
            String aux = context.getString(R.string.xbt_to) + " " + canviMoneda.getMoneda();
            TextView text = (TextView) itemView.findViewById(R.id.xtb_to);
            text.setText(aux);

            String aux2 = "$ " + String.valueOf(canviMoneda.getDiners());
            TextView priority = (TextView) itemView.findViewById(R.id.canvi);
            priority.setText(aux2);

            TextView data = (TextView) itemView.findViewById(R.id.resta);
            data.setText(String.valueOf(canviMoneda.getDiferencia()));
            if(canviMoneda.getDiferencia() < 0) {
                data.setTextColor(Color.RED);
            } else {
                data.setTextColor(Color.GREEN);
            }
            monedes.set(0,canviMoneda);
            moneyDataBase.updateCurrency(canviMoneda);


        } else if(canviMoneda.getMoneda().equals("GBP")) {
            ImageView imatge = (ImageView) itemView.findViewById(R.id.listview_cell_image);
            InputStream is = context.getResources().openRawResource(R.raw.gbp);
            imatge.setImageBitmap(BitmapFactory.decodeStream(is));
            String aux = context.getString(R.string.xbt_to) + " " + canviMoneda.getMoneda();
            TextView text = (TextView) itemView.findViewById(R.id.xtb_to);
            text.setText(aux);

            String aux2 = "£ " + String.valueOf(canviMoneda.getDiners());
            TextView priority = (TextView) itemView.findViewById(R.id.canvi);
            priority.setText(aux2);

            TextView data = (TextView) itemView.findViewById(R.id.resta);
            data.setText(String.valueOf(canviMoneda.getDiferencia()));

            if(canviMoneda.getDiferencia() < 0) {
                data.setTextColor(Color.RED);
            } else {
                data.setTextColor(Color.GREEN);
            }
            monedes.set(1,canviMoneda);
            moneyDataBase.updateCurrency(canviMoneda);



        } else {
            ImageView imatge = (ImageView) itemView.findViewById(R.id.listview_cell_image);
            InputStream is = context.getResources().openRawResource(R.raw.eur);
            imatge.setImageBitmap(BitmapFactory.decodeStream(is));

            String aux = context.getString(R.string.xbt_to) + " " + canviMoneda.getMoneda();
            TextView text = (TextView) itemView.findViewById(R.id.xtb_to);
            text.setText(aux);

            String aux2 = "€ " + String.valueOf(canviMoneda.getDiners());
            TextView priority = (TextView) itemView.findViewById(R.id.canvi);
            priority.setText(aux2);

            TextView data = (TextView) itemView.findViewById(R.id.resta);
            data.setText(String.valueOf(canviMoneda.getDiferencia()));
            if(canviMoneda.getDiferencia() < 0) {
                data.setTextColor(Color.RED);
            } else {
                data.setTextColor(Color.GREEN);
            }
            monedes.set(2,canviMoneda);
            moneyDataBase.updateCurrency(canviMoneda);
        }

        return itemView;

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
