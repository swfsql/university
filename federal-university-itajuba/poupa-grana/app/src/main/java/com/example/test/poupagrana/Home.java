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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;


public class Home extends AppCompatActivity implements AdapterView.OnItemClickListener {


    private DrawerLayout hDrawerLayout;
    private ListView drawer_list;
    private String[] drawer_list_itens;
    private ActionBarDrawerToggle hActionBarDrawerToggle;
    //public final static String EXTRA_MESSAGE = "com." + R.string.sub_domain + "." + R.string.domain
    //+ "." + R.string.app_name_sub;

    // lista ativa
    private ExpandableListView expandableListView;
    private ArrayAdapter expandableListView_arrayAdapter;
    private ArrayList expandableListView_arrayList;
    private DB.List DB_List = new DB.List();
    private ArrayList<DB.Item> DB_Items;
    private Button button;

    private static final SimpleDateFormat date_format = new SimpleDateFormat("dd/MM/yyyy HH:MM:SS");


    private EditText editText;

    // DB
    FeedReaderDBHelper dbHelper;

    public static class DB {
        public static class SupplierItem {
            public long id;
            public long supplier_id;
            public String name;
            public String info;
            public long price;
            public String date_modified;
        }
        public static class Item {
            public long item_id;
            public long list_id;
            public int quantity;
            public int max_quantity;
            public String name;
            public boolean created;
            public boolean modified;
            public ArrayList<SupplierItem> supplierItems;

            public boolean getSupplierItems(SQLiteDatabase dbr, Map<Long, Long> suppliersPrice) {
                Cursor c = dbr.query(
                        ContractDB.SupplierItemEntry.TABLE_NAME,
                        new String[]{"*"},
                        ContractDB.SupplierItemEntry.COLUMN_NAME_NAME + " = ?",
                        new String[]{this.name}, null, null,
                        ContractDB.SupplierItemEntry.COLUMN_NAME_PRICE + " ASC",
                        "5");

                this.supplierItems = new ArrayList<SupplierItem>();
                SupplierItem supplierItem = new SupplierItem();

                if (c != null) {
                    c.moveToFirst();
                    do {
                        supplierItem = new SupplierItem();
                        supplierItem.id = Long.parseLong(c.getString(0));
                        supplierItem.supplier_id = Long.parseLong(c.getString(1));
                        supplierItem.name = c.getString(2);
                        supplierItem.info = c.getString(3);
                        supplierItem.price = Long.parseLong(c.getString(4));
                        supplierItem.date_modified = date_format.format(new Date());
                        c.getString(5); // TODO
                        this.supplierItems.add(supplierItem);
                        if (suppliersPrice.get(supplierItem.supplier_id) == null) {
                            suppliersPrice.put(supplierItem.supplier_id, supplierItem.price);
                        } else {
                            suppliersPrice.put(supplierItem.supplier_id, (suppliersPrice.get(supplierItem.supplier_id) + supplierItem.price));
                        }
                    } while (c.moveToNext());

                    c.close();
                    java.util.Collections.sort(this.supplierItems, new java.util.Comparator<DB.SupplierItem>() {
                        @Override
                        public int compare(DB.SupplierItem a, DB.SupplierItem b) {
                            return (int) (a.price - b.price);
                        }
                    });
                } else {
                    return false;
                }
                return true;
            }
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
            public int price;
            public boolean enabled;
        }

    }

    private void onCreateToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void onCreateDrawer() {
        hDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        hActionBarDrawerToggle = new ActionBarDrawerToggle(this, hDrawerLayout, R.string.drawer_openned, R.string.home_drawer_closed) {
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

    Drawable expandableList_expandSymbol;

    private void onCreateExpandableList() {
        // cria lista e sua array
        expandableListView = (ExpandableListView) findViewById(R.id.list_active);

        expandableList_parents = new ArrayList<Map<String, String>>();
        expandableList_childsList = new ArrayList<java.util.List<Map<String, String>>>();
        SimpleExpandableListAdapter expListAdapter =
                new SimpleExpandableListAdapter(
                        this,
                        expandableList_parents,              // Creating group List.
                        android.R.layout.simple_expandable_list_item_2,
                        new String[]{ITEM},  // the key of group item.
                        new int[]{android.R.id.text1},    // ID of each group item.-Data under the key goes into this TextView.
                        expandableList_childsList,              // expandableList_childsList describes second-level entries.
                        android.R.layout.simple_expandable_list_item_2,
                        new String[]{ITEM},      // Keys in expandableList_childsList maps to display.
                        new int[]{android.R.id.text1}     // Data under the keys above go into these TextViews.
                );
        expandableListView.setAdapter(expListAdapter);
        TypedArray expandableListViewStyle = this.getTheme().obtainStyledAttributes(new int[]{android.R.attr.expandableListViewStyle});
        TypedArray groupIndicator = this.getTheme().obtainStyledAttributes(expandableListViewStyle.getResourceId(0, 0), new int[]{android.R.attr.groupIndicator});
        expandableList_expandSymbol = groupIndicator.getDrawable(0);
        expandableListViewStyle.recycle();
        groupIndicator.recycle();
        expandableListView.setGroupIndicator(null);
        //expandableListView.setGroupIndicator(expandableList_expandSymbol);
    }

    private final String ITEM = "ITEM";
    private java.util.List<Map<String, String>> expandableList_parents;
    private Map<String, String> expandableList_parent;
    private java.util.List<java.util.List<Map<String, String>>> expandableList_childsList;
    private java.util.List<Map<String, String>> expandableList_childs;
    private Map<String, String> expandableList_child;

    private void onCreateEditText() {

        // ao acabar de escrever no campo de texto, enviar pra lista ativa
        //final EditText editText = (EditText) findViewById(R.id.editText);
        editText = (EditText) findViewById(R.id.add_item);
        button = (Button) findViewById(R.id.list_active_info);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    DB.Item item = new DB.Item();
                    item.name = editText.getText().toString();
                    item.quantity = 0;
                    item.max_quantity = 1;
                    item.modified = true;
                    item.created = true;
                    item.list_id = DB_List.id;
                    item.item_id = -1;
                    DB_List.quantity += 0;
                    DB_List.max_quantity += item.max_quantity;
                    editText.setText("");
                    for (DB.Item item2 : DB_Items) {
                        if (item.name.equalsIgnoreCase(item2.name)) {
                            item2.quantity += item.quantity;
                            item2.max_quantity += item.max_quantity;
                            item2.modified = true;
                            expandableListView_arrayAdapter.notifyDataSetChanged();
                            return true;
                        }
                    }
                    expandableList_addItem(item); // TODO test
                    handled = true;
                }
                return handled;
            }
        });
        expandableListView.setOnItemClickListener(this);

    }


    private void onCreateDB() {
        SQLiteDatabase dbw;
        SQLiteDatabase dbr;
        dbHelper = new FeedReaderDBHelper(this);

        // DB Read most recent accessed list
        dbr = dbHelper.getReadableDatabase();
        String query;
        Cursor cList, cItem;
        cList = dbr.query(ContractDB.ListEntry.TABLE_NAME,
                new String[]{"*"},
                ContractDB.ListEntry.COLUMN_NAME_ACHIEVED + " = 0", null, null, null,
                ContractDB.ListEntry.COLUMN_NAME_DATE_ACESSED + " DESC",
                "1");

        //dbw = dbHelper.getWritableDatabase();
        DB_List.modified = false;
        DB_List.created = false;
        DB_Items = new ArrayList<DB.Item>();
        if (cList != null && cList.getCount() == 1) {
            Log.d("DB", "Lista ativa existente"); // TODO test
            cList.moveToFirst();
            DB_List.id = Long.parseLong(cList.getString(0), 10);
            DB_List.info = cList.getString(1);
            DB_List.date_created = date_format.format(new Date());
            cList.getString(2); // TODO fix
            DB_List.date_acessed = date_format.format(new Date());
            cList.getString(3); // TODO fix
            DB_List.date_modified = date_format.format(new Date());
            cList.getString(0); // TODO fix
            DB_List.price = Integer.parseInt(cList.getString(5));
            DB_List.achieved = Integer.parseInt(cList.getString(6));
            if (DB_List.price == 0) {
                //expandableListView.setGroupIndicator(null);
            } else {
                expandableListView.setGroupIndicator(expandableList_expandSymbol);
            }
            Log.d("List", "id: " + DB_List.id + " info: " + DB_List.info + " date_created: " +
                    DB_List.date_created + "date_acessed: " + DB_List.date_acessed +
                    " date_modified: " + DB_List.date_modified + " price: " + DB_List.price +
                    " achieved: " + DB_List.achieved);

            Log.d("DB", "lendo itens da lista");
            query =
                    "SELECT " + ContractDB.ItemInListEntry.COLUMN_NAME_QUANTITY + ", " +
                            ContractDB.ItemInListEntry.COLUMN_NAME_MAX_QUANTITY + ", " +
                            ContractDB.ItemEntry.COLUMN_NAME_NAME + ", " +
                            ContractDB.ItemInListEntry.TABLE_NAME + "." + ContractDB.ItemInListEntry.COLUMN_NAME_ITEM_ID + ", " +
                            ContractDB.ItemInListEntry.TABLE_NAME + "." + ContractDB.ItemInListEntry.COLUMN_NAME_LIST_ID +
                            " FROM " + ContractDB.ItemInListEntry.TABLE_NAME + " INNER JOIN " + ContractDB.ItemEntry.TABLE_NAME +
                            " WHERE " + ContractDB.ItemInListEntry.TABLE_NAME + "." + ContractDB.ItemInListEntry.COLUMN_NAME_LIST_ID +
                            " = " + DB_List.id +
                            " AND " + ContractDB.ItemInListEntry.TABLE_NAME + "." + ContractDB.ItemInListEntry.COLUMN_NAME_ITEM_ID +
                            " = " + ContractDB.ItemEntry.TABLE_NAME + "." + ContractDB.ItemEntry.COLUMN_NAME_ITEM_ID;
            cItem = dbr.rawQuery(query, null);
            int i = -1;
            if (cItem != null) { // TODO test
                cItem.moveToFirst();
                DB.Item item = new DB.Item();
                DB_List.quantity = 0;
                DB_List.max_quantity = 0;
                do {
                    item.quantity = Integer.parseInt(cItem.getString(0));
                    item.max_quantity = Integer.parseInt(cItem.getString(1));
                    item.name = cItem.getString(2);
                    item.item_id = Integer.parseInt(cItem.getString(3));
                    item.list_id = Integer.parseInt(cItem.getString(4));
                    item.modified = false;
                    item.created = false;
                    // adiciona no campo
                    DB_List.quantity += item.quantity;
                    DB_List.max_quantity += item.max_quantity;
                    expandableList_addItem(item); // TODO erro
                } while (cItem.moveToNext());
                ((BaseExpandableListAdapter) expandableListView.getExpandableListAdapter()).notifyDataSetChanged();
            }
            cItem.close();
        } else {
            Log.d("DB", "Lista ativa inexistente");
            DB_List.info = "Lista vazia";
            DB_List.quantity = 0;
            DB_List.max_quantity = 0;
            DB_List.date_created = date_format.format(new Date());
            DB_List.date_acessed = date_format.format(new Date());
            DB_List.date_modified = date_format.format(new Date());
            DB_List.price = 0;
            DB_List.achieved = 0;
            DB_List.created = true;
        }
        cList.close();
        dbr.close();

        { /* DB Delete
        selection = ContractDB.ItemEntry.COLUMN_NAME_ITEM_ID + " LIKE ?";
        selectionArgs = new String[] { String.valueOf(1) };
        //
        dbw = dbHelper.getWritableDatabase();
        dbw.delete(ContractDB.ItemEntry.TABLE_NAME, selection, selectionArgs);
        dbw.close();*/
        }


    }


    private void onCreateServer() {

        // popula Supplier e Supplier_Item
        ContentValues v;
        SQLiteDatabase dbw = dbHelper.getWritableDatabase();

        String[] suppliers = {"Bretas", "Pague Pouco", "Nova Europa", "JL"};
        for (String supplier : suppliers) {
            v = new ContentValues();
            v.put(ContractDB.SupplierEntry.COLUMN_NAME_NAME, supplier);
            v.put(ContractDB.SupplierEntry.COLUMN_NAME_INFO, supplier + "_info");
            v.put(ContractDB.SupplierEntry.COLUMN_NAME_ADDRESS, supplier + "_addr");
            dbw.insert(ContractDB.SupplierEntry.TABLE_NAME, null, v);
        }


        String[] produducts = {"Maca", "Banana", "Arroz", "Feijao", "Frango", "Refrigerante", "Leite", "Pao", "Presunto", "Mussarela", "Manteiga", "Cerveija", "Ovo", "Pizza"};
        Random rand = new Random();
        for (String product : produducts) {
            for (int j = 0; j < suppliers.length; j++) {
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
        onCreateExpandableList();
        onCreateEditText();
        onCreateDB();
        onCreateServer();
    }

    void expandableList_addItem(DB.Item item) { // TODO test
        DB_List.modified = true;
        DB_Items.add(item);
        if (DB_List.price == 0) {

            Map<String, String> curGroupMap = new HashMap<String, String>();
            curGroupMap.put(ITEM, item.name);
            expandableList_parents.add(curGroupMap);
            expandableList_childs = new ArrayList<Map<String, String>>();
            expandableList_childsList.add(expandableList_childs);
            button.setText(DB_List.quantity + " / " + DB_List.max_quantity +
                    " itens");


            ((BaseExpandableListAdapter) expandableListView.getExpandableListAdapter()).notifyDataSetChanged();

            //expandableListView.setGroupIndicator(expandableList_expandSymbol);
        } else {
            /*for (int i = 0; i < 5; i++) { // TODO https://stackoverflow.com/questions/9824074/android-expandablelistview-looking-for-a-tutorial
            // TODO
                Map<String, String> curGroupMap = new HashMap<String, String>();
                expandableList_parents.add(curGroupMap);
                curGroupMap.put(ITEM, "parent " + i);

                if (i != 1)
                    for (int j = 0; j < 3; j++) {
                        Map<String, String> curChildMap = new HashMap<String, String>();
                        expandableList_childs.add(curChildMap);
                        curChildMap.put(ITEM, "Child " + j);
                    }
                expandableList_childsList.add(expandableList_childs);
            }*/

        }

        /*expandableListView_arrayList.add(item.name);
        DB_Items.add(item); // TODO change
        expandableListView_arrayAdapter.notifyDataSetChanged();
        button.setText(DB_List.quantity + " / " + DB_List.max_quantity +
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
        if (!DB_List.modified) return true;
        if (DB_List.created) {
            Log.d("DB", "Lista criada");
            DB_List.date_modified = date_format.format(new Date());
            dbw = dbHelper.getWritableDatabase();
            values = new ContentValues();
            values.put(ContractDB.ListEntry.COLUMN_NAME_INFO, DB_List.info);
            values.put(ContractDB.ListEntry.COLUMN_NAME_DATE_CREATED, DB_List.date_created);
            values.put(ContractDB.ListEntry.COLUMN_NAME_DATE_ACESSED, DB_List.date_acessed);
            values.put(ContractDB.ListEntry.COLUMN_NAME_DATE_MODIFIED, DB_List.date_modified);
            values.put(ContractDB.ListEntry.COLUMN_NAME_PRICE, DB_List.price);
            values.put(ContractDB.ListEntry.COLUMN_NAME_ACHIEVED, DB_List.achieved);
            DB_List.id = dbw.insert(ContractDB.ListEntry.TABLE_NAME, null, values);
            dbw.close();
        } else {
            Log.d("DB", "Lista atualizada");

        }
        // um item pode ser (1) inexistente, (2) existente para outra lista ou (3) existente pra mesmoa lista.
        // > em (1) e (2) o item eh marcado como criado.
        // >> em (1) o item de mesmo nome nao existe para outra lista.
        // para salvar os itens, procuramos pelos ja existentes e os atualizamos, para entao criar os inexistentes


        String rawQuery;
        dbw = dbHelper.getWritableDatabase();
        for (DB.Item item : DB_Items) {
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
                                DB_List.id + ", " +
                                itemdb.quantity + ", " +
                                itemdb.max_quantity + ")";
                        dbw.execSQL(rawQuery);*/

                values = new ContentValues();
                values.put(ContractDB.ItemInListEntry.COLUMN_NAME_ITEM_ID, item.item_id);
                values.put(ContractDB.ItemInListEntry.COLUMN_NAME_LIST_ID, DB_List.id);
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


    private boolean unpricedList() {

        return true;
    }

    private boolean pricedList() {

        return true;
    }


    class ValueComparator implements java.util.Comparator {
        Map<Long, Long> base;

        public ValueComparator(Map base) {
            this.base = base;
        }

        @Override
        public int compare(Object a, Object b) {
            if (base.get((Long) a) >= base.get((Long) b)) {
                return -1;
            } else {
                return 1;
            }
        }
    }

    private boolean menuSearchPrice() {
        menuSave();

        SQLiteDatabase dbr = dbHelper.getReadableDatabase();
        Map<Long, Long> suppliersPrice = new HashMap<>();
        boolean noSupplier = false;
        for (DB.Item item : DB_Items) {
            if (!item.getSupplierItems(dbr, suppliersPrice))
                noSupplier = true;
        }
        dbr.close();

        // sort suppliers by total price
        ValueComparator bvc = new ValueComparator(suppliersPrice);
        TreeMap<Long, Long> suppliersPriceSorted = new TreeMap(bvc);
        suppliersPriceSorted.putAll(suppliersPrice);
        ArrayList<Long> selected_suppliers = new ArrayList<Long>();
        for (Map.Entry<Long, Long> entry : suppliersPriceSorted.entrySet()) {
            selected_suppliers.add(entry.getKey());
        }
        Log.d("$", "selected suppliers: " + selected_suppliers.toString());

        Map<Long, DB.Supplier> suppliers = new HashMap<Long, DB.Supplier>();

        // read suppliers
        dbr = dbHelper.getReadableDatabase();
        Cursor c = dbr.query(ContractDB.SupplierEntry.TABLE_NAME,
                new String[]{"*"},
                null, null,
                null, null, null,
                null); // LIMIT
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
                supplier.price = 0;
                suppliers.put(supplier.id, supplier);
            } while (c.moveToNext());
            for (Long selected : selected_suppliers) {
                Log.d("$", "suppliers: " + suppliers.toString() + ", selected: " + selected);
                suppliers.get(selected + 1).enabled = true;
            }
            c.close();
        }

        //
        this.expandableList_parents.clear();
        Map<String, String> curGroupMap;
        this.expandableList_childsList.clear();
        for (Long selected_supplier : selected_suppliers) {
            curGroupMap = new HashMap<String, String>();
            curGroupMap.put(ITEM, suppliers.get(selected_supplier + 1).name);
            expandableList_parents.add(curGroupMap);
            expandableList_childs = new ArrayList<Map<String, String>>();
            expandableList_childsList.add(expandableList_childs);
        }
        if (noSupplier) {
            curGroupMap = new HashMap<String, String>();
            curGroupMap.put(ITEM, "Sem mercado");
            expandableList_parents.add(curGroupMap);
            expandableList_childs = new ArrayList<Map<String, String>>();
            expandableList_childsList.add(expandableList_childs);
        }

        DB.SupplierItem supplierItem;
        for (DB.Item item : DB_Items) {
            int i, j;
            Log.d("$", "Item: " + item.name);
            for (i = 0; i < selected_suppliers.size(); i++) {
                for (j = 0; j < item.supplierItems.size(); j++) {
                    Log.d("$", "i:" + i + ", j:" + j);
                    supplierItem = item.supplierItems.get(j);
                    if (supplierItem.supplier_id == selected_suppliers.get(i)) {
                        Log.d("$", "BATEU");
                        Map<String, String> curChildMap = new HashMap<String, String>();
                        curChildMap.put(ITEM, item.quantity + "/" + item.max_quantity + "\t\t" + item.name + "\t\t$" + supplierItem.price);
                        expandableList_childsList.get(i).add(curChildMap);
                        suppliers.get(supplierItem.supplier_id).price += supplierItem.price;
                        break;
                    }
                }
                if (j != item.supplierItems.size()) break;
            }
            if (i == selected_suppliers.size()) {
                Map<String, String> curChildMap = new HashMap<String, String>();
                curChildMap.put(ITEM, item.quantity + "/" + item.max_quantity + "\t" + item.name);
                expandableList_childsList.get(i).add(curChildMap);
            }
        }

        expandableListView.setGroupIndicator(expandableList_expandSymbol);
        ((BaseExpandableListAdapter) expandableListView.getExpandableListAdapter()).notifyDataSetChanged();

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
        //EditText messageT = (EditText) findViewById(R.id.editText);
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
        //Log.d("List", position + ": " + expandableListView_arrayList.get(position));
    }
}
