package src.com.employeesurvey.model;

import java.io.Serializable;

public class GenderAgeModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String gender;
	private String ageGrp;
	private int rowId;

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAgeGrp() {
		return ageGrp;
	}

	public void setAgeGrp(String ageGrp) {
		this.ageGrp = ageGrp;
	}

	public int getrowId() {
		return rowId;
	}

	public void setrowId(int grpType) {
		this.rowId = rowId;
	}

}
