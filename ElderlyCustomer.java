class ElderlyCustomer extends Customer{
    public ElderlyCustomer(int ID, double arriveTime){
        super(ID, arriveTime);
        this.type = CustomerType.ELDERLY;
    }
}