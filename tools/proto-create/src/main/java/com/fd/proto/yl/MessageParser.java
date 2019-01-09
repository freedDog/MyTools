/****************************************************************************
*                     U N R E G I S T E R E D   C O P Y
* 
* You are on day 3 of your 30 day trial period.
* 
* This file was produced by an UNREGISTERED COPY of Parser Generator. It is
* for evaluation purposes only. If you continue to use Parser Generator 30
* days after installation then you are required to purchase a license. For
* more information see the online help or go to the Bumble-Bee Software
* homepage at:
* 
* http://www.bumblebeesoftware.com
* 
* This notice must remain present in the file. It cannot be removed.
****************************************************************************/

/****************************************************************************
* MessageParser.java
* Java source file generated from MessageParser.y.
* 
* Date: 11/11/16
* Time: 14:34:29
* 
* AYACC Version: 2.07
****************************************************************************/

// line 1 ".\\MessageParser.y"
package com.fd.proto.yl;
import java.io.IOException;
import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

import com.fd.proto.core.Constructor;
import com.fd.proto.entity.ProtoAttribute;
import com.fd.proto.entity.ProtoMessage;
import com.fd.proto.util.LuaGlobalUniqueNameMgr;

import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
/****************************************************************************
MessageParser.y
ParserWizard generated YACC file.

Date: 2016年11月11日
****************************************************************************/

// line 49 "MessageParser.java"
import yl.*;

/////////////////////////////////////////////////////////////////////////////
// MessageParser

public class MessageParser extends yyfparser {
// line 25 ".\\MessageParser.y"

	private AtomicInteger errCountor;
	private String rpFileName;
	private String rpParentPath;
	private String rpRootPath;
	private Set<String> attrsSet;//属性名集合，防重名使用
	private Set<String> messageNameSet;//消息名集合，同一个rp文件禁止重名
	private List<MessageType> types;
	private ProtoMessage protoMessage;

// line 67 "MessageParser.java"
	public MessageParser() {
		yytables();
// line 37 ".\\MessageParser.y"
	
	attrsSet = new HashSet<String>();
	messageNameSet = new HashSet<String>();
	types = new ArrayList<MessageType>();

// line 76 "MessageParser.java"
	}

	public class YYSTYPE extends yyattribute {
// line 44 ".\\MessageParser.y"

		public String value;

		public void yycopy(yyattribute source, boolean move) {
			YYSTYPE yy = (YYSTYPE)source;
			value = yy.value;
		}

// line 89 "MessageParser.java"
	}

	public static final int MODULE = 65537;
	public static final int IMP = 65538;
	public static final int PKG_OR_FILE_NAME = 65539;
	public static final int MSG = 65540;
	public static final int ATTR_NAME = 65541;
	public static final int DATA_TYPE = 65542;
	protected final YYSTYPE yyattribute(int index) {
		YYSTYPE attribute = (YYSTYPE)yyattributestackref[yytop + index];
		return attribute;
	}

	protected final yyattribute yynewattribute() {
		return new YYSTYPE();
	}

	protected final YYSTYPE[] yyinitdebug(int count) {
		YYSTYPE a[] = new YYSTYPE[count];
		for (int i = 0; i < count; i++) {
			a[i] = (YYSTYPE)yyattributestackref[yytop + i - (count - 1)];
		}
		return a;
	}

	protected static yyftables yytables = null;

	public final void yyaction(int action) {
		switch (action) {
		case 0:
			{
				YYSTYPE yya[];
				if (YYDEBUG) {
					yya = yyinitdebug(3);
				}
// line 71 ".\\MessageParser.y"
endCheck();
// line 127 "MessageParser.java"
			}
			break;
		case 1:
			{
				YYSTYPE yya[];
				if (YYDEBUG) {
					yya = yyinitdebug(4);
				}
// line 72 ".\\MessageParser.y"
endCheck();
// line 138 "MessageParser.java"
			}
			break;
		case 2:
			{
				YYSTYPE yya[];
				if (YYDEBUG) {
					yya = yyinitdebug(2);
				}
// line 73 ".\\MessageParser.y"
yyerror(" Miss [module|message] ? ");
// line 149 "MessageParser.java"
			}
			break;
		case 3:
			{
				YYSTYPE yya[];
				if (YYDEBUG) {
					yya = yyinitdebug(6);
				}
// line 77 ".\\MessageParser.y"
attrsSet.clear();if(!messageNameSet.add(yyattribute(2 - 5).value)){yyerror(" Message '"+yyattribute(2 - 5).value+"' is already import!");}protoMessage.setMessageName(yyattribute(2 - 5).value);
// line 160 "MessageParser.java"
			}
			break;
		case 4:
			{
				YYSTYPE yya[];
				if (YYDEBUG) {
					yya = yyinitdebug(2);
				}
// line 81 ".\\MessageParser.y"
if(!rpFileName.equals(yyattribute(1 - 1).value+".rp")){yyerror(" Message name must equals file name!");}attrsSet.clear();
// line 171 "MessageParser.java"
			}
			break;
		case 5:
			{
				YYSTYPE yya[];
				if (YYDEBUG) {
					yya = yyinitdebug(3);
				}
// line 85 ".\\MessageParser.y"

// line 182 "MessageParser.java"
			}
			break;
		case 6:
			{
				YYSTYPE yya[];
				if (YYDEBUG) {
					yya = yyinitdebug(6);
				}
// line 86 ".\\MessageParser.y"
onAttribute(yyattribute(1 - 5).value,yyattribute(4 - 5).value,true,false);
// line 193 "MessageParser.java"
			}
			break;
		case 7:
			{
				YYSTYPE yya[];
				if (YYDEBUG) {
					yya = yyinitdebug(6);
				}
// line 87 ".\\MessageParser.y"
onAttribute(yyattribute(1 - 5).value,yyattribute(4 - 5).value,true,true);
// line 204 "MessageParser.java"
			}
			break;
		case 8:
			{
				YYSTYPE yya[];
				if (YYDEBUG) {
					yya = yyinitdebug(4);
				}
// line 88 ".\\MessageParser.y"
onAttribute(yyattribute(1 - 3).value,yyattribute(2 - 3).value,false,false);
// line 215 "MessageParser.java"
			}
			break;
		case 9:
			{
				YYSTYPE yya[];
				if (YYDEBUG) {
					yya = yyinitdebug(4);
				}
// line 89 ".\\MessageParser.y"
onAttribute(yyattribute(1 - 3).value,yyattribute(2 - 3).value,false,true);
// line 226 "MessageParser.java"
			}
			break;
		case 10:
			{
				YYSTYPE yya[];
				if (YYDEBUG) {
					yya = yyinitdebug(4);
				}
// line 93 ".\\MessageParser.y"
onModule(yyattribute(2 - 3).value);
// line 237 "MessageParser.java"
			}
			break;
		case 11:
			{
				YYSTYPE yya[];
				if (YYDEBUG) {
					yya = yyinitdebug(4);
				}
// line 94 ".\\MessageParser.y"
onModule(yyattribute(2 - 3).value);
// line 248 "MessageParser.java"
			}
			break;
		case 12:
			{
				YYSTYPE yya[];
				if (YYDEBUG) {
					yya = yyinitdebug(3);
				}
// line 98 ".\\MessageParser.y"

// line 259 "MessageParser.java"
			}
			break;
		case 13:
			{
				YYSTYPE yya[];
				if (YYDEBUG) {
					yya = yyinitdebug(4);
				}
// line 99 ".\\MessageParser.y"
onImport(yyattribute(2 - 3).value);
// line 270 "MessageParser.java"
			}
			break;
		case 14:
			{
				YYSTYPE yya[];
				if (YYDEBUG) {
					yya = yyinitdebug(4);
				}
// line 100 ".\\MessageParser.y"
onImport(yyattribute(2 - 3).value);
// line 281 "MessageParser.java"
			}
			break;
		default:
			break;
		}
	}

	protected final void yytables() {
		yysstack_size = 100;
		yystack_max = 0;

		yycreatetables();
		yysymbol = yytables.yysymbol;
		yyrule = yytables.yyrule;
		yyreduction = yytables.yyreduction;
		yytokenaction = yytables.yytokenaction;
		yystateaction = yytables.yystateaction;
		yynontermgoto = yytables.yynontermgoto;
		yystategoto = yytables.yystategoto;
		yydestructorref = yytables.yydestructorref;
		yytokendestref = yytables.yytokendestref;
		yytokendestbaseref = yytables.yytokendestbaseref;
	}

	public static synchronized final void yycreatetables() {
		if (yytables == null) {
			yytables = new yyftables();

			if (YYDEBUG) {
				final yysymbol symbol[] = {
					new yysymbol("$end", 0),
					new yysymbol("\';\'", 59),
					new yysymbol("\'[\'", 91),
					new yysymbol("\']\'", 93),
					new yysymbol("\'{\'", 123),
					new yysymbol("\'}\'", 125),
					new yysymbol("error", 65536),
					new yysymbol("MODULE", 65537),
					new yysymbol("IMP", 65538),
					new yysymbol("PKG_OR_FILE_NAME", 65539),
					new yysymbol("MSG", 65540),
					new yysymbol("ATTR_NAME", 65541),
					new yysymbol("DATA_TYPE", 65542),
					new yysymbol(null, 0)
				};
				yytables.yysymbol = symbol;

				final String rule[] = {
					"$accept: program",
					"program: module message",
					"program: module imp message",
					"program: error",
					"message: MSG msgName \'{\' attr \'}\'",
					"msgName: ATTR_NAME",
					"attr: attr attr",
					"attr: DATA_TYPE \'[\' \']\' ATTR_NAME \';\'",
					"attr: ATTR_NAME \'[\' \']\' ATTR_NAME \';\'",
					"attr: DATA_TYPE ATTR_NAME \';\'",
					"attr: ATTR_NAME ATTR_NAME \';\'",
					"module: MODULE ATTR_NAME \';\'",
					"module: MODULE PKG_OR_FILE_NAME \';\'",
					"imp: imp imp",
					"imp: IMP PKG_OR_FILE_NAME \';\'",
					"imp: IMP ATTR_NAME \';\'"
				};
				yytables.yyrule = rule;
			}

			final yyreduction reduction[] = {
				new yyreduction(0, 1, -1),
				new yyreduction(1, 2, 0),
				new yyreduction(1, 3, 1),
				new yyreduction(1, 1, 2),
				new yyreduction(2, 5, 3),
				new yyreduction(3, 1, 4),
				new yyreduction(4, 2, 5),
				new yyreduction(4, 5, 6),
				new yyreduction(4, 5, 7),
				new yyreduction(4, 3, 8),
				new yyreduction(4, 3, 9),
				new yyreduction(5, 3, 10),
				new yyreduction(5, 3, 11),
				new yyreduction(6, 2, 12),
				new yyreduction(6, 3, 13),
				new yyreduction(6, 3, 14)
			};
			yytables.yyreduction = reduction;

			final yytokenaction tokenaction[] = {
				new yytokenaction(9, YYAT_SHIFT, 7),
				new yytokenaction(7, YYAT_SHIFT, 13),
				new yytokenaction(9, YYAT_SHIFT, 8),
				new yytokenaction(7, YYAT_SHIFT, 14),
				new yytokenaction(2, YYAT_SHIFT, 5),
				new yytokenaction(40, YYAT_SHIFT, 28),
				new yytokenaction(2, YYAT_SHIFT, 6),
				new yytokenaction(41, YYAT_SHIFT, 22),
				new yytokenaction(41, YYAT_SHIFT, 23),
				new yytokenaction(30, YYAT_SHIFT, 22),
				new yytokenaction(30, YYAT_SHIFT, 23),
				new yytokenaction(0, YYAT_SHIFT, 1),
				new yytokenaction(0, YYAT_SHIFT, 2),
				new yytokenaction(39, YYAT_SHIFT, 26),
				new yytokenaction(36, YYAT_SHIFT, 38),
				new yytokenaction(35, YYAT_SHIFT, 37),
				new yytokenaction(33, YYAT_SHIFT, 36),
				new yytokenaction(31, YYAT_SHIFT, 35),
				new yytokenaction(28, YYAT_SHIFT, 34),
				new yytokenaction(27, YYAT_SHIFT, 33),
				new yytokenaction(26, YYAT_SHIFT, 32),
				new yytokenaction(25, YYAT_SHIFT, 31),
				new yytokenaction(24, YYAT_SHIFT, 29),
				new yytokenaction(23, YYAT_SHIFT, 27),
				new yytokenaction(22, YYAT_SHIFT, 25),
				new yytokenaction(21, YYAT_ERROR, 0),
				new yytokenaction(17, YYAT_SHIFT, 7),
				new yytokenaction(16, YYAT_SHIFT, 21),
				new yytokenaction(14, YYAT_SHIFT, 20),
				new yytokenaction(13, YYAT_SHIFT, 19),
				new yytokenaction(8, YYAT_SHIFT, 15),
				new yytokenaction(6, YYAT_SHIFT, 12),
				new yytokenaction(5, YYAT_SHIFT, 11),
				new yytokenaction(3, YYAT_ACCEPT, 0)
			};
			yytables.yytokenaction = tokenaction;

			final yystateaction stateaction[] = {
				new yystateaction(-65525, true, YYAT_ERROR, 0),
				new yystateaction(0, false, YYAT_REDUCE, 3),
				new yystateaction(-65535, true, YYAT_ERROR, 0),
				new yystateaction(33, true, YYAT_ERROR, 0),
				new yystateaction(0, false, YYAT_DEFAULT, 9),
				new yystateaction(-27, true, YYAT_ERROR, 0),
				new yystateaction(-28, true, YYAT_ERROR, 0),
				new yystateaction(-65538, true, YYAT_ERROR, 0),
				new yystateaction(-65511, true, YYAT_ERROR, 0),
				new yystateaction(-65538, true, YYAT_ERROR, 0),
				new yystateaction(0, false, YYAT_REDUCE, 1),
				new yystateaction(0, false, YYAT_REDUCE, 12),
				new yystateaction(0, false, YYAT_REDUCE, 11),
				new yystateaction(-30, true, YYAT_ERROR, 0),
				new yystateaction(-31, true, YYAT_ERROR, 0),
				new yystateaction(0, false, YYAT_REDUCE, 5),
				new yystateaction(-96, true, YYAT_ERROR, 0),
				new yystateaction(-65512, true, YYAT_REDUCE, 13),
				new yystateaction(0, false, YYAT_REDUCE, 2),
				new yystateaction(0, false, YYAT_REDUCE, 14),
				new yystateaction(0, false, YYAT_REDUCE, 15),
				new yystateaction(-100, true, YYAT_DEFAULT, 24),
				new yystateaction(-67, true, YYAT_DEFAULT, 39),
				new yystateaction(-68, true, YYAT_DEFAULT, 40),
				new yystateaction(-103, true, YYAT_DEFAULT, 41),
				new yystateaction(-72, true, YYAT_ERROR, 0),
				new yystateaction(-39, true, YYAT_ERROR, 0),
				new yystateaction(-74, true, YYAT_ERROR, 0),
				new yystateaction(-41, true, YYAT_ERROR, 0),
				new yystateaction(0, false, YYAT_REDUCE, 4),
				new yystateaction(-65532, true, YYAT_REDUCE, 6),
				new yystateaction(-65524, true, YYAT_ERROR, 0),
				new yystateaction(0, false, YYAT_REDUCE, 10),
				new yystateaction(-65525, true, YYAT_ERROR, 0),
				new yystateaction(0, false, YYAT_REDUCE, 9),
				new yystateaction(-44, true, YYAT_ERROR, 0),
				new yystateaction(-45, true, YYAT_ERROR, 0),
				new yystateaction(0, false, YYAT_REDUCE, 8),
				new yystateaction(0, false, YYAT_REDUCE, 7),
				new yystateaction(-65528, true, YYAT_ERROR, 0),
				new yystateaction(-65536, true, YYAT_ERROR, 0),
				new yystateaction(-65534, true, YYAT_ERROR, 0)
			};
			yytables.yystateaction = stateaction;

			final yynontermgoto nontermgoto[] = {
				new yynontermgoto(4, 10),
				new yynontermgoto(0, 3),
				new yynontermgoto(30, 30),
				new yynontermgoto(21, 24),
				new yynontermgoto(4, 9),
				new yynontermgoto(0, 4),
				new yynontermgoto(17, 17),
				new yynontermgoto(9, 18),
				new yynontermgoto(8, 16)
			};
			yytables.yynontermgoto = nontermgoto;

			final yystategoto stategoto[] = {
				new yystategoto(0, -1),
				new yystategoto(0, -1),
				new yystategoto(0, -1),
				new yystategoto(0, -1),
				new yystategoto(-2, -1),
				new yystategoto(0, -1),
				new yystategoto(0, -1),
				new yystategoto(0, -1),
				new yystategoto(5, -1),
				new yystategoto(5, 17),
				new yystategoto(0, -1),
				new yystategoto(0, -1),
				new yystategoto(0, -1),
				new yystategoto(0, -1),
				new yystategoto(0, -1),
				new yystategoto(0, -1),
				new yystategoto(0, -1),
				new yystategoto(0, -1),
				new yystategoto(0, -1),
				new yystategoto(0, -1),
				new yystategoto(0, -1),
				new yystategoto(-1, -1),
				new yystategoto(0, -1),
				new yystategoto(0, -1),
				new yystategoto(0, 30),
				new yystategoto(0, -1),
				new yystategoto(0, -1),
				new yystategoto(0, -1),
				new yystategoto(0, -1),
				new yystategoto(0, -1),
				new yystategoto(-2, -1),
				new yystategoto(0, -1),
				new yystategoto(0, -1),
				new yystategoto(0, -1),
				new yystategoto(0, -1),
				new yystategoto(0, -1),
				new yystategoto(0, -1),
				new yystategoto(0, -1),
				new yystategoto(0, -1),
				new yystategoto(0, -1),
				new yystategoto(0, -1),
				new yystategoto(0, -1)
			};
			yytables.yystategoto = stategoto;

			yytables.yydestructorref = null;

			yytables.yytokendestref = null;

			yytables.yytokendestbaseref = null;
		}
	}
// line 102 ".\\MessageParser.y"


// programs section

	private void onModule(String moduleName){
		protoMessage.setModule(moduleName);
		String rpModule = moduleName.replaceAll("\\.","\\" + File.separator);
		if(!rpParentPath.endsWith(rpModule)){
			yyerror(" file module error ! The rp file must in the folder '"+rpModule+"' !");
			return;
		}
		this.rpRootPath = rpParentPath.substring(0,rpParentPath.lastIndexOf(rpModule)-1);
	}

	/**
	 * 设置属性
	 */
	private void onAttribute(String type,String name,boolean isArray,boolean isComplexType){
		checkAttributeName(name);
		if(isComplexType){ // 保存类型名用以检查类型是否存在
			types.add(new MessageType(yylexerref.yylineno,type));
		}
		if(protoMessage!=null){
			protoMessage.addAttr(new ProtoAttribute(type,name,isArray));
		}else{
			yyerror(" Current message is null .");
		}
	}

	/**
	 * 文件结束检查
	 */
	private void endCheck(){
		if(!LuaGlobalUniqueNameMgr.checkAndAdd(protoMessage)){
			yyerror(" Message name must be unique ! ");
		}
		for(MessageType type:types){
			boolean isExist = false;
			for(ProtoMessage childMessage:protoMessage.getChildMessages()){
				if(type.messageName.equals(protoMessage.getMessageName())){
					isExist = true;
					break;
				}
				if(type.messageName.equals(childMessage.getMessageName())){
					isExist = true;
					break;
				}
			}
			if(!isExist){
				yyerror(" Can't find message type '"+type.messageName+"' at line : "+type.lineNo, false);
			}
		}
	}

	/**
	 * 解析引用的文件
	 */
	private void onImport(String rpImportName){
		checkImportRepeat(rpImportName);
		if(!rpRootPath.endsWith(File.separator)){
			rpRootPath += File.separator;
		}
		String impFileName = rpImportName.replaceAll("\\.","\\"+File.separator) +".rp";
		String importFilePath = rpRootPath + impFileName;
		Constructor wc = new Constructor(importFilePath, errCountor, yyerr);
		if (wc.parse())
		{
			ProtoMessage impMessage = wc.getProtoMessage();
			if(impMessage!=null){
				protoMessage.addChildMessage(impMessage);
			}else{
				yyerror(" Return data of file '"+impFileName+"' is null!");
			}
		}
		else
		{
			yyerror(" Parse File '" + impFileName + "' error! cause:" + wc.getParseResult() +", error count : "+wc.getParseErrorCount());
		}
	}
	
	private void checkImportRepeat(String rpImportName){
		rpImportName = rpImportName.substring(rpImportName.lastIndexOf(".")+1,rpImportName.length());
		if(!messageNameSet.add(rpImportName)){
			yyerror(" Message '"+rpImportName+"' is already import!");
		}
	}
	
	/**
	 *检查属性名重复
	 */
	private void checkAttributeName(String name){
		if(!attrsSet.add(name)){
			yyerror(" Attribute '"+name+"' is duplicate!");
		}
	}

	@Override
	public void yyerror(String text) {
		yyerror(text, true);
	}

	private void yyerror(String text, boolean hasLineNo)
	{
		try
		{
			int id = errCountor.incrementAndGet();
			String head = "EID : " + id + ", File : '" + rpFileName + "'\n";
			String errorInfo = "Yacc error !" + text;
			String lineNoAndNearBy = "";
			if (hasLineNo)
			{
				lineNoAndNearBy = " at line : " + yylexerref.yylineno + " , near by : '" + new String(yylexerref.yytext, 0, yylexerref.yyleng);
			}
			yyerr.write(head + errorInfo + lineNoAndNearBy + "'\n");
			if (yyerrflush)
			{
				yyerr.flush();
			}
		}
		catch (IOException e) {
			// do nothing
		}
	}
	
	public void setErrorCountor(AtomicInteger errCountor){
		this.errCountor = errCountor;
	}
	
	public void setRpFilePath(String rpFilePath){
		int lastIndex = rpFilePath.lastIndexOf(File.separator);
		rpParentPath = rpFilePath.substring(0,lastIndex);
		rpFileName = rpFilePath.substring(lastIndex + 1, rpFilePath.length());
	}
	
	public void setProtoMessage(ProtoMessage protoMessage){
		this.protoMessage = protoMessage;
	}
	
	class MessageType {
		int lineNo;
		String messageName;
		public MessageType(int lineNo,String messageName){
			this.lineNo = lineNo;
			this.messageName = messageName;
		}
	}	
}
