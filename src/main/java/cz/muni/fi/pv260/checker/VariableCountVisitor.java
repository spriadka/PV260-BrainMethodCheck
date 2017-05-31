package cz.muni.fi.pv260.checker;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.HashSet;
import java.util.Set;

public class VariableCountVisitor extends AbstractCheckVisitor {

    private int max;
    private Set<String> variableNames;

    public VariableCountVisitor(BrainMethodCheck check, int max) {
        super(check);
        acceptableTokens.add(TokenTypes.VARIABLE_DEF);
        acceptableTokens.add(TokenTypes.PARAMETER_DEF);
        acceptableTokens.add(TokenTypes.EXPR);
        variableNames = new HashSet<>();
        this.max = max;
        name = "Variable Count";
        reset();
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (!acceptableTokens.contains(ast.getType())) {
            return;
        }
        DetailAST identifier = null;
        switch (ast.getType()) {
            case TokenTypes.VARIABLE_DEF:
            case TokenTypes.PARAMETER_DEF:
                identifier = ast.findFirstToken(TokenTypes.IDENT);
                break;
            default:
                if (ast.getParent().getType() == TokenTypes.VARIABLE_DEF) {
                    identifier = ast.getParent();
                    break;
                }
                if (ast.getChildCount() == 1){
                    break;
                }
                identifier = ast.getFirstChild().findFirstToken(TokenTypes.IDENT);
                break;
        }
        if (identifier != null){
            variableNames.add(identifier.getText());
        }
        if (variableNames.size() > max){
            String message = String.format("Found %d variables, maximum allowed is %d",variableNames.size(),max);
            report = new VisitReport(false,"Variable count exceeds line",message);
        }
    }

    @Override
    public void leaveToken(DetailAST ast) {

    }

    @Override
    public void reset() {
        variableNames.clear();
        report = new VisitReport(true,name,"");
    }

}
