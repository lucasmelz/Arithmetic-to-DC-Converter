grammar Arithmetic;

prog: stat+;

stat:
	expr NEWLINE			# printExpr
	| assignment NEWLINE	# assignStat
	| expr ';'				# printExprSemi
	| assignment ';'		# assignStatSemi
	| NEWLINE				# emptyStat;

assignment: ID '=' expr;

expr:
	expr op = ('*' | '/') expr		# MulDiv
	| expr op = ('+' | '-') expr	# AddSub
	| ID							# Var
	| INT							# Int
	| '(' expr ')'					# Parens;

ID: [a-zA-Z][a-zA-Z0-9_]*;
INT: [0-9]+;
NEWLINE: '\r'? '\n';
WS: [ \t]+ -> skip;