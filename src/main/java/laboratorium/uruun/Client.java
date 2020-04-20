package laboratorium.uruun;

public class Client {
    private String email;
    public Client(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public void receive(Message message) {};
}
