package sample.View;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.Controller.Controller;
import sample.Model.CodeSection;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by jeffryporter on 8/30/16.
 */
public class DisplayResults implements EventHandler<ActionEvent>
{
    private Stage theStage;
    private ListView<CodeSection> snippetList;
    private CodeSection snippet;
    private Button cancelBtn;
    private Label fNameLabel;
    private Label lNameLabel;
    private Label majorLabel;
    private Text fNameText;
    private Text lNameText;

    public class updateButtonHandler implements EventHandler<ActionEvent>
    {
        public void handle(ActionEvent event)
        {


            theStage.hide();
            UpdateSnippet updatepage = new UpdateSnippet();
            updatepage.show();
        }

    }

    public class viewButtonHandler implements EventHandler<ActionEvent>
    {
        public void handle(ActionEvent event)
        {

            theStage.hide();
            ViewSnippet updatepage = new ViewSnippet();
            updatepage.show();
        }

    }

    public class deleteButtonHandler implements EventHandler<ActionEvent>
    {
        public void handle(ActionEvent event)
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete");
            alert.setHeaderText("You are about to delete this code.");
            alert.setContentText("Are you sure?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK)
            {
                Controller.deleteSnippet(snippet);
            }
            else
            {
                alert.close();
            }
        }

    }

    public DisplayResults( Stage stage, ArrayList<CodeSection> codeSearchList)
    {

        theStage = new Stage();
        GridPane pane = new GridPane();
        Scene scene = new Scene(pane);
        theStage.setScene(scene);
        theStage.setTitle("Search Results");
        pane.setHgap(5);
        pane.setVgap(5);
        pane.setPadding(new Insets(10,10,10,10));


        ObservableList<CodeSection> olCodeSearchList = FXCollections.observableArrayList(codeSearchList);
        snippetList.setItems(olCodeSearchList);

        pane.add(snippetList, 0 ,0);

        lNameText = new Text(lName);
        lNameLabel = new Label("Last Name:");
        pane.add(lNameLabel, 0, 1);
        pane.add(lNameText, 1, 1);


        majorLabel = new Label("Major");
        pane.add(majorLabel, 0, 2);
        pane.add(majorCbo, 1, 2);

        saveBtn = new Button("Save");
        cancelBtn = new Button("Cancel");
        pane.add(saveBtn, 0, 3);
        pane.add(cancelBtn, 1, 3);

        saveBtn.setOnAction(new saveButtonHandler());
        cancelBtn.setOnAction(this);


        // roomsLV.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void show()
    {
        theStage.show();
    }

    public void handle(ActionEvent event)
    {
        theStage.hide();
    }
}
