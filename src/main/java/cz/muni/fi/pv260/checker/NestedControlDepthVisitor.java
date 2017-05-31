package cz.muni.fi.pv260.checker;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class NestedControlDepthVisitor extends AbstractCheckVisitor {

    private int max;
    private int depth;
    private final String MSG_KEY = "nested.control.depth";

    public NestedControlDepthVisitor(BrainMethodCheck check, int max){
        super(check);
        acceptableTokens.add(TokenTypes.LITERAL_FOR);
        acceptableTokens.add(TokenTypes.LITERAL_WHILE);
        acceptableTokens.add(TokenTypes.LITERAL_TRY);
        acceptableTokens.add(TokenTypes.LITERAL_IF);
        depth = 0;
        this.max = max;
        name = "Nested Control Depth";
        reset();
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (!acceptableTokens.contains(ast.getType())){
            return;
        }
        if (brainMethodCheck.isMethodEntered()){
            if (!acceptableTokens.contains(ast.getType())){
                return;
            }
            if (depth > max)
            {
                String message = String.format("Maximum nested control depth is %d, got %d",max,depth);
                report = new VisitReport(false,"Nested Control Depth",message);
            }
            ++depth;
        }
    }

    @Override
    public void leaveToken(DetailAST ast) {
        if (acceptableTokens.contains(ast.getType())) {
            --depth;
        }
    }

    @Override
    public void reset() {
        depth = 0;
        report = new VisitReport(true,name,"");
    }
}
