package sample.Controller;

import jodd.json.JsonParser;
import sample.Model.CodeSection;

import jodd.json.JsonSerializer;
import sample.Model.SnippetLibrary;

import java.io.*;


import java.util.ArrayList;
import java.util.Scanner;


/**
 * Created by jeffryporter on 8/30/16.
 */
public class Controller
{

    static SnippetLibrary theSnippetLibrary = SnippetLibrary.getTheSnippetLibrary();
    public static final String FILENAME = "snippets.json";
    public static final String FILE_LOCATION = System.getProperty("user.home") + File.separator;
    private static ArrayList<String> languages;

    //initial load from JSON file
    public static void initialLoad() throws FileNotFoundException
    {
        File f = new File((FILE_LOCATION + FILENAME));
        if (f.canRead())
        {
            Scanner fileScanner = new Scanner(f);
            String json = fileScanner.nextLine();
            JsonParser p = new JsonParser();
            theSnippetLibrary = p.parse(json, SnippetLibrary.class);
        }
        if(theSnippetLibrary.hasData())
        {
            int highest = 0;
            for(CodeSection c : theSnippetLibrary.getSnippets())
            {
                if(c.getId()>highest)
                {
                    highest = c.getId();
                }
            }
            highest++;
            CodeSection.setCounter(highest);
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
        File f = new File((FILE_LOCATION + FILENAME));
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
        saveFile();
        if(snippet.toString().equals(theSnippetLibrary.getSnippetById(snippet.getId()).toString()))
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
        ArrayList<String> keywordsList = new ArrayList<>();
        ArrayList<CodeSection> removalList = new ArrayList<>();
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

        //inclusive Search
        for (CodeSection code : theSnippetLibrary.getSnippets())
        {

            if (tags.length() > 0)
            {
                for (String t : tagsList)
                {
                    if(code.getTags().contains(t))
                    {
                        codeSearch.add(code);
                    }
                }
            }

            if ((code.getWriter().equalsIgnoreCase(author) && !author.isEmpty()) ||
                (code.getLanguage().equalsIgnoreCase(lang) && !lang.isEmpty()) ||
                (code.getSnippet().contains(keywords) && !keywords.isEmpty()) ||
                (code.getComments().contains(keywords) && !keywords.isEmpty()))
            {
                if (!codeSearch.contains(code))
                {
                    codeSearch.add(code);
                }
            }
        }

        //exclusive search
        if (!isInclusive)
        {
            for(CodeSection code : theSnippetLibrary.getSnippets())
            {
                if (tags.length() > 0)
                {
                    for (String t : tagsList)
                    {
                        if (code.getTags().contains(t.toLowerCase()))
                        {
                            if (!codeSearch.contains(code))
                            {
                                codeSearch.add(code);
                            }
                        }
                        else
                        {
                            if (!removalList.contains(code) && codeSearch.contains(code))
                            {
                                removalList.add(code);
                            }
                        }
                    }
                }

                if(author.length() > 0)
                {
                    if(code.getWriter().equalsIgnoreCase(author))
                    {
                        if (!codeSearch.contains(code))
                        {
                            codeSearch.add(code);
                        }
                    }
                    else
                    {
                        if (!removalList.contains(code) && codeSearch.contains(code))
                        {
                            removalList.add(code);
                        }
                    }
                }

                if(lang.length() > 0)
                {
                    if(code.getLanguage().equalsIgnoreCase(lang))
                    {
                        if (!codeSearch.contains(code))
                        {
                            codeSearch.add(code);
                        }
                    }
                    else
                    {
                        if (!removalList.contains(code) && codeSearch.contains(code))
                        {
                            removalList.add(code);
                        }
                    }
                }

                if(keywords.length() > 0)
                {
                    String fields[] = tags.split(" ");
                    for(String f:fields)
                    {
                        keywordsList.add(f);
                    }
                    for (String k : keywordsList)
                    {

                        if (code.getSnippet().contains(k) || code.getComments().contains(k))
                        {
                            if (!codeSearch.contains(code))
                            {
                                codeSearch.add(code);
                            }
                        }
                        else
                        {
                            if (!removalList.contains(code) && codeSearch.contains(code))
                            {
                                removalList.add(code);
                            }
                        }
                    }
                }
                if (tags.length()==0 && author.length()==0 && lang.length()==0 && keywords.length()==0)
                {
                    removalList.add(code);
                }
            }
        }
        for (CodeSection r : removalList)
        {
            codeSearch.remove(r);
        }
        return codeSearch;
    }

    //create an arraylist of languages
    public static ArrayList<String> getAllLanguages()
    {
        languages = new ArrayList<>();
        String[] langList = {"java", "javascript", "php", "c", "c++", "c#", "angular.js", "node.js", "android studio",
                "sql", "clojure", "python", "ruby", "react.js", "springMVC", ".NET"};
        for(String l: langList)
        {
            languages.add(l);
        }
        if (theSnippetLibrary.hasData())
        {
            ArrayList<String> snipLangList = theSnippetLibrary.getLanguages(languages);
            if (snipLangList.size()<theSnippetLibrary.getLanguages(languages).size())
            {
                for (String lang : snipLangList)
                {
                    languages.add(lang);
                }
            }
        }
        return languages;
    }

    //delete snippet
    public static boolean deleteSnippet(CodeSection snippet)
    {
        CodeSection codeRemoved = theSnippetLibrary.removeSnippet(snippet);
        saveFile();
        if (codeRemoved.getId() == snippet.getId())
        {
            return true;
        }
        return false;
    }

}
