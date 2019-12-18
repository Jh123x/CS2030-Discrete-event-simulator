enum CustomerType{
    ELDERLY(0),GENERAL(1);
    private int type;
    private CustomerType(int i){
        type = i;
    }
    public int getType(){
        return this.type;
    }
}