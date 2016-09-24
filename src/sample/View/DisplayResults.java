package sample.View;

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

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Optional;



/**
 * Created by jeffryporter on 8/30/16.
 */

public class DisplayResults implements EventHandler<ActionEvent>
{
    private int STD_WIDTH = 800;
    private int STD_HEIGHT = 400;
    private Stage theStage;
    protected Stage selectionStage = null;


    private ArrayList<Button> viewBtnList;
    private ArrayList<Button> updateBtnList;
    private ArrayList<Button> deleteBtnList;
    private ArrayList<GridPane> snippetPaneList;
    private ArrayList<CodeSection> search;
    private CodeSection snippet = new CodeSection();

    public class updateButtonHandler implements EventHandler<ActionEvent>
    {
        private CodeSection mysnippet;
        public updateButtonHandler(CodeSection snippet)
        {
            mysnippet = snippet;
        }

        public void handle(ActionEvent event)
        {
            snippet = mysnippet;
            theStage.hide();
            UpdateSnippet updatepage = null;
            try
            {
                updatepage = new UpdateSnippet(theStage, mysnippet, search);
            } catch (FileNotFoundException e)
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("DANGER WILL ROBINSON!");
                alert.setHeaderText("Something went terribly wrong.");
                alert.setContentText("We're not sure what went wrong.  Honestly, you should never have seen this warning, but here we are.  Click okay to try again.");
                alert.showAndWait();
            }
            updatepage.show();
        }
    }

    public class viewButtonHandler implements EventHandler<ActionEvent>
    {
        private CodeSection mysnippet;
        public viewButtonHandler(CodeSection snippet)
        {
            mysnippet = snippet;
        }

        public void handle(ActionEvent event)
        {
            snippet = mysnippet;
            theStage.hide();
            ViewSnippet viewpage = null;
            try
            {
                viewpage = new ViewSnippet(theStage, mysnippet, search);
            } catch (FileNotFoundException e)
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("DANGER WILL ROBINSON!");
                alert.setHeaderText("Something went terribly wrong.");
                alert.setContentText("We're not sure what went wrong.  Honestly, you should never have seen this warning, but here we are.  Click okay to try again.");
                alert.showAndWait();
            }
            viewpage.show();
        }
    }

    public class deleteButtonHandler implements EventHandler<ActionEvent>
    {
        private CodeSection mysnippet;
        public deleteButtonHandler(CodeSection snippet)
        {
            mysnippet = snippet;
        }
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

                //selectionStage.hide();

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
        search = new ArrayList<>();
        search = codeSearchList;
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
        scene.getStylesheets().add("/sample/Resources/style.css");
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
        snippetPaneList = new ArrayList();
        int i = 0;

        for(CodeSection c: codeSearchList)
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
            String snippet =c.getSnippet();
            code.setText(snippet);
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
            insetPane.setPadding(new Insets (0,0,0,0));
            insetPane.add(buttonPane, 0, 0);
            insetPane.add(insetInnerPane, 1, 0);
            insetPane.add(code, 2, 0);
            insetPane.setGridLinesVisible(true);

            //build outset Pane
            outsetPane.setPrefSize(STD_WIDTH+40.0, STD_HEIGHT+40.0);
            outsetPane.add(insetPane, 0, i);
            snippetPaneList.add(insetPane);

            //add data to lists
            //viewBtnList.get(i).setUserData(c.getId());
            viewBtnList.get(i).setOnAction(new viewButtonHandler(c));
            updateBtnList.get(i).setOnAction(new updateButtonHandler(c));
            deleteBtnList.get(i).setOnAction(new deleteButtonHandler(c));

            //alternate colors
            i++;
            int j = i%2;
            insetPane.setStyle("-fx-background-color: white;");
            code.setStyle("text-area-background: white;");
            if(j == 1)
            {
                insetPane.setStyle("-fx-background-color: lavender;");
                code.setStyle("text-area-background: lavender;");
            }

            insetPane = new GridPane();
            insetInnerPane = new GridPane();

        }

        //add outsetPane to scrollPane
        scrollPane.setMinSize(STD_WIDTH, STD_HEIGHT);
        scrollPane.setContent(outsetPane);
        topPane.add(scrollPane, 0, 0);

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
        SelectionUI selectionUI;
        try
        {
            selectionUI = new SelectionUI(theStage);
            theStage.hide();
            selectionUI.show();

        } catch (FileNotFoundException e)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("DANGER WILL ROBINSON!");
            alert.setHeaderText("Something went terribly wrong.");
            alert.setContentText("We're not sure what went wrong.  Honestly, you should never have seen this warning, but here we are.  Click okay to try again.");
            alert.showAndWait();
        }


    }

    public CodeSection getSnippet()
    {
        return snippet;
    }
}
