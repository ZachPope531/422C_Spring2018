package assignment5;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

public class Main extends Application {

    private String critterNameText = "";
    private String critterNumberText = "";
    private String timestepNumberText = "";

	public static void main(String[] args) {
		launch(args);
	}

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Critters.fxml"));
        primaryStage.setTitle("Critters");
        //Scene scene = new Scene(root, 700, 420);
        GridPane gridPane = new GridPane();
        for(int i = 0; i < Params.world_width; i++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(300 / Params.world_width));
        }
        for(int i = 0; i < Params.world_height; i++){
            gridPane.getRowConstraints().add(new RowConstraints(300 / Params.world_height));
        }
        gridPane.hgapProperty().setValue(1.0);
        gridPane.vgapProperty().setValue(1.0);
        gridPane.gridLinesVisibleProperty().setValue(true);
        gridPane.setAlignment(Pos.CENTER);

        AnchorPane anchorPane = new AnchorPane(gridPane);
        AnchorPane.setRightAnchor(gridPane, 25.0);
        AnchorPane.setTopAnchor(gridPane, 60.0);
        Scene scene = new Scene(anchorPane, 700, 420);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

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

    public void critterNumberText(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.BACK_SPACE && critterNumberText.length() > 0){
            critterNumberText = critterNumberText.substring(0, critterNumberText.length()-1);
        } else {
            critterNumberText = critterNumberText.concat(keyEvent.getText());
        }
        //Debug
        //System.out.println(critterNumberText);
    }

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

    public void doWorldTimestep(MouseEvent mouseEvent) {
	    int timestepNumber = 0;
	    try{
	        timestepNumber = Integer.parseInt(timestepNumberText);
        } catch (NumberFormatException e){
	        System.out.println("Invalid number input");
        }
        for(int i = 0; i < timestepNumber; i++) {
            Critter.worldTimeStep();
        }
    }

    public void timestepNumberText(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.BACK_SPACE && timestepNumberText.length() > 0){
            timestepNumberText = timestepNumberText.substring(0, timestepNumberText.length()-1);
        } else {
            timestepNumberText = timestepNumberText.concat(keyEvent.getText());
        }
    }
}
