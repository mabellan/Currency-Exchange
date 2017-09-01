package edu.lasalle.pprog2.ac4.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import edu.lasalle.pprog2.ac4.model.CanviMoneda;
import edu.lasalle.pprog2.ac4.utils.DataBaseHelper;

/**
 * Created by miquelabellan on 26/4/17.
 */

public class MoneyDataBase {

    private Context context;
    private static final String TABLE_NAME = "money";
    private static final String COLUMN_MONEDA = "moneda";
    private static final String COLUMN_DINERS = "diners";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_DIFERENCIA = "diferencia";
    private static final String COLUMN_UPDATE = "last_update";



    public MoneyDataBase(Context context) {
        this.context = context;
    }

    public void addCurrency(CanviMoneda cm){
        DataBaseHelper helper = DataBaseHelper.getInstance(context);
        ContentValues values = new ContentValues();
        values.put(COLUMN_MONEDA, cm.getMoneda());
        values.put(COLUMN_DINERS, cm.getDiners());
        values.put(COLUMN_DIFERENCIA, cm.getDiferencia());
        values.put(COLUMN_UPDATE, cm.getLastUpdate());
        helper.getWritableDatabase().insert(TABLE_NAME, null, values);

    }

    public void updateCurrency(CanviMoneda cm){
        DataBaseHelper helper = DataBaseHelper.getInstance(context);

        ContentValues values = new ContentValues();
        values.put(COLUMN_MONEDA, cm.getMoneda());
        values.put(COLUMN_DINERS, cm.getDiners());
        values.put(COLUMN_DIFERENCIA, cm.getDiferencia());
        values.put(COLUMN_UPDATE, cm.getLastUpdate());

        String whereClause = COLUMN_MONEDA+ "=?";
        String[] whereArgs = {cm.getMoneda()};

        helper.getWritableDatabase().update(TABLE_NAME, values, whereClause, whereArgs);
    }

    public CanviMoneda getCurrency(int id){
        CanviMoneda canviMoneda = new CanviMoneda();
        DataBaseHelper helper = DataBaseHelper.getInstance(context);
        String[] selectColumns = null;
        String whereClause =  COLUMN_ID+ "=?";
        String[] whereArgs = {String.valueOf(id)};

        Cursor cursor = helper.getReadableDatabase().
                query(TABLE_NAME, selectColumns, whereClause, whereArgs, null, null, null);


        if (cursor != null) {
            if (cursor.moveToFirst()) {
                String moneda = cursor.getString(cursor.getColumnIndex(COLUMN_MONEDA));
                canviMoneda.setMoneda(moneda);

                double diners = cursor.getDouble(cursor.getColumnIndex(COLUMN_DINERS));
                canviMoneda.setDiners(diners);

            }
            //Cerramos el cursor al terminar.
            cursor.close();
        }

        return canviMoneda;
    }


}
