package src.com.employeesurvey.model;

import java.io.Serializable;

public class EmployeeModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int personCount;
	private String time;
	private String latitude;
	private String longitude;
	private String buttonAddDelete;
	private boolean formCompleted;

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

	public String getButtonAddDelete() {
		return buttonAddDelete;
	}

	public void setButtonAddDelete(String buttonAddDelete) {
		this.buttonAddDelete = buttonAddDelete;
	}

	public boolean isFormCompleted() {
		return formCompleted;
	}

	public void setFormCompleted(boolean formCompleted) {
		this.formCompleted = formCompleted;
	}
}
