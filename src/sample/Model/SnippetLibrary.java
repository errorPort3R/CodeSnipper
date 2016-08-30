package sample.Model;

/**
 * Created by jeffryporter on 8/30/16.
 */
public class SnippetLibrary
{
    public static SnippetLibrary theSnippetLibrary = new SnippetLibrary();

    public static SnippetLibrary getSnippetLibrary()
    {
        return theSnippetLibrary;
    }

    private SnippetLibrary()
    {
    }


}
