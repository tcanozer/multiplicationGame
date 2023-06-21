package application;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable{

	private static final long serialVersionUID = 1887867694527666449L;
	private String username;
	private String password;
	private boolean isAdmin;
    private List<Diary> diaries;


	public User(String username, String password, boolean isAdmin) {
		this.username = username;
		this.password = password;
		this.isAdmin = isAdmin;
        this.diaries = new ArrayList<>();

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	public void addDiary(Diary diary) {
        diaries.add(diary);
    }

    public List<Diary> getDiaries() {
        return diaries;
    }

}
