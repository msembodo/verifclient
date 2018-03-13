package com.idemia.jkt.tec.VerifClient.view;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import javafx.fxml.*;
import javafx.scene.control.TextArea;

import com.idemia.jkt.tec.VerifClient.VerifClientApplication;

@Component
public class AboutController {
	
	private VerifClientApplication application;
	private ResourceLoader resourceLoader;
	
	@FXML
	private TextArea txtChangeLog;
	
	@Autowired
	public AboutController(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}
	
	public AboutController() {}
	
	public void setMainApp(VerifClientApplication application) {
		this.application = application;
	}
	
	@FXML
	private void initialize() {		
		Resource resource = resourceLoader.getResource("classpath:change.log");
		try {
			InputStream logStream = resource.getInputStream();
			String logStr = IOUtils.toString(logStream);
			txtChangeLog.setText(logStr);
			IOUtils.closeQuietly(logStream);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void handleButtonClose() {
		application.getAboutDialogStage().close();
	}

}
