package sample.Controller;

import sample.Model.CodeSection;

import jodd.json.JsonSerializer;
import sample.Model.SnippetLibrary;

import java.io.File;


import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by jeffryporter on 8/30/16.
 */
public class Controller
{
    static SnippetLibrary theSnippetLibrary = SnippetLibrary.getTheSnippetLibrary();
    public static final String FILENAME = "snippets.json";

    public static void initialLoad()
    {

    }

    public static void saveFile()
    {
        JsonSerializer serializer = new JsonSerializer();
        String json = serializer.include("*").serialize(theSnippetLibrary);
        File f = new File(FILENAME);
        try
        {
            FileWriter fw = new FileWriter(f);
            fw.write(json);
            fw.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void editSnippets()
    {

    }

    public static ArrayList<CodeSection> searchSnippets(String tags, String Keywords, String Code, String Lang)
    {
        ArrayList<CodeSection> codeSearch = new ArrayList<>();


        return codeSearch;
    }
    public static ArrayList<String> getAllLanguages()
    {
        ArrayList<String> languages = new ArrayList<>();
        if(!theSnippetLibrary.hasData())
        {
            String[] langList = {"java", "javascript", "php", "c", "c++", "c#", "angular.js", "node.js", "android studio",
                    "sql", "clojure", "python", "ruby", "react.js", "springMVC", "hibernate"};
            for(String l: langList)
            {
                languages.add(l);
            }
        }
        else
        {

        }
        return languages;
    }



}
