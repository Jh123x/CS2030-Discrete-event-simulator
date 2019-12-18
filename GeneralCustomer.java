class GeneralCustomer extends Customer{
    public GeneralCustomer(int id, double arriveTime, double custReTime){
        super(id, arriveTime);
        this.type = CustomerType.GENERAL;
        this.reTime = custReTime;
    }
}