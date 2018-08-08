package homeworkapp.instigatemobile.com.interviewapplication.models;

public class User {
    private final String imageUrl;
    private final String name;
    private final String number;
    private final String email;


    public User(final String imageUrl, final String name,
                final String number, final String email) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.number = number;
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getEmail() {
        return email;
    }
}
