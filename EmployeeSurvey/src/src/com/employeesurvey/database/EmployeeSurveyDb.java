package src.com.employeesurvey.database;

import src.com.employeesurvey.util.Constants;
import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 
 * Database class for storing employee data to local database
 * 
 */
public class EmployeeSurveyDb {

	private static final int DATABASE_VERSION = 1;
	private static Context mContext;

	private static EmployeeSurveyDb instance;
	private static String TAG = EmployeeSurveyDb.class.getSimpleName();
	private boolean opened = false;
	/** Wrapper class for SQLite helper class */
	private DatabaseWrapper databaseWrapper;
	/** Internal access to database */
	private SQLiteDatabase database;
	private int lastRowId;

	/**
	 * username and storename table
	 */
	private static final String USERNAME_STORENAME_TABLE = "userdetails";
	private static final String FIELD_USER_ROW_ID = "user_row_id";
	private static final String FIELD_USERNAME = "username_id";
	private static final String FIELD_STORENAME = "storename_id";

	private final static String QUERY_USERNAME_STORENAME_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ USERNAME_STORENAME_TABLE
			+ " ("
			+ FIELD_USER_ROW_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ FIELD_USERNAME
			+ " TEXT, " + FIELD_STORENAME + " TEXT);";

	/**
	 * left row table
	 */

	private static final String LEFT_ROW_DETAIL_TABLE = "left_row_detail";
	private static final String FIELD_ROW_ID = "row_id";
	private static final int FIELD_ROW_ID_COULMN_INDEX = 0;
	private static final String FIELD_PERSON_COUNT = "person_count";
	private static final String FIELD_TIME = "time";
	private static final String FIELD_LATITUDE = "latitude";
	private static final String FIELD_lONGITUDE = "longitude";
	private static final String FIELD_COMPLETED = "completed";
	private static final String FIELD_DELETE = "should_delete";

	private final static String QUERY_LEFT_ROW_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ LEFT_ROW_DETAIL_TABLE
			+ " ("
			+ FIELD_ROW_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ FIELD_PERSON_COUNT
			+ " INTEGER, "
			+ FIELD_TIME
			+ " TEXT, "
			+ FIELD_LATITUDE
			+ " TEXT, "
			+ FIELD_lONGITUDE
			+ " TEXT, "
			+ FIELD_COMPLETED
			+ " INTEGER, " + FIELD_DELETE + " INTEGER);";

	/** Projection for getting value of an error key */
	private final static String[] PROJECTION_LEFT_ROW_VALUE = { FIELD_ROW_ID,
			FIELD_PERSON_COUNT, FIELD_TIME };

	/**
	 * gender age grp and grp type table
	 */
	public static final String GENDER_DETAIL_TABLE = "gender_detail";
	private static final String FIELD_GENDER_TYPE = "gender_type";
	private static final String FIELD_AGE_GROUP = "age_group";

	private final static String QUERY_GENDER_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ GENDER_DETAIL_TABLE
			+ " ("
			+ FIELD_ROW_ID
			+ " TEXT, "
			+ FIELD_GENDER_TYPE + " TEXT, " + FIELD_AGE_GROUP + " INTEGER);";

	/** Projection for getting value of an error key */
	private final static String[] PROJECTION_GENDER_VALUE = { FIELD_ROW_ID,
			FIELD_GENDER_TYPE, FIELD_AGE_GROUP };

	/**
	 * Private constructor, enforces use of singleton
	 * 
	 * @param aContext
	 *            the context to use
	 */
	private EmployeeSurveyDb(Context aContext) {
		databaseWrapper = new DatabaseWrapper(aContext);
	}

	/**
	 * Initializes database singleton object
	 * 
	 * @param application
	 *            the application object
	 */
	public static void init(Application application) {
		if (instance == null) {
			mContext = application.getApplicationContext();
			Log.i(TAG, "Creating static instance of DBAdapter");
			instance = new EmployeeSurveyDb(application.getApplicationContext());
			instance.open();
		}
	}

	/**
	 * Gets an instance of DBAdapter
	 * 
	 * @return an DBAdapter instance
	 */
	public static EmployeeSurveyDb getInstance() {
		if (instance == null) {
			throw new RuntimeException(
					"Must run init(Application application) before an instance can be obtained");
		}

		return instance;
	}

	/**
	 * Open database
	 * 
	 * @return database adapter
	 * @throws SQLException
	 */
	private EmployeeSurveyDb open() throws SQLException {
		database = databaseWrapper.getWritableDatabase();
		opened = true;

		return this;
	}

	/**
	 * Close database
	 */
	private void close() {
		databaseWrapper.close();
		opened = false;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();

		if (instance != null) {
			instance.close();
		}
	}

	public boolean isOpened() {
		return opened;
	}

	public synchronized long insertUsernameStorename(String username,
			String storename) {

		// Create object holding values
		ContentValues cv = new ContentValues();

		// cv.put(FIELD_ROW_ID, rowID);
		cv.put(FIELD_USERNAME, username);
		cv.put(FIELD_STORENAME, storename);

		long result = database.insert(USERNAME_STORENAME_TABLE, null, cv);

		return result;
	}

	/**
	 * delete username and storename table
	 * 
	 * @param username
	 * @param storename
	 */
	public synchronized void deleteUsernameStorename(String username,
			String storename) {

		database.delete(USERNAME_STORENAME_TABLE, FIELD_USERNAME + " = "
				+ username, null);
	}

	/**
	 * insert left row in table
	 * 
	 * @param rowID
	 *            the position of row in db. Its autoincrement
	 * @param personCount
	 *            no of person
	 * @param time
	 *            time when user created row.
	 * @param latitude
	 *            latitude of location
	 * @param longitude
	 *            longitude of location
	 * @param completed
	 *            all values are entered or not
	 * @param delete
	 *            is deleted or not
	 * @return long whether value was insterted or not
	 */
	public synchronized long insertLeftRow(int rowID, int personCount,
			String time, String latitude, String longitude, int completed,
			int delete) {

		// Create object holding values
		ContentValues cv = new ContentValues();

		// cv.put(FIELD_ROW_ID, rowID);
		cv.put(FIELD_PERSON_COUNT, personCount);
		cv.put(FIELD_TIME, time);
		cv.put(FIELD_LATITUDE, latitude);
		cv.put(FIELD_lONGITUDE, longitude);
		cv.put(FIELD_COMPLETED, completed);
		cv.put(FIELD_DELETE, delete);

		long result = database.insert(LEFT_ROW_DETAIL_TABLE, null, cv);

		System.out.println("inserted left row to table " + result);

		return result;
	}

	public synchronized long insertGenderRow(int rowID, String genderType,
			int ageGroup) {

		// Create object holding values
		ContentValues cv = new ContentValues();

		cv.put(FIELD_ROW_ID, rowID);
		cv.put(FIELD_GENDER_TYPE, genderType);
		cv.put(FIELD_AGE_GROUP, ageGroup);

		long result = database.insert(GENDER_DETAIL_TABLE, null, cv);

		return result;
	}

	/**
	 * query to get a particular row gender details
	 * 
	 * @return A cursor with gender details
	 */
	public Cursor getGenderRowDetail(String rowId) {
		String whereClause = FIELD_ROW_ID + "=?";
		String[] whereArgs = new String[] { rowId };
		Cursor cursor = database.query(GENDER_DETAIL_TABLE,
				PROJECTION_GENDER_VALUE, whereClause, whereArgs, null, null,
				null);

		Log.d(TAG, "Returning " + cursor.getCount());

		return cursor;
	}

	/**
	 * query to get timeStamp for particular error key without any filter for
	 * delete
	 * 
	 * @return A cursor with time stamp for particular error key
	 */
	public Cursor getLeftRowEntries(int deleteCheck) {
		String whereClause = FIELD_DELETE + "= ?";
		String[] whereArgs = new String[] { "" + deleteCheck };
		Cursor cursor = database.query(LEFT_ROW_DETAIL_TABLE,
				PROJECTION_LEFT_ROW_VALUE, null, null, null, null, null);

		Log.d(TAG, "Returning getLeftRowEntries " + cursor.getCount());

		return cursor;
	}

	/**
	 * This method will return row Id of last row in table
	 * 
	 * @return int Row ID
	 */
	public int getRowIdToSet() {

		Cursor cursor = database.query(LEFT_ROW_DETAIL_TABLE,
				PROJECTION_LEFT_ROW_VALUE, null, null, null, null, null);

		Log.d(TAG, "Returning getLeftRowEntries " + cursor.getCount());
		cursor.moveToLast();
		if (cursor.getCount() > 0) {

			lastRowId = cursor.getInt(FIELD_ROW_ID_COULMN_INDEX);
		}
		Log.i(TAG, "last row ID" + lastRowId);
		return ++lastRowId;
	}

	public int getLeftListCount() {

		int count = 0;
		Cursor cursor = database.query(LEFT_ROW_DETAIL_TABLE,
				PROJECTION_LEFT_ROW_VALUE, null, null, null, null, null);

		Log.d(TAG, "Returning getLeftRowEntries " + cursor.getCount());
		if (cursor.getCount() == 0) {
			++count;

		} else {
			count = cursor.getCount() + 1;
		}
		return count;
	}

	/**
	 * Private class that extends the SQLite helper class.
	 * 
	 */
	private static class DatabaseWrapper extends SQLiteOpenHelper {
		public DatabaseWrapper(Context context) {
			super(context, Constants.DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(QUERY_USERNAME_STORENAME_TABLE);
			db.execSQL(QUERY_LEFT_ROW_TABLE);
			db.execSQL(QUERY_GENDER_TABLE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

			Log.i(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion);

		}
	}
}
