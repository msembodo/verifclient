package com.idemia.jkt.tec.VerifClient.service;

import com.idemia.jkt.tec.VerifClient.model.VerifConfig;
import com.idemia.jkt.tec.VerifClient.response.VerificationResponse;

public interface VerifConfigService {
	
	public VerifConfig initConfig();
	public void saveConfig(VerifConfig verifConfig);
	public VerificationResponse runVerif() throws Exception;

}