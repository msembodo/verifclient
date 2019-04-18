package com.idemia.jkt.tec.VerifClient.view;

import java.net.URL;

import org.springframework.stereotype.Controller;

import com.idemia.jkt.tec.VerifClient.VerifClientApplication;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

@Controller
public class UserGuideController {
	
	private VerifClientApplication application;
	
	@FXML
	private WebView webUserGuide;
	
	public UserGuideController() {}
	
	public void setMainApp(VerifClientApplication application) {
		this.application = application;
	}
	
	@FXML
	private void initialize() {
		WebEngine webEngine = webUserGuide.getEngine();
		URL urlHowTo = this.getClass().getResource("/com/idemia/jkt/tec/VerifClient/view/howto_local.html");
		webEngine.load(urlHowTo.toString());
	}

	public VerifClientApplication getApplication() {
		return application;
	}

}
