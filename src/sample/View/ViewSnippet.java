package sample.View;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.Controller.Controller;

import java.io.FileNotFoundException;

/**
 * Created by jeffryporter on 9/2/16.
 */
public class ViewSnippet
{
    private Stage viewStage;
    private ComboBox language;
    private TextField newLanguage;
    private TextField author;
    private TextField tags;
    private TextField keywords;
    private TextArea code;
    private TextArea comments;
    private ToggleGroup searchGroup;
    ObservableList<String> oListLanguages = FXCollections.observableArrayList(Controller.getAllLanguages());


    public ViewSnippet(Stage stage) throws FileNotFoundException
    {
        Controller.initialLoad();
        viewStage = stage;
        GridPane pane = new GridPane();
        GridPane topInsertPane = new GridPane();
        GridPane bottomInsertPane = new GridPane();
        Scene scene = new Scene(pane);
        viewStage.setScene(scene);
        viewStage.setTitle("Snippet");
        pane.setVgap(5);
        pane.setPadding(new Insets(10, 10, 10, 10));
        topInsertPane.setHgap(5);
        topInsertPane.setVgap(5);
        topInsertPane.setPadding(new Insets(10, 10, 10, 10));
        bottomInsertPane.setHgap(5);
        bottomInsertPane.setVgap(5);
        bottomInsertPane.setPadding(new Insets(10, 10, 10, 10));

        //build top pane


        //build bottom pane
        

    }

    public void show()
    {
        viewStage.show();
    }

    public void handle(ActionEvent event)
    {
        viewStage.hide();
    }
}
