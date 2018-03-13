package com.idemia.jkt.tec.VerifClient.view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.TerminalFactory;

import org.apache.log4j.Logger;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.StatusBar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.idemia.jkt.tec.VerifClient.VerifClientApplication;
import com.idemia.jkt.tec.VerifClient.model.VerifConfig;
import com.idemia.jkt.tec.VerifClient.response.VerificationResponse;
import com.idemia.jkt.tec.VerifClient.service.VerifConfigService;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.*;
import javafx.geometry.Orientation;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;

@Component
public class RootLayoutController {
	
	static Logger logger = Logger.getLogger(RootLayoutController.class.getName());
	
	private VerifClientApplication application;
	private VerifConfig verifConfig;
	private TerminalFactory terminalFactory;
	private VerificationResponse verificationResponse;
	private File selectedCsv;
	
	@Autowired
	private VerifConfigService verifConfigService;
	
	@Autowired
	private VerifClientController vClient;
	
	@FXML
	private BorderPane rootBorderPane;
	@FXML
	private MenuBar menuBar;
	
	private StatusBar appStatusBar;
	private Label lblTerminalInfo;
	
	public RootLayoutController() {}
	
	public void setMainApp(VerifClientApplication application) {
		this.application = application;
	}
	
	@FXML
	private void initialize() {
		appStatusBar = new StatusBar();
		rootBorderPane.setBottom(appStatusBar);
		
		// get verification configuration from config.xml or by default values
		verifConfig = verifConfigService.initConfig();
		
		terminalFactory = TerminalFactory.getDefault();
		try {
			// list available readers
			List<CardTerminal> terminals = terminalFactory.terminals().list();
			lblTerminalInfo = new Label();
			appStatusBar.getRightItems().add(new Separator(Orientation.VERTICAL));
			appStatusBar.getRightItems().add(lblTerminalInfo);
			if (terminals.isEmpty())
				lblTerminalInfo.setText("(no terminal/reader detected)");
			else
				if (verifConfig.getReaderNumber() != -1)
					lblTerminalInfo.setText(terminals.get(verifConfig.getReaderNumber()).getName());
			
		} catch (CardException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void handleMenuClose() {
		// quit application
		Platform.exit();
	}
	
	@FXML
	private void handleMenuLoadCsv() {
		// user select input csv
		FileChooser csvFileChooser = new FileChooser();
		csvFileChooser.setTitle("Select CSV");
		csvFileChooser.getExtensionFilters().addAll(new ExtensionFilter("Verification Data", "*.csv"));
		selectedCsv = csvFileChooser.showOpenDialog(application.getPrimaryStage());
		if (selectedCsv != null) {
			verifConfig.setPathToCsv(selectedCsv.getAbsolutePath());
			application.getPrimaryStage().setTitle("VerifClient - " + selectedCsv.getAbsolutePath()); // update window bar
			appStatusBar.setText("CSV file selected: " + selectedCsv.getAbsolutePath()); // update status bar
		}
	}
	
	@FXML
	private void handleMenuSaveConfiguration() {
		vClient.saveOptionsAndCodes();
		verifConfigService.saveConfig(verifConfig);
	}
	
	@FXML
	private void handleMenuSelectReader() {
		application.showSelectReader();
	}
	
	@FXML
	private void handleMenuEditLiterals() {
		application.showEditLiterals();
	}
	
	@FXML
	private void handleMenuAbout() {
		application.showAbout();
	}
	
	@FXML
	private void handleMenuRun() {
		// save configurations
		handleMenuSaveConfiguration();
		
		// ask user confirmation
		Alert runAlert = new Alert(AlertType.CONFIRMATION);
		runAlert.initModality(Modality.APPLICATION_MODAL);
		runAlert.initOwner(application.getPrimaryStage());
		runAlert.setTitle("Confirmation");
		runAlert.setHeaderText("Run verification?");
		runAlert.setContentText("This will verify card against CSV.");
		Optional<ButtonType> result = runAlert.showAndWait();
		
		if (result.get() == ButtonType.OK) {
			// make user wait as verification executes
			vClient.getMaskerPane().setText("Running verification. Please wait..");
			// display masker pane
			vClient.getMaskerPane().setVisible(true);
			menuBar.setDisable(true);
			appStatusBar.setDisable(true);
			
			// use threads to avoid application freeze
			Task<Void> task = new Task<Void>() {

				@Override
				protected Void call() throws Exception {
					// call verif API
					verificationResponse = verifConfigService.runVerif();
					if (verificationResponse.isVerificationSuccess())
						logger.info(verificationResponse.toJson());
					else
						logger.error(verificationResponse.toJson());
					
					return null;
				}

				@Override
				protected void succeeded() {
					super.succeeded();
					// dismiss masker pane
					vClient.getMaskerPane().setVisible(false);
					menuBar.setDisable(false);
					appStatusBar.setDisable(false);
					
					// update status bar
					if (verificationResponse.isVerificationSuccess()) {
						appStatusBar.setText("Verification success.");
						
						// show notification
						Notifications.create().title("VerifClient").text("Verification complete.").showInformation();
						
						// display error report
						vClient.getWebErrorReport().setDisable(false);
						WebEngine webEngine = vClient.getWebErrorReport().getEngine();
						String errorReportPath = selectedCsv.getParent();
						String errorReportFileName = selectedCsv.getName().substring(0, selectedCsv.getName().indexOf(".")) + "_error.html";
						logger.info("Error report file: " + errorReportPath + File.separator + errorReportFileName);
						File errorReportFile = new File(errorReportPath + File.separator + errorReportFileName);
						try {
							URL urlErrorReport = errorReportFile.toURI().toURL();
							webEngine.load(urlErrorReport.toString());
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
						
						// display run log
						vClient.getTxtRunLog().setDisable(false);
						String runLogFileName = "run.log";
						try (BufferedReader br = new BufferedReader(new FileReader(runLogFileName))) {
							StringBuffer sb = new StringBuffer();
							String currentLine;
							while ((currentLine = br.readLine()) != null)
								sb.append(currentLine + "\n");
							vClient.getTxtRunLog().setText(sb.toString());
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					else {
						appStatusBar.setText("Verification fails.");
						
						// show error notification
						Notifications.create().title("VerifClient").text("Verification fails.").showError();
						
						Alert verifAlert = new Alert(AlertType.ERROR);
						verifAlert.initModality(Modality.APPLICATION_MODAL);
						verifAlert.initOwner(application.getPrimaryStage());
						verifAlert.setTitle("Verification error");
						verifAlert.setHeaderText("Failed to perform verification");
						verifAlert.setContentText(verificationResponse.getMessage());
						verifAlert.showAndWait();
					}
				}
			};
			
			Thread verifThread = new Thread(task);
			verifThread.start(); // run in background
		}
	}

	public VerifConfig getVerifConfig() {
		return verifConfig;
	}

	public StatusBar getAppStatusBar() {
		return appStatusBar;
	}

	public TerminalFactory getTerminalFactory() {
		return terminalFactory;
	}

	public Label getLblTerminalInfo() {
		return lblTerminalInfo;
	}

}
