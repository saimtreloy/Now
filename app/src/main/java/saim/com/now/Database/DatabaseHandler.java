package saim.com.now.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import saim.com.now.Model.ModelItemList;

/**
 * Created by NREL on 2/25/18.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ShoppingCart";

    // Contacts table name
    private static final String TABLE_ITEM = "shoppingItem";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_ITEM_ID = "item_id";
    private static final String KEY_NAME = "item_name";
    private static final String KEY_PRICE = "item_price";
    private static final String KEY_PRICE_D = "item_d_price";
    private static final String KEY_QUANTITY = "item_quantity";
    private static final String KEY_ICON = "item_icon";
    private static final String KEY_VENDOR = "item_vendor";
    private static final String KEY_VENDOR_ICON = "item_vendor_icon";
    private static final String KEY_CART_Q = "cart_q";
    private static final String KEY_TOTAL_PRICE = "total_price";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_ITEM + " ("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_ITEM_ID + " TEXT,"
                + KEY_NAME + " TEXT,"
                + KEY_PRICE + " TEXT,"
                + KEY_PRICE_D + " TEXT,"
                + KEY_QUANTITY + " TEXT,"
                + KEY_ICON + " TEXT,"
                + KEY_VENDOR + " TEXT,"
                + KEY_VENDOR_ICON + " TEXT,"
                + KEY_CART_Q + " TEXT,"
                + KEY_TOTAL_PRICE + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEM);
        onCreate(db);
    }


    public void addContact(ModelItemList itemList) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, itemList.getId());
        values.put(KEY_ITEM_ID, itemList.getItem_id());
        values.put(KEY_NAME, itemList.getItem_name());
        values.put(KEY_PRICE, itemList.getItem_price());
        values.put(KEY_PRICE_D, itemList.getItem_d_price());
        values.put(KEY_QUANTITY, itemList.getItem_quantity());
        values.put(KEY_ICON, itemList.getItem_icon());
        values.put(KEY_VENDOR, itemList.getItem_vendor());
        values.put(KEY_VENDOR_ICON, itemList.getItem_vendor_icon());
        values.put(KEY_CART_Q, itemList.getCartQ());
        values.put(KEY_TOTAL_PRICE, itemList.getTotal_price());

        // Inserting Row
        db.insert(TABLE_ITEM, null, values);
        db.close();
    }

    public void updateCartQ(String id, String valueCart, String valueTotalPrice) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CART_Q, valueCart);
        values.put(KEY_TOTAL_PRICE, valueTotalPrice);

        db.update(TABLE_ITEM, values, KEY_ID + " = ?", new String[] { String.valueOf(id) });
    }

    // Getting single contact
    ModelItemList getItemList(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ITEM, new String[] { KEY_ID, KEY_ITEM_ID, KEY_NAME , KEY_PRICE , KEY_PRICE_D , KEY_QUANTITY , KEY_ICON , KEY_VENDOR , KEY_VENDOR_ICON , KEY_CART_Q, KEY_TOTAL_PRICE},
                KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        ModelItemList modelItemList = new ModelItemList(cursor.getString(0),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),
                cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8),
                cursor.getString(9), cursor.getString(10) );

        // return contact
        return modelItemList;
    }

    // Getting All Contacts
    public ArrayList<ModelItemList> getAllContacts() {
        ArrayList<ModelItemList> modelItemLists = new ArrayList<ModelItemList>();

        String selectQuery = "SELECT  * FROM " + TABLE_ITEM;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ModelItemList modelItemList = new ModelItemList(cursor.getString(0),
                        cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),
                        cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8),
                        cursor.getString(9), cursor.getString(10) );
                // Adding contact to list
                modelItemLists.add(modelItemList);
            } while (cursor.moveToNext());
        }

        // return contact list
        return modelItemLists;
    }

    // Updating single contact
    public int updateContact(ModelItemList itemList) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CART_Q, itemList.getCartQ());
        values.put(KEY_TOTAL_PRICE, itemList.getTotal_price());

        // updating row
        return db.update(TABLE_ITEM, values, KEY_ID + " = ?", new String[] { String.valueOf(itemList.getId()) });
    }

    // Deleting single contact
    public void deleteContact(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ITEM, KEY_ID + " = ?", new String[] { String.valueOf(id) });
        db.close();
    }


    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_ITEM;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    // Getting contacts Count
    public int getTotalPrice() {
        int totalPrice = 0;

        String selectQuery = "SELECT " + KEY_TOTAL_PRICE + " FROM " + TABLE_ITEM;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                totalPrice = totalPrice + Integer.parseInt(cursor.getString(0));
                Log.d("SAIM TOTAL PRICE", cursor.getString(0)+ "");
            } while (cursor.moveToNext());
        }
        return totalPrice;
    }

}