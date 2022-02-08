package kr.spot.vo;

import java.sql.Date;

public class SpotVO {
	private int spot_num;

	private String title; // 제목
	private String content; // 내용
	private int hit; // 조회수
	private Date reg_date; // 등록일
	private Date modify_date; // 수정일
	private String filename; // 파일명
	private String ip; // ip주소
	private int user_num; // 회원번호(작성자)

	public int getSpot_num() {
		return spot_num;
	}

	public void setSpot_num(int spot_num) {
		this.spot_num = spot_num;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public Date getReg_date() {
		return reg_date;
	}

	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}

	public Date getModify_date() {
		return modify_date;
	}

	public void setModify_date(Date modify_date) {
		this.modify_date = modify_date;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getUser_num() {
		return user_num;
	}

	public void setUser_num(int user_num) {
		this.user_num = user_num;
	}

}
