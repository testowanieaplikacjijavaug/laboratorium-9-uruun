package laboratorium.uruun;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class CarTest {
    private Car myFerrari = mock(Car.class);

    @Test
    public void test_instance_car(){
        assertTrue(myFerrari instanceof Car);
    }

    @Test
    public void test_default_behavior_needsFuel(){
        assertFalse(myFerrari.needsFuel(), "New test double should return False as boolean");
    }

    @Test
    public void test_default_behavior_temperature(){
        assertEquals(0.0, myFerrari.getEngineTemperature(), "New test double should return 0.0");
    }

    @Test
    public void test_stubbing_mock(){
        when(myFerrari.needsFuel()).thenReturn(true);
        assertTrue(myFerrari.needsFuel());
    }

    @Test
    public void test_exception(){
        when(myFerrari.needsFuel()).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> myFerrari.needsFuel());
    }

    @Test
    public void testVerification(){
        myFerrari.driveTo("Kartuzy");
        myFerrari.needsFuel();

        verify(myFerrari).driveTo("Kartuzy");
        verify(myFerrari).needsFuel();
        assertFalse(myFerrari.needsFuel());
    }

    @Test
    public void testDefaultGetEngineTemperature() {
        when(myFerrari.getEngineTemperature()).thenReturn(20.0);
        double temperature = myFerrari.getEngineTemperature();
        verify(myFerrari).getEngineTemperature();
        assertEquals(20.0, temperature);
    }

    @Test
    public void testGetEngineTemperatureAfterDriveTo() {
        when(myFerrari.getEngineTemperature()).thenReturn(80.0);
        myFerrari.driveTo("Kartuzy");
        double temperature = myFerrari.getEngineTemperature();
        verify(myFerrari).driveTo("Kartuzy");
        verify(myFerrari).getEngineTemperature();
        assertEquals(80.0, temperature);
    }
}