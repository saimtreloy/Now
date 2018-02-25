package saim.com.now.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import saim.com.now.Model.ModelItemList;

/**
 * Created by NREL on 2/25/18.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "ShoppingCart";

    // Contacts table name
    private static final String TABLE_CONTACTS = "shoppingItem";

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
    private static final String KEY_CART_Q = "cartQ";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_ITEM_ID + " TEXT,"
                + KEY_NAME + " TEXT,"
                + KEY_PRICE + " TEXT,"
                + KEY_PRICE_D + " TEXT,"
                + KEY_QUANTITY + " TEXT,"
                + KEY_ICON + " TEXT,"
                + KEY_VENDOR + " TEXT,"
                + KEY_VENDOR_ICON + " TEXT"
                + KEY_CART_Q + " INTEGER" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    void addContact(ModelItemList itemList) {
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

        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close();
    }

    public int updateCartQ(int c) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CART_Q, c);

        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?", new String[] { String.valueOf(c) });
    }

    // Getting single contact
    ModelItemList getItemList(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID, KEY_ITEM_ID, KEY_NAME , KEY_PRICE , KEY_PRICE_D , KEY_QUANTITY , KEY_ICON , KEY_VENDOR , KEY_VENDOR_ICON , KEY_CART_Q},
                KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        ModelItemList modelItemList = new ModelItemList(cursor.getString(0),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),
                cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8),
                Integer.parseInt(cursor.getString(9)) );

        // return contact
        return modelItemList;
    }

    // Getting All Contacts
    public ArrayList<ModelItemList> getAllContacts() {
        ArrayList<ModelItemList> modelItemLists = new ArrayList<ModelItemList>();

        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ModelItemList modelItemList = new ModelItemList(cursor.getString(0),
                        cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),
                        cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8),
                        Integer.parseInt(cursor.getString(9)) );
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

        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?", new String[] { String.valueOf(itemList.getId()) });
    }

    // Deleting single contact
    public void deleteContact(ModelItemList itemList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?", new String[] { String.valueOf(itemList.getId()) });
        db.close();
    }


    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}