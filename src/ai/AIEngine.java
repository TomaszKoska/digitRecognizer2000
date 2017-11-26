package ai;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.datavec.image.loader.NativeImageLoader;
import org.deeplearning4j.datasets.iterator.impl.MnistDataSetIterator;
import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.Updater;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.preprocessor.ImagePreProcessingScaler;
import org.nd4j.linalg.lossfunctions.LossFunctions.LossFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import application.Main;
import helper.GeneralHelper;

public class AIEngine {
	private Logger log; // co to robi???
	private MultiLayerNetwork model;
	
	public AIEngine() {
		this.initialize();
	}
	
	public void initialize() {
		this.log = LoggerFactory.getLogger(Main.class);
		this.model = null;
	}
	
	public void loadModelFromFile(String path) {
		FileInputStream fis;
		ObjectInputStream ois;
		try  
        {
            fis = new FileInputStream(path);
            ois = new ObjectInputStream(fis);
            model = (MultiLayerNetwork)ois.readObject();
            ois.close();
        } 
        catch (Exception e)
        { 
            e.printStackTrace(); }
		
//		model.init();
        
	}
	
	
	
	
	
	
	
	
	
	
	public static void justShow() throws IOException {
        int batchSize = 1; // batch size for each epoch
        int rngSeed = 123; // random number seed for reproducibility

        DataSetIterator mnistTest = new MnistDataSetIterator(batchSize, false, rngSeed);

            DataSet next = mnistTest.next();
            System.out.println(next.getLabels());
            System.out.println(GeneralHelper.findIndexOfMax(next.getLabels()));
            next = mnistTest.next();
            System.out.println(next.getLabels());
            System.out.println(GeneralHelper.findIndexOfMax(next.getLabels()));

            System.out.println("\n\n\n");
            GeneralHelper.printWithBreaks(next.getFeatures(),28);
            
            double[] pozdro = new double[10];
            for(int i =0;i<10;i++) {
            	pozdro[i] = next.getLabels().ravel().getDouble(i);	
                System.out.println(pozdro[i]);
            }
        		

	}

	public void clasifyMyShit(String path, String modelPath, int seed) throws IOException {
		
		//import model
        MultiLayerNetwork model = null;
        
    	try   
        {
            FileInputStream fis = new FileInputStream(modelPath);
            @SuppressWarnings("resource")
			ObjectInputStream ois = new ObjectInputStream(fis);
            model = (MultiLayerNetwork)ois.readObject();
        } 
        catch (Exception e)
        { 
            e.printStackTrace(); }
		
//		model.init();	// ok, inicjalizujê sobie
		// model imported!
		
		
		for(int i =0; i<=9;i++) {
			NativeImageLoader loader = new NativeImageLoader(28, 28, 1);// load and scale
			INDArray image = loader.asMatrix(new File("images/cyfra"+i+".png")); // create INDarray
			ImagePreProcessingScaler ipps = new ImagePreProcessingScaler(1,0);
			ipps.preProcess(image);
			INDArray output = model.output(image.ravel());   // get model prediction for image
			System.out.println("Cyfra " + i  +" to podobno...:");
			System.out.println(image.ravel());
//			System.out.println("\n\n\n");
			System.out.println(output);
			System.out.println(GeneralHelper.findIndexOfMax(output));	
		}
    System.out.println("\n\n\n\n");
	NativeImageLoader loader = new NativeImageLoader(28, 28, 1);// load and scale
	INDArray image = loader.asMatrix(new File("C:/Users/Tomek/AppData/Local/Temp/tmpDigit.png")); // create INDarray
	ImagePreProcessingScaler ipps = new ImagePreProcessingScaler(1,0);
	ipps.preProcess(image);
	INDArray output = model.output(image.ravel());   // get model prediction for image
	System.out.println("Tmp digit to podobno...:");
	GeneralHelper.printWithBreaks(image.ravel(),28);
	System.out.println(output);
	System.out.println(GeneralHelper.findIndexOfMax(output));	
	}
	
	public String classifyMyFile(String filePath) throws IOException {
		NativeImageLoader loader = new NativeImageLoader(28, 28, 1);// load and scale
		INDArray image = loader.asMatrix(new File("C:/Users/Tomek/AppData/Local/Temp/tmpDigit.png")); // create INDarray
		ImagePreProcessingScaler ipps = new ImagePreProcessingScaler(1,0);
		ipps.preProcess(image);
		INDArray output = model.output(image.ravel());   // get model prediction for image
//		System.out.println("Tmp digit to podobno...:");
		GeneralHelper.printWithBreaks(image.ravel(),28);
//		System.out.println(output);

		return 	Integer.toString(GeneralHelper.findIndexOfMax(output));
	}
	
	public void train(String inputPath ,String outputPath,int howManyEpochs) throws IOException {
		//inputPath does not work yet
		//"D:\\test\\MyObject2.ser"
		final int numRows = 28;
        final int numColumns = 28;
        int outputNum = 10; 
        int batchSize =  32;
        int rngSeed = 123; 
        int numEpochs = howManyEpochs; // 15 number of epochs to perform	
		
        DataSetIterator mnistTrain = new MnistDataSetIterator(batchSize, true, rngSeed);
        DataSetIterator mnistTest = new MnistDataSetIterator(batchSize, false, rngSeed);
		
        
        log.info("Build model....");
        //ok, poni¿ej konfigujrujê sobie sieæ
        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                .seed(rngSeed) //include a random seed for reproducibility
                // use stochastic gradient descent as an optimization algorithm
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .iterations(1)
                .learningRate(0.006) //specify the learning rate
                .updater(Updater.NESTEROVS).momentum(0.9) //specify the rate of change of the learning rate.
                .regularization(true).l2(1e-4)
                .list()
                .layer(0, new DenseLayer.Builder() //create the first, input layer with xavier initialization
                        .nIn(numRows * numColumns)
                        .nOut(1000)
                        .activation(Activation.RELU)
                        .weightInit(WeightInit.XAVIER)
                        .build())
                .layer(1, new DenseLayer.Builder() //create hidden layer
                        .nIn(1000)
                        .nOut(2000)
                        .activation(Activation.RELU)
                        .weightInit(WeightInit.XAVIER)
                        .build())
                .layer(2, new OutputLayer.Builder(LossFunction.NEGATIVELOGLIKELIHOOD) //create hidden layer
                        .nIn(2000)
                        .nOut(outputNum)
                        .activation(Activation.SOFTMAX)
                        .weightInit(WeightInit.XAVIER)
                        .build())
                .pretrain(false).backprop(true) //use backpropagation to adjust weights
                .build();
		
		//a teraz tworzê sieæ z u¿yciem tej konfiguracji
        model = new MultiLayerNetwork(conf);
        model.init();	// ok, inicjalizujê sobie
        //print the score with every 1 iteration
        model.setListeners(new ScoreIterationListener(1));	// co to?


        
        log.info("Train model....");
        for( int i=0; i<numEpochs; i++ ){
            model.fit(mnistTrain); // co siê dzieje w funkcji fit ???
//            System.out.println(mnistTrain.next());
        }
		
        log.info("Evaluate model....");
        Evaluation eval = new Evaluation(outputNum); //create an evaluation object with 10 possible classes
        while(mnistTest.hasNext()){
            DataSet next = mnistTest.next();

            INDArray output = model.output(next.getFeatureMatrix()); //get the networks prediction
            eval.eval(next.getLabels(), output); //check the prediction against the true class
        }

        log.info(eval.stats());
        log.info("****************Example finished********************");
        
        try{
        	// Serialize data object to a file
        	ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(outputPath));
        	out.writeObject(model);
        	out.close();
        	} catch (IOException e) {
        	e.printStackTrace();
        	}

	}
}
