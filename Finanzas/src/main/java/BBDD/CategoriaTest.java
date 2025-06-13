package BBDD;

//@author AlejandroGpublic

import org.junit.Test;

import java.time.YearMonth;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CategoriaTest {

    @Test
   public  void testObtenerGastosPorMes() {
        Usuario mockUsuario = new Usuario();
        mockUsuario.setId(1);

        CategoriaService service = new CategoriaService();
        Map<String, Integer> resultado = service.obtenerGastosPorMes(mockUsuario, YearMonth.of(2025, 6));

        System.out.println(" Resultado de obtenerGastosPorMes: " + resultado);

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty(), "El mapa no debería estar vacío");
        assertTrue(resultado.containsKey("Comida"), "Debe contener la categoría Comida");
        assertEquals(100, resultado.get("Comida"));
    }

    @Test
    public void testObtenerGastoTotalPorUsuario() {
        Usuario mockUsuario = new Usuario();
        mockUsuario.setId(1);
        Categoria c;

        CategoriaService service = new CategoriaService();
        int total = service.obtenerGastoTotalDelMes(mockUsuario, YearMonth.of(2025, 6));
        System.out.println(" Resultado de obtenerGastoTotalPorUsuario: " + total);

        assertTrue(total > 0, "El total debería ser mayor que 0");
        assertEquals(750, total);  // Suponiendo que hay 2 categorías: 100 + 50
    }

}
