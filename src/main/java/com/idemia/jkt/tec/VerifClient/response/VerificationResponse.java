package com.idemia.jkt.tec.VerifClient.response;

import com.google.gson.Gson;

public class VerificationResponse {
	
	private boolean verificationSuccess;
	private String message;
	
	public VerificationResponse() {}

	public VerificationResponse(boolean verificationSuccess, String message) {
		super();
		this.verificationSuccess = verificationSuccess;
		this.message = message;
	}

	public boolean isVerificationSuccess() {
		return verificationSuccess;
	}

	public void setVerificationSuccess(boolean verificationSuccess) {
		this.verificationSuccess = verificationSuccess;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	// for debug
	public String toJson() {
		return new Gson().toJson(this);
	}

}
