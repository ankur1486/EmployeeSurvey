package src.com.employeesurvey.model;

import java.io.Serializable;

public class GenderAgeModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String gender = "female";
	private String ageGrp = "0-10";
	private int rowId;
	private String groupType="Friends";

	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

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
