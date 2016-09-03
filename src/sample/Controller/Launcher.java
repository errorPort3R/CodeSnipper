package sample.Controller;

import javafx.application.Application;
import javafx.stage.Stage;
import sample.View.SelectionUI;

import java.io.FileNotFoundException;

public class Launcher extends Application
{
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException
    {
        SelectionUI ui = new SelectionUI(primaryStage);
        ui.show();
    }

    public static void main(String[] args)
    {
        Application.launch(args);
    }

}
