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

    public void addSnippet(CodeSection snip)
    {
        snippets.add(snip);
    }

    public CodeSection removeSnippet(int id)
    {
        CodeSection code = snippets.get(id);
        snippets.remove(id);
        return code;
    }

    public void updateSnippet(CodeSection snip)
    {
        snippets.set(snip.getId(), snip);
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
}
