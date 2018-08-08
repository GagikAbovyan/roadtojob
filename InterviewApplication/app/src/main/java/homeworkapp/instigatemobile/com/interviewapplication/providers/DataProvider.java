package homeworkapp.instigatemobile.com.interviewapplication.providers;

import java.util.ArrayList;
import java.util.List;

import homeworkapp.instigatemobile.com.interviewapplication.models.User;

public class DataProvider {
    private final static List<User> list = new ArrayList();

    public static List<User> getList() {
        return list;
    }

}
