package laboratorium.uruun;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.util.reflection.FieldSetter;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RaceResultServiceTest {
    private RaceResultService rrs;

    @BeforeEach
    public void setup() {
        rrs = new RaceResultService();
    }

    @Test
    public void testAddSubscriber() throws NoSuchFieldException {
        HashSet<Client> set = new HashSet<Client>();
        HashSet<Client> clients = spy(set);
        FieldSetter.setField(rrs, rrs.getClass().getDeclaredField("clients"), clients);
        Client c1 = new Client("abc@xyz.com");
        Client c2 = new Client("123@111.com");
        rrs.addSubscriber(c1);
        rrs.addSubscriber(c2);
        HashSet<Client> expected = new HashSet<Client>(Arrays.asList(c1, c2));
        assertTrue(expected.equals(clients));
    }

    @Test
    public void testSend() {
        Client c1 = mock(Client.class);
        rrs.addSubscriber(c1);
        Message message = new Message();
        rrs.send(message);
        verify(c1).receive(message);
    }

    @Test
    public void testRemoveSubscriber() throws NoSuchFieldException {
        HashSet<Client> set = new HashSet<Client>();
        HashSet<Client> clients = spy(set);
        FieldSetter.setField(rrs, rrs.getClass().getDeclaredField("clients"), clients);
        Client c1 = new Client("abc@xyz.com");
        Client c2 = new Client("123@111.com");
        rrs.addSubscriber(c1);
        rrs.addSubscriber(c2);
        rrs.removeSubscriber(c1);
        HashSet<Client> expected = new HashSet<Client>(Arrays.asList(c2));
        assertTrue(expected.equals(clients));
    }
}
