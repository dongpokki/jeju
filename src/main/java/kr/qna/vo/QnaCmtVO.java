package kr.qna.vo;

import java.sql.Date;

public class QnaCmtVO {
	private int qnacmt_num;
	private int qna_num;
	private String cmt_content;
	private String reg_date;
	private String modify_date;
	private int user_num;
	private String name;
	
	public int getQnacmt_num() {
		return qnacmt_num;
	}
	public void setQnacmt_num(int qnacmt_num) {
		this.qnacmt_num = qnacmt_num;
	}
	public int getQna_num() {
		return qna_num;
	}
	public void setQna_num(int qna_num) {
		this.qna_num = qna_num;
	}
	public String getCmt_content() {
		return cmt_content;
	}
	public void setCmt_content(String cmt_content) {
		this.cmt_content = cmt_content;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public String getModify_date() {
		return modify_date;
	}
	public void setModify_date(String modify_date) {
		this.modify_date = modify_date;
	}
	public int getUser_num() {
		return user_num;
	}
	public void setUser_num(int user_num) {
		this.user_num = user_num;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
