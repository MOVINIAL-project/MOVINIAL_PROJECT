package com.movinial.community.model.vo;

import java.sql.Date;

public class Community { // 커뮤니티 (자유게시판)
	
	// 필드부
	private int communityNo;//COMMUNITY_NO	NUMBER
	private String communityTitle;//COMMUNITY_TITLE	VARCHAR2(200 BYTE)
	private String communityCategory;//COMMUNITY_CATEGORY	VARCHAR2(20 BYTE)
	private String communityWriter;//COMMUNITY_WRITER	NUMBER
	private String commounityContent;//COMMUNITY_CONTENT	VARCHAR2(4000 BYTE)
	private int views;//VIEWS	NUMBER
	private int likes;//LIKES	NUMBER
	private Date createDate;//CREATE_DATE	DATE
	private Date modifyDate;//MODIFY_DATE	DATE
	private String spoiler;//SPOILER	VARCHAR2(1 BYTE)
	private int reportCount;//REPORT_COUNT	NUMBER
	private String status;//STATUS	VARCHAR2(1 BYTE)
	private int isNotice;//ISNOTICE	NUMBER
	
	private int replyCount; // 커뮤니티 글별 댓글 개수 
	
	private int memberNo; // 커뮤니티 글 작성자 확인 용도
	
	private String mainPageCreateDate;//MAIN PAGE CREATE_DATE
	
	// 생성자부
	
	// 기본생성자
	public Community() {
		super();
	}
	
	// 주현님 : 내 글 보기 생성자
	public Community(int communityNo, String communityTitle, String communityCategory, Date createDate) {
		super();
		this.communityNo = communityNo;
		this.communityTitle = communityTitle;
		this.communityCategory = communityCategory;
		this.createDate = createDate;
	}
	
	// 게시글 리스트 보기 용도 생성자
	public Community(int communityNo, String communityTitle, String communityCategory, String communityWriter,
			int views, int likes, Date createDate, String spoiler, int reportCount, int replyCount) {
		super();
		this.communityNo = communityNo;
		this.communityTitle = communityTitle;
		this.communityCategory = communityCategory;
		this.communityWriter = communityWriter;
		this.views = views;
		this.likes = likes;
		this.createDate = createDate;
		this.spoiler = spoiler;
		this.reportCount = reportCount;
		this.replyCount = replyCount;
	}
	
	// 게시글 상세보기 용도 생성자
	public Community(int communityNo, String communityTitle, String communityCategory, String communityWriter,
			String commounityContent, int views, int likes, Date createDate, String spoiler, int reportCount,
			int isNotice, int memberNo, int replyCount) {
		super();
		this.communityNo = communityNo;
		this.communityTitle = communityTitle;
		this.communityCategory = communityCategory;
		this.communityWriter = communityWriter;
		this.commounityContent = commounityContent;
		this.views = views;
		this.likes = likes;
		this.createDate = createDate;
		this.spoiler = spoiler;
		this.reportCount = reportCount;
		this.isNotice = isNotice;
		this.memberNo = memberNo;
		this.replyCount = replyCount;
	}
	
	// 게시글 등록 용도 생성자
	public Community(String communityTitle, String communityCategory, String communityWriter, String commounityContent,
			String spoiler, int isNotice) {
		super();
		this.communityTitle = communityTitle;
		this.communityCategory = communityCategory;
		this.communityWriter = communityWriter;
		this.commounityContent = commounityContent;
		this.spoiler = spoiler;
		this.isNotice = isNotice;
	}
	
	// 모든 매개변수가 있는 생성자
	public Community(int communityNo, String communityTitle, String communityCategory, String communityWriter,
			String commounityContent, int views, int likes, Date createDate, Date modifyDate, String spoiler,
			int reportCount, String status, int isNotice, int replyCount, int memberNo) {
		super();
		this.communityNo = communityNo;
		this.communityTitle = communityTitle;
		this.communityCategory = communityCategory;
		this.communityWriter = communityWriter;
		this.commounityContent = commounityContent;
		this.views = views;
		this.likes = likes;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
		this.spoiler = spoiler;
		this.reportCount = reportCount;
		this.status = status;
		this.isNotice = isNotice;
		this.replyCount = replyCount;
		this.memberNo = memberNo;
	}
  
  //주현 : 좋아요 누른 커뮤니티 게시글 리스트
	public Community(int communityNo, String communityTitle, String communityCategory, String communityWriter,
			int likes, Date createDate) {
    super();
		this.communityNo = communityNo;
		this.communityTitle = communityTitle;
		this.communityCategory = communityCategory;
		this.communityWriter = communityWriter;
   	this.likes = likes;
		this.createDate = createDate;
	}
  
  // 메인 페이지에서 띄우기
	public Community(int communityNo, String communityTitle, String communityCategory, String communityWriter,
			int views, int likes, Date createDate) {
    super();
		this.communityNo = communityNo;
		this.communityTitle = communityTitle;
		this.communityCategory = communityCategory;
		this.communityWriter = communityWriter;
  	this.views = views;
		this.likes = likes;
		this.createDate = createDate;
	}
	
   // 메인 페이지에서 띄우기
	public Community(int communityNo, String communityTitle, String communityCategory, String communityWriter, int views,
		int likes, String mainPageCreateDate) {
	super();
	this.communityNo = communityNo;
	this.communityTitle = communityTitle;
	this.communityCategory = communityCategory;
	this.communityWriter = communityWriter;
	this.views = views;
	this.likes = likes;
	this.mainPageCreateDate = mainPageCreateDate;
}

	// 메소드부
	public int getCommunityNo() {
		return communityNo;
	}
	public void setCommunityNo(int communityNo) {
		this.communityNo = communityNo;
	}
	public String getCommunityTitle() {
		return communityTitle;
	}
	public void setCommunityTitle(String communityTitle) {
		this.communityTitle = communityTitle;
	}
	public String getCommunityCategory() {
		return communityCategory;
	}
	public void setCommunityCategory(String communityCategory) {
		this.communityCategory = communityCategory;
	}
	public String getCommunityWriter() {
		return communityWriter;
	}
	public void setCommunityWriter(String communityWriter) {
		this.communityWriter = communityWriter;
	}
	public String getCommounityContent() {
		return commounityContent;
	}
	public void setCommounityContent(String commounityContent) {
		this.commounityContent = commounityContent;
	}
	public int getViews() {
		return views;
	}
	public void setViews(int views) {
		this.views = views;
	}
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
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
	public String getSpoiler() {
		return spoiler;
	}
	public void setSpoiler(String spoiler) {
		this.spoiler = spoiler;
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
	public int getIsNotice() {
		return isNotice;
	}
	public void setIsNotice(int isNotice) {
		this.isNotice = isNotice;
	}
	public int getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(int memberNo) {
		this.memberNo = memberNo;
	}
	public int getReplyCount() {
		return replyCount;
	}
	public void setReplyCount(int replyCount) {
		this.replyCount = replyCount;
	}
	
	public String getMainPageCreateDate() {
		return mainPageCreateDate;
	}
	public void setMainPageCreateDate(String mainPageCreateDate) {
		this.mainPageCreateDate = mainPageCreateDate;
	}

	@Override
	public String toString() {
		return "Community [communityNo=" + communityNo + ", communityTitle=" + communityTitle + ", communityCategory="
				+ communityCategory + ", communityWriter=" + communityWriter + ", commounityContent="
				+ commounityContent + ", views=" + views + ", likes=" + likes + ", createDate=" + createDate
				+ ", modifyDate=" + modifyDate + ", spoiler=" + spoiler + ", reportCount=" + reportCount + ", status="
				+ status + ", isNotice=" + isNotice + ", replyCount=" + replyCount + ", memberNo=" + memberNo + "]";
	}
	
}
