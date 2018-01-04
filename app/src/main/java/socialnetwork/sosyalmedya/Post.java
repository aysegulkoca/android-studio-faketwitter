package socialnetwork.sosyalmedya;

/**
 * Created by User on 21.12.2017.
 */

public class Post {

    private String author;
    private String postId;
    private String post;

    public Post() {
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public Post(String author, String post) {
        this.author = author;
        this.post = post;
    }
}


