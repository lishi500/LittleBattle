package Object.Imp;

import Object.Player;

public class PlayerImp extends Player{

	public PlayerImp(int id, String username, String nickname) {
		super(id, username, nickname);
		// TODO Auto-generated constructor stub
	}
	public PlayerImp(String username, String nickname, String email,
			String password){
		super(username, nickname,email,password);
		
	}
}
