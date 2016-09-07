package sample.View;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxListCell;
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
    private ListView<CodeSection> snippetList = new ListView<>();
    ObservableList<CodeSection> olCodeSearchList;
    private CodeSection snippet;
    private Button viewBtn;
    private Button updateBtn;
    private Button deleteBtn;
    private Button cancelBtn;


    public class updateButtonHandler implements EventHandler<ActionEvent>
    {
        public void handle(ActionEvent event)
        {
            snippet= new CodeSection();
            snippet = snippetList.selectionModelProperty().getValue().getSelectedItem();
            Controller.editSnippets(snippet);
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

    public DisplayResults(ArrayList<CodeSection> codeSearchList)
    {
        theStage = new Stage();
        GridPane pane = new GridPane();
        Scene scene = new Scene(pane);
        theStage.setScene(scene);
        theStage.setTitle("Search Results");
        pane.setHgap(5);
        pane.setVgap(5);
        pane.setPadding(new Insets(10,10,10,10));

        olCodeSearchList = FXCollections.observableArrayList(codeSearchList);
        snippetList.setItems(olCodeSearchList);
        snippetList.setCellFactory(ComboBoxListCell.forListView(olCodeSearchList));

        pane.add(snippetList, 0 ,0);

        viewBtn = new Button("View");
        updateBtn = new Button("Edit");
        deleteBtn = new Button("Delete");
        cancelBtn = new Button("Go Back");
        pane.add(viewBtn, 0, 1);
        pane.add(updateBtn, 1, 1);
        pane.add(deleteBtn, 2, 1);
        pane.add(cancelBtn, 3, 1);

        viewBtn.setOnAction(new viewButtonHandler());
        updateBtn.setOnAction(new updateButtonHandler());
        deleteBtn.setOnAction(new deleteButtonHandler());
        cancelBtn.setOnAction(this);
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
