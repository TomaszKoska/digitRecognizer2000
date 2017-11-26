package control;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import ai.AIEngine;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import view.WindowDisplayer;

public class MainWindowController {
	
	Stage stage;
	AIEngine aie;
	
	//Main GUI
	@FXML private Button recognizeButton;
	@FXML private Button clearButton;
	@FXML private Canvas paintCanvas;
	@FXML private Label outcomeLabel;
	private WindowDisplayer windowDisplayer;
	
	
	public void saveImage(String path) {
		WritableImage writableImage = new WritableImage(280, 280);
		paintCanvas.snapshot(null, writableImage);
		RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
		try {
			ImageIO.write(renderedImage, "png", new File(path));
			System.out.println(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	
	
	@FXML public void handleRecognizeButton(ActionEvent event) throws IOException{
	System.out.println("Rekognizacja w toku");
	saveImage(System.getProperty("java.io.tmpdir")+"tmpDigit.png");
	String result = aie.classifyMyFile(System.getProperty("java.io.tmpdir")+"tmpDigit.png");
	System.out.println(result);
	windowDisplayer.updateLabel(result);
	
	}	
	@FXML public void handleClearButton(ActionEvent event){
	System.out.println("czyszczenie w toku!");

	    GraphicsContext gc = this.paintCanvas.getGraphicsContext2D();	    
	    gc.setFill(Color.WHITE);
	    gc.fillRect(0, 0, paintCanvas.getHeight(), paintCanvas.getWidth());
	   
	    stage.show();
	}
	@FXML public void handlePaintCanvasPressed(MouseEvent event){
	System.out.println("jakiœ canvasowy event w toku");
	GraphicsContext gc = this.paintCanvas.getGraphicsContext2D();	

	gc.setFill(Color.LIGHTGRAY);
    gc.setStroke(Color.BLACK);
    gc.setLineWidth(5);
     
	gc.beginPath();
    gc.moveTo(event.getX(), event.getY());
    gc.stroke();
	}
	

	@FXML public void handlePaintCanvasDragged(MouseEvent event){
		System.out.println("jakiœ canvasowy event w toku");
		GraphicsContext gc = this.paintCanvas.getGraphicsContext2D();	

		gc.setFill(Color.BLACK);
	    gc.setStroke(Color.BLACK);
	    gc.setLineWidth(20);
	     
	    gc.lineTo(event.getX(), event.getY());
        gc.stroke();
		}

	
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	public AIEngine getAie() {
		return aie;
	}
	public void setAie(AIEngine aie) {
		this.aie = aie;
	}

	public WindowDisplayer getWindowDisplayer() {
		return windowDisplayer;
	}
	public void setWindowDisplayer(WindowDisplayer windowDisplayer) {
		this.windowDisplayer = windowDisplayer;
	}
	public Button getRecognizeButton() {
		return recognizeButton;
	}
	public void setRecognizeButton(Button recognizeButton) {
		this.recognizeButton = recognizeButton;
	}
	public Button getClearButton() {
		return clearButton;
	}
	public void setClearButton(Button clearButton) {
		this.clearButton = clearButton;
	}
	public Canvas getPaintCanvas() {
		return paintCanvas;
	}
	public void setPaintCanvas(Canvas paintCanvas) {
		this.paintCanvas = paintCanvas;
	}
	public Label getOutcomeLabel() {
		return outcomeLabel;
	}
	public void setOutcomeLabel(Label outcomeLabel) {
		this.outcomeLabel = outcomeLabel;
	}
	public Stage getStage() {
		return stage;
	}
	
	
}
