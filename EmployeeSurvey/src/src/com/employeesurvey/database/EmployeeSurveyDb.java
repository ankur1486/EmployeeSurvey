package src.com.employeesurvey.database;

import java.util.ArrayList;
import java.util.HashMap;

import src.com.employeesurvey.model.EmployeeModel;
import src.com.employeesurvey.model.GenderAgeModel;
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
	private static final int FIELD_USERNAME_COULMN_INDEX = 1;
	private static final String FIELD_STORENAME = "storename_id";
	private static final int FIELD_STORENAME_COULMN_INDEX = 2;

	private final static String QUERY_USERNAME_STORENAME_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ USERNAME_STORENAME_TABLE
			+ " ("
			+ FIELD_USER_ROW_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ FIELD_USERNAME
			+ " TEXT, " + FIELD_STORENAME + " TEXT);";

	/** Projection for getting value of an error key */
	private final static String[] PROJECTION_USERNAME_STORENAME = {
			FIELD_USERNAME, FIELD_STORENAME };
	/**
	 * left row table
	 */

	private static final String LEFT_ROW_DETAIL_TABLE = "left_row_detail";
	private static final String FIELD_ROW_ID = "row_id";
	private static final int FIELD_ROW_ID_COULMN_INDEX = 0;
	private static final String FIELD_PERSON_COUNT = "person_count";
	private static final int FIELD_PERSON_COUNT_COULMN_INDEX = 1;
	private static final String FIELD_TIME = "time";
	private static final int FIELD_TIME_COULMN_INDEX = 2;
	private static final String FIELD_LATITUDE = "latitude";
	private static final int FIELD_LATITUDE_COULMN_INDEX = 3;
	private static final String FIELD_lONGITUDE = "longitude";
	private static final int FIELD_lONGITUDE_COULMN_INDEX = 4;
	private static final String FIELD_COMPLETED = "completed";
	private static final int FIELD_COMPLETED_COULMN_INDEX = 5;
	private static final String FIELD_DELETE = "should_delete";
	private static final int FIELD_DELETE_COULMN_INDEX = 6;

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
			FIELD_PERSON_COUNT, FIELD_TIME, FIELD_LATITUDE, FIELD_lONGITUDE,
			FIELD_COMPLETED, FIELD_DELETE };

	/** Projection for getting value of an error key */
	private final static String[] PROJECTION_PERSON_COUNT = { FIELD_PERSON_COUNT };

	/**
	 * gender age grp and grp type table
	 */
	public static final String GENDER_DETAIL_TABLE = "gender_detail";

	private static final String FIELD_GENDER_ROW_ID = "gender_row_id";
	private static final int FIELD_GENDER_ROW_ID_COULMN_INDEX = 0;
	private static final String FIELD_GENDER_TYPE = "gender_type";
	private static final int FIELD_GENDER_TYPE_COULMN_INDEX = 1;
	private static final String FIELD_AGE_GROUP = "age_group";
	private static final int FIELD_AGE_GROUP_COULMN_INDEX = 2;
	private static final String FIELD_GROUP_TYPE = "group_type";
	private static final int FIELD_GROUP_TYPE_COULMN_INDEX = 3;

	private final static String QUERY_GENDER_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ GENDER_DETAIL_TABLE
			+ " ("
			+ FIELD_GENDER_ROW_ID
			+ " INTEGER, "
			+ FIELD_ROW_ID
			+ " INTEGER, "
			+ FIELD_GENDER_TYPE
			+ " TEXT, "
			+ FIELD_AGE_GROUP
			+ " TEXT, " + FIELD_GROUP_TYPE + " TEXT);";

	/** Projection for getting value of an error key */
	private final static String[] PROJECTION_GENDER_VALUE = { FIELD_ROW_ID,
			FIELD_GENDER_TYPE, FIELD_AGE_GROUP, FIELD_GROUP_TYPE };

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

		cv.put(FIELD_ROW_ID, rowID);
		cv.put(FIELD_PERSON_COUNT, personCount);
		cv.put(FIELD_TIME, time);
		cv.put(FIELD_LATITUDE, latitude);
		cv.put(FIELD_lONGITUDE, longitude);
		cv.put(FIELD_COMPLETED, completed);
		cv.put(FIELD_DELETE, delete);

		long result = database.insert(LEFT_ROW_DETAIL_TABLE, null, cv);

		System.out.println("Row Id " + rowID);
		System.out.println("inserted left row to table " + result);

		return result;
	}

	public synchronized long insertGenderRow(int genderRowID, int rowID,
			String genderType, String ageGroup, String groupType) {

		// Create object holding values
		ContentValues cv = new ContentValues();

		 cv.put(FIELD_GENDER_ROW_ID, genderRowID);
		cv.put(FIELD_ROW_ID, rowID);
		cv.put(FIELD_GENDER_TYPE, genderType);
		cv.put(FIELD_AGE_GROUP, ageGroup);
		cv.put(FIELD_GROUP_TYPE, groupType);
		long result = database.insert(GENDER_DETAIL_TABLE, null, cv);

		return result;
	}

	/*
	 * update right gender row on click of row
	 */
	public synchronized long updateGenderRow(int genderRowID, int rowID,
			String genderType, String ageGroup, String groupType) {

		String whereClause = FIELD_GENDER_ROW_ID + "= ?" + " AND "
				+ FIELD_ROW_ID + "= ?";
		String[] whereArgs = new String[] { "" + genderRowID, "" + rowID };

		// Create object holding values
		ContentValues cv = new ContentValues();

		cv.put(FIELD_GENDER_ROW_ID, genderRowID);
		cv.put(FIELD_ROW_ID, rowID);
		cv.put(FIELD_GENDER_TYPE, genderType);
		cv.put(FIELD_AGE_GROUP, ageGroup);
		cv.put(FIELD_GROUP_TYPE, groupType);
		long result = database.update(GENDER_DETAIL_TABLE, cv, whereClause,
				whereArgs);

		System.out.println("Result :" + result + " position :" + genderRowID
				+ "Left row ID :" + rowID);

		return result;
	}

	/**
	 * This method will update person count against row ID
	 * 
	 * @param rowID
	 * @param personCount
	 * @return long number of rows affected
	 */
	public synchronized long updatePersonCount(String rowID, int personCount) {

		String whereClause = FIELD_ROW_ID + "=?";
		String[] whereArgs = new String[] { rowID };
		// Create object holding values
		ContentValues cv = new ContentValues();

		cv.put(FIELD_PERSON_COUNT, personCount);

		long result = database.update(LEFT_ROW_DETAIL_TABLE, cv, whereClause,
				whereArgs);

		return result;
	}

	/**
	 * This method will update completed status
	 * 
	 * @param rowID
	 * @param personCount
	 * @return long number of rows affected
	 */
	public synchronized long updateFormCompleted(String rowID) {

		String whereClause = FIELD_ROW_ID + "=?";
		String[] whereArgs = new String[] { rowID };
		// Create object holding values
		ContentValues cv = new ContentValues();

		cv.put(FIELD_COMPLETED, 1);

		long result = database.update(LEFT_ROW_DETAIL_TABLE, cv, whereClause,
				whereArgs);

		return result;
	}

	/**
	 * query to get a particular row gender details
	 * 
	 * @return A cursor with gender details
	 */
	public synchronized Cursor getGenderRowDetail(String rowId) {
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
	public synchronized Cursor getLeftRowEntries(int deleteCheck) {
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
	public synchronized int getRowIdToSet() {

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

	/**
	 * This method will return the store and user name
	 * 
	 * @return HashMap stor and user name
	 */
	public synchronized HashMap<String, String> getUserNameStoreName() {
		Cursor cursor = database.query(USERNAME_STORENAME_TABLE, null, null,
				null, null, null, null);
		cursor.moveToFirst();
		String userName = cursor.getString(FIELD_USERNAME_COULMN_INDEX);
		String storeName = cursor.getString(FIELD_STORENAME_COULMN_INDEX);
		HashMap<String, String> userNameStoreName = new HashMap<String, String>();
		userNameStoreName.put(Constants.USER_NAME_KEY, userName);
		userNameStoreName.put(Constants.STORE_NAME_KEY, storeName);
		return userNameStoreName;
	}

	/**
	 * This method will return total number of person count
	 * 
	 * @return int person count
	 */
	public synchronized int getPersonCount() {
		int totalPersonCount = 0;
		Cursor cursor = database.query(LEFT_ROW_DETAIL_TABLE, null, null, null,
				null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			totalPersonCount = totalPersonCount
					+ cursor.getInt(FIELD_PERSON_COUNT_COULMN_INDEX);
			cursor.moveToNext();
		}
		return totalPersonCount;
	}

	/**
	 * This method will return the number of males and females depending upon
	 * the parameter passed.
	 * 
	 * @param maleOrFemale
	 * @return int number of males and females.
	 */
	public synchronized int getNumberOfMalesFemales(String maleOrFemale) {
		String whereClause = FIELD_GENDER_TYPE + "= ?";
		String[] whereArgs = new String[] { maleOrFemale };
		String[] project = { FIELD_GENDER_TYPE };
		Cursor cursor = database.query(GENDER_DETAIL_TABLE, project,
				whereClause, whereArgs, null, null, null);
		return cursor.getCount();
	}

	public synchronized int getMaleFemaleByAgeGroup(String ageGroup,
			String maleOrFemale) {
		String whereClause = FIELD_AGE_GROUP + "= ?" + " AND "
				+ FIELD_GENDER_TYPE + "= ?";
		String[] whereArgs = new String[] { ageGroup, maleOrFemale };
		String[] project = { FIELD_GENDER_TYPE };
		Cursor cursor = database.query(GENDER_DETAIL_TABLE, project,
				whereClause, whereArgs, null, null, null);
		return cursor.getCount();

	}

	public synchronized int getGroupTypeDetail(String grouptype,
			String ageGroup, String maleOrFemale) {
		String whereClause = FIELD_GROUP_TYPE + "= ?" + " AND "
				+ FIELD_AGE_GROUP + "= ?" + " AND " + FIELD_GENDER_TYPE + "= ?";
		String[] whereArgs = new String[] { grouptype, ageGroup, maleOrFemale };
		String[] project = { FIELD_GENDER_TYPE };
		Cursor cursor = database.query(GENDER_DETAIL_TABLE, project,
				whereClause, whereArgs, null, null, null);
		return cursor.getCount();

	}

	public synchronized ArrayList<EmployeeModel> getTimeLocationPersonCount() {

		String[] project = { FIELD_TIME, FIELD_LATITUDE, FIELD_lONGITUDE,
				FIELD_PERSON_COUNT };
		Cursor cursor = database.query(LEFT_ROW_DETAIL_TABLE, project, null,
				null, null, null, null);

		ArrayList<EmployeeModel> employeeModelsList = new ArrayList<EmployeeModel>();
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			EmployeeModel employeeModel = new EmployeeModel();
			employeeModel.setTime(cursor.getString(0));
			employeeModel.setLatitude(cursor.getString(1));
			employeeModel.setLongitude(cursor.getString(2));
			employeeModel.setPersonCount(cursor.getInt(3));
			employeeModelsList.add(employeeModel);
			cursor.moveToNext();
		}

		return employeeModelsList;

	}

	/**
	 * This method will give total number of rows in Db
	 * 
	 * @return int row count
	 */
	public synchronized int getLeftListCount() {

		int count = 0;
		Cursor cursor = database.query(LEFT_ROW_DETAIL_TABLE,
				PROJECTION_LEFT_ROW_VALUE, null, null, null, null, null);

		Log.d(TAG, "Returning getLeftRowEntries " + cursor.getCount());
		if (cursor.getCount() == 0) {
			++count;

		} else {
			cursor.moveToLast();
			count = cursor.getInt(FIELD_ROW_ID_COULMN_INDEX) + 1;
		}
		return count;
	}

	/**
	 * This method will delete left panel row
	 * 
	 * @param rowId
	 */
	public synchronized void deleteLeftRow(String rowId) {
		String whereClause = FIELD_ROW_ID + "= ?";
		String[] whereArgs = new String[] { rowId };
		int rowsDeleted = database.delete(LEFT_ROW_DETAIL_TABLE, whereClause,
				whereArgs);
		Log.d(TAG, "number of left panel row deleted" + rowsDeleted);
	}

	/**
	 * This method will delete gender row for a particular row ID
	 * 
	 * @param rowId
	 */
	public synchronized void deleteGenderDetailByRowId(String rowId) {
		String whereClause = FIELD_ROW_ID + "= ?";
		String[] whereArgs = new String[] { rowId };
		int rowsDeleted = database.delete(GENDER_DETAIL_TABLE, whereClause,
				whereArgs);
		Log.d(TAG, "number of gender row deleted " + rowsDeleted);
	}

	public synchronized ArrayList<EmployeeModel> getDataModelForList() {
		ArrayList<EmployeeModel> employeeModelList = new ArrayList<EmployeeModel>();
		Cursor cursor = database.query(LEFT_ROW_DETAIL_TABLE,
				PROJECTION_LEFT_ROW_VALUE, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			EmployeeModel employeeModel = new EmployeeModel();
			employeeModel.setFormCompleted(cursor
					.getInt(FIELD_COMPLETED_COULMN_INDEX));
			employeeModel.setLatitude(cursor
					.getString(FIELD_LATITUDE_COULMN_INDEX));
			employeeModel.setLongitude(cursor
					.getString(FIELD_lONGITUDE_COULMN_INDEX));
			employeeModel.setPersonCount(cursor
					.getInt(FIELD_PERSON_COUNT_COULMN_INDEX));
			employeeModel.setTime(cursor.getString(FIELD_TIME_COULMN_INDEX));
			employeeModel.setRowId(cursor.getInt(FIELD_ROW_ID_COULMN_INDEX));
			Cursor genderAgecursor = getGenderRowDetail(""
					+ cursor.getInt(FIELD_ROW_ID_COULMN_INDEX));

			// database.query(GENDER_DETAIL_TABLE,
			// PROJECTION_GENDER_VALUE, null, null, null, null, null);
			genderAgecursor.moveToFirst();
			ArrayList<GenderAgeModel> genderAgeModelList = new ArrayList<GenderAgeModel>();

			while (!genderAgecursor.isAfterLast()) {
				GenderAgeModel genderAgeModel = new GenderAgeModel();
				genderAgeModel.setrowId(genderAgecursor
						.getInt(FIELD_ROW_ID_COULMN_INDEX));
				genderAgeModel.setGender(genderAgecursor
						.getString(FIELD_GENDER_TYPE_COULMN_INDEX));
				genderAgeModel.setAgeGrp(genderAgecursor
						.getString(FIELD_AGE_GROUP_COULMN_INDEX));
				genderAgeModel.setGroupType(genderAgecursor
						.getString(FIELD_GROUP_TYPE_COULMN_INDEX));
				genderAgeModelList.add(genderAgecursor.getPosition(),
						genderAgeModel);
				genderAgecursor.moveToNext();
			}
			employeeModel.setGenderAgeModel(genderAgeModelList);
			employeeModelList.add(cursor.getPosition(), employeeModel);
			cursor.moveToNext();
		}
		return employeeModelList;
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

	public void deleteUserStoreTable() {
		database.delete(USERNAME_STORENAME_TABLE, null, null);
	}

	public void deleteLeftListTable() {
		database.delete(LEFT_ROW_DETAIL_TABLE, null, null);

	}

	public void deleteGenderListTable() {
		database.delete(GENDER_DETAIL_TABLE, null, null);

	}

	public void updateGroupType(int mRowID, String mGroupType) {
		String whereClause = FIELD_ROW_ID + "= ?";
		String[] whereArgs = new String[] { "" + mRowID, };

		// Create object holding values
		ContentValues cv = new ContentValues();

		cv.put(FIELD_GROUP_TYPE, mGroupType);
		long result = database.update(GENDER_DETAIL_TABLE, cv, whereClause,
				whereArgs);

		Log.w(TAG, "gender type rows update"+result);

	}

}
