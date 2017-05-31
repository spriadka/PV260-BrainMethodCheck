package cz.muni.fi.pv260.checker;

import com.puppycrawl.tools.checkstyle.api.DetailAST;

import java.util.Collection;
import java.util.HashSet;

public abstract class AbstractCheckVisitor {
    protected VisitReport report;
    protected BrainMethodCheck brainMethodCheck;
    protected String name;
    protected Collection<Integer> acceptableTokens;

    public AbstractCheckVisitor(BrainMethodCheck check){
        this.brainMethodCheck = check;
        acceptableTokens = new HashSet<>();
    }

    public abstract void visitToken(DetailAST ast);

    public abstract void leaveToken(DetailAST ast);

    public abstract void reset();

    public VisitReport getReport(){
        return report;
    }

    public Collection<Integer> getAcceptableTokens(){
        return acceptableTokens;
    }
}
