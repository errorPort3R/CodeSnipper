package sample.View;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.Controller.Controller;
import sample.Model.CodeSection;

import java.util.ArrayList;
import java.util.Optional;

import static javafx.scene.input.KeyCode.H;

/**
 * Created by jeffryporter on 8/30/16.
 */
public class DisplayResults implements EventHandler<ActionEvent>
{
    private Stage theStage;
    private TableView<CodeSection> snippetList = new TableView<>();
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
        GridPane topPane = new GridPane();
        GridPane bottomPane = new GridPane();
        pane.add(topPane, 0, 0);
        pane.add(bottomPane, 0, 1);
        ScrollPane scrollPane = new ScrollPane();
        HBox Hbox1 = new HBox();
        GridPane insetPane = new GridPane();
        Hbox1.getChildren().add(0, insetPane);
        Scene scene = new Scene(pane);
        theStage.setScene(scene);
        theStage.setTitle("Search Results");
        pane.setHgap(5);
        pane.setVgap(5);
        pane.setPadding(new Insets(10,10,10,10));

        olCodeSearchList = FXCollections.observableArrayList(codeSearchList);
        ArrayList<Node> scrollListNodes = new ArrayList<>();
        int i = 0;
        for(CodeSection c: olCodeSearchList)
        {
            Text id = new Text();
            id.setText("ID: " + c.getId());
            Text language = new Text();
            id.setText("Lang: " + c.getLanguage());
            Text tags = new Text();
            id.setText("Tags: " + c.getTags());
            Text writer = new Text();
            id.setText("Writer: " + c.getWriter());
            Text code = new Text();
            id.setText(c.getSnippet());
            insetPane.add(id, 0, 0);
            insetPane.add(language, 1, 0);
            insetPane.add(tags, 2, 0);
            insetPane.add(writer, 3, 0);
            insetPane.add(code, 0, 1, 100, 4);
            scrollListNodes.add(i, insetPane);
            insetPane = new GridPane();
            i++;
            int j = i%2;
            insetPane.setStyle("-fx-background-color: white;");
            if(j == 0)
            {
                insetPane.setStyle("-fx-background-color: lightgrey;");
            }
            scrollPane.setContent(insetPane);
        }


        topPane.add(scrollPane, 0 ,0);

        viewBtn = new Button("View");
        updateBtn = new Button("Edit");
        deleteBtn = new Button("Delete");
        cancelBtn = new Button("Go Back");
        bottomPane.add(viewBtn, 0, 0);
        bottomPane.add(updateBtn, 1, 0);
        bottomPane.add(deleteBtn, 2, 0);
        bottomPane.add(cancelBtn, 3, 0);

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
