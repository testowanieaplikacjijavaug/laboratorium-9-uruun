package laboratorium.uruun;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.util.reflection.FieldSetter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FriendshipsMongoTest {

    FriendshipsMongo friendships;
    FriendsCollection friends;

    @BeforeEach
    public void setup() throws IllegalAccessException, InstantiationException, NoSuchFieldException {
        friendships = new FriendshipsMongo();
        friends = mock(FriendsCollection.class);
        FieldSetter.setField(friendships, friendships.getClass().getDeclaredField("friends"), friends);
    }

    @Test
    public void mockingWorksAsExpected(){
        Person joe = new Person("Joe");
        when(friends.findByName("Joe")).thenReturn(joe);
        assertThat(friends.findByName("Joe")).isEqualTo(joe);
    }

    @Test
    public void alexDoesNotHaveFriends(){
        Person alex = new Person("Alex");
        when(friends.findByName("Alex")).thenReturn(alex);
        assertThat(friendships.getFriendsList("Alex")).isEmpty();
    }

    @Test
    public void joeHas5Friends() {
        List<String> expected = Arrays.asList("Karol", "Dawid", "Maciej", "Tomek", "Adam");
        Person joe = mock(Person.class);
        when(friends.findByName("Joe")).thenReturn(joe);
        when(joe.getFriends()).thenReturn(expected);
        assertThat(friendships.getFriendsList("Joe")).hasSize(5).containsOnly("Karol", "Dawid", "Maciej", "Tomek", "Adam");
    }

    @Test
    public void test_areFriends() {
        Person karol = mock(Person.class);
        when(friends.findByName("Karol")).thenReturn(karol);
        when(karol.getFriends()).thenReturn(Collections.singletonList("Dawid"));
        assertThat(friendships.areFriends("Karol", "Dawid")).isTrue();
    }

    @Test
    public void test_addFriend() {
        Person karol = mock(Person.class);
        when(friends.findByName("Karol")).thenReturn(karol);
        karol.addFriend("Dawid");
        when(karol.getFriends()).thenReturn(Arrays.asList(new String[]{"Dawid"}));
        assertThat(friendships.getFriendsList("Karol")).containsOnly("Dawid");
    }

    @Test
    public void test_getName_null(){
        Person karol = mock(Person.class);
        doThrow(new NullPointerException()).when(karol).setName(null);
        assertThrows(NullPointerException.class, () -> karol.setName(null));
    }

    @Test
    public void test_getName(){
        Person karol = mock(Person.class);
        when(karol.getName()).thenReturn("Karol");
        assertThat(karol.getName()).isEqualTo("Karol");
    }
}