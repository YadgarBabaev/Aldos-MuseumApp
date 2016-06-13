package kg.aldos.museumapp.items;

public class Exhibition {

    private int id;
    private String title;
    private String text;
    private byte[] image;

    public Exhibition(int id, String title, String text, byte[] image) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.image = image;
    }

    public int getId() {return id;}

    public String getTitle() {return title;}

    public String getText() {return text;}

    public byte[] getImage(){return image;}
}
