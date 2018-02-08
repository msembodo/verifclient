package com.idemia.jkt.tec.VerifClient;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.idemia.jkt.tec.VerifClient.view.EditLiteralsController;
import com.idemia.jkt.tec.VerifClient.view.RootLayoutController;
import com.idemia.jkt.tec.VerifClient.view.SelectReaderController;
import com.idemia.jkt.tec.VerifClient.view.VerifClientController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

@SpringBootApplication
public class VerifClientApplication extends Application {
	
	private ConfigurableApplicationContext springContext;
	
	static Logger logger = Logger.getLogger(VerifClientApplication.class.getName());
	
	private BorderPane rootLayout;
	private Stage primaryStage;
	private Stage editLiteralsDialogStage;
	private Stage selectReaderDialogStage;

	public static void main(String[] args) {
		launch(VerifClientApplication.class, args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("VerifClient");
		
		initRootLayout();
		showVerifClient();
	}

	@Override
	public void init() throws Exception {
		springContext = SpringApplication.run(VerifClientApplication.class);
	}

	@Override
	public void stop() throws Exception {
		springContext.stop();
	}
	
	public void initRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("view/RootLayout.fxml"));
			loader.setControllerFactory(springContext::getBean);
			rootLayout = (BorderPane) loader.load();
			
			// give controller access to main app
			RootLayoutController controller = loader.getController();
			controller.setMainApp(this);
			
			Scene scene = new Scene(rootLayout);
			
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showVerifClient() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("view/VerifClient.fxml"));
			loader.setControllerFactory(springContext::getBean);
			AnchorPane verifClient = (AnchorPane) loader.load();
			
			rootLayout.setCenter(verifClient);
			
			// give controller access to main app
			VerifClientController controller = loader.getController();
			controller.setMainApp(this);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showEditLiterals() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("view/EditLiterals.fxml"));
			loader.setControllerFactory(springContext::getBean);
			AnchorPane editLiterals = (AnchorPane) loader.load();
			
			// give controller access to main app
			EditLiteralsController controller = loader.getController();
			controller.setMainApp(this);
			
			// create dialog
			editLiteralsDialogStage = new Stage();
			editLiteralsDialogStage.setTitle("Edit Literals");
			editLiteralsDialogStage.setResizable(false);
			editLiteralsDialogStage.initModality(Modality.WINDOW_MODAL);
			editLiteralsDialogStage.initOwner(primaryStage);
			Scene scene = new Scene(editLiterals);
			editLiteralsDialogStage.setScene(scene);
			
			editLiteralsDialogStage.showAndWait();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void showSelectReader() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("view/SelectReader.fxml"));
			loader.setControllerFactory(springContext::getBean);
			AnchorPane selectReader = (AnchorPane) loader.load();
			
			// give controller access to main app
			SelectReaderController controller = loader.getController();
			controller.setMainApp(this);
			
			// create dialog
			selectReaderDialogStage = new Stage();
			selectReaderDialogStage.setTitle("Select Reader");
			selectReaderDialogStage.setResizable(false);
			selectReaderDialogStage.initModality(Modality.WINDOW_MODAL);
			selectReaderDialogStage.initOwner(primaryStage);
			Scene scene = new Scene(selectReader);
			selectReaderDialogStage.setScene(scene);
			
			selectReaderDialogStage.showAndWait();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public Stage getEditLiteralsDialogStage() {
		return editLiteralsDialogStage;
	}

	public Stage getSelectReaderDialogStage() {
		return selectReaderDialogStage;
	}
	
}
