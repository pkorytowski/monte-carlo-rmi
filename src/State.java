public enum State {
    TRANSMITTED(0),
    ABSORBED(1),
    REFLECTED(2);
    private int num;


    State(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }

}
