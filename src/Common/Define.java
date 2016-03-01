package Common;

import Object.Position;
import Object.ValueType;

public class Define {
	private static Define define;
	private Define(){}
	public static Define getInstance(){
		if(define==null){
			synchronized (Define.class) {
				if(define==null)
					define = new Define();
			}
		}
		return define;
	}
	
	/* ---------------------------------------  Gear ---------------------------------*/
	public static final int HEAD=0 ; 
	public static final int CHEST=1 ; 
	public static final int HANDS=2; 
	public static final int LEG= 3; 
	public static final int BOOTS= 4; 
	public static final int WEAPON_L= 5; 
	public static final int WEAPON_R= 6; 
	public static final int RING= 7; 
	public static final String[] PARTS_NAME = {"HEAD","CHEST","HANDS","LEG","BOOTS","WEAPON_LEFT","WEAPON_RIGHT","RING"};
	
	public static final Position[] GEAR_POSITIONS = {
		new Position(150, 20), //HEAD
		new Position(150, 120), //CHEST
		new Position(50, 150), //HANDS
		new Position(150,250), //LEG
		new Position(150, 350), //BOOTS
		new Position(250, 270), //WEAPON_L
		new Position(50,270), //WEAPON_R
		new Position(250, 150) //RING
	};

	public static final int BOX = 65; 
	
	/* ---------------------------------------  Attribute ---------------------------------*/
	
	public static final int[] LEVELS = {0,100,200,350,750,1500,2500,4000,7500,10000};
	
	
	public static final int INT_TYPE= 0; 
	public static final int FLOAT_TYPE= 1; 
	public static final int STRINF_TYPE= 2; 
	public static final int BOOLEAN_TYPE= 3; 
	
	public static final ValueType[] TYPE_DEFINE = {
		new ValueType("Integer", INT_TYPE),
		new ValueType("Float", FLOAT_TYPE),
		new ValueType("String", STRINF_TYPE),
		new ValueType("Boolean", BOOLEAN_TYPE)
	};
	public static final Class[] TYPE_OBJECT_CLASS ={
		Integer.class,Float.class,String.class,Boolean.class
	};
	public static final Object[] TYPE_OBJECT_Instance ={
		new Integer(1),new Float(1),new String("1"),new Boolean(true)
	};
	
	public static final int HP=0 ; 
	public static final int HP_MAX=1 ; 
	public static final int Mana=2; 
	public static final int Mana_MAX= 3; 
	public static final int EXP= 4; 
	public static final int Level= 5; 
	public static final int AD= 6; 
	public static final int AP= 7; 
	public static final int Armor= 8; 
	public static final int MR= 9; 
	public static final int alive= 10; 
	public static final int Money= 11; 
	public static final int RoleName = 12;
	public static final int Speed = 13;
	
	public static final int[] ATTRI_TYPE = {
		FLOAT_TYPE,	//HP
		FLOAT_TYPE,	//HP_MAX
		FLOAT_TYPE,	//MANA
		FLOAT_TYPE,	//MANA_MAX
		INT_TYPE,	//EXP
		INT_TYPE,	//LEVEL
		FLOAT_TYPE,	//AD
		FLOAT_TYPE,	//AP
		FLOAT_TYPE,	//ARMOR
		FLOAT_TYPE,	//MR
		BOOLEAN_TYPE,//ALIVE
		FLOAT_TYPE,	//MOney
		STRINF_TYPE,//RoleName
		FLOAT_TYPE //Speed
	};
	public static final String[] ATTRI_NAME={
		"HP",
		"HP_MAX",
		"Mana",
		"Mana_MAX",
		"EXP",
		"Level",
		"AD",
		"AP",
		"Armor",
		"MR",
		"alive",
		"Money",
		"RoleName ",
		"Speed "
	};
	/* ---------------------------------------  Role Class ---------------------------------*/
	public static final int ORIGIN=0; 
	public static final int Warrior=1; 
	public static final int Wizard=2; 
	public static final int Archer= 3; 
	public static final int Knight= 4; 
	public static final int Ranger= 5; 
	public static final int Priest= 6; 

	public static final String[] CLASS_TYPE = {
		"ORIGIN","Warrior","Wizard","Archer","Knight","Ranger","Priest"
	};
	
	/* ---------------------------------------  Action ---------------------------------*/
	public static final int PRE_ACTION = 0x100;
	public static final int IN_ACTION = 0x010;
	public static final int AFTER_ACTION = 0x001;
	
	public static final int DO_ALL_ACTION = 0x111;
	public static final int DO_PRE_IN_ACTION = 0x110;
	public static final int DO_PRE_AFTER_ACTION = 0x101;
	public static final int DO_IN_AFTER_ACTION = 0x011;
	
	
	public static final int ACTION_ATTACK = 1;
	public static final int ACTION_SKILL = 2;
	public static final int ACTION_ITEM = 3;
	public static final int ACTION_DEFENCE = 4;
	public static final int ACTION_SKIP = 5;
	public static final int ACTION_RUN_AWAY = 6;
	
	public static final int SELECT_NOTHING = -1;
	/* ---------------------------------------  MASK ---------------------------------*/	
	public static final int MASK_ACTION_ID = 0x0000000f;
	public static final int MASK_SOURCE_ROLE_ID= 0x000000f0;
	public static final int MASK_TARGET_ROLE_ID1 = 0x00000f00;
	public static final int MASK_TARGET_ROLE_ID2= 0x0000f000;
	public static final int MASK_SELECT_ID= 0x00ff0000;
	/* ---------------------------------------  Battle ---------------------------------*/
	public static final int WAIT = 1;
	public static final int ACTION = 2 ;
	public static final int DEFINCE = 3;
	public static final int RUNAWAY = 4;
	public static final int STUNNED = 5;
	public static final int SLEEP = 6;
	public static final int IMMUE = 7;
	
	/* ---------------------------------------  SKILL ---------------------------------*/
	public static final int SELECT_SELF=0;
	public static final int SELECT_ENEMY=1;
	public static final int SELECT_ALLIES=2;
	public static final int SELECT_ENEMY_AND_ALLIES=3;
	public static final int SELECT_ENEMY_AND_SELF = 4;
	public static final int SELECT_ALLIES_ONLY = 5;
	public static final int SELECT_PASSIVE = 6;
	
	
	public static final String[] SELECT_TYPE = {"SELECT_SELF","SELECT_ENEMY","SELECT_ALLIES","SELECT_ENEMY_AND_ALLIES","SELECT_ENEMY_AND_SELF","SELECT_ALLIES_ONLY"};
	
	public static final int SKILL_SINGLE = 0; // type
	public static final int SKILL_GROUP = 1 ;
	public static final int SKILL_ENVIRONMENT = 2;
	public static final int SKILL_AURA = 3;
	public static final int SKILL_ACTIVE = 4;
	
	public static final String[] TARGET_TYPE = {"SKILL_SINGLE","SKILL_GROUP","SKILL_ENVIRONMENT","SKILL_AURA","SKILL_ACTIVE"};
	
	public static final int HP_EXTENDTYPE=0 ; 
	public static final int HP_MAX_EXTENDTYPE=1 ; 
	public static final int Mana_EXTENDTYPE=2; 
	public static final int Mana_MAX_EXTENDTYPE= 3; 
	public static final int Level_EXTENDTYPE= 5; 
	public static final int AD_EXTENDTYPE= 6; 
	public static final int AP_EXTENDTYPE= 7; 
	public static final int Armor_EXTENDTYPE= 8; 
	public static final int MR_EXTENDTYPE= 9; 
	public static final int Speed_EXTENDTYPE = 13;
	public static final String[] SCALE_TYPE = { "HP_EXTENDTYPE","HP_MAX_EXTENDTYPE","Mana_EXTENDTYPE","Mana_MAX_EXTENDTYPE","","Level_EXTENDTYPE","AD_EXTENDTYPE","AP_EXTENDTYPE","Armor_EXTENDTYPE","MR_EXTENDTYPE","","","","Speed_EXTENDTYPE"};
	
	public static final int PHYSICAL_DAMAGE = 0;
	public static final int MAGIC_DAMAGE = 1;
	public static final int TRUE_DAMAGE = 2;
	public static final int HEAL_DAMAGE = 3;
	public static final int NONE_DAMAGE = 4;
	public static final String[] DAMAGE_TYPE = {"Physical","Magic","True","Heal","None"};
	
	public static final int PHY_ATTACK_ACTION = 1;
	public static final int MAG_ATTACK_ACTION = 2;
	public static final int HEAL_ACTION = 3;
	public static final int DEATH_ACTION = 4;
	public static final int CONTROL_ACTION = 5;
	
	public static final int DAMAGE_SKILL_ATTRIBUTE = 0;
	public static final int HEAL_SKILL_ATTRIBUTE = 1;
	public static final int MANA_ELIMINATE_SKILL_ATTRIBUTE = 2;
	public static final int MANA_GENERATE_SKILL_ATTRIBUTE = 3;
	public static final int DAMAGE_PERC_SKILL_ATTRIBUTE = 4;
	public static final int HEAL_PERC_SKILL_ATTRIBUTE = 5;
	public static final int MANA_ELIMINATE_PERC_SKILL_ATTRIBUTE = 6;
	public static final int MANA_GENERATE_SKILL_PERC_ATTRIBUTE = 7;
	public static final int STUN_SKILL_ATTRIBUTE = 8;
	public static final int SLEEP_SKILL_ATTRIBUTE = 9;
	public static final int MUTE_SKILL_ATTRIBUTE = 10;
	public static final int IMMUNE_SKILL_ATTRIBUTE = 11;
	public static final String[] SKILL_ATTRIBUTE_TYPE = {"DAMAGE_SKILL_ATTRIBUTE","HEAL_SKILL_ATTRIBUTE","MANA_ELIMINATE_SKILL_ATTRIBUTE","MANA_GENERATE_SKILL_ATTRIBUTE","DAMAGE_PERC_SKILL_ATTRIBUTE","HEAL_PERC_SKILL_ATTRIBUTE","MANA_ELIMINATE_PERC_SKILL_ATTRIBUTE","MANA_GENERATE_SKILL_PERC_ATTRIBUTE","STUN_SKILL_ATTRIBUTE","SLEEP_SKILL_ATTRIBUTE","MUTE_SKILL_ATTRIBUTE","IMMUNE_SKILL_ATTRIBUTE"};
	
	public static final int TRIGGER_ATTACK =1;
	public static final int TRIGGER_DEFENCE =2;
	public static final int TRIGGER_CASTSKILL =4;
	public static final int TRIGGER_SKILL_APPLY =8;
	public static final int TRIGGER_BEEN_ATTACK =16;
	public static final int TRIGGER_BEEN_HEAL =32;
	public static final int TRIGGER_MOVE =64;
	public static final int TRIGGER_COOLDOWN =128;
	public static final int TRIGGER_SKIP =256;
	public static final int TRIGGER_DIE =512;
	public static final int TRIGGER_RESURRECTION =1024;
	public static final int TRIGGE_USE_ITEM =2048;
	public static final int TRIGGE_SKILL_AREA_EFFACT =4096;
	public static final int TRIGGE_BUFF =8192;
	public static final int TRIGGE_DEBUFF =16384;
	public static final int TRIGGE_DOT =32768;
//	public static final int TRIGGE_ =65536;
//	public static final int TRIGGE_ =131072;
//	public static final int TRIGGE_ =262144;
	
	/* ---------------------------------------  EFFECT ---------------------------------*/
	public static final int NOT_TRIGGER = 0;
	public static final int MODIFY_VALUE = 1;
	public static final int ELIMINATE_ACTION=2;
	public static final int IMMUE_ACTION = 3;
	/* ---------------------------------------  GAME STAGE ---------------------------------*/
	public static final int GAME_INITIAL =0;
	public static final int GAME_START =1;
	public static final int GAME_MENU =2;
	public static final int GAME_PLAY =3;
	public static final int GAME_CLOSE =4;
	/* ---------------------------------------  MAP ---------------------------------*/
	
	public static final String[] MAP_Land_TYPE={"Land","Grass","Water","Tree","Snow","Mountain","Rock","Wall"}; 
	public static final String[] MAP_Object_TYPE = {"Tree_obj", "Rock_obj","Grass_obj","Weapon_obj","Well_obj","Sign_obj","General_obj","House_obj","Castel_obj","Window_obj"};
	
}


//(	public static final int )([A-Z_a-z]*)( *=.*)(\n*)