import java.io.IOException;
import java.util.ArrayList;

import javax.net.ssl.SSLContext;
import javax.swing.JFrame;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import Battle.Action;
import Battle.AttactAction;
import Battle.BasicBattle;
import Battle.Battle;
import Battle.BattleState;
import Common.Define;
import Data.DOMReader;
import Data.DAO.RoleDAO;
import Data.DAO.SkillDAO;
import GUI.GUIFrame;
import Object.Player;
import Object.Role;
import Object.Skill;
import Object.SkillAttribute;
import Object.Imp.SingleTargetSkill;


public class Driver {
	public static void main(String[] args) {
		//new Driver().init();
		new Driver().init2();
	}

	private void init2() {
		GUIFrame guiFrame = GUIFrame.getGUI();
	}

	private void init() {
		
//			DOMReader domReader = new DOMReader(Player.id);
		Role r1 =new RoleDAO().getRoleById(1);
		r1.toPrint();
		Role r2 =new RoleDAO().getRoleById(2);
		r2.toPrint();
		ArrayList<Role> g1 = new ArrayList<Role>();
		ArrayList<Role> g2 = new ArrayList<Role>();
		g1.add(r1);
		g2.add(r2);
		
		Battle battle =new BasicBattle(g1,g2);
		Skill s = new SingleTargetSkill();
		s.name = "Power Attack";
		s.cooldown = 3;
		s.manacost = 220;
		s.to_cooldown = 0;
		s.targetType = Define.SKILL_SINGLE;
		s.selectType =  Define.SELECT_ENEMY; 
		s.triggerChance = 0.25f;
		s.triggerType = Define.TRIGGER_ATTACK;
		
		SkillAttribute sa1 = new SkillAttribute();
		sa1.base = 125;
		sa1.damageType = Define.PHYSICAL_DAMAGE;
		sa1.scale = 0.4f;
		sa1.scaleType = Define.AD_EXTENDTYPE;
		sa1.valueType = 0;

		ArrayList<SkillAttribute> skillAttributes = new ArrayList<SkillAttribute>();
		skillAttributes.add(sa1);
		s.setAttributeList(skillAttributes);
		ArrayList<Skill> skills = new ArrayList<Skill>();
		
		skills.add(s);
		r2.setSkills(skills);
		
		
		
		new Thread(battle).start();
		
	}

	
}
