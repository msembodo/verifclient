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

import com.idemia.jkt.tec.VerifClient.response.ConverterResponse;
import javafx.scene.control.*;
import org.apache.log4j.Logger;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.StatusBar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.idemia.jkt.tec.VerifClient.VerifClientApplication;
import com.idemia.jkt.tec.VerifClient.model.VerifConfig;
import com.idemia.jkt.tec.VerifClient.response.VerificationResponse;
import com.idemia.jkt.tec.VerifClient.service.VerifConfigService;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.*;
import javafx.geometry.Orientation;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
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
	private ConverterResponse converterResponse;
	private File selectedCsv;
	
	@Autowired
	private VerifConfigService verifConfigService;
	
	@Autowired
	private VerifClientController vClient;
	
	@FXML
	private BorderPane rootBorderPane;
	@FXML
	private MenuBar menuBar;
	
	@FXML
	private MenuItem menuLoadCsv;
	@FXML
	private Menu menuImportFrom;
	@FXML
	private MenuItem menuExMorphoXml;
	@FXML
	private MenuItem menuSimpml;
	@FXML
	private MenuItem menuSave;
	@FXML
	private MenuItem menuQuit;
	@FXML
	private MenuItem menuSelectReader;
	@FXML
	private MenuItem menuEditLiterals;
	@FXML
	private MenuItem menuCustomApdu;
	@FXML
	private MenuItem menuRun;
	@FXML
	private MenuItem menuUserGuide;
	@FXML
	private MenuItem menuAbout;
	
	@FXML
	private ToolBar toolBar;
	
	private Button btnOpenCsv;
	private Button btnScdl;
	private Button btnOpenUxp;
	private Button btnSaveConfiguration;
	private Button btnSelectReader;
	private Button btnEditLiterals;
	private Button btnCustomApdu;
	private Button btnRun;
	
	private StatusBar appStatusBar;
	private Label lblTerminalInfo;
	private String previousSearch;
	private int searchCount;
	private int searchStep;
	
	public RootLayoutController() {}
	
	public void setMainApp(VerifClientApplication application) {
		this.application = application;
	}
	
	@FXML
	private void initialize() {
		menuLoadCsv.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.FOLDER_OPEN));
		menuLoadCsv.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
		menuImportFrom.setGraphic(new MaterialDesignIconView(MaterialDesignIcon.IMPORT));
		menuExMorphoXml.setGraphic(new MaterialDesignIconView(MaterialDesignIcon.FILE_EXCEL));
		menuExMorphoXml.setAccelerator(new KeyCodeCombination(KeyCode.F6));
		menuSimpml.setGraphic(new MaterialDesignIconView(MaterialDesignIcon.XML));
		menuSimpml.setAccelerator(new KeyCodeCombination(KeyCode.F7));
		menuSave.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.SAVE));
		menuSave.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
		menuQuit.setGraphic(new MaterialDesignIconView(MaterialDesignIcon.CLOSE));
		menuQuit.setAccelerator(new KeyCodeCombination(KeyCode.W, KeyCombination.CONTROL_DOWN));
		menuSelectReader.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.USB));
		menuSelectReader.setAccelerator(new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN));
		menuEditLiterals.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.FONT));
		menuEditLiterals.setAccelerator(new KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN));
		menuCustomApdu.setGraphic(new MaterialDesignIconView(MaterialDesignIcon.SETTINGS));
		menuRun.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.PLAY));
		menuRun.setAccelerator(new KeyCodeCombination(KeyCode.F5));
		menuUserGuide.setGraphic(new MaterialDesignIconView(MaterialDesignIcon.HELP));
		menuAbout.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.USER));
		
		btnOpenCsv = new Button("", new FontAwesomeIconView(FontAwesomeIcon.FOLDER_OPEN));
		btnOpenCsv.setTooltip(new Tooltip("Load CSV"));
		btnOpenCsv.setOnAction((event) -> { handleMenuLoadCsv(); });
		btnScdl = new Button("", new MaterialDesignIconView(MaterialDesignIcon.FILE_EXCEL));
		btnScdl.setTooltip(new Tooltip("Import SCDL Technical Report"));
		btnScdl.setOnAction((event) -> { handleMenuImportFromExMorphoXml(); });
		btnOpenUxp = new Button("", new MaterialDesignIconView(MaterialDesignIcon.XML));
		btnOpenUxp.setTooltip(new Tooltip("Import UXP"));
		btnOpenUxp.setOnAction((event) -> { handleMenuImportFromUxp(); });
		btnSaveConfiguration = new Button("", new FontAwesomeIconView(FontAwesomeIcon.SAVE));
		btnSaveConfiguration.setTooltip(new Tooltip("Save Configuration"));
		btnSaveConfiguration.setOnAction((event) -> { handleMenuSaveConfiguration(); });
		btnSelectReader = new Button("", new FontAwesomeIconView(FontAwesomeIcon.USB));
		btnSelectReader.setTooltip(new Tooltip("Select Reader"));
		btnSelectReader.setOnAction((event) -> { handleMenuSelectReader(); });
		btnEditLiterals = new Button("", new FontAwesomeIconView(FontAwesomeIcon.FONT));
		btnEditLiterals.setTooltip(new Tooltip("Edit Literals"));
		btnEditLiterals.setOnAction((event) -> { handleMenuEditLiterals(); });
		btnCustomApdu = new Button("", new MaterialDesignIconView(MaterialDesignIcon.SETTINGS));
		btnCustomApdu.setTooltip(new Tooltip("Custom APDU for Security Codes Verification"));
		btnCustomApdu.setOnAction((event) -> { handleMenuCustomApdu(); });
		btnRun = new Button("", new FontAwesomeIconView(FontAwesomeIcon.PLAY));
		btnRun.setTooltip(new Tooltip("Run Verification"));
		btnRun.setOnAction((event) -> { handleMenuRun(); });

		toolBar.getItems().add(btnOpenCsv);
		toolBar.getItems().add(btnScdl);
		toolBar.getItems().add(btnOpenUxp);
		toolBar.getItems().add(new Separator());
		toolBar.getItems().add(btnEditLiterals);
		toolBar.getItems().add(btnCustomApdu);
		toolBar.getItems().add(btnSaveConfiguration);
		toolBar.getItems().add(btnRun);
		toolBar.getItems().add(new Separator());
		toolBar.getItems().add(btnSelectReader);
		
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
		Alert exitAlert = new Alert(AlertType.CONFIRMATION);
		exitAlert.initModality(Modality.APPLICATION_MODAL);
		exitAlert.initOwner(application.getPrimaryStage());
		exitAlert.setTitle("Confirmation");
		exitAlert.setHeaderText("Quit VerifClient?");
		exitAlert.setContentText("Changes made in configuration before running verification will not be saved.");
		Optional<ButtonType> result = exitAlert.showAndWait();
		if (result.get() == ButtonType.OK) {
			try {
				verifConfigService.shutdownVerifServer();
			} catch (Exception e) {
				logger.info("Verification server has been killed: " + e.getMessage());
			}
			Platform.exit();
		}
	}
	
	@FXML
	public void handleMenuLoadCsv() {
		// user select input csv
		FileChooser csvFileChooser = new FileChooser();
		csvFileChooser.setTitle("Select CSV");
		String csvFolder = "";
		if (verifConfig.getPathToCsv() != "C:\\") {
			csvFolder = new File(verifConfig.getPathToCsv()).getParent();
			if (csvFolder == null)
				csvFolder = "C:\\";
		}
		else
			csvFolder = "C:\\";
		csvFileChooser.setInitialDirectory(new File(csvFolder));
		csvFileChooser.getExtensionFilters().addAll(new ExtensionFilter("Verification Data", "*.csv"));
		selectedCsv = csvFileChooser.showOpenDialog(application.getPrimaryStage());
		if (selectedCsv != null) {
			verifConfig.setPathToCsv(selectedCsv.getAbsolutePath());
			application.getPrimaryStage().setTitle("VerifClient - " + selectedCsv.getAbsolutePath()); // update window bar
			appStatusBar.setText("CSV file selected: " + selectedCsv.getAbsolutePath()); // update status bar
		}
	}

	@FXML
	private void handleMenuImportFromUxp() {
		// user select uxp
		FileChooser uxpChooser = new FileChooser();
		uxpChooser.setTitle("Select UXP");
		uxpChooser.getExtensionFilters().addAll(new ExtensionFilter("SIMPML document", "*.uxp"));
		File selectedUxp = uxpChooser.showOpenDialog(application.getPrimaryStage());
		if (selectedUxp != null) {
			logger.info("UXP selected: " + selectedUxp.getAbsolutePath());
			// make user wait as uxp conversion executes
			vClient.getMaskerPane().setText("Converting UXP. Please wait..");
			// display masker pane
			vClient.getMaskerPane().setVisible(true);
			menuBar.setDisable(true);
			toolBar.setDisable(true);
			appStatusBar.setDisable(true);

			// use threads to avoid application freeze
			Task<Void> task = new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					// call conversion API
					converterResponse = verifConfigService.convertUxp(selectedUxp.getAbsolutePath());
					if (converterResponse.isConvertSuccess())
						logger.info(converterResponse.toJson());
					else
						logger.error(converterResponse.toJson());

					return null;
				}

				@Override
				protected void succeeded() {
					super.succeeded();
					// dismiss masker pane
					vClient.getMaskerPane().setVisible(false);
					menuBar.setDisable(false);
					toolBar.setDisable(false);
					appStatusBar.setDisable(false);

					// update status bar
					if (converterResponse.isConvertSuccess()) {
						appStatusBar.setText(converterResponse.getMessage());

						// show notification
						Notifications.create().title("VerifClient").text("UXP converted successfully").showInformation();

						selectedCsv = new File(converterResponse.getGeneratedCsv());
						verifConfig.setPathToCsv(selectedCsv.getAbsolutePath()); // update verif config
						application.getPrimaryStage().setTitle("VerifClient - " + converterResponse.getGeneratedCsv() + " (IMPORTED)");

						// display converter log
						vClient.getTxtRunLog().setDisable(false);
						String converterLogFileName = "converter.log";
						try (BufferedReader br = new BufferedReader(new FileReader(converterLogFileName))) {
							StringBuffer sb = new StringBuffer();
							String currentLine;
							while ((currentLine = br.readLine()) != null)
								sb.append(currentLine + "\n");
							vClient.getTxtRunLog().setText(sb.toString());
							vClient.getLogReportTabPane().getSelectionModel().select(1); // select run log tab

						} catch (IOException e) {
							e.printStackTrace();
						}

					} else {
						appStatusBar.setText("Converter failed");

						// show error notification
						Notifications.create().title("VerifClient").text("Converter failed").showError();

						Alert convertAlert = new Alert(AlertType.ERROR);
						convertAlert.initModality(Modality.APPLICATION_MODAL);
						convertAlert.initOwner(application.getPrimaryStage());
						convertAlert.setTitle("Converter error");
						convertAlert.setHeaderText("Failed to convert UXP");
						convertAlert.setContentText(converterResponse.getMessage());
						convertAlert.showAndWait();
					}
				}
			};

			Thread converterThread = new Thread(task);
			converterThread.start(); // run in background
		}
	}

	@FXML
	private void handleMenuImportFromExMorphoXml() {
		// user select xml
		FileChooser xmlChooser = new FileChooser();
		xmlChooser.setTitle("Select ex-Morpho document (exported from SCDL)");
		xmlChooser.getExtensionFilters().addAll(new ExtensionFilter("Customer specification", "*.xml"));
		File selectedXml = xmlChooser.showOpenDialog(application.getPrimaryStage());
		if (selectedXml != null) {
			logger.info("XML selected: " + selectedXml.getAbsolutePath());
			// make user wait as uxp conversion executes
			vClient.getMaskerPane().setText("Converting XML. Please wait..");
			// display masker pane
			vClient.getMaskerPane().setVisible(true);
			menuBar.setDisable(true);
			toolBar.setDisable(true);
			appStatusBar.setDisable(true);

			// use threads to avoid application freeze
			Task<Void> task = new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					// call conversion API
					converterResponse = verifConfigService.convertExMorphoDoc(selectedXml.getAbsolutePath());
					if (converterResponse.isConvertSuccess())
						logger.info(converterResponse.toJson());
					else
						logger.error(converterResponse.toJson());

					return null;
				}

				@Override
				protected void succeeded() {
					super.succeeded();
					// dismiss masker pane
					vClient.getMaskerPane().setVisible(false);
					menuBar.setDisable(false);
					toolBar.setDisable(false);
					appStatusBar.setDisable(false);

					// update status bar
					if (converterResponse.isConvertSuccess()) {
						appStatusBar.setText(converterResponse.getMessage());

						// show notification
						Notifications.create().title("VerifClient").text("XML converted successfully").showInformation();

						selectedCsv = new File(converterResponse.getGeneratedCsv());
						verifConfig.setPathToCsv(selectedCsv.getAbsolutePath()); // update verif config
						application.getPrimaryStage().setTitle("VerifClient - " + converterResponse.getGeneratedCsv() + " (IMPORTED)");

						// display converter log
						vClient.getTxtRunLog().setDisable(false);
						String converterLogFileName = "converter.log";
						try (BufferedReader br = new BufferedReader(new FileReader(converterLogFileName))) {
							StringBuffer sb = new StringBuffer();
							String currentLine;
							while ((currentLine = br.readLine()) != null)
								sb.append(currentLine + "\n");
							vClient.getTxtRunLog().setText(sb.toString());
							vClient.getLogReportTabPane().getSelectionModel().select(1); // select run log tab

						} catch (IOException e) {
							e.printStackTrace();
						}

					} else {
						appStatusBar.setText("Converter failed");

						// show error notification
						Notifications.create().title("VerifClient").text("Converter failed").showError();

						Alert convertAlert = new Alert(AlertType.ERROR);
						convertAlert.initModality(Modality.APPLICATION_MODAL);
						convertAlert.initOwner(application.getPrimaryStage());
						convertAlert.setTitle("Converter error");
						convertAlert.setHeaderText("Failed to convert XML");
						convertAlert.setContentText(converterResponse.getMessage());
						convertAlert.showAndWait();
					}
				}
			};

			Thread converterThread = new Thread(task);
			converterThread.start(); // run in background
		}
	}
	
	@FXML
	public void handleMenuSaveConfiguration() {
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
	private void handleMenuCustomApdu() {
		application.showCustomApdu();
	}
	
	@FXML
	private void handleMenuUserGuide() {
		application.showUserGuide();
	}
	
	@FXML
	private void handleMenuAbout() {
		application.showAbout();
	}
	
	@FXML
	private void handleMenuRun() {
		if (selectedCsv == null) {
			Alert noCsvAlert = new Alert(AlertType.WARNING);
			noCsvAlert.initModality(Modality.APPLICATION_MODAL);
			noCsvAlert.initOwner(application.getPrimaryStage());
			noCsvAlert.setTitle("Warning");
			noCsvAlert.setHeaderText("No CSV selected");
			noCsvAlert.setContentText("Verification will not proceed.");
			noCsvAlert.showAndWait();
			
		} else {
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
				toolBar.setDisable(true);
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
						toolBar.setDisable(false);
						appStatusBar.setDisable(false);
						
						// update status bar
						if (verificationResponse.isVerificationSuccess()) {
							appStatusBar.setText("Verification success.");
							
							// show notification
							Notifications.create().title("VerifClient").text("Verification complete.").showInformation();
							
							// display error report
							vClient.getWebErrorReport().setDisable(false);
							vClient.getWebErrorReport().setZoom(1.25);
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
							
							// enable search box and define handler
							vClient.getToolbarErrorReport().setDisable(false);
							previousSearch = "";
							vClient.getLblSearchCount().setText("");
							vClient.getTxtSearchReport().setText("");
							vClient.getTxtSearchReport().setOnAction((event) -> {
								if (webEngine.getDocument() != null) {
									String searchText = (String) vClient.getTxtSearchReport().getText();
									if (!searchText.equals(previousSearch)) {
										searchStep = 0;
										removeHighlight(webEngine, previousSearch);
										searchCount = (int) webEngine.executeScript("doCount('" + searchText + "')");
										if (searchCount == 0)
											vClient.getLblSearchCount().setText("no match found");
									}
									highlight(webEngine, searchText);
									if (searchStep < searchCount) {
										searchStep++;
										vClient.getLblSearchCount().setText("match: " + searchStep + " of " + searchCount);
									}
								}
							});
							
							// display run log
							vClient.getTxtRunLog().setDisable(false);
							String runLogFileName = "run.log";
							try (BufferedReader br = new BufferedReader(new FileReader(runLogFileName))) {
								StringBuffer sb = new StringBuffer();
								String currentLine;
								while ((currentLine = br.readLine()) != null)
									sb.append(currentLine + "\n");
								vClient.getTxtRunLog().setText(sb.toString());
								vClient.getLogReportTabPane().getSelectionModel().select(0); // select error report tab
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
	}
	
	private void highlight(WebEngine webEngine, String text) {
		webEngine.executeScript("doSearch('" + text + "')");
		previousSearch = text;
	}
	
	private void removeHighlight(WebEngine webEngine, String textToClear) {
		webEngine.executeScript("doClear('" + textToClear + "')");
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
