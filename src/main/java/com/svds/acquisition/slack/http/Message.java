package com.svds.acquisition.slack.http;

public class Message {
	
	private String token;
	private Long teamId;
	private String teamDomain;
	private String channelName;
	private String timestamp;
	private String userId;
	private String userName;
	private String text;
	private String triggerWord;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Long getTeamId() {
		return teamId;
	}
	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}
	public String getTeamDomain() {
		return teamDomain;
	}
	public void setTeamDomain(String teamDomain) {
		this.teamDomain = teamDomain;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getTriggerWord() {
		return triggerWord;
	}
	public void setTriggerWord(String triggerWord) {
		this.triggerWord = triggerWord;
	}
	
	@Override
	public String toString() {
		return "Message [token=" + token + ", teamId=" + teamId
				+ ", teamDomain=" + teamDomain + ", channelName=" + channelName
				+ ", timestamp=" + timestamp + ", userId=" + userId
				+ ", userName=" + userName + ", text=" + text
				+ ", triggerWord=" + triggerWord + "]";
	}
	

	

}
