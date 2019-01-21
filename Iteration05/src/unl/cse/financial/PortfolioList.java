package unl.cse.financial;

public class PortfolioList {
	private PortfolioListNode head = null;
	private PortfolioListNode tail = null;
	//TODO: add the head of your list here

	/**
	 * This function returns the size of the list, the number of
	 * elements currently stored in it.
	 * @return
	 */
	public int getSize() { 
    	PortfolioListNode current = head;
    	int counter = 0;
    	while(current!=null){
    		current=current.getNext();
    		counter++;
    	}
    	return counter;
	}

	/**
	 * This function clears out the contents of the list, making it an
	 * empty list.
	 */
    public void clear() {
    	head = null;
    }

    /**
     * This method adds the given {@link Portfolio} instance to the beginning
     * of the list.
     * @param t
     */
    public void addToStart(Portfolio t) {
    	PortfolioListNode newHead = new PortfolioListNode(t);
    	newHead.setNext(this.head);
    	this.head = newHead;
    }

    /**
     * This method adds the given {@link Portfolio} instance to the end of
     * the list.
     * @param t
     */
    public void addToEnd(Portfolio t) {
    	this.getTail();
    	if(this.tail != null){
		PortfolioListNode newTail = new PortfolioListNode(t);
		this.tail.setNext(newTail);
		this.tail = newTail;
    	}
    	else{
    		this.addToStart(t);
    	}
    }
    
    public void getTail(){
    	this.tail = getPortfolioListNode(this.getSize());
    }
    /**
     * This method removes the {@link Portfolio} from the given 
     * <code>position</code>, indices start at 0.  Implicitly, the 
     * remaining elements' indices are reduced.
     * @param position
     */
    public void remove(int position) {
    	PortfolioListNode previous = this.getPortfolioListNode(position-1);
    	PortfolioListNode after = this.getPortfolioListNode(position+1);
    	previous.setNext(after);
    }
    
    /**
     * This is a private helper method that returns a {@link PortfolioListNode}
     * corresponding to the given position.  Implementing this method
     * is optional but may help you with other methods.
     * @param position
     * @return current
     */
    private PortfolioListNode getPortfolioListNode(int position) {
    	if(position < 0 || position > this.getSize()) {
			throw new IllegalArgumentException("index out of bounds");
		}
		PortfolioListNode current = head;
		for(int i=0; i<position-1; i++) {
			current = current.getNext();
		}
		return current;
    }
//insertion function for inserting into the sorted linked list via name.
    public void insertPortfolioListNodeName(Portfolio portfolio) {
    	PortfolioListNode current = head;
		PortfolioListNode next = null;
		PortfolioListNode before = null;
		PortfolioListNode after = null;
		//sets nodes that are used for comparisons
		current = head;
		int i = 0;
		int test = 0;
		if(this.getSize() == 0){
			this.addToStart(portfolio);
		}
		else if(this.getSize() == 1){
			if(portfolio.compareToName(head.getPortfolio()) > 0){
				this.addToEnd(portfolio);
			}
			else{
				this.addToStart(portfolio);
			}
		}
		else{  //sets if the list is not empty or has only one node.
				while(i < this.getSize()) {
					if(i == 0){
						if(portfolio.compareToName(head.getPortfolio()) < 0){
							test = i;
							before = head;
							break;
						}
						else{
							test = 1;
							before = head;
						}
					}
					else if(portfolio.compareToName(current.getPortfolio()) > 0){
						test = i;
						before = current;
					}	
					current = current.getNext();
					i++;
				}  //test triggers this to do the actual insertion for the function.
				if(test != this.getSize()) {

					if(before == null){
						this.addToEnd(portfolio);
					} 
					else{
						if(test == 0){
							this.addToStart(portfolio);
						}
						else{
						next = before.getNext();
						PortfolioListNode new1 = new PortfolioListNode(portfolio);
						before.setNext(new1);
						new1.setNext(next);
						}
					}
				}
				if(i == this.getSize()){
					this.addToEnd(portfolio);
				}
			}
    }
    
  //comparison and insertion function for inserting into the sorted linked list via discriminated value.

    public void insertPortfolioListNodeValue(Portfolio portfolio) {
		PortfolioListNode current = head;
		PortfolioListNode next = null;
		PortfolioListNode before = null;
		PortfolioListNode after = null;
		current = head;
		int test = 0;
		int i = 0;
		if(this.getSize() == 0){
			this.addToStart(portfolio);
		}
		//compares the the values without a comparator as its simple numerical math.
		else if(this.getSize() == 1){
			if(portfolio.getTotalValue() < head.getPortfolio().getTotalValue()){
				this.addToEnd(portfolio);
			}
			else{
				this.addToStart(portfolio);
			}
		}
		//insertion part
		else{
				while(i < this.getSize()) {
					if(portfolio.getTotalValue() < current.getPortfolio().getTotalValue()){
						before = current;
						test = i;
					}
					current = current.getNext();
					i++;
				}
				if(portfolio.getTotalValue() >= head.getPortfolio().getTotalValue()){
					this.addToStart(portfolio);
				}
				else if(test != this.getSize() && before != null){
					next = before.getNext();
					PortfolioListNode new1 = new PortfolioListNode(portfolio);
					before.setNext(new1);
					new1.setNext(next);
				}
				else if(i == this.getSize()){
					this.addToEnd(portfolio);
				}
			}
		
    }
    
  //insertion function for inserting into the sorted linked list via manager letter and then their name if its ==

    
    public void insertPortfolioListManager(Portfolio portfolio) {
    	PortfolioListNode current = head;
		PortfolioListNode next = null;
		PortfolioListNode before = null;
		PortfolioListNode after = null;
		int i = 0;
		int test = 0;
		if(this.getSize() == 0){
			this.addToStart(portfolio);
		}
		else if(this.getSize() == 1){
			//inserts if the list is empty
			if(portfolio.compareToManagerLetter(head.getPortfolio()) < 0){
				this.addToEnd(portfolio);
			}
			else{
				this.addToStart(portfolio);
			}
		}    //compares if the list is not <= 1
		else{
				while(i < this.getSize()) {

					Manager m = portfolio.getManager();
					if(i == 0){
						if(portfolio.compareToManagerLetter(head.getPortfolio()) > 0){
							test = i;
							before = head;
							break;
						}
						else{
							test = 1;
							before = head;
						}
					}
					else if(portfolio.compareToManagerLetter(current.getPortfolio()) <= 0){
						test = i;
						before = current;
					}
					current = current.getNext();
					i++;
				}
				//inserts into it
				if(test < this.getSize()) {

					if(before == null){
						this.addToEnd(portfolio);
					} 
					else{
						if(test == 0){
							this.addToStart(portfolio);
						}
						else{
						next = before.getNext();
						PortfolioListNode new1 = new PortfolioListNode(portfolio);
						before.setNext(new1);
						new1.setNext(next);
						}
					}
				}
				if(i == this.getSize()){
					this.addToEnd(portfolio);
				}
			}
	}
    
    /**
     * Returns the {@link Portfolio} element stored at the given 
     * <code>position</code>.
     * @param position
     * @return
     */
    public Portfolio getPortfolio(int position) {
    	PortfolioListNode t = getPortfolioListNode(position);
    	Portfolio Portfolio = t.getPortfolio();
    	return Portfolio;
    }

    /**
     * Prints this list to the standard output.
     */
    public void print() {
    	StringBuilder sb = new StringBuilder();
    	if(this.head == null) {
			sb.append("[empty]");
		}
    	else{
    	for(int i = 1; i <= this.getSize(); i++){
    		PortfolioListNode t = getPortfolioListNode(i);
    		Portfolio portfolio = t.getPortfolio();
    		sb.append(i+" " +portfolio.toString()+"\n");
    	}
    	}
    	System.out.println(sb.toString());
    }
    public Double getTotalFees() {
    	StringBuilder sb = new StringBuilder();
    	Double fees = (double) 0;
    	for(int i = 1; i <= this.getSize(); i++){
    		PortfolioListNode t = getPortfolioListNode(i);
    		Portfolio portfolio = t.getPortfolio();
    		fees = fees+portfolio.getFee();
    	}
    	return fees;
    }
    public Double getCommission() {
    	StringBuilder sb = new StringBuilder();
    	Double commission = (double) 0;
    	for(int i = 1; i <= this.getSize(); i++){
    		PortfolioListNode t = getPortfolioListNode(i);
    		Portfolio portfolio = t.getPortfolio();
    		commission = commission+portfolio.getCommission();
    	}
    	return commission;
    }
    public Double getReturn() {
    	StringBuilder sb = new StringBuilder();
    	Double return1 = (double) 0;
    	for(int i = 1; i <= this.getSize(); i++){
    		PortfolioListNode t = getPortfolioListNode(i);
    		Portfolio portfolio = t.getPortfolio();
    		return1 = return1+portfolio.getAnnualReturn();
    	}
    	return return1;
    }
    public Double getTotal() {
    	StringBuilder sb = new StringBuilder();
    	Double return1 = (double) 0;
    	for(int i = 1; i <= this.getSize(); i++){
    		PortfolioListNode t = getPortfolioListNode(i);
    		Portfolio portfolio = t.getPortfolio();
    		return1 = return1+portfolio.getTotalValue();
    	}
    	return return1;
    }
}

