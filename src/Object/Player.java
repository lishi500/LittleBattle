package Object;

import java.util.ArrayList;

public abstract class Player {
	public Player(String username, String nickname, String email,
			String password) {
		super();
		this.username = username;
		this.nickname = nickname;
		this.email = email;
		this.password = password;
	}
	public Player(int id, String username, String nickname) {
		super();
		this.id = id;
		this.username = username;
		this.nickname = nickname;
	}
	ArrayList<Role> roles;
	public int id;
	public String username;
	public String nickname;
	public String email;
	public String password;
	@Override
	public String toString() {
		return "Player [id=" + id + ", username=" + username + ", nickname="
				+ nickname + "]";
	}
	
}
