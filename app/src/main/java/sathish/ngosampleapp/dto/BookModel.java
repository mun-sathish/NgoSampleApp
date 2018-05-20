package sathish.ngosampleapp.dto;

import com.google.gson.annotations.SerializedName;

public class BookModel {

    @SerializedName("book_id")
    private String bookId;
    @SerializedName("name")
    private String name;
    @SerializedName("isbn")
    private String isbn;
    @SerializedName("author")
    private String author;
    @SerializedName("publisher")
    private String publisher;
    @SerializedName("edition")
    private String edition;
    @SerializedName("edition_number")
    private String editionNumber;
    @SerializedName("no_of_pages")
    private String noOfPages;
    @SerializedName("binding")
    private String binding;
    @SerializedName("flipkart_link")
    private String flipkartLink;
    @SerializedName("amazon_link")
    private String amazonLink;
    @SerializedName("banner")
    private String banner;

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getEditionNumber() {
        return editionNumber;
    }

    public void setEditionNumber(String editionNumber) {
        this.editionNumber = editionNumber;
    }

    public String getNoOfPages() {
        return noOfPages;
    }

    public void setNoOfPages(String noOfPages) {
        this.noOfPages = noOfPages;
    }

    public String getBinding() {
        return binding;
    }

    public void setBinding(String binding) {
        this.binding = binding;
    }

    public String getFlipkartLink() {
        return flipkartLink;
    }

    public void setFlipkartLink(String flipkartLink) {
        this.flipkartLink = flipkartLink;
    }

    public String getAmazonLink() {
        return amazonLink;
    }

    public void setAmazonLink(String amazonLink) {
        this.amazonLink = amazonLink;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

}