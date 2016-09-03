package sample.View;

import javafx.event.ActionEvent;
import javafx.stage.Stage;

/**
 * Created by jeffryporter on 9/2/16.
 */
public class ViewSnippet
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
