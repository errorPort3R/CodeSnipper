package sample.Controller;

import jodd.json.JsonParser;
import sample.Model.CodeSection;

import jodd.json.JsonSerializer;
import sample.Model.SnippetLibrary;

import java.io.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


/**
 * Created by jeffryporter on 8/30/16.
 */
public class Controller
{

    static SnippetLibrary theSnippetLibrary = SnippetLibrary.getTheSnippetLibrary();
    public static final String FILENAME = "snippets.json";
    private static ArrayList<String> languages;

    //initial load from JSON file
    public static void initialLoad() throws FileNotFoundException
    {
        File f = new File(FILENAME);
        if (f.canRead())
        {
            Scanner fileScanner = new Scanner(f);
            String json = fileScanner.nextLine();
            JsonParser p = new JsonParser();
            theSnippetLibrary = p.parse(json, SnippetLibrary.class);
        }
    }

    //add a new snippet
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

    //save current state of theSnippetLibrary
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

    //edit a snippet
    public static boolean editSnippets(CodeSection snippet)
    {
        theSnippetLibrary.updateSnippet(snippet);
        if(snippet.toString().equals(theSnippetLibrary.getSnippetById(snippet.getId())))
        {
            return true;
        }
        return false;
    }

    //search snippets
    public static ArrayList<CodeSection> searchSnippets(String author, String tags, String keywords, String lang, boolean isInclusive)
    {
        ArrayList<CodeSection> codeSearch = new ArrayList<>();
        ArrayList<String> tagsList = new ArrayList<>();
        //check for null values
        if (author == null){author = "";}
        if (tags == null){tags = "";}
        if (keywords == null){keywords = "";}
        if (lang == null){lang = "";}

        if (tags.length() > 0)
        {
            String fields[] = tags.split(" ");
            for(String f:fields)
            {
                tagsList.add(f);
            }
        }

        if (isInclusive)
        {
            for (CodeSection code : theSnippetLibrary.getSnippets())
            {
                if (tags.length() > 0)
                {
                    for (String t : tagsList)
                    if (code.getWriter().equalsIgnoreCase(author) ||
                            code.getTags().contains(t.toLowerCase()) ||
                            code.getLanguage().equalsIgnoreCase(lang) ||
                            code.getSnippet().contains(keywords) ||
                            code.getComments().contains(keywords))
                    {
                        codeSearch.add(code);
                    }
                }
            }

        }
        else
        {
            for(CodeSection code : theSnippetLibrary.getSnippets())
            {
                if (tags.length() > 0)
                {
                    for (String t : tagsList)
                    {
                        if (code.getTags().contains(t.toLowerCase()))
                        {
                            codeSearch.add(code);
                        }
                    }
                }
                if(author.length() > 0)
                {
                    if(code.getWriter().equalsIgnoreCase(author))
                    {
                        codeSearch.add(code);
                    }
                    else
                    {
                        codeSearch.remove(code);
                    }
                }
                if(lang.length() > 0)
                {
                    if(code.getLanguage().equalsIgnoreCase(lang))
                    {
                        codeSearch.add(code);
                    }
                    else
                    {
                        codeSearch.remove(code);
                    }
                }
                if(keywords.length() > 0)
                {
                    if(code.getSnippet().equalsIgnoreCase(keywords) || code.getComments().equalsIgnoreCase(keywords))
                    {
                        codeSearch.add(code);
                    }
                    else
                    {
                        codeSearch.remove(code);
                    }
                }
            }
        }
        return codeSearch;
    }

    //create an arraylist of languages
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
            for(String lang: snipLangList)
            {
                languages.add(lang);
            }
        }
        return languages;
    }

    //delete snippet
    public static boolean deleteSnippet(CodeSection snippet)
    {
        CodeSection codeRemoved = theSnippetLibrary.removeSnippet(snippet.getId());
        if (codeRemoved.getId() == snippet.getId())
        {
            return true;
        }
        return false;
    }



}
