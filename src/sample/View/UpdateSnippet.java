package sample.View;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

/**
 * Created by jeffryporter on 9/2/16.
 */
public class UpdateSnippet implements EventHandler<ActionEvent>
{
    Stage theStage = new Stage();


    public void show()
    {
        theStage.show();
    }

    public void handle(ActionEvent event)
    {
        theStage.hide();
    }
}
