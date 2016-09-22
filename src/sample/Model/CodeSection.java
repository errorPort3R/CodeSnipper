package sample.Model;

import java.util.ArrayList;

/**
 * Created by jeffryporter on 6/17/16.5
 */
public class CodeSection
{
    private Integer id;
    private String writer;
    private String language;
    private ArrayList<String> tags;
    private String snippet;
    private String comments;
    private static int counter = 10110;


    public CodeSection()
    {
    }

    public CodeSection(String writer, String language, ArrayList<String> tags, String snippet, String comments)
    {
        this.id = counter;
        this.writer = writer;
        this.language = language;
        this.tags = tags;
        this.snippet = snippet;
        this.comments = comments;
        counter++;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getWriter()
    {
        return writer;
    }

    public void setWriter(String writer)
    {
        this.writer = writer;
    }

    public String getLanguage()
    {
        return language;
    }

    public void setLanguage(String language)
    {
        this.language = language;
    }

    public ArrayList<String> getTags()
    {
        return tags;
    }

    public void setTags(ArrayList<String> tags)
    {
        this.tags = tags;
    }

    public String getSnippet()
    {
        return snippet;
    }

    public void setSnippet(String snippet)
    {
        this.snippet = snippet;
    }

    public String getComments()
    {
        return comments;
    }

    public void setComments(String comments)
    {
        this.comments = comments;
    }

    public static void setCounter(int lowestValue)
    {

        counter = lowestValue;
    }

    @Override
    public String toString()
    {
        return "CodeSection{" +
                "id=" + id +
                ", writer='" + writer + '\'' +
                ", language='" + language + '\'' +
                ", tags=" + tags +
                ", snippet='" + snippet + '\'' +
                ", comments='" + comments + '\'' +
                '}';
    }
}
