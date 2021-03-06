package sample.View;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import sample.Model.CodeSection;

import java.io.FileNotFoundException;
import java.util.ArrayList;


/**
 * Created by jeffryporter on 9/2/16.
 */
public class ViewSnippet implements EventHandler<ActionEvent>
{
    private Stage viewStage;
    private ArrayList<CodeSection> search;


    public ViewSnippet(Stage stage, CodeSection snippet, ArrayList<CodeSection> searchResults) throws FileNotFoundException
    {
        search = new ArrayList<CodeSection>();
        search = searchResults;
        viewStage = stage;
        GridPane pane = new GridPane();
        GridPane topPane = new GridPane();
        GridPane bottomPane = new GridPane();
        GridPane buttonPane = new GridPane();
        Scene scene = new Scene(pane);
        viewStage.setScene(scene);
        viewStage.setTitle("Snippet");
        pane.setVgap(5);
        pane.setPadding(new Insets(10, 10, 10, 10));
        topPane.setHgap(5);
        topPane.setVgap(5);
        topPane.setPadding(new Insets(10, 10, 10, 10));
        bottomPane.setHgap(5);
        bottomPane.setVgap(5);
        bottomPane.setPadding(new Insets(10, 10, 10, 10));
        buttonPane.setHgap(5);
        buttonPane.setVgap(5);
        buttonPane.setPadding(new Insets(10, 10, 10, 10));


        //build top pane
        Label langLabel = new Label();
        Label authLabel = new Label();
        Label tagsLabel = new Label();
        Label commentLabel = new Label();
        langLabel.setText("Language:");
        topPane.add(langLabel,0 ,0);
        authLabel.setText("Author:");
        topPane.add(authLabel,0 ,1);
        tagsLabel.setText("Tags:");
        topPane.add(tagsLabel,0 ,2);
        commentLabel.setText("Comments:");
        topPane.add(commentLabel,0 ,3);

        TextField lang = new TextField();
        if(snippet.getLanguage() != null){lang.setText(snippet.getLanguage());}
        TextField author = new TextField();
        if(snippet.getWriter() != null){author.setText(snippet.getWriter());}
        TextField tags = new TextField();
        String tag =  "";
        if(snippet.getTags() != null)
        {
            for (String t : snippet.getTags())
            {
                tag = tag + t + " ";
            }
        }
        tags.setText(tag);
        TextArea comments = new TextArea();
        if(snippet.getComments() != null){comments.setText(snippet.getComments());}
        comments.setMaxHeight(50);
        topPane.add(lang,1 ,0);
        topPane.add(author, 1, 1);
        topPane.add(tags, 1, 2);
        topPane.add(comments, 1, 3);

        //build bottom pane
        TextArea code = new TextArea();
        code.setText(snippet.getSnippet());
        bottomPane.add(code, 0,0);
        Button cancelBtn = new Button("Cancel");
        buttonPane.add(cancelBtn, 0, 0);
        cancelBtn.setOnAction(this);
        bottomPane.add(buttonPane, 1, 0);
        //build page
        pane.add(topPane,0 ,0);
        pane.add(bottomPane, 0, 1);
    }

    public void show()
    {
        viewStage.show();
    }

    public void handle(ActionEvent event)
    {
        DisplayResults ds = new DisplayResults(search, viewStage);
        viewStage.hide();
        ds.show();
    }
}
