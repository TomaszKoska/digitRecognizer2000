package view;

import ai.AIEngine;
import control.MainWindowController;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
W



public class WindowDisplayer {
	private  Stage primaryStage;
	private  AIEngine aie;
	
	private  MainWindowController mainWindowController;
	private  AnchorPane mainPane;

	
	public WindowDisplayer() {
		
	}
	
	
	public void showMainWindow(Stage primaryStage){
		primaryStage = new Stage();
		
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("MainWindow.fxml"));
            mainPane = (AnchorPane) loader.load();
            
            Scene scene = new Scene(mainPane);
            scene.getStylesheets().clear();
//            scene.getStylesheets().add("view/application.css");

            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.centerOnScreen();
            primaryStage.show();
            
            mainWindowController = loader.getController();
            mainWindowController.setStage(primaryStage);
            mainWindowController.setAie(this.aie);
            mainWindowController.setWindowDisplayer(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
	}	

	public void updateLabel(String content) {
		mainWindowController.getOutcomeLabel().setText(content);
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public MainWindowController getMainWindowController() {
		return mainWindowController;
	}

	public void setMainWindowController(MainWindowController mainWindowController) {
		this.mainWindowController = mainWindowController;
	}

	public AnchorPane getMainPane() {
		return mainPane;
	}

	public void setMainPane(AnchorPane mainPane) {
		this.mainPane = mainPane;
	}


	public AIEngine getAie() {
		return aie;
	}


}
