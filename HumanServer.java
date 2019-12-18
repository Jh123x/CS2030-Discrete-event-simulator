class HumanServer extends Server{
    private double restTime; 
    protected boolean rest;
    protected double probRest;

    public HumanServer(int id,int servMaxCust, int servQueueSize, double probRest){
        super(id, servMaxCust, servQueueSize,ServerType.HUMAN);
        this.probRest = probRest;
    }
    public void initRest(){
        this.rest = true;
    }
    @Override
    public boolean isBusy(){
        return super.isBusy() || this.rest;
    }

    public double getRestTime(){
        return this.restTime;
    }

    public double getRestRate(){
        return this.probRest;
    }

    public boolean getResting(){
        return this.rest;
    }  

    public void doRest(){
        this.rest = true;
        this.restTime = Server.randomizer.restTime();
    }

    public void doBack() {
        this.rest = false;
    }

}