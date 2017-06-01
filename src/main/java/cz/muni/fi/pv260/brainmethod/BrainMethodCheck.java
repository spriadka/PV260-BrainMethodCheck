package cz.muni.fi.pv260.brainmethod;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import cz.muni.fi.pv260.brainmethod.visitor.*;

import java.util.HashSet;
import java.util.Set;

public class BrainMethodCheck extends AbstractCheck {

    private static final int DEFAULT_LOC_MAX = 20;
    private static final int DEFAULT_CYCLOMATIC_COMPLEXITY_MAX = 2;
    private static final int DEFAULT_NESTED_DEPTH_MAX = 2;
    private static final int DEFAULT_VARIABLE_COUNT_MAX = 2;

    private boolean methodEntered = false;
    private boolean switchBlockAsSingleDecisionPoint = false;

    private Set<Integer> acceptableTokens;
    private Set<AbstractCheckVisitor> visitors;

    private int methodLengthMax = DEFAULT_LOC_MAX;
    private int cyclomaticComplexityMax = DEFAULT_CYCLOMATIC_COMPLEXITY_MAX;
    private int nestedControlDepthMax = DEFAULT_NESTED_DEPTH_MAX;
    private int variableCountMax = DEFAULT_VARIABLE_COUNT_MAX;

    public void setMethodLengthMax(int locCount){
        methodLengthMax = locCount;
    }

    public void setCyclomaticComplexityMax(int cyclo){
        cyclomaticComplexityMax = cyclo;
    }

    public void setNestedControlDepthMax(int nested){
        nestedControlDepthMax = nested;
    }

    public void setVariableCountMax(int varCount){
        variableCountMax = varCount;
    }

    public void setSwitchBlockAsSingleDecisionPoint(boolean switchBlockAsSingleDecisionPoint){
        this.switchBlockAsSingleDecisionPoint = switchBlockAsSingleDecisionPoint;
    }

    @Override
    public void init() {
        visitors = new HashSet<>();
        visitors.add(new CyclomaticComplexityVisitor(this,switchBlockAsSingleDecisionPoint,cyclomaticComplexityMax));
        visitors.add(new NestedControlDepthVisitor(this,nestedControlDepthMax));
        visitors.add(new VariableCountVisitor(this,variableCountMax));
        visitors.add(new MethodLengthVisitor(this,methodLengthMax));
        acceptableTokens = new HashSet<>();
        acceptableTokens.add(TokenTypes.METHOD_DEF);
        acceptableTokens.add(TokenTypes.CTOR_DEF);
        visitors.forEach(visitor -> acceptableTokens.addAll(visitor.getAcceptableTokens()));
    }

    public boolean isMethodEntered(){
        return methodEntered;
    }

    public int[] getDefaultTokens() {
        return acceptableTokens.stream().mapToInt(i->i).toArray();
    }

    @Override
    public void visitToken(DetailAST ast) {
        switch (ast.getType()){
            case TokenTypes.CTOR_DEF:
            case TokenTypes.METHOD_DEF:
                methodEntered = true;
                break;
            default:
                break;
        }
        visitors.forEach(visitor -> visitor.visitToken(ast));
    }

    @Override
    public void leaveToken(DetailAST ast) {
        visitors.forEach(visitor -> visitor.leaveToken(ast));
        switch (ast.getType()){
            case TokenTypes.CTOR_DEF:
            case TokenTypes.METHOD_DEF:
                leaveMethodDef(ast);
                break;
            default:
                break;
        }
    }

    private void leaveMethodDef(DetailAST ast) {
        methodEntered = false;
        visitors.forEach(visitor -> {
            VisitReport report = visitor.getReport();
            if (report.hasFailed()){
                log(ast,report.getMessage());
            }
        });
        visitors.forEach(AbstractCheckVisitor::reset);
    }
}
