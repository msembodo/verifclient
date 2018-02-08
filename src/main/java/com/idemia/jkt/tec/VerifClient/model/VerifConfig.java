package com.idemia.jkt.tec.VerifClient.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class VerifConfig {
	
	private int readerNumber;
	
	// options
	private boolean chv1Disabled;
	private boolean hexSfi;
	private boolean hexRecordNumber;
	private boolean useVariablesTxt;
	private boolean useAdm2;
	private boolean useAdm3;
	private boolean useAdm4;
	
	// security codes
	private String codeAdm1;
	private String codeAdm2;
	private String codeAdm3;
	private String codeAdm4;
	private String codeChv1;
	private String codeChv2;
	
	// literals
	private VerifLiterals verifLiterals;
	
	private String pathToCsv;
	private String pathToVariablesTxt;
	
	public VerifConfig() {}

	public VerifConfig(int readerNumber, boolean chv1Disabled, boolean hexSfi, boolean hexRecordNumber, boolean useVariablesTxt,
			boolean useAdm2, boolean useAdm3, boolean useAdm4, String codeAdm1, String codeAdm2, String codeAdm3,
			String codeAdm4, String codeChv1, String codeChv2, VerifLiterals verifLiterals, String pathToCsv,
			String pathToVariablesTxt) {
		
		this.readerNumber = readerNumber;
		this.chv1Disabled = chv1Disabled;
		this.hexSfi = hexSfi;
		this.hexRecordNumber = hexRecordNumber;
		this.useVariablesTxt = useVariablesTxt;
		this.useAdm2 = useAdm2;
		this.useAdm3 = useAdm3;
		this.useAdm4 = useAdm4;
		this.codeAdm1 = codeAdm1;
		this.codeAdm2 = codeAdm2;
		this.codeAdm3 = codeAdm3;
		this.codeAdm4 = codeAdm4;
		this.codeChv1 = codeChv1;
		this.codeChv2 = codeChv2;
		this.verifLiterals = verifLiterals;
		this.pathToCsv = pathToCsv;
		this.pathToVariablesTxt = pathToVariablesTxt;
	}

	public int getReaderNumber() {
		return readerNumber;
	}
	
	@XmlElement
	public void setReaderNumber(int readerNumber) {
		this.readerNumber = readerNumber;
	}

	public String getPathToCsv() {
		return pathToCsv;
	}
	
	@XmlElement
	public void setPathToCsv(String pathToCsv) {
		this.pathToCsv = pathToCsv;
	}

	public String getPathToVariablesTxt() {
		return pathToVariablesTxt;
	}
	
	@XmlElement
	public void setPathToVariablesTxt(String pathToVariablesTxt) {
		this.pathToVariablesTxt = pathToVariablesTxt;
	}

	public boolean isChv1Disabled() {
		return chv1Disabled;
	}
	
	@XmlAttribute
	public void setChv1Disabled(boolean chv1Disabled) {
		this.chv1Disabled = chv1Disabled;
	}

	public boolean isHexSfi() {
		return hexSfi;
	}
	
	@XmlAttribute
	public void setHexSfi(boolean hexSfi) {
		this.hexSfi = hexSfi;
	}

	public boolean isHexRecordNumber() {
		return hexRecordNumber;
	}
	
	@XmlAttribute
	public void setHexRecordNumber(boolean hexRecordNumber) {
		this.hexRecordNumber = hexRecordNumber;
	}

	public boolean isUseVariablesTxt() {
		return useVariablesTxt;
	}
	
	@XmlAttribute
	public void setUseVariablesTxt(boolean useVariablesTxt) {
		this.useVariablesTxt = useVariablesTxt;
	}

	public boolean isUseAdm2() {
		return useAdm2;
	}
	
	@XmlAttribute
	public void setUseAdm2(boolean useAdm2) {
		this.useAdm2 = useAdm2;
	}

	public boolean isUseAdm3() {
		return useAdm3;
	}
	
	@XmlAttribute
	public void setUseAdm3(boolean useAdm3) {
		this.useAdm3 = useAdm3;
	}

	public boolean isUseAdm4() {
		return useAdm4;
	}
	
	@XmlAttribute
	public void setUseAdm4(boolean useAdm4) {
		this.useAdm4 = useAdm4;
	}

	public String getCodeAdm1() {
		return codeAdm1;
	}
	
	@XmlElement
	public void setCodeAdm1(String codeAdm1) {
		this.codeAdm1 = codeAdm1;
	}

	public String getCodeAdm2() {
		return codeAdm2;
	}
	
	@XmlElement
	public void setCodeAdm2(String codeAdm2) {
		this.codeAdm2 = codeAdm2;
	}

	public String getCodeAdm3() {
		return codeAdm3;
	}
	
	@XmlElement
	public void setCodeAdm3(String codeAdm3) {
		this.codeAdm3 = codeAdm3;
	}

	public String getCodeAdm4() {
		return codeAdm4;
	}
	
	@XmlElement
	public void setCodeAdm4(String codeAdm4) {
		this.codeAdm4 = codeAdm4;
	}

	public String getCodeChv1() {
		return codeChv1;
	}
	
	@XmlElement
	public void setCodeChv1(String codeChv1) {
		this.codeChv1 = codeChv1;
	}

	public String getCodeChv2() {
		return codeChv2;
	}
	
	@XmlElement
	public void setCodeChv2(String codeChv2) {
		this.codeChv2 = codeChv2;
	}

	public VerifLiterals getVerifLiterals() {
		return verifLiterals;
	}
	
	@XmlElement
	public void setVerifLiterals(VerifLiterals verifLiterals) {
		this.verifLiterals = verifLiterals;
	}
	
}
