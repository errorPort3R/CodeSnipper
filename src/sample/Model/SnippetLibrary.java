package sample.Model;

import java.util.ArrayList;

/**
 * Created by jeffryporter on 8/30/16.
 */
public class SnippetLibrary
{
    public static SnippetLibrary theSnippetLibrary = new SnippetLibrary();
    private ArrayList<CodeSection> snippets = new ArrayList<>();

    private SnippetLibrary()
    {
    }

    public static SnippetLibrary getTheSnippetLibrary()
    {
        if (theSnippetLibrary == null)
        {
            theSnippetLibrary = new SnippetLibrary();
        }
        return theSnippetLibrary;
    }

    public void addSnippet(CodeSection snippet)
    {
        snippets.add(snippet);
    }

    public CodeSection removeSnippet(CodeSection snippet)
    {
        CodeSection code = snippets.get(snippets.indexOf(snippet));
        snippets.remove(snippet);
        return code;
    }

    public void updateSnippet(CodeSection snippet)
    {
        snippets.set(snippets.indexOf(snippet), snippet);
    }

    public boolean hasData()
    {
        if (snippets.isEmpty())
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public ArrayList<String> getLanguages(ArrayList<String> langs)
    {
        for(CodeSection c : snippets)
        {
            if(!langs.contains(c.getLanguage()))
            {
                langs.add(c.getLanguage());
            }
        }
        return langs;
    }

    public ArrayList<CodeSection> getSnippets()
    {
        return snippets;
    }

    public CodeSection getSnippetById(Integer id)
    {
        for (CodeSection c : snippets)
        {
            if(c.getId() == id)
            {
                return c;
            }
        }
        return null;
    }

    @Override
    public String toString()
    {
        return "SnippetLibrary{" +
                "snippets=" + snippets +
                '}';
    }
}
