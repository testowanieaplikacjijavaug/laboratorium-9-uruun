package laboratorium.uruun;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class MessengerTest {
    private Messenger messenger;
    private TemplateEngine tplEngine;
    private MailServer mailServer;
    private Client client;
    private Template tpl;

    @BeforeEach
    void setup() {
        tplEngine = mock(TemplateEngine.class);
        client = new Client("abc@xyz.com");
        tpl = new Template();
    }

    @Test
    public void testSendMessage() {
        mailServer = mock(MailServer.class);
        messenger = new Messenger(mailServer, tplEngine);
        when(tplEngine.prepareMessage(tpl, client)).thenReturn("stub message");
        messenger.sendMessage(client, tpl);
        verify(mailServer).send("abc@xyz.com", "stub message");
    }

    @Test
    public void testSendMessageWithSpy() {
        mailServer = new MailServer();
        MailServer spyServer = spy(mailServer);
        messenger = new Messenger(spyServer, tplEngine);
        when(tplEngine.prepareMessage(tpl, client)).thenReturn("stub message");
        doThrow(new NullPointerException()).when(spyServer).send(any(String.class), any(String.class));
        assertThrows(NullPointerException.class, () -> messenger.sendMessage(client, tpl));
    }

    @Test
    public void testSendMessageWithCaptor() {
        ArgumentCaptor<String> arg1 = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> arg2 = ArgumentCaptor.forClass(String.class);
        mailServer = mock(MailServer.class);
        messenger = new Messenger(mailServer, tplEngine);
        when(tplEngine.prepareMessage(tpl, client)).thenReturn("stub message");
        messenger.sendMessage(client, tpl);
        verify(mailServer).send(arg1.capture(), arg2.capture());
        assertAll(
                () -> assertEquals("abc@xyz.com", arg1.getValue()),
                () -> assertEquals("stub message", arg2.getValue())
        );
    }
}
