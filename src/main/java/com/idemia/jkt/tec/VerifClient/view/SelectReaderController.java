package com.idemia.jkt.tec.VerifClient.view;

import java.util.ArrayList;
import java.util.List;

import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.idemia.jkt.tec.VerifClient.VerifClientApplication;

import javafx.fxml.*;
import javafx.scene.control.ComboBox;

@Component
public class SelectReaderController {
	
	static Logger logger = Logger.getLogger(SelectReaderController.class.getName());
		
	private VerifClientApplication application;
	private List<CardTerminal> terminals;

	@FXML
	private ComboBox<String> cmbReader;
	
	@Autowired
	private RootLayoutController root;
	
	public SelectReaderController() {}
	
	public void setMainApp(VerifClientApplication application) {
		this.application = application;
	}
	
	@FXML
	private void initialize() {
		// get list of actual readers
		try {
			terminals = root.getTerminalFactory().terminals().list();
			if (!terminals.isEmpty()) {
				List<String> readerNames = new ArrayList<>();
				for (CardTerminal terminal : terminals)
					readerNames.add(terminal.getName());
				cmbReader.getItems().addAll(readerNames);
				
				if (root.getVerifConfig().getReaderNumber() == -1)
					cmbReader.getSelectionModel().select(0);
				else
					cmbReader.getSelectionModel().select(root.getVerifConfig().getReaderNumber());
				
			} else
				logger.warn("No terminal/reader detected!");
		} catch (CardException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void handleButtonCancel() {
		application.getSelectReaderDialogStage().close();
	}
	
	@FXML
	public void handleButtonOk() {
		int selectedReaderIndex = cmbReader.getSelectionModel().getSelectedIndex();
		root.getVerifConfig().setReaderNumber(selectedReaderIndex);
		
		if (selectedReaderIndex != -1)
			root.getLblTerminalInfo().setText(terminals.get(selectedReaderIndex).getName());
		
		application.getSelectReaderDialogStage().close();
	}

}
