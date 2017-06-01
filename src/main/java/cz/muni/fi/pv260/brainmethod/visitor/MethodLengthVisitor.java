package cz.muni.fi.pv260.brainmethod.visitor;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import cz.muni.fi.pv260.brainmethod.BrainMethodCheck;

public class MethodLengthVisitor extends AbstractCheckVisitor {

    private int methodLength = 0;

    private int methodLengthMax;

    public MethodLengthVisitor(BrainMethodCheck brainMethodCheck, int methodLengthMax){
        super(brainMethodCheck);
        acceptableTokens.add(TokenTypes.METHOD_DEF);
        methodLength = 0;
        this.methodLengthMax = methodLengthMax;
    }

    @Override
    public void visitToken(DetailAST ast) {

    }

    @Override
    public void leaveToken(DetailAST ast) {
        if (ast.getType() != TokenTypes.METHOD_DEF){
            return;
        }
        int firstLine = ast.getLineNo();
        DetailAST endCurlyParanthesis = ast.findFirstToken(TokenTypes.SLIST).getLastChild();
        methodLength = endCurlyParanthesis.getLineNo() - firstLine;
        if (methodLength > methodLengthMax / 2){
            String message = String.format("Method length exceeds maximum method length allowed, got %d, maximum is %d",methodLength, methodLengthMax);
            report = new VisitReport(false,"Method Length",message);
        }
    }

    @Override
    public void reset() {
        methodLength = 0;
        report = new VisitReport(true,name,"");
    }
}
