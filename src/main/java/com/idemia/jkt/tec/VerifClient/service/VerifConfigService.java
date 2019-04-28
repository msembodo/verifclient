package com.idemia.jkt.tec.VerifClient.service;

import com.idemia.jkt.tec.VerifClient.model.VerifConfig;
import com.idemia.jkt.tec.VerifClient.response.ConverterResponse;
import com.idemia.jkt.tec.VerifClient.response.VerificationResponse;

public interface VerifConfigService {
	
	public VerifConfig initConfig();
	public void saveConfig(VerifConfig verifConfig);
	public VerificationResponse runVerif() throws Exception;
	public ConverterResponse convertUxp(String docPath) throws Exception;
	public ConverterResponse convertExMorphoDoc(String docPath) throws Exception;
	public void shutdownVerifServer() throws Exception;

}
