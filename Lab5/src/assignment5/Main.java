package assignment5;

import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;

import java.time.Duration;

public class Main extends Application {

    private String critterNameText = "";
    private String critterNumberText = "";
    private String timestepNumberText = "";
    private String runStatsNameText = "";
    private String runStatsTextReturn = "";
    private String consoleText = "";
    private String myPackage;
    private Text runStatsText;
    private GridPane gridPane;
    TextField makeCritterText;
    ComboBox makeCritterComboBox;
    TextField makeCritterNumber;
    TextField runStatsTextField;
    TextField timestepNumber;
    TextField seedNumber;

	public static void main(String[] args) {
		launch(args);
	}

	int window_width = 1000;
	int window_height = 600;
    @Override
    public void start(Stage primaryStage) throws Exception {
        myPackage = Main.class.getPackage().toString().split(" ")[1];
        //Parent root = FXMLLoader.load(getClass().getResource("Critters.fxml"));
        primaryStage.setTitle("Critters");
        //Scene scene = new Scene(root, 700, 420);

        //Gridpane
        gridPane = new GridPane();
        gridPane.setPrefSize(500, 500);
        gridPane.setMaxSize(500, 500);
        //Add children
        /*
        for(int i = 0; i < Params.world_width; i++){
            for(int j = 0; j < Params.world_height; j++) {
                Rectangle filler = new Rectangle();
                filler.setHeight(500 / Params.world_width - 150 / Params.world_width);
                filler.setWidth(500 / Params.world_height - 150 / Params.world_height);
                filler.setVisible(true);
                gridPane.add(filler, i, j);
                GridPane.setHalignment(filler, HPos.CENTER);
                GridPane.setValignment(filler, VPos.CENTER);
            }
        }
        */

        /*
        Rectangle yellow = new Rectangle();
        yellow.setHeight(500 / Params.world_width - 150 / Params.world_width);
        yellow.setWidth(500 / Params.world_height - 150 / Params.world_height);
        yellow.setFill(Color.YELLOW);
        int columnIndex = 7;
        int rowIndex = 3;
        removeCritter(gridPane, 7, 3);
        gridPane.add(yellow, columnIndex, rowIndex);
        GridPane.setHalignment(yellow, HPos.CENTER);
        GridPane.setValignment(yellow, VPos.CENTER);
        */



        //Set constraints
        for(int i = 0; i < Params.world_width; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setPercentWidth(100.0 / Params.world_width);
            gridPane.getColumnConstraints().add(col);
        }
        for(int i = 0; i < Params.world_height; i++){
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(100.0/Params.world_height);
            gridPane.getRowConstraints().add(row);
        }
        gridPane.hgapProperty().setValue(1.0);
        gridPane.vgapProperty().setValue(1.0);
        gridPane.gridLinesVisibleProperty().setValue(true);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setStyle("-fx-background-color: #7A70FE");



        //Anchor pane
        AnchorPane anchorPane = new AnchorPane(gridPane);
        AnchorPane.setRightAnchor(gridPane, 50.0); //Set x-offset from right
        AnchorPane.setTopAnchor(gridPane, 50.0); //Set y-offset from top
        anchorPane.setStyle("-fx-background-color: #D2D2D2");

        //Control panel gridpane
        GridPane controlPanel = new GridPane();
        controlPanel.alignmentProperty().setValue(Pos.CENTER);
        controlPanel.setHgap(10.0);
        controlPanel.setVgap(5.0);
        //controlPanel.setGridLinesVisible(true);


        //MakeCritter name textfield
        makeCritterText = new TextField();
        makeCritterText.setPromptText("Critter name");
        makeCritterText.setAlignment(Pos.CENTER);
        makeCritterText.addEventHandler(KeyEvent.KEY_PRESSED, critterNameTextHandler);
        controlPanel.add(makeCritterText, 0, 0);

        makeCritterComboBox = new ComboBox();
        makeCritterComboBox.getItems().addAll();

        //MakeCritter number textfield
        makeCritterNumber = new TextField();
        makeCritterNumber.setPromptText("Amount");
        makeCritterNumber.setAlignment(Pos.CENTER);
        makeCritterNumber.addEventHandler(KeyEvent.KEY_PRESSED, critterNumberTextHandler);
        controlPanel.add(makeCritterNumber, 0, 1);

        //MakeCritter button
        Button makeCritter = new Button("Make");
        makeCritter.addEventHandler(MouseEvent.MOUSE_RELEASED, makeCritterHandler);
        controlPanel.add(makeCritter, 1, 0);

        //Timestep number textfield
        timestepNumber = new TextField();
        timestepNumber.setPromptText("Time steps");
        timestepNumber.setAlignment(Pos.CENTER);
        timestepNumber.addEventHandler(KeyEvent.KEY_PRESSED, timestepNumberHandler);
        controlPanel.add(timestepNumber, 0, 8);

        //doWorldTimeStep button
        Button doWorldTimeStep = new Button("Step Time");
        doWorldTimeStep.addEventHandler(MouseEvent.MOUSE_RELEASED, doWorldTimeStepHandler);
        controlPanel.add(doWorldTimeStep, 1, 8);

        //Set seed textfield
        seedNumber = new TextField();
        seedNumber.setPromptText("New Seed");
        seedNumber.setAlignment(Pos.CENTER);
        controlPanel.add(seedNumber, 0, 16);

        //Set seed button
        Button seedButton = new Button("Seed");
        seedButton.addEventHandler(MouseEvent.MOUSE_RELEASED, seedButtonHandler);
        controlPanel.add(seedButton, 1, 16);

        //Runstats text
        Rectangle textBG = new Rectangle(400, 100);
        textBG.setFill(Color.WHITE);
        runStatsText = new Text();
        runStatsText.setFont(Font.font(14.0));
        runStatsText.setWrappingWidth(396.0);
        runStatsText.setText(runStatsTextReturn);

        anchorPane.getChildren().add(textBG);
        AnchorPane.setLeftAnchor(textBG, 10.0);
        AnchorPane.setBottomAnchor(textBG, 100.0);
        anchorPane.getChildren().add(runStatsText);
        AnchorPane.setLeftAnchor(runStatsText, 12.0);
        AnchorPane.setTopAnchor(runStatsText, 400.0);

        //Runstats textfield
        runStatsTextField = new TextField();
        runStatsTextField.setAlignment(Pos.CENTER);
        runStatsTextField.setPromptText("Run Stats");

        anchorPane.getChildren().add(runStatsTextField);
        AnchorPane.setBottomAnchor(runStatsTextField, 70.0);
        AnchorPane.setLeftAnchor(runStatsTextField, 10.0);

        //Runstats button
        Button runStatsButton = new Button("Run");
        runStatsButton.addEventHandler(MouseEvent.MOUSE_RELEASED, runStatsButtonHandler);

        anchorPane.getChildren().add(runStatsButton);
        AnchorPane.setBottomAnchor(runStatsButton, 70.0);
        AnchorPane.setLeftAnchor(runStatsButton, 170.0);


        anchorPane.getChildren().add(controlPanel);
        AnchorPane.setLeftAnchor(controlPanel, 10.0);
        AnchorPane.setTopAnchor(controlPanel, 10.0);

        //Scene
        Scene scene = new Scene(anchorPane, window_width, window_height);
        //scene.getStylesheets().add(myPackage + "/bg_colors.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void removeCritter(GridPane gridPane, int columnIndex, int rowIndex){
        for(int i = 0; i < gridPane.getChildren().size(); i++){
            Node child = gridPane.getChildren().get(i);
            if(GridPane.getColumnIndex(child) == columnIndex && GridPane.getRowIndex(child) == rowIndex){
                gridPane.getChildren().remove(child);
            }
        }
    }
    EventHandler<KeyEvent> critterNameTextHandler = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent keyEvent) {
            if(keyEvent.getCode() == KeyCode.BACK_SPACE && critterNameText.length() > 0){
                critterNameText = critterNameText.substring(0, critterNameText.length()-1);
            } else {
                if(keyEvent.isShiftDown()) {
                    critterNameText = critterNameText.concat(keyEvent.getText().toUpperCase());
                } else {
                    critterNameText = critterNameText.concat(keyEvent.getText());
                }
            }
        }
    };

    EventHandler<KeyEvent> critterNumberTextHandler = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent keyEvent) {
            if(keyEvent.getCode() == KeyCode.BACK_SPACE && critterNumberText.length() > 0){
                critterNumberText = critterNumberText.substring(0, critterNumberText.length()-1);
            } else {
                critterNumberText = critterNumberText.concat(keyEvent.getText());
            }
        }
    };

    /*
    public void critterNameText(KeyEvent keyEvent) {
	    if(keyEvent.getCode() == KeyCode.BACK_SPACE && critterNameText.length() > 0){
	        critterNameText = critterNameText.substring(0, critterNameText.length()-1);
        } else {
	        if(keyEvent.isShiftDown()) {
                critterNameText = critterNameText.concat(keyEvent.getText().toUpperCase());
            } else {
	            critterNameText = critterNameText.concat(keyEvent.getText());
            }
        }
        //Debug
	    //System.out.println(critterNameText);
    }
    */

    /*
    public void critterNumberText(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.BACK_SPACE && critterNumberText.length() > 0){
            critterNumberText = critterNumberText.substring(0, critterNumberText.length()-1);
        } else {
            critterNumberText = critterNumberText.concat(keyEvent.getText());
        }
        //Debug
        //System.out.println(critterNumberText);
    }
    */

    EventHandler<MouseEvent> makeCritterHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            int critterNumber = 0;
            try{
                critterNumber = Integer.parseInt(makeCritterNumber.getText());
            } catch (NumberFormatException e){
                consoleText = "Invalid number input";
                return;
            }
            try{
                for(int i = 0; i < critterNumber; i++){
                    Critter.makeCritter(makeCritterText.getText());
                }
            } catch (InvalidCritterException | NoClassDefFoundError e){
                consoleText = "error processing: ";
                consoleText = consoleText.concat(makeCritterText.getText() + " " + makeCritterNumber.getText());
            }

            updateConsole.handle(1);
        }
    };

    /*
    public void makeCritter(MouseEvent mouseEvent) {
	    int critterNumber = 0;
	    try{
	        critterNumber = Integer.parseInt(critterNumberText);
        } catch (NumberFormatException e){
	        System.out.println("Invalid number input");
        }
        try{
	        for(int i = 0; i < critterNumber; i++){
	            Critter.makeCritter(critterNameText);
            }
        } catch (InvalidCritterException e){
            System.out.print("error processing: ");
            System.out.println(critterNameText + critterNumberText);
        }
    }
    */

    EventHandler<MouseEvent> doWorldTimeStepHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            int timestepNumber = 0;
            try{
                timestepNumber = Integer.parseInt(Main.this.timestepNumber.getText());
            } catch (NumberFormatException e){
                consoleText = "Invalid number input";
                return;
            }
            for(int i = 0; i < timestepNumber; i++) {
                Critter.worldTimeStep();
            }
            updateWorld.handle(1);
            updateConsole.handle(1);
        }
    };

    EventHandler<MouseEvent> seedButtonHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            long seedNumber = 0;
            try{
                seedNumber = Long.parseLong(Main.this.seedNumber.getText());
            } catch (NumberFormatException e){
                consoleText = "Invalid number input";
                return;
            }
            Critter.setSeed(seedNumber);
        }
    };



    EventHandler<KeyEvent> timestepNumberHandler = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent keyEvent) {
            if(keyEvent.getCode() == KeyCode.BACK_SPACE && timestepNumberText.length() > 0){
                timestepNumberText = timestepNumberText.substring(0, timestepNumberText.length()-1);
            } else {
                timestepNumberText = timestepNumberText.concat(keyEvent.getText());
            }
        }
    };

    EventHandler<MouseEvent> runStatsButtonHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            try {
                Class<?> statsClass = Class.forName(myPackage + "." + runStatsTextField.getText());
                Critter statsObj = (Critter) statsClass.newInstance();
                java.lang.reflect.Method runStats = statsObj.getClass().getDeclaredMethod("runStats", java.util.List.class);
                java.util.List<Critter> theseCritters = Critter.getInstances(runStatsTextField.getText());
                Object retString = runStats.invoke(statsObj, theseCritters);
                runStatsTextReturn = (String) retString;
            }catch(ArrayIndexOutOfBoundsException | InstantiationException | java.lang.reflect.InvocationTargetException | IllegalAccessException | ClassNotFoundException | InvalidCritterException e){
                runStatsTextReturn = "error processing: " + runStatsTextField.getText();
            }catch(NoSuchMethodException e){
                try{
                    Class<?> statsClass = Class.forName(myPackage + "." + runStatsTextField.getText());
                    Critter statsObj = (Critter) statsClass.newInstance();
                    runStatsTextReturn = Critter.runStats(Critter.getInstances(runStatsTextField.getText()));
                } catch(Exception a) {
                    runStatsTextReturn = "error processing: " + runStatsTextField.getText();
                }
            }

            updateStats.handle(1);
        }
    };

    AnimationTimer updateStats = new AnimationTimer() {
        @Override
        public void handle(long now) {
            runStatsText.setText(runStatsTextReturn);
        }
    };

    AnimationTimer updateConsole = new AnimationTimer() {
        @Override
        public void handle(long now) {
            runStatsText.setText(consoleText);
            consoleText = "";
        }
    };

    AnimationTimer updateWorld = new AnimationTimer() {
        @Override
        public void handle(long now) {
            Critter.displayWorld(gridPane);
        }
    };
}
