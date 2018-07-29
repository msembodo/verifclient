package com.idemia.jkt.tec.VerifClient.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.idemia.jkt.tec.VerifClient.VerifClientApplication;

import javafx.fxml.*;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

@Component
public class EditLiteralsController {
	
	private VerifClientApplication application;
	
	@Autowired
	private RootLayoutController root;
		
	@FXML
	private TextField txtTr;
	@FXML
	private TextField txtLf;
	@FXML
	private TextField txtCy;
	@FXML
	private TextField txtLk;
	@FXML
	private TextField txtAlw;
	@FXML
	private TextField txtChv1;
	@FXML
	private TextField txtChv2;
	@FXML
	private TextField txtAdm1;
	@FXML
	private TextField txtAdm2;
	@FXML
	private TextField txtAdm3;
	@FXML
	private TextField txtAdm4;
	@FXML
	private TextField txtAdm5;
	@FXML
	private TextField txtAdm6;
	@FXML
	private TextField txtAdm7;
	@FXML
	private TextField txtAdm8;
	@FXML
	private TextField txtNev;
	@FXML
	private TextField txtAnd;
	@FXML
	private TextField txtOr;
	
	public EditLiteralsController() {}
	
	public void setMainApp(VerifClientApplication application) {
		this.application = application;
	}
	
	@FXML
	private void initialize() {
		Font fixedWidthFont;

		if (Font.getFamilies().contains("Consolas"))
			fixedWidthFont = Font.font("Consolas");
		else
			fixedWidthFont = Font.font("Monospaced");

		txtTr.setFont(fixedWidthFont);
		txtTr.setText(root.getVerifConfig().getVerifLiterals().getsFileStructTR());
		
		txtLf.setFont(fixedWidthFont);
		txtLf.setText(root.getVerifConfig().getVerifLiterals().getsFileStructLF());
		
		txtCy.setFont(fixedWidthFont);
		txtCy.setText(root.getVerifConfig().getVerifLiterals().getsFileStructCY());
		
		txtLk.setFont(fixedWidthFont);
		txtLk.setText(root.getVerifConfig().getVerifLiterals().getsFileStructLK());
		
		txtAlw.setFont(fixedWidthFont);
		txtAlw.setText(root.getVerifConfig().getVerifLiterals().getsAccALW());
		
		txtChv1.setFont(fixedWidthFont);
		txtChv1.setText(root.getVerifConfig().getVerifLiterals().getsAccCHV1());
		
		txtChv2.setFont(fixedWidthFont);
		txtChv2.setText(root.getVerifConfig().getVerifLiterals().getsAccCHV2());
		
		txtAdm1.setFont(fixedWidthFont);
		txtAdm1.setText(root.getVerifConfig().getVerifLiterals().getsAccADM1());
		
		txtAdm2.setFont(fixedWidthFont);
		txtAdm2.setText(root.getVerifConfig().getVerifLiterals().getsAccADM2());
		
		txtAdm3.setFont(fixedWidthFont);
		txtAdm3.setText(root.getVerifConfig().getVerifLiterals().getsAccADM3());
		
		txtAdm4.setFont(fixedWidthFont);
		txtAdm4.setText(root.getVerifConfig().getVerifLiterals().getsAccADM4());
		
		txtAdm5.setFont(fixedWidthFont);
		txtAdm5.setText(root.getVerifConfig().getVerifLiterals().getsAccADM5());
		
		txtAdm6.setFont(fixedWidthFont);
		txtAdm6.setText(root.getVerifConfig().getVerifLiterals().getsAccADM6());
		
		txtAdm7.setFont(fixedWidthFont);
		txtAdm7.setText(root.getVerifConfig().getVerifLiterals().getsAccADM7());
		
		txtAdm8.setFont(fixedWidthFont);
		txtAdm8.setText(root.getVerifConfig().getVerifLiterals().getsAccADM8());
		
		txtNev.setFont(fixedWidthFont);
		txtNev.setText(root.getVerifConfig().getVerifLiterals().getsAccNEV());
		
		txtAnd.setFont(fixedWidthFont);
		txtAnd.setText(root.getVerifConfig().getVerifLiterals().getsAccAND());
		
		txtOr.setFont(fixedWidthFont);
		txtOr.setText(root.getVerifConfig().getVerifLiterals().getsAccOR());
	}
	
	@FXML
	public void handleButtonCancel() {
		application.getEditLiteralsDialogStage().close();
	}
	
	@FXML
	public void handleButtonSave() {
		root.getVerifConfig().getVerifLiterals().setsFileStructTR(txtTr.getText());
		root.getVerifConfig().getVerifLiterals().setsFileStructLF(txtLf.getText());
		root.getVerifConfig().getVerifLiterals().setsFileStructCY(txtCy.getText());
		root.getVerifConfig().getVerifLiterals().setsFileStructLK(txtLk.getText());
		
		root.getVerifConfig().getVerifLiterals().setsAccALW(txtAlw.getText());
		root.getVerifConfig().getVerifLiterals().setsAccCHV1(txtChv1.getText());
		root.getVerifConfig().getVerifLiterals().setsAccCHV2(txtChv2.getText());
		root.getVerifConfig().getVerifLiterals().setsAccADM1(txtAdm1.getText());
		root.getVerifConfig().getVerifLiterals().setsAccADM2(txtAdm2.getText());
		root.getVerifConfig().getVerifLiterals().setsAccADM3(txtAdm3.getText());
		root.getVerifConfig().getVerifLiterals().setsAccADM4(txtAdm4.getText());
		root.getVerifConfig().getVerifLiterals().setsAccADM5(txtAdm5.getText());
		root.getVerifConfig().getVerifLiterals().setsAccADM6(txtAdm6.getText());
		root.getVerifConfig().getVerifLiterals().setsAccADM7(txtAdm7.getText());
		root.getVerifConfig().getVerifLiterals().setsAccADM8(txtAdm8.getText());
		root.getVerifConfig().getVerifLiterals().setsAccNEV(txtNev.getText());
		root.getVerifConfig().getVerifLiterals().setsAccAND(txtAnd.getText());
		root.getVerifConfig().getVerifLiterals().setsAccOR(txtOr.getText());
		
		application.getEditLiteralsDialogStage().close();
	}

}
