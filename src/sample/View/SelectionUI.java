package sample.View;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import javafx.stage.Stage;
import sample.Controller.Controller;
import sample.Model.SnippetLibrary;

import java.util.Collections;

/**
 * Created by jeffryporter on 6/17/16.
 */
public class SelectionUI implements EventHandler<ActionEvent>
{
    SnippetLibrary theSnippetLibrary = SnippetLibrary.getTheSnippetLibrary();
    private Stage selectionStage;
    private ComboBox language;
    private TextField newLanguage;
    private TextField author;
    private TextField tags;
    private TextField keywords;
    private TextArea code;
    private TextArea comments;
    ObservableList<String> oListLanguages = FXCollections.observableArrayList(Controller.getAllLanguages());


    public class searchBtnHandler implements EventHandler<ActionEvent>
    {
        public void handle(ActionEvent event)
        {
            Controller.addSnippet(language.getValue().toString(), author.getText(), tags.getText(), keywords.getText(), code.getText(), comments.getText());
            Controller.saveFile();
        }
    }

    public class addSnippetHandler implements EventHandler<ActionEvent>
    {
        public void handle(ActionEvent event)
        {

        }
    }

    public class addLanguageHandler implements EventHandler<ActionEvent>
    {
        public void handle(ActionEvent event)
        {
            oListLanguages.add(newLanguage.getText());
            Collections.sort(oListLanguages);
            newLanguage.clear();
        }
    }

    public SelectionUI(Stage stage)
    {
        selectionStage = stage;
        GridPane pane = new GridPane();
        GridPane topInsertPane = new GridPane();
        GridPane bottomInsertPane = new GridPane();
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
        Label lbl = new Label("Lang/Lib/Framework:");
        Collections.sort(oListLanguages);
        language = new ComboBox(oListLanguages);
        language.setPromptText("Select");
        Button langPlusBtn = new Button("+");
        newLanguage = new TextField();
        newLanguage.setPromptText("New Language");
        topInsertPane.add(lbl, 0, 1);
        topInsertPane.add(language, 1, 1);
        topInsertPane.add(langPlusBtn,2,1);
        topInsertPane.add(newLanguage, 3, 1);

        lbl = new Label("Author:");
        author = new TextField();
        topInsertPane.add(lbl, 0, 2);
        topInsertPane.add(author, 1, 2);

        lbl = new Label("Tags:");
        tags = new TextField();
        topInsertPane.add(lbl, 0, 3);
        topInsertPane.add(tags, 1, 3);

        lbl = new Label("Keywords:");
        keywords = new TextField();
        topInsertPane.add(lbl, 0, 4);
        topInsertPane.add(keywords, 1, 4);
        //TOP INSERT PANE END

        pane.add(topInsertPane, 0,1);

        code = new TextArea();
        code.setPromptText("Code");
        pane.add(code, 0, 2);

        comments = new TextArea();
        comments.setPromptText("Comments");
        pane.add(comments, 0, 3);

        Button searchBtn = new Button("Search");
        Button addBtn = new Button("Add");
        pane.add(searchBtn, 0, 0);
        bottomInsertPane.add(addBtn, 0, 0);

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
