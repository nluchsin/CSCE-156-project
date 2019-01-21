package unl.cse.financial;

public class PortfolioListNode {

	// most should be self-explanatory.
	
    private PortfolioListNode next;
    private Portfolio item;

    //constructor for a Portfolio List Node.
    public PortfolioListNode(Portfolio item) {
        this.item = item;
        this.next = null;
    }

    public Portfolio getPortfolio() {
        return item;
    }

    public PortfolioListNode getNext() {
        return next;
    }

    public void setNext(PortfolioListNode next) {
        this.next = next;
    }
}
