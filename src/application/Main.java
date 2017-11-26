package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import view.WindowDisplayer;
import ai.AIEngine;
import control.MainWindowController;

public class Main extends Application{  
	static AIEngine aie ;
	static WindowDisplayer wd;
	static MainWindowController mwc ;
	
	@Override
	public void start(Stage primaryStage){		
		aie = new AIEngine();
		aie.loadModelFromFile("neuralNetDigits.ser");
		wd = new WindowDisplayer();
		wd.showMainWindow(primaryStage);
		
		mwc = wd.getMainWindowController();
		mwc.setAie(aie);
		
		System.out.println(mwc);
		System.out.println(aie);
	}

	public static void main(String[] args) throws Exception{ //serio tak siê robi...?
		launch(args);
	}

	
}
