public enum OrderStatus {
    WAITING("대기"), COMPLETED("완료");

    private String message;

    OrderStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
