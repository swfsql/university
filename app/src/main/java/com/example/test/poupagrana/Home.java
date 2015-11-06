package com.example.test.poupagrana;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.KeyEvent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.view.inputmethod.EditorInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.content.ContentValues;

import android.widget.AdapterView.*;


public class Home extends AppCompatActivity implements AdapterView.OnItemClickListener {


    private DrawerLayout hDrawerLayout;
    private ActionBarDrawerToggle hActionBarDrawerToggle;
    //public final static String EXTRA_MESSAGE = "com." + R.string.sub_domain + "." + R.string.domain
            //+ "." + R.string.app_name_sub;

    // lista ativa
    private ListView list_active;
    private ArrayAdapter list_active_adapter;
    private ArrayList list_active_array;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // drawer
        hDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        hActionBarDrawerToggle = new ActionBarDrawerToggle(this, hDrawerLayout, R.string.drawer_openned, R.string.home_drawer_closed){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (getSupportActionBar() == null) return;
                getSupportActionBar().setTitle(R.string.drawer_openned);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (getSupportActionBar() == null) return;
                getSupportActionBar().setTitle(R.string.home_drawer_closed);
            }
        };
        hDrawerLayout.setDrawerListener(hActionBarDrawerToggle);

        // cria lista e sua array
        list_active = (ListView) findViewById(R.id.list_active);
        list_active_array = new ArrayList();
        list_active_adapter =  new ArrayAdapter(
                this,
                android.R.layout.simple_expandable_list_item_1,
                list_active_array);
        list_active.setAdapter(list_active_adapter);


        // ao acabar de escrever no campo de texto, enviar pra lista ativa
        final EditText add_item = (EditText) findViewById(R.id.add_item);
        add_item.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String add_item_str = add_item.getText().toString();
                    list_active_array.add(add_item_str);
                    list_active_adapter.notifyDataSetChanged();
                    add_item.setText("");
                    handled = true;
                }
                return handled;
            }
        });

        list_active.setOnItemClickListener(this);


        // DB

        // BD Helper
        FeedReaderDBHelper mDBHelper = new FeedReaderDBHelper(this);

        // BD Write
        SQLiteDatabase dbw = mDBHelper.getWritableDatabase();
        mDBHelper.drop(dbw);
        mDBHelper.onCreate(dbw);

        //
        String item_name = "nome do item";
        int item_price = 199;
        int item_supermarket = 1;
        SimpleDateFormat item_date_format = new SimpleDateFormat("dd/MM/yyyy HH:MM:SS");
        String item_update_date = item_date_format.format(new Date());
        //
        ContentValues values = new ContentValues();
        values.put(ContractDB.ItemEntry.COLUMN_NAME_NAME, item_name);
        values.put(ContractDB.ItemEntry.COLUMN_NAME_PRICE, item_price);
        values.put(ContractDB.ItemEntry.COLUMN_NAME_SUPERMARKET, item_supermarket);
        values.put(ContractDB.ItemEntry.COLUMN_NAME_UPDATE_DATE, item_update_date);
        // Insert the new row, returning the primary key value of the new row
        long primaryKeyReturned = dbw.insert(ContractDB.ItemEntry.TABLE_NAME, null, values);
        dbw.close();

        Log.d("DB", "DB recriado");

        // DB Read
        SQLiteDatabase dbr = mDBHelper.getReadableDatabase();
        //
        String[] projection = {
                ContractDB.ItemEntry.COLUMN_NAME_ITEM_ID,
                ContractDB.ItemEntry.COLUMN_NAME_NAME,
                ContractDB.ItemEntry.COLUMN_NAME_PRICE,
                ContractDB.ItemEntry.COLUMN_NAME_SUPERMARKET,
                ContractDB.ItemEntry.COLUMN_NAME_UPDATE_DATE
        };
        //
        String selection = ContractDB.ItemEntry.COLUMN_NAME_ITEM_ID + "=?";
        String[] selectionArgs = new String[] {
                String.valueOf(1)};
        //
        String sortOrder =
                ContractDB.ItemEntry.COLUMN_NAME_NAME + " DESC";
        //
        Cursor c = dbr.query(
                ContractDB.ItemEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        // DB Read Cycle
        if (c != null){
            c.moveToFirst();
        } else {
            Log.d("DB", "DB nao lido");
        }
        Log.d("DB", "DB lido");
        Log.d("DB", c.getString(0));
        Log.d("DB", c.getString(1));
        Log.d("DB", c.getString(2));
        Log.d("DB", c.getString(3));
        Log.d("DB", c.getString(4));
        c.close();
        dbr.close();


        /*
        // DB Delete
        selection = ContractDB.ItemEntry.COLUMN_NAME_ITEM_ID + " LIKE ?";
        selectionArgs = new String[] { String.valueOf(1) };
        //
        dbw = mDBHelper.getWritableDatabase();
        dbw.delete(ContractDB.ItemEntry.TABLE_NAME, selection, selectionArgs);
        dbw.close();

        // DB Read
        dbr = mDBHelper.getReadableDatabase();
        //
        projection = new String[]{
                ContractDB.ItemEntry.COLUMN_NAME_ITEM_ID,
                ContractDB.ItemEntry.COLUMN_NAME_NAME,
                ContractDB.ItemEntry.COLUMN_NAME_PRICE,
                ContractDB.ItemEntry.COLUMN_NAME_SUPERMARKET,
                ContractDB.ItemEntry.COLUMN_NAME_UPDATE_DATE
        };
        //
        selection = ContractDB.ItemEntry.COLUMN_NAME_ITEM_ID + "=?";
        selectionArgs = new String[] {
                String.valueOf(1)};
        //
        sortOrder = ContractDB.ItemEntry.COLUMN_NAME_NAME + " DESC";
        //
        c = dbr.query(
                ContractDB.ItemEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        // DB Read Cycle
        if (c != null){
            c.moveToFirst();
            Log.d("DB", "DB lido");
            Log.d("DB", c.getString(0));
            Log.d("DB", c.getString(1));
            Log.d("DB", c.getString(2));
            Log.d("DB", c.getString(3));
            Log.d("DB", c.getString(4));
            c.close();
        } else {
            Log.d("DB", "DB nao lido");
        }
        dbr.close();
        */

    }


    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        hActionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        hActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        if (hActionBarDrawerToggle.onOptionsItemSelected(item))
            return true;

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.home_menu_save) {
            return true;
        }
        if (id == R.id.home_menu_searchprices) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void sendMessage(View view) {

        Intent intent = new Intent(this, List.class);
        //EditText messageT = (EditText) findViewById(R.id.add_item);
        //String message = messageT.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d("omg android", i + ": " + list_active_array.get(i));
    }
}
