package kr.qna.vo;

import java.sql.Date;

public class QnaCmtVO {
	private int qnacmt_num;
	private int qna_num;
	private String content;
	private Date reg_date;
	private Date modify_date;
	private int user_num;
	
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public int getUser_num() {
		return user_num;
	}
	public void setUser_num(int user_num) {
		this.user_num = user_num;
	}
	
}
