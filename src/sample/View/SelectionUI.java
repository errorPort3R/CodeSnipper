package sample.View;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.Controller.Controller;
import sample.Model.CodeSection;
import sample.Model.SnippetLibrary;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by jeffryporter on 6/17/16.
 */
public class SelectionUI implements EventHandler<ActionEvent>
{
    private Stage selectionStage;
    private ComboBox language;
    private TextField newLanguage;
    private TextField author;
    private TextField tags;
    private TextField keywords;
    private TextArea code;
    private TextArea comments;
    private ToggleGroup searchGroup;
    private ObservableList<String> oListLanguages = FXCollections.observableArrayList(Controller.getAllLanguages());

    public class searchBtnHandler implements EventHandler<ActionEvent>
    {
        public void handle(ActionEvent event)
        {
            //get variables for search
            selectionStage.hide();
            boolean selectedInclusive = false;
            String lang = null;
            if (language.getValue().toString().equals("Language"))
            {
                language.setValue("");
            }
            if(!language.getValue().toString().isEmpty())
            {
                lang = language.getValue().toString();
            }
            else if (!newLanguage.getText().isEmpty())
            {
                lang = newLanguage.getText();
            }
            if (searchGroup.getSelectedToggle() != null)
            {
                selectedInclusive =  (Boolean) searchGroup.getSelectedToggle().getUserData();
            }

            ArrayList<CodeSection> searchedCode = Controller.searchSnippets(author.getText(), tags.getText(), keywords.getText(), lang, selectedInclusive);
            DisplayResults ds = new DisplayResults(searchedCode, selectionStage);
            ds.show();
        }
    }

    public class addSnippetHandler implements EventHandler<ActionEvent>
    {
        public void handle(ActionEvent event)
        {
            if (language.getValue() == null)
            {
                language.setValue("");
            }
            boolean valid = Controller.addSnippet(language.getValue().toString(),newLanguage.getText(), author.getText(), tags.getText(), keywords.getText(), code.getText(), comments.getText());
            //Add snippet if it's valid
            if(valid)
            {
                language.setPromptText("Language");
                if(newLanguage.getText().length() > 0)
                {
                    oListLanguages.add(newLanguage.getText());
                    Collections.sort(oListLanguages);
                }
                newLanguage.clear();
                author.clear();
                tags.clear();
                code.clear();
                comments.clear();
            }
            //Alertbox for a submission error.
            else
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR!!!!!");
                alert.setHeaderText("Not a valid Snippet Submission!");
                alert.setContentText("You must have code and a language selected.");
                alert.showAndWait();
            }
        }
    }

    public class addLanguageHandler implements EventHandler<ActionEvent>
    {
        public void handle(ActionEvent event)
        {
            if(!newLanguage.getText().toString().isEmpty())
            {
                oListLanguages.add(newLanguage.getText());
                Collections.sort(oListLanguages);
                newLanguage.clear();
            }
        }
    }

    public SelectionUI(Stage stage) throws FileNotFoundException
    {
        Controller.initialLoad();
        selectionStage = stage;
        GridPane pane = new GridPane();
        GridPane topInsertPane = new GridPane();
        GridPane bottomInsertPane = new GridPane();
        VBox radioBox = new VBox();
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        selectionStage.setTitle("Code Snippets");
        pane.setHgap(5);
        pane.setVgap(5);
        pane.setPadding(new Insets(10,10,10,10));
        topInsertPane.setHgap(5);
        topInsertPane.setVgap(5);
        topInsertPane.setPadding(new Insets(10,10,10,10));
        bottomInsertPane.setHgap(5);
        bottomInsertPane.setVgap(5);
        bottomInsertPane.setPadding(new Insets(10,10,10,10));

        //TOP INSERT PANE START
        Button searchBtn = new Button("Search");
        Button addBtn = new Button("Add Snippet");
        searchGroup = new ToggleGroup();
        RadioButton exclusiveBtn = new RadioButton("Exclusive");
        exclusiveBtn.setToggleGroup(searchGroup);
        exclusiveBtn.setSelected(true);
        exclusiveBtn.setUserData(false);
        exclusiveBtn.setStyle("-fx-font-size: 9;");
        RadioButton inclusiveBtn = new RadioButton("Inclusive");
        inclusiveBtn.setToggleGroup(searchGroup);
        inclusiveBtn.setUserData(true);
        inclusiveBtn.setStyle("-fx-font-size: 9;");

        radioBox.getChildren().add(exclusiveBtn);
        radioBox.getChildren().add(inclusiveBtn);

        Collections.sort(oListLanguages);
        language = new ComboBox(oListLanguages);
        language.setValue("Language");
        Button langPlusBtn = new Button("+");
        newLanguage = new TextField();
        newLanguage.setPromptText("New Language");
        topInsertPane.add(searchBtn, 0, 0);
        topInsertPane.add(radioBox, 1, 0);
        topInsertPane.add(addBtn, 4, 0);

        keywords = new TextField();
        keywords.setPromptText("Code Search Keywords");
        topInsertPane.add(keywords, 1, 4);
        topInsertPane.add(language, 1, 1);
        topInsertPane.add(langPlusBtn,2,1);
        topInsertPane.add(newLanguage, 3, 1);

        author = new TextField();
        author.setPromptText("Author");
        topInsertPane.add(author, 1, 2);

        tags = new TextField();
        tags.setPromptText("Tags");
        topInsertPane.add(tags, 1, 3);


        //TOP INSERT PANE END
        pane.add(topInsertPane, 0,1);

        code = new TextArea();
        code.setPromptText("Code");
        code.setMinWidth(300.0);
        bottomInsertPane.add(code, 0, 2);

        comments = new TextArea();
        comments.setPromptText("Comments");
        comments.setMaxWidth(150.0);
        bottomInsertPane.add(comments, 1, 2);

        pane.add(bottomInsertPane, 0,4);

        searchBtn.setOnAction(new searchBtnHandler());
        addBtn.setOnAction(new addSnippetHandler());
        langPlusBtn.setOnAction(new addLanguageHandler());

    }

    public void show()
    {
        selectionStage.show();
    }

    @Override
    public void handle(ActionEvent event)
    {
        selectionStage.close();
    }
}
