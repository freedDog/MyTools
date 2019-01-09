package com.fd.proto.yl;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

import yl.yyflexer;

/////////////////////////////////////////////////////////////////////////////
//MessageLexer

public class MessageLexer extends yyflexer {
	// line 19 ".\\MessageLexer.l"

	private AtomicInteger errCountor;
	private String fileName;

	// line 52 "MessageLexer.java"
	public MessageLexer() {
		yytables();
		// line 25 ".\\MessageLexer.l"

		// place any extra initialisation code here

		// line 60 "MessageLexer.java"
	}

	public static final int INITIAL = 0;

	protected static yyftables yytables = null;

	public final int yyaction(int action) {
		// line 58 ".\\MessageLexer.l"

		// extract yylval for use later on in actions
		MessageParser.YYSTYPE yylval = (MessageParser.YYSTYPE) yyparserref.yylvalref;

		// line 73 "MessageLexer.java"
		yyreturnflg = true;
		switch (action) {
		case 1: {
			// line 67 ".\\MessageLexer.l"
			yylvalFormat(yylval);
			return MessageParser.DATA_TYPE;
			// line 80 "MessageLexer.java"
		}
		case 2: {
			// line 68 ".\\MessageLexer.l"
			yylvalFormat(yylval);
			return MessageParser.DATA_TYPE;
			// line 86 "MessageLexer.java"
		}
		case 3: {
			// line 69 ".\\MessageLexer.l"
			yylvalFormat(yylval);
			return MessageParser.DATA_TYPE;
			// line 92 "MessageLexer.java"
		}
		case 4: {
			// line 70 ".\\MessageLexer.l"
			yylvalFormat(yylval);
			return MessageParser.DATA_TYPE;
			// line 98 "MessageLexer.java"
		}
		case 5: {
			// line 71 ".\\MessageLexer.l"
			yylvalFormat(yylval);
			return MessageParser.DATA_TYPE;
			// line 104 "MessageLexer.java"
		}
		case 6: {
			// line 72 ".\\MessageLexer.l"
			yylvalFormat(yylval);
			return MessageParser.DATA_TYPE;
			// line 110 "MessageLexer.java"
		}
		case 7: {
			// line 73 ".\\MessageLexer.l"
			yylvalFormat(yylval);
			return MessageParser.DATA_TYPE;
			// line 116 "MessageLexer.java"
		}
		case 8: {
			// line 74 ".\\MessageLexer.l"
			yylvalFormat(yylval);
			return MessageParser.DATA_TYPE;
			// line 122 "MessageLexer.java"
		}
		case 9: {
			// line 75 ".\\MessageLexer.l"
			yylvalFormat(yylval);
			return MessageParser.DATA_TYPE;
			// line 128 "MessageLexer.java"
		}
		case 10: {
			// line 78 ".\\MessageLexer.l"
			yylvalFormat(yylval);
			return MessageParser.MODULE;
			// line 134 "MessageLexer.java"
		}
		case 11: {
			// line 79 ".\\MessageLexer.l"
			yylvalFormat(yylval);
			return MessageParser.IMP;
			// line 140 "MessageLexer.java"
		}
		case 12: {
			// line 80 ".\\MessageLexer.l"
			yylvalFormat(yylval);
			return MessageParser.MSG;
			// line 146 "MessageLexer.java"
		}
		case 13: {
			// line 81 ".\\MessageLexer.l"
			yylvalFormat(yylval);
			return MessageParser.ATTR_NAME;
			// line 152 "MessageLexer.java"
		}
		case 14: {
			// line 82 ".\\MessageLexer.l"
			yylvalFormat(yylval);
			return MessageParser.PKG_OR_FILE_NAME;
			// line 158 "MessageLexer.java"
		}
		case 15: {
			// line 83 ".\\MessageLexer.l"
			return '{';
			// line 164 "MessageLexer.java"
		}
		case 16: {
			// line 84 ".\\MessageLexer.l"
			return '}';
			// line 170 "MessageLexer.java"
		}
		case 17: {
			// line 85 ".\\MessageLexer.l"
			return '[';
			// line 176 "MessageLexer.java"
		}
		case 18: {
			// line 86 ".\\MessageLexer.l"
			return ']';
			// line 182 "MessageLexer.java"
		}
		case 19: {
			// line 87 ".\\MessageLexer.l"
			return ';';
			// line 188 "MessageLexer.java"
		}
		case 20: {
			// line 91 ".\\MessageLexer.l"
			/* nothing */
			// line 194 "MessageLexer.java"
		}
			break;
		case 21: {
			// line 92 ".\\MessageLexer.l"
			/* nothing */
			// line 201 "MessageLexer.java"
		}
			break;
		case 22: {
			// line 93 ".\\MessageLexer.l"
			invalidCharacter();
			// line 208 "MessageLexer.java"
		}
			break;
		default:
			break;
		}
		yyreturnflg = false;
		return 0;
	}

	protected final void yytables() {
		yystext_size = 100;
		yysunput_size = 100;
		yytext_max = 0;
		yyunput_max = 0;

		yycreatetables();
		yymatch = yytables.yymatch;
		yytransition = yytables.yytransition;
		yystate = yytables.yystate;
		yybackup = yytables.yybackup;
	}

	public static synchronized final void yycreatetables() {
		if (yytables == null) {
			yytables = new yyftables();

			final short match[] = { 0 };
			yytables.yymatch = match;

			final yytransition transition[] = { new yytransition(0, 0), new yytransition(4, 1), new yytransition(4, 1),
					new yytransition(33, 19), new yytransition(25, 13), new yytransition(5, 1),
					new yytransition(30, 17), new yytransition(31, 17), new yytransition(27, 14),
					new yytransition(28, 15), new yytransition(29, 16), new yytransition(23, 7),
					new yytransition(32, 18), new yytransition(34, 19), new yytransition(26, 13),
					new yytransition(24, 10), new yytransition(35, 20), new yytransition(0, 23),
					new yytransition(36, 24), new yytransition(37, 25), new yytransition(38, 26),
					new yytransition(39, 27), new yytransition(40, 28), new yytransition(41, 29),
					new yytransition(4, 1), new yytransition(42, 30), new yytransition(43, 31),
					new yytransition(44, 32), new yytransition(45, 33), new yytransition(46, 34),
					new yytransition(47, 35), new yytransition(48, 36), new yytransition(49, 37),
					new yytransition(50, 38), new yytransition(51, 39), new yytransition(52, 40),
					new yytransition(53, 41), new yytransition(54, 42), new yytransition(6, 1), new yytransition(7, 1),
					new yytransition(8, 1), new yytransition(8, 1), new yytransition(8, 1), new yytransition(8, 1),
					new yytransition(8, 1), new yytransition(8, 1), new yytransition(8, 1), new yytransition(8, 1),
					new yytransition(8, 1), new yytransition(8, 1), new yytransition(55, 44), new yytransition(9, 1),
					new yytransition(56, 45), new yytransition(57, 46), new yytransition(58, 47),
					new yytransition(59, 48), new yytransition(60, 49), new yytransition(8, 1), new yytransition(8, 1),
					new yytransition(8, 1), new yytransition(8, 1), new yytransition(8, 1), new yytransition(8, 1),
					new yytransition(8, 1), new yytransition(8, 1), new yytransition(8, 1), new yytransition(8, 1),
					new yytransition(8, 1), new yytransition(8, 1), new yytransition(8, 1), new yytransition(8, 1),
					new yytransition(8, 1), new yytransition(8, 1), new yytransition(8, 1), new yytransition(8, 1),
					new yytransition(10, 1), new yytransition(8, 1), new yytransition(8, 1), new yytransition(8, 1),
					new yytransition(8, 1), new yytransition(8, 1), new yytransition(8, 1), new yytransition(8, 1),
					new yytransition(11, 1), new yytransition(61, 52), new yytransition(12, 1),
					new yytransition(62, 53), new yytransition(8, 1), new yytransition(63, 54), new yytransition(8, 1),
					new yytransition(13, 1), new yytransition(14, 1), new yytransition(15, 1), new yytransition(8, 1),
					new yytransition(16, 1), new yytransition(8, 1), new yytransition(8, 1), new yytransition(17, 1),
					new yytransition(8, 1), new yytransition(8, 1), new yytransition(18, 1), new yytransition(19, 1),
					new yytransition(8, 1), new yytransition(8, 1), new yytransition(8, 1), new yytransition(8, 1),
					new yytransition(8, 1), new yytransition(20, 1), new yytransition(8, 1), new yytransition(8, 1),
					new yytransition(8, 1), new yytransition(8, 1), new yytransition(8, 1), new yytransition(8, 1),
					new yytransition(8, 1), new yytransition(21, 1), new yytransition(6, 74), new yytransition(22, 1),
					new yytransition(8, 74), new yytransition(8, 74), new yytransition(8, 74), new yytransition(8, 74),
					new yytransition(8, 74), new yytransition(8, 74), new yytransition(8, 74), new yytransition(8, 74),
					new yytransition(8, 74), new yytransition(8, 74), new yytransition(64, 56),
					new yytransition(65, 57), new yytransition(66, 58), new yytransition(67, 59),
					new yytransition(68, 60), new yytransition(69, 61), new yytransition(70, 63),
					new yytransition(8, 74), new yytransition(8, 74), new yytransition(8, 74), new yytransition(8, 74),
					new yytransition(8, 74), new yytransition(8, 74), new yytransition(8, 74), new yytransition(8, 74),
					new yytransition(8, 74), new yytransition(8, 74), new yytransition(8, 74), new yytransition(8, 74),
					new yytransition(8, 74), new yytransition(8, 74), new yytransition(8, 74), new yytransition(8, 74),
					new yytransition(8, 74), new yytransition(8, 74), new yytransition(8, 74), new yytransition(8, 74),
					new yytransition(8, 74), new yytransition(8, 74), new yytransition(8, 74), new yytransition(8, 74),
					new yytransition(8, 74), new yytransition(8, 74), new yytransition(71, 64),
					new yytransition(72, 65), new yytransition(73, 68), new yytransition(74, 71),
					new yytransition(8, 74), new yytransition(4, 5), new yytransition(8, 74), new yytransition(8, 74),
					new yytransition(8, 74), new yytransition(8, 74), new yytransition(8, 74), new yytransition(8, 74),
					new yytransition(8, 74), new yytransition(8, 74), new yytransition(8, 74), new yytransition(8, 74),
					new yytransition(8, 74), new yytransition(8, 74), new yytransition(8, 74), new yytransition(8, 74),
					new yytransition(8, 74), new yytransition(8, 74), new yytransition(8, 74), new yytransition(8, 74),
					new yytransition(8, 74), new yytransition(8, 74), new yytransition(8, 74), new yytransition(8, 74),
					new yytransition(8, 74), new yytransition(8, 74), new yytransition(8, 74), new yytransition(8, 74),
					new yytransition(6, 6), new yytransition(6, 6), new yytransition(6, 6), new yytransition(6, 6),
					new yytransition(6, 6), new yytransition(6, 6), new yytransition(6, 6), new yytransition(6, 6),
					new yytransition(6, 6), new yytransition(6, 6), new yytransition(0, 0), new yytransition(0, 0),
					new yytransition(0, 0), new yytransition(0, 0), new yytransition(0, 0), new yytransition(0, 0),
					new yytransition(0, 0), new yytransition(6, 6), new yytransition(6, 6), new yytransition(6, 6),
					new yytransition(6, 6), new yytransition(6, 6), new yytransition(6, 6), new yytransition(6, 6),
					new yytransition(6, 6), new yytransition(6, 6), new yytransition(6, 6), new yytransition(6, 6),
					new yytransition(6, 6), new yytransition(6, 6), new yytransition(6, 6), new yytransition(6, 6),
					new yytransition(6, 6), new yytransition(6, 6), new yytransition(6, 6), new yytransition(6, 6),
					new yytransition(6, 6), new yytransition(6, 6), new yytransition(6, 6), new yytransition(6, 6),
					new yytransition(6, 6), new yytransition(6, 6), new yytransition(6, 6), new yytransition(0, 0),
					new yytransition(0, 0), new yytransition(0, 0), new yytransition(0, 0), new yytransition(6, 6),
					new yytransition(0, 0), new yytransition(6, 6), new yytransition(6, 6), new yytransition(6, 6),
					new yytransition(6, 6), new yytransition(6, 6), new yytransition(6, 6), new yytransition(6, 6),
					new yytransition(6, 6), new yytransition(6, 6), new yytransition(6, 6), new yytransition(6, 6),
					new yytransition(6, 6), new yytransition(6, 6), new yytransition(6, 6), new yytransition(6, 6),
					new yytransition(6, 6), new yytransition(6, 6), new yytransition(6, 6), new yytransition(6, 6),
					new yytransition(6, 6), new yytransition(6, 6), new yytransition(6, 6), new yytransition(6, 6),
					new yytransition(6, 6), new yytransition(6, 6), new yytransition(6, 6) };
			yytables.yytransition = transition;

			final yystate state[] = { new yystate(0, 0, 0), new yystate(-3, -8, 0), new yystate(1, 0, 0),
					new yystate(0, 0, 22), new yystate(0, 0, 20), new yystate(0, 156, 22), new yystate(74, 145, 14),
					new yystate(0, -36, 22), new yystate(74, 0, 13), new yystate(0, 0, 19), new yystate(74, -101, 13),
					new yystate(0, 0, 17), new yystate(0, 0, 18), new yystate(74, -107, 13), new yystate(74, -96, 13),
					new yystate(74, -102, 13), new yystate(74, -98, 13), new yystate(74, -103, 13),
					new yystate(74, -99, 13), new yystate(74, -98, 13), new yystate(74, -88, 13), new yystate(0, 0, 15),
					new yystate(0, 0, 16), new yystate(-23, 7, 21), new yystate(74, -96, 13), new yystate(74, -92, 13),
					new yystate(74, -96, 13), new yystate(74, -76, 13), new yystate(74, -95, 13),
					new yystate(74, -88, 13), new yystate(74, -87, 13), new yystate(74, -90, 13),
					new yystate(74, -83, 13), new yystate(74, -87, 13), new yystate(74, -71, 13),
					new yystate(74, -81, 13), new yystate(74, -74, 13), new yystate(74, -76, 13),
					new yystate(74, -68, 13), new yystate(74, -80, 13), new yystate(74, -63, 13),
					new yystate(74, -61, 13), new yystate(74, -74, 13), new yystate(74, 0, 5), new yystate(74, -53, 13),
					new yystate(74, -63, 13), new yystate(74, -64, 13), new yystate(74, -60, 13),
					new yystate(74, -55, 13), new yystate(74, -45, 13), new yystate(74, 0, 3), new yystate(74, 0, 2),
					new yystate(74, -24, 13), new yystate(74, -30, 13), new yystate(74, -26, 13), new yystate(74, 0, 6),
					new yystate(74, 31, 13), new yystate(74, 21, 13), new yystate(74, 14, 13), new yystate(74, 28, 13),
					new yystate(74, 35, 13), new yystate(74, 32, 13), new yystate(74, 0, 7), new yystate(74, 18, 13),
					new yystate(74, 58, 13), new yystate(74, 61, 13), new yystate(74, 0, 4), new yystate(74, 0, 9),
					new yystate(74, 53, 13), new yystate(74, 0, 8), new yystate(74, 0, 11), new yystate(74, 63, 13),
					new yystate(74, 0, 10), new yystate(74, 0, 1), new yystate(0, 70, 12) };
			yytables.yystate = state;

			final boolean backup[] = { false, false, false, false, false, false, false, false, false, false, false,
					false, false, false, false, false, false, false, false, false, false, false, false };
			yytables.yybackup = backup;
		}
	}
	// line 94 ".\\MessageLexer.l"

	// programs section

	private void yylvalFormat(MessageParser.YYSTYPE yylval) {
		yylval.value = new String(yytext, 0, yyleng);
	}

	private void invalidCharacter() {
		int id = errCountor.incrementAndGet();
		yyerror("EID : " + id + ", File : '" + fileName + "'\nLex error ! invalid character : '"
				+ new String(yytext, 0, yyleng) + "', at line " + yylineno);
	}

	public void setErrorCountor(AtomicInteger errCountor) {
		this.errCountor = errCountor;
	}

	public void setFilePath(String filePath) {
		int lastIndex = filePath.lastIndexOf(File.separator);
		fileName = filePath.substring(lastIndex + 1, filePath.length());
	}
}
