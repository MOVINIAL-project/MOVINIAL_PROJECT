package com.movinial.review.model.vo;

import java.sql.Date;

public class Review {
	
	// 필드부
	private int reviewNo;			// REVIEW_NO	NUMBER
	private String reviewWriter;	// REVIEW_WRITER	NUMBER
	private String reviewTitle;		// REVIEW_TITLE	VARCHAR2(400 BYTE)
	private String reviewContent;	// REVIEW_CONTENT	VARCHAR2(4000 BYTE)
	private Date createDate;		// CREATE_DATE	DATE
	private Date modifyDate;		// MODIFY_DATE	DATE
	private String publicStatus;	// PUBLIC_STATUS	VARCHAR2(1 BYTE)
	private int likes;				// LIKES	NUMBER
	private int reportCount;		// REPORT_COUNT	NUMBER
	private String status;			// STATUS	VARCHAR2(1 BYTE)
	private int refMno;				// REF_MNO	NUMBER
	
	// 생성자부
	public Review() {
		super();
	}

	/**
	 * 해당 영화의 리뷰 정보 받아오기
	 * @param reviewNo
	 * @param reviewWriter
	 * @param reviewContent
	 * @param createDate
	 * @param likes
	 */
	public Review(int reviewNo, String reviewWriter, String reviewContent, Date createDate, int likes) {
		super();
		this.reviewNo = reviewNo;
		this.reviewWriter = reviewWriter;
		this.reviewContent = reviewContent;
		this.createDate = createDate;
		this.likes = likes;
	}

	/**
	 * 해당 영화 리뷰 상세보기 페이지 출력
	 * @param reviewNo
	 * @param reviewWriter
	 * @param reviewContent
	 * @param createDate
	 * @param likes
	 * @param refMno
	 */
	public Review(int reviewNo, String reviewWriter, String reviewContent, Date createDate, int likes, int refMno) {
		super();
		this.reviewNo = reviewNo;
		this.reviewWriter = reviewWriter;
		this.reviewContent = reviewContent;
		this.createDate = createDate;
		this.likes = likes;
		this.refMno = refMno;
	}

	public Review(int reviewNo, String reviewWriter, String reviewTitle, String reviewContent, Date createDate,
			Date modifyDate, String publicStatus, int likes, int reportCount, String status, int refMno) {
		super();
		this.reviewNo = reviewNo;
		this.reviewWriter = reviewWriter;
		this.reviewTitle = reviewTitle;
		this.reviewContent = reviewContent;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
		this.publicStatus = publicStatus;
		this.likes = likes;
		this.reportCount = reportCount;
		this.status = status;
		this.refMno = refMno;
	}
	
	// 메인 페이지에 출력
	public Review(int reviewNo, String reviewWriter, String reviewContent, int refMno, String reviewTitle) {
		super();
		this.reviewNo = reviewNo;
		this.reviewWriter = reviewWriter;
		this.reviewContent = reviewContent;
		this.refMno = refMno;
		this.reviewTitle = reviewTitle;
	}

	// 메소드부
	public int getReviewNo() {
		return reviewNo;
	}

	public void setReviewNo(int reviewNo) {
		this.reviewNo = reviewNo;
	}

	public String getReviewWriter() {
		return reviewWriter;
	}

	public void setReviewWriter(String reviewWriter) {
		this.reviewWriter = reviewWriter;
	}

	public String getReviewTitle() {
		return reviewTitle;
	}

	public void setReviewTitle(String reviewTitle) {
		this.reviewTitle = reviewTitle;
	}

	public String getReviewContent() {
		return reviewContent;
	}

	public void setReviewContent(String reviewContent) {
		this.reviewContent = reviewContent;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getPublicStatus() {
		return publicStatus;
	}

	public void setPublicStatus(String publicStatus) {
		this.publicStatus = publicStatus;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public int getReportCount() {
		return reportCount;
	}

	public void setReportCount(int reportCount) {
		this.reportCount = reportCount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getRefMno() {
		return refMno;
	}

	public void setRefMno(int refMno) {
		this.refMno = refMno;
	}

	@Override
	public String toString() {
		return "Review [reviewNo=" + reviewNo + ", reviewWriter=" + reviewWriter + ", reviewTitle=" + reviewTitle
				+ ", reviewContent=" + reviewContent + ", createDate=" + createDate + ", modifyDate=" + modifyDate
				+ ", publicStatus=" + publicStatus + ", likes=" + likes + ", reportCount=" + reportCount + ", status="
				+ status + ", refMno=" + refMno + "]";
	}
	
}
