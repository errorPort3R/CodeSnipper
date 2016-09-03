package sample.Controller;

import jodd.json.JsonParser;
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
    private static ArrayList<String> languages;

    public static void initialLoad()
    {
        File f = new File(FILENAME);
        JsonParser p = new JsonParser();
        CodeSection codeSnippet = p.parse(body,CodeSection.class);
    }

    public static boolean addSnippet(String language, String newLanguage, String author, String tags, String keywords, String code, String comments)
    {
        boolean valid = false;
        ArrayList<String> tagsList = new ArrayList<>();

        //get spaced out tags and put in ArrayList
        if (tags.length() > 0)
        {
            String fields[] = tags.split(" ");
            for(String f:fields)
            {
                tagsList.add(f);
            }
        }

        //validate entry and add to library if it is valid
        if (newLanguage.isEmpty() && language.length() > 0 && code.length() > 0)
        {
            theSnippetLibrary.addSnippet(new CodeSection(author, language, tagsList, code, comments));
            valid = true;
            saveFile();
        }
        else if(!newLanguage.isEmpty() && language.length() == 0 && code.length() > 0)
        {
            theSnippetLibrary.addSnippet(new CodeSection(author, newLanguage, tagsList, code, comments));
            valid = true;
            saveFile();
        }
        return valid;
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

    public static boolean editSnippets()
    {
        boolean valid = false;
        return valid;
    }

    public static ArrayList<CodeSection> searchSnippets(String tags, String keywords, boolean isInclusive, String lang)
    {
        ArrayList<CodeSection> codeSearch = new ArrayList<>();


        return codeSearch;
    }

    public static ArrayList<String> getAllLanguages()
    {
        languages = new ArrayList<>();
        String[] langList = {"java", "javascript", "php", "c", "c++", "c#", "angular.js", "node.js", "android studio",
                "sql", "clojure", "python", "ruby", "react.js", "springMVC"};
        for(String l: langList)
        {
            languages.add(l);
        }
        if (theSnippetLibrary.hasData())
        {
            ArrayList<String> snipLangList = theSnippetLibrary.getLanguages(languages);
            for(String l: snipLangList)
            {
                languages.add(l);
            }
        }
        return languages;
    }



}
