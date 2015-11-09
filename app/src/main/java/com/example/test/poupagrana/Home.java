package com.example.test.poupagrana;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class Home extends AppCompatActivity implements AdapterView.OnItemClickListener {


    private DrawerLayout hDrawerLayout;
    private ListView drawer_list;
    private String[] drawer_list_itens;
    private ActionBarDrawerToggle hActionBarDrawerToggle;
    //public final static String EXTRA_MESSAGE = "com." + R.string.sub_domain + "." + R.string.domain
            //+ "." + R.string.app_name_sub;

    // lista ativa
    private ExpandableListView list_active;
    private ArrayAdapter list_active_adapter;
    private ArrayList list_active_array;
    DB.List list_active_ram = new DB.List();
    ArrayList<DB.Item> list_active_ram_itens;
    private Button list_active_info;

    private SimpleDateFormat date_format = new SimpleDateFormat("dd/MM/yyyy HH:MM:SS");


    private EditText add_item;

    // DB
    FeedReaderDBHelper mDBHelper;

    public static class DB {
        public static class Item {
            public long item_id;
            public long list_id;
            public int quantity;
            public int max_quantity;
            public String name;
            public boolean created;
            public boolean modified;
            public ArrayList<SupplierItem> supplierItem;
        }
        public static class List {
            public long id;
            public String info;
            public String date_created;
            public String date_acessed;
            public String date_modified;
            public int price; //
            public int achieved; //
            public boolean created;
            public boolean modified;
            public int quantity;
            public int max_quantity;
        }
        public static class ItemInSupplier {
            public long item_id;
            public long supplier_item_id;
        }
        public static class Supplier {
            public long id;
            public String name;
            public String info;
            public String address;
            public boolean enabled;
        }
        public static class SupplierItem {
            public long id;
            public long supplier_id;
            public String name;
            public String info;
            public long price;
            public String date_modified;
        }
    }

    private void onCreateToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void onCreateDrawer(){
        hDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        hActionBarDrawerToggle = new ActionBarDrawerToggle(this, hDrawerLayout,  R.string.drawer_openned, R.string.home_drawer_closed) {
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

        drawer_list = (ListView) findViewById(R.id.drawer_list);
        drawer_list_itens = getResources().getStringArray(R.array.drawer_entries);
        drawer_list.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, drawer_list_itens));
        drawer_list.setOnItemClickListener(new DrawerItemClickListener());

    }

    Drawable canExpand;
    private void onCreateList(){
        // cria lista e sua array
        list_active = (ExpandableListView) findViewById(R.id.list_active);
        /*list_active_array = new ArrayList();
        list_active_adapter = new ArrayAdapter(
                this,
                android.R.layout.simple_expandable_list_item_1,
                list_active_array);
        list_active.setAdapter(list_active_adapter);
        */

        //

        groupData = new ArrayList<Map<String, String>>();
        childData = new ArrayList<java.util.List<Map<String, String>>>();
        SimpleExpandableListAdapter expListAdapter =
                new SimpleExpandableListAdapter(
                        this,
                        groupData,              // Creating group List.
                        android.R.layout.simple_expandable_list_item_2,
                        new String[] { ITEM  },  // the key of group item.
                        new int[] { android.R.id.text1 },    // ID of each group item.-Data under the key goes into this TextView.
                        childData,              // childData describes second-level entries.
                        android.R.layout.simple_expandable_list_item_2,
                        new String[] { ITEM },      // Keys in childData maps to display.
                        new int[] { android.R.id.text1}     // Data under the keys above go into these TextViews.
                );
        list_active.setAdapter(expListAdapter);
        TypedArray expandableListViewStyle = this.getTheme().obtainStyledAttributes(new int[]{android.R.attr.expandableListViewStyle});
        TypedArray groupIndicator = this.getTheme().obtainStyledAttributes(expandableListViewStyle.getResourceId(0,0),new int[]{android.R.attr.groupIndicator});
        Drawable canExpand = groupIndicator.getDrawable(0);
        expandableListViewStyle.recycle();
        groupIndicator.recycle();
        //list_active.setGroupIndicator(null);
        //list_active.setGroupIndicator(canExpand);


    }

    private final String ITEM = "ITEM";
    private java.util.List<Map<String, String>> groupData;
    private java.util.List<java.util.List<Map<String, String>>> childData;
    private java.util.List<Map<String, String>> children;


    private void onCreateEditText(){


        //


        // ao acabar de escrever no campo de texto, enviar pra lista ativa
        //final EditText add_item = (EditText) findViewById(R.id.add_item);
        add_item = (EditText) findViewById(R.id.add_item);
        list_active_info = (Button) findViewById(R.id.list_active_info);
        add_item.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    DB.Item item = new DB.Item();
                    item.name = add_item.getText().toString();
                    item.quantity = 0;
                    item.max_quantity = 1;
                    item.modified = true;
                    item.created = true;
                    item.list_id = list_active_ram.id;
                    item.item_id = -1;
                    list_active_ram.quantity += 0;
                    list_active_ram.max_quantity += item.max_quantity;
                    add_item.setText("");
                    for (DB.Item item2 : list_active_ram_itens) {
                        if (item.name.equalsIgnoreCase(item2.name)) {
                            item2.quantity += item.quantity;
                            item2.max_quantity += item.max_quantity;
                            item2.modified = true;
                            list_active_adapter.notifyDataSetChanged();
                            return true;
                        }
                    }
                    addToList(item); // TODO test
                    handled = true;
                }
                return handled;
            }
        });
        list_active.setOnItemClickListener(this);

    }



    private void onCreateDB(){
        SQLiteDatabase dbw;
        SQLiteDatabase dbr;
        mDBHelper = new FeedReaderDBHelper(this);

        // tmp
        /*DB.Item item = new DB.Item();
        DB.ItemInList itemInList = new DB.ItemInList();
        DB.ItemInSupplier itemInSupplier = new DB.ItemInSupplier();
        DB.Supplier supplier = new DB.Supplier();
        DB.SupplierItem supplierItem = new DB.SupplierItem();
        itemInSupplier.item_id = 0;
        itemInSupplier.supplier_item_id = 0;
        supplier.id = 0;
        supplier.name = "mercadoA";
        supplier.info = "infoA";
        supplier.address = "addrA";
        supplierItem.id = 0;
        supplierItem.supplier_id = 0;
        supplierItem.name = "banana";
        supplierItem.info = "";
        supplierItem.price = 199;
        supplierItem.date_modified = date_format.format(new Date());;
        */

        // DB Read most recent accessed list
        dbr = mDBHelper.getReadableDatabase();
        String rawQuery;/* = "SELECT * FROM " + ContractDB.ListEntry.TABLE_NAME +
                " WHERE " + ContractDB.ListEntry.COLUMN_NAME_ACHIEVED + " = 0 " +
                " ORDER BY " + ContractDB.ListEntry.COLUMN_NAME_DATE_ACESSED + " DESC" +
                " LIMIT 1";*/
        //
        Cursor cList, cItem;
        cList = dbr.query(ContractDB.ListEntry.TABLE_NAME,
                new String[] { "*" },
                ContractDB.ListEntry.COLUMN_NAME_ACHIEVED + " = 0", null, null, null,
                ContractDB.ListEntry.COLUMN_NAME_DATE_ACESSED + " DESC",
                "1");
        //cList = dbr.rawQuery(rawQuery, null);
        //dbw = mDBHelper.getWritableDatabase();
        list_active_ram.modified = false;
        list_active_ram.created = false;
        list_active_ram_itens = new ArrayList<DB.Item>();
        if (cList != null && cList.getCount() == 1) {
            Log.d("DB", "Lista ativa existente"); // TODO test
            cList.moveToFirst();
            list_active_ram.id = Long.parseLong(cList.getString(0), 10);
            list_active_ram.info = cList.getString(1);
            list_active_ram.date_created = date_format.format(new Date());
            cList.getString(2); // TODO fix
            list_active_ram.date_acessed = date_format.format(new Date());
            cList.getString(3); // TODO fix
            list_active_ram.date_modified = date_format.format(new Date());
            cList.getString(0); // TODO fix
            list_active_ram.price = Integer.parseInt(cList.getString(5));
            list_active_ram.achieved = Integer.parseInt(cList.getString(6));
            if(list_active_ram.price == 0) {
                list_active.setGroupIndicator(null);
            } else {
                list_active.setGroupIndicator(canExpand);
            }
            Log.d("List", "id: " + list_active_ram.id + " info: " + list_active_ram.info + " date_created: " +
                    list_active_ram.date_created + "date_acessed: " + list_active_ram.date_acessed +
                    " date_modified: " + list_active_ram.date_modified + " price: " + list_active_ram.price +
                    " achieved: " + list_active_ram.achieved);

            Log.d("DB", "lendo itens da lista");
            rawQuery =
                    "SELECT " + ContractDB.ItemInListEntry.COLUMN_NAME_QUANTITY + ", " +
                            ContractDB.ItemInListEntry.COLUMN_NAME_MAX_QUANTITY + ", " +
                            ContractDB.ItemEntry.COLUMN_NAME_NAME + ", " +
                            ContractDB.ItemInListEntry.TABLE_NAME + "." + ContractDB.ItemInListEntry.COLUMN_NAME_ITEM_ID + ", " +
                            ContractDB.ItemInListEntry.TABLE_NAME + "." + ContractDB.ItemInListEntry.COLUMN_NAME_LIST_ID +
                            " FROM " + ContractDB.ItemInListEntry.TABLE_NAME + " INNER JOIN " + ContractDB.ItemEntry.TABLE_NAME +
                            " WHERE " + ContractDB.ItemInListEntry.TABLE_NAME + "." + ContractDB.ItemInListEntry.COLUMN_NAME_LIST_ID +
                            " = " + list_active_ram.id +
                            " AND " + ContractDB.ItemInListEntry.TABLE_NAME + "." + ContractDB.ItemInListEntry.COLUMN_NAME_ITEM_ID +
                            " = " + ContractDB.ItemEntry.TABLE_NAME + "." + ContractDB.ItemEntry.COLUMN_NAME_ITEM_ID;
            cItem = dbr.rawQuery(rawQuery, null);
            int i = -1;
            if (cItem != null) { // TODO test
                cItem.moveToFirst();
                DB.Item item = new DB.Item();
                list_active_ram.quantity = 0;
                list_active_ram.max_quantity = 0;
                do {
                    item.quantity = Integer.parseInt(cItem.getString(0));
                    item.max_quantity = Integer.parseInt(cItem.getString(1));
                    item.name = cItem.getString(2);
                    item.item_id = Integer.parseInt(cItem.getString(3));
                    item.list_id = Integer.parseInt(cItem.getString(4));
                    item.modified = false;
                    item.created = false;
                    // adiciona no campo
                    list_active_ram.quantity +=item.quantity;
                    list_active_ram.max_quantity +=item.max_quantity;
                    addToList(item); // TODO erro
                } while (cItem.moveToNext());
                ((BaseExpandableListAdapter) list_active.getExpandableListAdapter()).notifyDataSetChanged();
            }
            cItem.close();
        } else {
            Log.d("DB", "Lista ativa inexistente");
            list_active_ram.info = "Lista vazia";
            list_active_ram.quantity = 0;
            list_active_ram.max_quantity = 0;
            list_active_ram.date_created = date_format.format(new Date());
            list_active_ram.date_acessed = date_format.format(new Date());
            list_active_ram.date_modified = date_format.format(new Date());
            list_active_ram.price = 0;
            list_active_ram.achieved = 0;
            list_active_ram.created = true;
            Log.d("List", "id: " + list_active_ram.id + " info: " + list_active_ram.info + " date_created: " +
                    list_active_ram.date_created + "date_acessed: " + list_active_ram.date_acessed +
                    " date_modified: " + list_active_ram.date_modified + " price: " + list_active_ram.price +
                    " achieved: " + list_active_ram.achieved);
        }
        cList.close();
        dbr.close();

        { /* DB Delete
        selection = ContractDB.ItemEntry.COLUMN_NAME_ITEM_ID + " LIKE ?";
        selectionArgs = new String[] { String.valueOf(1) };
        //
        dbw = mDBHelper.getWritableDatabase();
        dbw.delete(ContractDB.ItemEntry.TABLE_NAME, selection, selectionArgs);
        dbw.close();*/
        }


        { /* DB Read

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
        dbr.close(); */
        }

    }


    private void onCreateServer() {

        // popula Supplier e Supplier_Item
        ContentValues v;
        SQLiteDatabase dbw = mDBHelper.getWritableDatabase();

        String[]suppliers = {"Bretas", "Pague Pouco", "Nova Europa", "JL"};
        for(String supplier : suppliers) {
            v = new ContentValues();
            v.put(ContractDB.SupplierEntry.COLUMN_NAME_NAME, supplier);
            v.put(ContractDB.SupplierEntry.COLUMN_NAME_INFO, supplier + "_info");
            v.put(ContractDB.SupplierEntry.COLUMN_NAME_ADDRESS, supplier + "_addr");
            dbw.insert(ContractDB.SupplierEntry.TABLE_NAME, null, v);
        }


        String[]produducts = {"Maca", "Banana", "Arroz", "Feijao", "Frango", "Refrigerante", "Leite", "Pao", "Presunto", "Mussarela", "Manteiga", "Cerveija", "Ovo", "Pizza"};
        Random rand = new Random();
        for(String product : produducts) {
            for (int j = 0; j < suppliers.length ;j++){
                v = new ContentValues();
                v.put(ContractDB.SupplierItemEntry.COLUMN_NAME_SUPPLIER_ID, j);
                v.put(ContractDB.SupplierItemEntry.COLUMN_NAME_NAME, product);
                v.put(ContractDB.SupplierItemEntry.COLUMN_NAME_INFO, product + "_info");
                v.put(ContractDB.SupplierItemEntry.COLUMN_NAME_PRICE, rand.nextInt(10));
                v.put(ContractDB.SupplierItemEntry.COLUMN_NAME_DATE_MODIFIED, date_format.format(new Date()));
                dbw.insert(ContractDB.SupplierItemEntry.TABLE_NAME, null, v);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        onCreateToolbar();
        onCreateDrawer();
        onCreateList();
        onCreateEditText();
        onCreateDB();
        onCreateServer();
    }

    void addToList(DB.Item item) { // TODO test
        list_active_ram.modified = true;
        list_active_ram_itens.add(item);
        if(list_active_ram.price == 0) {

            Map<String, String> curGroupMap = new HashMap<String, String>();
            curGroupMap.put(ITEM, item.name);
            groupData.add(curGroupMap);
            children = new ArrayList<Map<String, String>>();
            childData.add(children);
            list_active_info.setText(list_active_ram.quantity + " / " + list_active_ram.max_quantity +
                    " itens");


            //list_active.setGroupIndicator(canExpand);
        } else {
            /*for (int i = 0; i < 5; i++) { // TODO https://stackoverflow.com/questions/9824074/android-expandablelistview-looking-for-a-tutorial
            // TODO
                Map<String, String> curGroupMap = new HashMap<String, String>();
                groupData.add(curGroupMap);
                curGroupMap.put(ITEM, "parent " + i);

                if (i != 1)
                    for (int j = 0; j < 3; j++) {
                        Map<String, String> curChildMap = new HashMap<String, String>();
                        children.add(curChildMap);
                        curChildMap.put(ITEM, "Child " + j);
                    }
                childData.add(children);
            }*/

        }

        /*list_active_array.add(item.name);
        list_active_ram_itens.add(item); // TODO change
        list_active_adapter.notifyDataSetChanged();
        list_active_info.setText(list_active_ram.quantity + " / " + list_active_ram.max_quantity +
                " itens");*/
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

    private boolean menuSave() {
        ContentValues values;
        SQLiteDatabase dbw;
        if (!list_active_ram.modified) return true;
        if (list_active_ram.created) {
            Log.d("DB", "Lista criada");
            list_active_ram.date_modified = date_format.format(new Date());
            dbw = mDBHelper.getWritableDatabase();
            values = new ContentValues();
            values.put(ContractDB.ListEntry.COLUMN_NAME_INFO, list_active_ram.info);
            values.put(ContractDB.ListEntry.COLUMN_NAME_DATE_CREATED, list_active_ram.date_created);
            values.put(ContractDB.ListEntry.COLUMN_NAME_DATE_ACESSED, list_active_ram.date_acessed);
            values.put(ContractDB.ListEntry.COLUMN_NAME_DATE_MODIFIED, list_active_ram.date_modified);
            values.put(ContractDB.ListEntry.COLUMN_NAME_PRICE, list_active_ram.price);
            values.put(ContractDB.ListEntry.COLUMN_NAME_ACHIEVED, list_active_ram.achieved);
            list_active_ram.id = dbw.insert(ContractDB.ListEntry.TABLE_NAME, null, values);
            dbw.close();
        } else {
            Log.d("DB", "Lista atualizada");

        }
        // um item pode ser (1) inexistente, (2) existente para outra lista ou (3) existente pra mesmoa lista.
        // > em (1) e (2) o item eh marcado como criado.
        // >> em (1) o item de mesmo nome nao existe para outra lista.
        // para salvar os itens, procuramos pelos ja existentes e os atualizamos, para entao criar os inexistentes


        String rawQuery;
        dbw = mDBHelper.getWritableDatabase();
        for (DB.Item item : list_active_ram_itens) {
            if (item.created) {
                Log.d("DB", "salvando item:" + item.name);

                // add or replace in Item table
                        /*rawQuery = "INSERT OR REPLACE INTO " + ContractDB.ItemEntry.TABLE_NAME +
                                " ( " + ContractDB.ItemEntry.COLUMN_NAME_ITEM_ID + ",  " + ContractDB.ItemEntry.COLUMN_NAME_NAME + ")" +
                                " VALUES " +
                                "(" +
                                "(SELECT " + ContractDB.ItemEntry.COLUMN_NAME_ITEM_ID +
                                " FROM " + ContractDB.ItemEntry.TABLE_NAME  +
                                " WHERE " + ContractDB.ItemEntry.COLUMN_NAME_NAME + " = " + itemdb.name + ")" +
                                ", " + itemdb.name + ")";*/
                        /*rawQuery = "INSERT OR IGNORE INTO " + ContractDB.ItemEntry.TABLE_NAME + " ( " + ContractDB.ItemEntry.COLUMN_NAME_NAME + ")" +
                                " VALUES " + "(" + "" + itemdb.name + "" + ")";
                        dbw.execSQL(rawQuery);*/

                values = new ContentValues();
                values.put(ContractDB.ItemEntry.COLUMN_NAME_NAME, item.name);
                item.item_id = dbw.insertWithOnConflict(ContractDB.ItemEntry.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE);

                // criar tudo // TODO na hora de criar tem que ver se relamente eh criado, ou se ja existe na lista
                // add in ItenInList table
                        /*Log.d("DB", "salvando relacao item lista");
                        rawQuery = "INSERT INTO " + ContractDB.ItemInListEntry.TABLE_NAME + " ( " +
                                ContractDB.ItemInListEntry.TABLE_NAME + "." + ContractDB.ItemInListEntry.COLUMN_NAME_ITEM_ID + ", " +
                                ContractDB.ItemInListEntry.TABLE_NAME + "." + ContractDB.ItemInListEntry.COLUMN_NAME_LIST_ID + ", " +
                                ContractDB.ItemInListEntry.TABLE_NAME + "." + ContractDB.ItemInListEntry.COLUMN_NAME_QUANTITY + ", " +
                                ContractDB.ItemInListEntry.TABLE_NAME + "." + ContractDB.ItemInListEntry.COLUMN_NAME_MAX_QUANTITY  + ")" +
                                " VALUES " + "(" +
                                    "(SELECT " + ContractDB.ItemEntry.COLUMN_NAME_ITEM_ID +
                                    " FROM " + ContractDB.ItemEntry.TABLE_NAME  +
                                    " WHERE " + ContractDB.ItemEntry.COLUMN_NAME_NAME + " = " + itemdb.name + "), " +
                                list_active_ram.id + ", " +
                                itemdb.quantity + ", " +
                                itemdb.max_quantity + ")";
                        dbw.execSQL(rawQuery);*/

                values = new ContentValues();
                values.put(ContractDB.ItemInListEntry.COLUMN_NAME_ITEM_ID, item.item_id);
                values.put(ContractDB.ItemInListEntry.COLUMN_NAME_LIST_ID, list_active_ram.id);
                values.put(ContractDB.ItemInListEntry.COLUMN_NAME_QUANTITY, item.quantity);
                values.put(ContractDB.ItemInListEntry.COLUMN_NAME_MAX_QUANTITY, item.max_quantity);
                item.item_id = dbw.insert(ContractDB.ItemInListEntry.TABLE_NAME, null, values);

            } else {
                // atualizar tabela itemInList
                Log.d("DB", "atualizando item:" + item.name);

            }

        }
        dbw.close();




        return true;
    }

    private boolean menuSearchPrice() {
        menuSave();

        SQLiteDatabase dbr = mDBHelper.getReadableDatabase();
        DB.SupplierItem supplierItem;
        Map<Long, Long> suppliers_price = new HashMap<>();

        int supplierNum;
            for (DB.Item item : list_active_ram_itens) {
                Cursor c = dbr.query(ContractDB.SupplierItemEntry.TABLE_NAME,
                        new String[]{"*"},
                        ContractDB.SupplierItemEntry.COLUMN_NAME_NAME + " = ?", new String[]{item.name},
                        null, null, ContractDB.SupplierItemEntry.COLUMN_NAME_PRICE + " ASC", "5");

                item.supplierItem = new ArrayList<DB.SupplierItem>();

                if (c != null) {
                    c.moveToFirst();
                    do {
                        supplierItem = new DB.SupplierItem();
                        supplierItem.id = Long.parseLong(c.getString(0));
                        supplierItem.supplier_id = Long.parseLong(c.getString(1));
                        supplierItem.name = c.getString(2);
                        supplierItem.info = c.getString(3);
                        supplierItem.price = Long.parseLong(c.getString(4));
                        supplierItem.date_modified = date_format.format(new Date());
                        c.getString(5); // TODO
                        item.supplierItem.add(supplierItem);
                        if (suppliers_price.get(supplierItem.supplier_id) == null){
                            suppliers_price.put(supplierItem.supplier_id, supplierItem.price);
                        } else {
                            suppliers_price.put(supplierItem.supplier_id, (suppliers_price.get(supplierItem.supplier_id) + supplierItem.price));
                        }
                    } while (c.moveToNext());

                    c.close();
                }
                Collections.sort(item.supplierItem, new Comparator<DB.SupplierItem>() {
                    @Override
                    public int compare(DB.SupplierItem a, DB.SupplierItem b)
                    {
                        return (int) (a.price - b.price);
                    }
                });
            }


        dbr.close();


        // considerar somente o mercado que tem o menor preço
        Map.Entry<Long, Long> min = null;
        for (Map.Entry<Long, Long> entry : suppliers_price.entrySet()) {
            if (min == null || min.getValue() > entry.getValue()) {
                min = entry;
            }
        }

        ArrayList<Long> selected_suppliers = new ArrayList<Long>();
        selected_suppliers.add(min.getKey()); // apenas um mercado foi escolhido
        Log.d("$", "selected suppliers: " + selected_suppliers.toString());

        Map<Long, DB.Supplier> suppliers = new HashMap<Long, DB.Supplier>();
        dbr = mDBHelper.getReadableDatabase();
        Cursor c = dbr.query(ContractDB.SupplierEntry.TABLE_NAME,
                new String[]{"*"},
                null, null,
                null, null, null, null);
        if (c != null) {
            c.moveToFirst();
            DB.Supplier supplier;
            do {
                supplier = new DB.Supplier();
                supplier.id = Long.parseLong(c.getString(0));
                supplier.name = c.getString(1);
                supplier.info = c.getString(2);
                supplier.address = c.getString(3);
                supplier.enabled = false;
                suppliers.put(supplier.id, supplier);
            } while (c.moveToNext());
            for (Long selected : selected_suppliers) {
                Log.d("$", "suppliers: " + suppliers.toString() + ", selected: " + selected);
                suppliers.get(selected).enabled = true;
            }
            c.close();
        }

        java.util.List<Map<String,String>> newGroupData = new ArrayList<Map<String,String>>();
        Map<String, String> curGroupMap;
        java.util.List<java.util.List<Map<String, String>>> newChildData = new ArrayList<java.util.List<Map<String, String>>>();
        for (Long selected_supplier : selected_suppliers) {
            curGroupMap = new HashMap<String, String>();
            curGroupMap.put(ITEM, suppliers.get(selected_supplier).name);
            newGroupData.add(curGroupMap);
            children = new ArrayList<Map<String, String>>();
            newChildData.add(children);
        }
        curGroupMap = new HashMap<String, String>();
        curGroupMap.put(ITEM, "Sem mercado");
        newGroupData.add(curGroupMap);
        children = new ArrayList<Map<String, String>>();
        newChildData.add(children);

        for (DB.Item item : list_active_ram_itens) {
            int i, j;
            Log.d("$", "Item: " + item.name);
            for (i = 0; i < selected_suppliers.size(); i++){
                for(j = 0; j < item.supplierItem.size(); j++) {
                    Log.d("$", "i:" + i + ", j:"+ j);
                    supplierItem = item.supplierItem.get(j);
                    if (supplierItem.supplier_id == selected_suppliers.get(i)) {
                        Log.d("$", "BATEU");
                        Map<String, String> curChildMap = new HashMap<String, String>();
                        curChildMap.put(ITEM, item.quantity + "/" + item.max_quantity + "\t\t" + item.name + "\t\t$" + supplierItem.price);
                        newChildData.get(i).add(curChildMap);
                        break;
                    }
                }
                if (j != item.supplierItem.size()) break;
            }
            if (i == selected_suppliers.size()) {
                Map<String, String> curChildMap = new HashMap<String, String>();
                curChildMap.put(ITEM, item.quantity + "/" + item.max_quantity + "\t" + item.name);
                newChildData.get(i).add(curChildMap);
            }
        }


        groupData.clear();
        /*for (Map<String,String> gd : groupData){
            groupData.remove(gd);
        }*/
        childData.clear();
        /*for (java.util.List<Map<String, String>> cd : childData){
            childData.remove(cd);
        }*/
        for (Map<String,String> gd : newGroupData){
            groupData.add(gd);
        }
        for (java.util.List<Map<String, String>> cd : newChildData) {
            childData.add(cd);
        }


        ((BaseExpandableListAdapter) list_active.getExpandableListAdapter()).notifyDataSetChanged();

        // add pais e filhos
            /*for (int i = 0; i < 5; i++) { // TODO https://stackoverflow.com/questions/9824074/android-expandablelistview-looking-for-a-tutorial
            // TODO
                Map<String, String> curGroupMap = new HashMap<String, String>();
                groupData.add(curGroupMap);
                curGroupMap.put(ITEM, "parent " + i);

                if (i != 1)
                    for (int j = 0; j < 3; j++) {
                        Map<String, String> curChildMap = new HashMap<String, String>();
                        children.add(curChildMap);
                        curChildMap.put(ITEM, "Child " + j);
                    }
                childData.add(children);
            }*/


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        if (hActionBarDrawerToggle.onOptionsItemSelected(item))
            return true;

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case R.id.home_menu_searchprices:
                return menuSearchPrice();
            case R.id.home_menu_save: // TODO
                return menuSave();
            case R.id.home_menu_list_new:

                return true;
            case R.id.home_menu_list_active_remove:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void sendMessage(View view) {

        Intent intent = new Intent(this, List.class);
        //EditText messageT = (EditText) findViewById(R.id.add_item);
        //String message = messageT.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);

    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            Log.d("Drawer", position + ": " + drawer_list_itens[position]);
            hDrawerLayout.closeDrawer(drawer_list);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        //Log.d("List", position + ": " + list_active_array.get(position));
    }
}
