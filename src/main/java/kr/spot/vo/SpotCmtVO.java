package kr.spot.vo;

public class SpotCmtVO {
	private int spotcmt_num;
	private int spot_num;
	private String cmt_content;
	private String modify_date;
	private String reg_date;
	private int user_num;
	private String id;
	private String user_photo;

	public int getSpotcmt_num() {
		return spotcmt_num;
	}

	public void setSpotcmt_num(int spotcmt_num) {
		this.spotcmt_num = spotcmt_num;
	}

	public int getSpot_num() {
		return spot_num;
	}

	public void setSpot_num(int spot_num) {
		this.spot_num = spot_num;
	}

	public String getCmt_content() {
		return cmt_content;
	}

	public void setCmt_content(String cmt_content) {
		this.cmt_content = cmt_content;
	}

	public int getUser_num() {
		return user_num;
	}

	public void setUser_num(int user_num) {
		this.user_num = user_num;
	}

	public String getModify_date() {
		return modify_date;
	}

	public void setModify_date(String modify_date) {
		this.modify_date = modify_date;
	}

	public String getReg_date() {
		return reg_date;
	}

	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUser_photo() {
		return user_photo;
	}

	public void setUser_photo(String user_photo) {
		this.user_photo = user_photo;
	}

}
