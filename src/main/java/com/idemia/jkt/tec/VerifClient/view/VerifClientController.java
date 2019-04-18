package com.idemia.jkt.tec.VerifClient.view;

import java.io.File;
import javafx.scene.control.*;
import org.apache.log4j.Logger;
import org.controlsfx.control.MaskerPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.idemia.jkt.tec.VerifClient.VerifClientApplication;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.*;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

@Component
public class VerifClientController {
	
	static Logger logger = Logger.getLogger(VerifClientController.class.getName());
	
	private VerifClientApplication application;
	
	@Autowired
	private RootLayoutController root;
	
	@FXML
	private StackPane stackPane;
	
	private MaskerPane maskerPane;
	
	@FXML
	private CheckBox chkPin1Disabled;
	@FXML
	private CheckBox chkHexSfi;
	@FXML
	private CheckBox chkHexRecordNumber;
	@FXML
	private CheckBox chkUseVariablesTxt;
	@FXML
	private CheckBox chkUsimIn3gMode;
	@FXML
	private CheckBox chkAdm2;
	@FXML
	private CheckBox chkAdm3;
	@FXML
	private CheckBox chkAdm4;
	@FXML
	private Label lblUsimAid;
	@FXML
	private TextField txtUsimAid;
	@FXML
	private TextField txtVariables;
	@FXML
	private TextField txtAdm1;
	@FXML
	private TextField txtAdm2;
	@FXML
	private TextField txtAdm3;
	@FXML
	private TextField txtAdm4;
	@FXML
	private TextField txtChv1;
	@FXML
	private TextField txtChv2;
	@FXML
	private Button btnVariables;
	
	@FXML
	private WebView webErrorReport;
	@FXML
	private TextArea txtRunLog;
	@FXML
    private TabPane logReportTabPane;

	public VerifClientController() {}
	
	public void setMainApp(VerifClientApplication application) {
		this.application = application;
	}
	
	@FXML
	private void initialize() {
		Font fixedWidthFont;

		chkPin1Disabled.setSelected(root.getVerifConfig().isChv1Disabled());
		chkHexSfi.setSelected(root.getVerifConfig().isHexSfi());
		chkHexRecordNumber.setSelected(root.getVerifConfig().isHexRecordNumber());
		chkUseVariablesTxt.setSelected(root.getVerifConfig().isUseVariablesTxt());
		chkUsimIn3gMode.setSelected(root.getVerifConfig().isUsimIn3gMode());
		
		// USIM AID field is disabled by default
		lblUsimAid.setDisable(true);
		txtUsimAid.setDisable(true);
		
		// ADM2-ADM3-ADM4 fields are disabled by default
		txtAdm2.setDisable(true);
		txtAdm3.setDisable(true);
		txtAdm4.setDisable(true);
		
		// 'use variables.txt' is disabled by default
		txtVariables.setDisable(true);
		btnVariables.setDisable(true);

		if (Font.getFamilies().contains("Consolas"))
			fixedWidthFont = Font.font("Consolas");
		else
			fixedWidthFont = Font.font("Monospaced");
		
		txtVariables.setFont(fixedWidthFont);
		txtVariables.setText(root.getVerifConfig().getPathToVariablesTxt());
		if (chkUseVariablesTxt.isSelected()) {
			txtVariables.setDisable(false);
			btnVariables.setDisable(false);
		}
		
		txtUsimAid.setFont(fixedWidthFont);
		txtUsimAid.setText(root.getVerifConfig().getUsimAid());
//		if (chkUsimIn3gMode.isSelected()) {
//			lblUsimAid.setDisable(false);
//			txtUsimAid.setDisable(false);
//		}

		chkAdm2.setSelected(root.getVerifConfig().isUseAdm2());
		chkAdm3.setSelected(root.getVerifConfig().isUseAdm3());
		chkAdm4.setSelected(root.getVerifConfig().isUseAdm4());
		txtAdm1.setFont(fixedWidthFont);
		txtAdm1.setText(root.getVerifConfig().getCodeAdm1());
		
		txtAdm2.setFont(fixedWidthFont);
		txtAdm2.setText(root.getVerifConfig().getCodeAdm2());
		if (chkAdm2.isSelected())
			txtAdm2.setDisable(false);
		
		txtAdm3.setFont(fixedWidthFont);
		txtAdm3.setText(root.getVerifConfig().getCodeAdm3());
		if (chkAdm3.isSelected())
			txtAdm3.setDisable(false);
		
		txtAdm4.setFont(fixedWidthFont);
		txtAdm4.setText(root.getVerifConfig().getCodeAdm4());
		if (chkAdm4.isSelected())
			txtAdm4.setDisable(false);
		
		txtChv1.setFont(fixedWidthFont);
		txtChv1.setText(root.getVerifConfig().getCodeChv1());
		txtChv2.setFont(fixedWidthFont);
		txtChv2.setText(root.getVerifConfig().getCodeChv2());
		
		chkUseVariablesTxt.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (chkUseVariablesTxt.isSelected()) {
					txtVariables.setDisable(false);
					btnVariables.setDisable(false);
				}
				else {
					txtVariables.setDisable(true);
					btnVariables.setDisable(true);
				}
			}
		});
		
//		chkUsimIn3gMode.selectedProperty().addListener(new ChangeListener<Boolean>() {
//
//			@Override
//			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
//				if (chkUsimIn3gMode.isSelected()) {
//					lblUsimAid.setDisable(false);
//					txtUsimAid.setDisable(false);
//				}
//				else {
//					lblUsimAid.setDisable(true);
//					txtUsimAid.setDisable(true);
//				}
//			}
//		});
		
		chkAdm2.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (chkAdm2.isSelected())
					txtAdm2.setDisable(false);
				else
					txtAdm2.setDisable(true);
			}
		});
		chkAdm3.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (chkAdm3.isSelected())
					txtAdm3.setDisable(false);
				else
					txtAdm3.setDisable(true);
			}
		});
		chkAdm4.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (chkAdm4.isSelected())
					txtAdm4.setDisable(false);
				else
					txtAdm4.setDisable(true);
			}
		});
		
		// for masking main window while verification is executing
		maskerPane = new MaskerPane();
		maskerPane.setVisible(false);
		stackPane.getChildren().add(maskerPane);
		
		webErrorReport.setDisable(true);
		
		// control for displaying error report
//		WebEngine webEngine = webErrorReport.getEngine();

		// display quick guide
//		URL urlHowto = this.getClass().getResource("/com/idemia/jkt/tec/VerifClient/view/howto_local.html");
//		webEngine.load(urlHowto.toString());
		
		// control for displaying run log
		txtRunLog.setFont(fixedWidthFont);
		txtRunLog.setEditable(false);
		txtRunLog.setDisable(true);
		
		logReportTabPane.getSelectionModel().select(1);
	}

	public VerifClientApplication getApplication() {
		return application;
	}
	
	public void saveOptionsAndCodes() {
		root.getVerifConfig().setChv1Disabled(chkPin1Disabled.isSelected());
		root.getVerifConfig().setHexSfi(chkHexSfi.isSelected());
		root.getVerifConfig().setHexRecordNumber(chkHexRecordNumber.isSelected());
		root.getVerifConfig().setUseVariablesTxt(chkUseVariablesTxt.isSelected());
		root.getVerifConfig().setUsimIn3gMode(chkUsimIn3gMode.isSelected());
		root.getVerifConfig().setUseAdm2(chkAdm2.isSelected());
		root.getVerifConfig().setUseAdm3(chkAdm3.isSelected());
		root.getVerifConfig().setUseAdm4(chkAdm4.isSelected());
		
		root.getVerifConfig().setPathToVariablesTxt(txtVariables.getText());
		root.getVerifConfig().setUsimAid(txtUsimAid.getText());
		root.getVerifConfig().setCodeAdm1(txtAdm1.getText());
		root.getVerifConfig().setCodeAdm2(txtAdm2.getText());
		root.getVerifConfig().setCodeAdm3(txtAdm3.getText());
		root.getVerifConfig().setCodeAdm4(txtAdm4.getText());
		root.getVerifConfig().setCodeChv1(txtChv1.getText());
		root.getVerifConfig().setCodeChv2(txtChv2.getText());
	}
	
	@FXML
	public void handleButtonSelectVariables() {
		FileChooser varFileChooser = new FileChooser();
		varFileChooser.setTitle("Select Variables");
		varFileChooser.getExtensionFilters().addAll(new ExtensionFilter("Variables", "*.txt"));
		File selectedVariables = varFileChooser.showOpenDialog(application.getPrimaryStage());
		if (selectedVariables != null) {
			root.getVerifConfig().setPathToVariablesTxt(selectedVariables.getAbsolutePath());
			txtVariables.setText(selectedVariables.getAbsolutePath());
		}
	}

	public MaskerPane getMaskerPane() {
		return maskerPane;
	}

	public WebView getWebErrorReport() {
		return webErrorReport;
	}

	public TextArea getTxtRunLog() {
		return txtRunLog;
	}

    public TabPane getLogReportTabPane() {
        return logReportTabPane;
    }
}
