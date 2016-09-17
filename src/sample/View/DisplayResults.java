package sample.View;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.Controller.Controller;
import sample.Model.CodeSection;

import java.lang.Character.UnicodeBlock;
import java.util.ArrayList;
import java.util.Optional;



/**
 * Created by jeffryporter on 8/30/16.
 */

public class DisplayResults implements EventHandler<ActionEvent>
{
    private Stage theStage;
    protected Stage selectionStage =null;
    private TableView<CodeSection> snippetList = new TableView<>();
    ObservableList<CodeSection> olCodeSearchList;
    private CodeSection snippet;
    private ArrayList<Button> viewBtnList;
    private ArrayList<Button> updateBtnList;
    private ArrayList<Button> deleteBtnList;
    private int currentId = 0;

    public void setID(String id)
    {
        currentId = Integer.valueOf(id);
    }

    public class updateButtonHandler implements EventHandler<ActionEvent>
    {
        public void handle(ActionEvent event)
        {
            snippet= new CodeSection();
            snippet = snippetList.selectionModelProperty().getValue().getSelectedItem();
            if(snippet == null)
            {
                //TODO alertbox
            }
            else
            {
                Controller.editSnippets(snippet);
                UpdateSnippet updatepage = new UpdateSnippet();
                updatepage.show();
            }
        }
    }

    public class viewButtonHandler implements EventHandler<ActionEvent>
    {
        public void handle(ActionEvent event)
        {
            theStage.hide();
            ViewSnippet viewpage = new ViewSnippet();
            viewpage.show();
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

    // start of displayResults page
    public DisplayResults(ArrayList<CodeSection> codeSearchList, Stage stage)
    {
        selectionStage = stage;
        theStage = new Stage();
        GridPane pane = new GridPane();
        GridPane topPane = new GridPane();
        GridPane bottomPane = new GridPane();
        pane.add(topPane, 0, 0);
        pane.add(bottomPane, 0, 1);
        ScrollPane scrollPane = new ScrollPane();
        GridPane insetPane = new GridPane();
        GridPane insetInnerPane =  new GridPane();
        GridPane outsetPane = new GridPane();
        GridPane buttonPane = new GridPane();
        buttonPane.setPadding(new Insets(10,10,10,10));
        Scene scene = new Scene(pane);
        theStage.setScene(scene);
        theStage.setTitle("Search Results");
        pane.setHgap(5);
        pane.setVgap(5);
        pane.setPadding(new Insets(10,10,10,10));
        bottomPane.setHgap(5);
        bottomPane.setVgap(5);
        viewBtnList = new ArrayList<>();
        updateBtnList = new ArrayList<>();
        deleteBtnList = new ArrayList<>();
        olCodeSearchList = FXCollections.observableArrayList(codeSearchList);
        int i = 0;

        for(CodeSection c: olCodeSearchList)
        {
            //create buttonPane
            buttonPane = new GridPane();
            buttonPane.setPadding(new Insets (10,10,10,10));
            buttonPane.setHgap(5);
            buttonPane.setVgap(5);

            //create view button
            Tooltip vtip = new Tooltip("View");
            Button viewBtn = new Button();
            Image eye = new Image(getClass().getResourceAsStream("/sample/Resources/eye.png"));
            ImageView eyeView = new ImageView(eye);
            eyeView.setFitHeight(15);
            eyeView.setFitWidth(15);
            viewBtn.setGraphic(eyeView);
            viewBtn.setId("" + c.getId());
            viewBtn.setTooltip(vtip);
            viewBtn.setMinWidth(40);
            viewBtn.setMaxWidth(40);

            //create update button
            Tooltip utip = new Tooltip("Update");
            Button updateBtn = new Button("✎");
            updateBtn.setId("" + c.getId());
            updateBtn.setTooltip(utip);
            updateBtn.setMinWidth(40);
            updateBtn.setMaxWidth(40);

            //create delete button
            Tooltip dtip = new Tooltip("Delete");
            Button deleteBtn = new Button("✗");
            deleteBtn.setId("" + c.getId());
            deleteBtn.setTooltip(dtip);
            deleteBtn.setMinWidth(40);
            deleteBtn.setMaxWidth(40);

            //add buttons to list
            viewBtnList.add(viewBtn);
            updateBtnList.add(updateBtn);
            deleteBtnList.add(deleteBtn);
            buttonPane.add(viewBtnList.get(i), 0, 0);
            buttonPane.add(updateBtnList.get(i), 0, 1);
            buttonPane.add(deleteBtnList.get(i), 0, 2);
            viewBtn.setOnAction(new viewButtonHandler());
            updateBtn.setOnAction(new updateButtonHandler());
            deleteBtn.setOnAction(new deleteButtonHandler());

            //create insetInnerPane
            Text id = new Text();
            id.setText("ID: " + c.getId());
            Text language = new Text();
            language.setText("Lang: " + c.getLanguage());
            Text tags = new Text();
            tags.setText("Tags: " + c.getTags());
            Text writer = new Text();
            writer.setText("Writer: " + c.getWriter());
            TextArea code = new TextArea();
            code.setText(c.getSnippet());
            insetInnerPane.setPadding(new Insets (0,0,0,10));
            insetInnerPane.add(id, 0, 0);
            insetInnerPane.add(language, 0, 1);
            insetInnerPane.add(tags, 0, 2);
            insetInnerPane.add(writer, 0, 3);

            //format inset pane
            ColumnConstraints column1 = new ColumnConstraints();
            column1.setPercentWidth(6);
            ColumnConstraints column2 = new ColumnConstraints();
            column2.setPercentWidth(20);
            ColumnConstraints column3 = new ColumnConstraints();
            column3.setPercentWidth(74);
            insetPane.getColumnConstraints().addAll(column1, column2, column3);

            //make inset pane
            insetPane.setMaxHeight(5);
            insetPane.setPadding(new Insets (0,0,0,10));
            insetPane.add(buttonPane, 0, 0);
            insetPane.add(insetInnerPane, 1, 0);
            insetPane.add(code, 2, 0);
            insetPane.setGridLinesVisible(true);
            outsetPane.setPrefSize(800, 400);
            outsetPane.add(insetPane, 0,i);

            //alternate colors
            i++;
            int j = i%2;
            insetPane.setStyle("-fx-background-color: white;");
            if(j == 1)
            {
                insetPane.setStyle("-fx-background-color: lavender;");
                code.setStyle("-fx-background-color: lavender;");
            }
            insetPane = new GridPane();
            insetInnerPane = new GridPane();
        }

        //scrollPane.setContent(outsetPane);
        topPane.add(outsetPane, 0 ,0);
        Button cancelBtn = new Button("Go Back");
        bottomPane.add(cancelBtn, 0, 0);
        cancelBtn.setOnAction(this);
    }

    public void show()
    {
        theStage.show();
    }

    public void handle(ActionEvent event)
    {
        selectionStage.show();
        theStage.hide();
    }
}
