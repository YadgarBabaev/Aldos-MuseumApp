package kg.aldos.museumapp.items;

public class News {

    private int id;
    private String date;
    private String text;
    private byte[] cover;

    public News(int news_id, String news_date, String news_text, byte[] news_cover) {
        this.id = news_id;
        this.date = news_date;
        this.text = news_text;
        this.cover = news_cover;
    }

    public int getId() {return id;}

    public String getDate() {return date;}

    public String getText() {return text;}

    public byte[] getCover(){return cover;}
}
