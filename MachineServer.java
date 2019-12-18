class MachineServer extends Server{
    public MachineServer(int ID, int servMaxCust, int servQueueSize){
        super(ID, servMaxCust, servQueueSize,ServerType.MACHINE);
    }

    @Override
    public boolean getResting() {
        return false;
    }

    @Override
    public double getRestRate() {
        return 0;
    }

    @Override
    public void initRest() {
        return;
    }

    @Override
    public double getRestTime() {
        assert false;
        return 0;
    }
}