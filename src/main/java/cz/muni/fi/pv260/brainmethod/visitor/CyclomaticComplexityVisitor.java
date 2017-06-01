package cz.muni.fi.pv260.brainmethod.visitor;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import cz.muni.fi.pv260.brainmethod.BrainMethodCheck;

import java.util.Arrays;
import java.util.stream.Collectors;

public class CyclomaticComplexityVisitor extends AbstractCheckVisitor {

    private boolean switchBlockAsSingleDecisionPoint;
    private int currentCyclomaticComplexity;
    private int max;

    public CyclomaticComplexityVisitor(BrainMethodCheck check, boolean switchBlockAsSingleDecisionPoint, int max){
        super(check);
        int[] tokens = new int[]{TokenTypes.INSTANCE_INIT,
                TokenTypes.STATIC_INIT,
                TokenTypes.LITERAL_WHILE,
                TokenTypes.LITERAL_DO,
                TokenTypes.LITERAL_FOR,
                TokenTypes.LITERAL_IF,
                TokenTypes.LITERAL_SWITCH,
                TokenTypes.LITERAL_CASE,
                TokenTypes.LITERAL_CATCH,
                TokenTypes.QUESTION,
                TokenTypes.LAND,
                TokenTypes.LOR};
        acceptableTokens.addAll(Arrays.stream(tokens).boxed().collect(Collectors.toSet()));
        currentCyclomaticComplexity = 0;
        this.max = max;
        this.switchBlockAsSingleDecisionPoint = switchBlockAsSingleDecisionPoint;
        report = new VisitReport(true,"CyclomaticComplexity","");
    }



    @Override
    public void visitToken(DetailAST ast) {
        if (brainMethodCheck.isMethodEntered()){
            if (!acceptableTokens.contains(ast.getType())){
                return;
            }
            visitHook(ast);
        }
    }

    private void visitHook(DetailAST ast){
        if (switchBlockAsSingleDecisionPoint) {
            if (ast.getType() != TokenTypes.LITERAL_CASE) {
                ++currentCyclomaticComplexity;
            }
        }
        else if (ast.getType() != TokenTypes.LITERAL_SWITCH) {
            ++currentCyclomaticComplexity;
        }
    }

    @Override
    public void leaveToken(DetailAST ast) {
        if (!acceptableTokens.contains(ast.getType())){
            return;
        }
        if (currentCyclomaticComplexity > max / 2) {
            String message = String.format("Cyclomatic complexity exceeds limit, got %d, maximum allowed id %d",currentCyclomaticComplexity,max);
            report = new VisitReport(false,"Cyclomatic Complexity",message);
        }
    }

    @Override
    public void reset() {
        currentCyclomaticComplexity = 0;
        report = new VisitReport(true,name,"");
    }
}
