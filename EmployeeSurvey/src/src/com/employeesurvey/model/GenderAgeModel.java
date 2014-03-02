package src.com.employeesurvey.model;

import java.io.Serializable;

public class GenderAgeModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String gender;
	private String ageGrp;
	private String grpType;

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

	public String getGrpType() {
		return grpType;
	}

	public void setGrpType(String grpType) {
		this.grpType = grpType;
	}

}
