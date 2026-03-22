class BookConstructor {

    private String ISBN;
    private String title;
    private String author;
    private String genre;
    private boolean availabilityStatus;

    // Setters

    public BookConstructor(String ISBN, String title, String author, String genre, boolean availabilityStatus) {
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.availabilityStatus = availabilityStatus;
    }

    //Getters

    public String getISBN() {
        return this.ISBN;
    }

    public String getTitle() {
        return this.title;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getGenre() {
        return this.genre;
    }

    public boolean getAvailabilityStatus() {
        return this.availabilityStatus;
    }

    // display Book

    public String displayBook() {
        return ISBN + ", " + title + ", " + author + ", " + genre + ", " + availabilityStatus;
    }
}
