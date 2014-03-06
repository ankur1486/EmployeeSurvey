package src.com.employeesurvey.model;

import java.io.Serializable;
import java.util.ArrayList;

public class EmployeeModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int personCount;
	private String time;
	private String latitude;
	private String longitude;
	private int buttonAddDelete;
	private int formCompleted;
	private int rowId;
	
	public int getRowId() {
		return rowId;
	}

	public void setRowId(int rowId) {
		this.rowId = rowId;
	}

	private ArrayList<GenderAgeModel> genderAgeModel;

	public ArrayList<GenderAgeModel> getGenderAgeModel() {
		return genderAgeModel;
	}

	public void setGenderAgeModel(ArrayList<GenderAgeModel> genderAgeModel) {
		this.genderAgeModel = genderAgeModel;
	}

	public int getPersonCount() {
		return personCount;
	}

	public void setPersonCount(int personCount) {
		this.personCount = personCount;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public int getButtonAddDelete() {
		return buttonAddDelete;
	}

	public void setButtonAddDelete(int buttonAddDelete) {
		this.buttonAddDelete = buttonAddDelete;
	}

	public int isFormCompleted() {
		return formCompleted;
	}

	public void setFormCompleted(int formCompleted) {
		this.formCompleted = formCompleted;
	}
}
