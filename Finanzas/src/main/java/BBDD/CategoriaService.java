package BBDD;

//@author AlejandroGpublic

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.sql.Date;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class CategoriaService {

    EntityManager em = Conector.getEntityManager();

    public int obtenerGastoTotalDelMes(Usuario usuario, YearMonth mes) {
        int total = 0;
        try {
            LocalDate desde = mes.atDay(1);
            LocalDate hasta = mes.atEndOfMonth();

            List<Integer> lista = em.createQuery(
                            "SELECT c.inicial FROM Categoria c WHERE c.usuario.id = :id AND c.fecha BETWEEN :desde AND :hasta", Integer.class)
                    .setParameter("id", usuario.getId())
                    .setParameter("desde", desde)
                    .setParameter("hasta", hasta)
                    .getResultList();

            for (Integer gasto : lista) {
                if (gasto != null) total += gasto;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }

    public List<YearMonth> obtenerMesesUnicos(Usuario usuario) {
        List<LocalDate> fechas = em.createQuery(
                        "SELECT DISTINCT c.fecha FROM Categoria c WHERE c.usuario.id = :id ", LocalDate.class)
                .setParameter("id", usuario.getId())
                .getResultList();

        return fechas.stream()
                .map(YearMonth::from)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    public Map<String, String> categoriassPorMes(Usuario usuario, YearMonth mes) {
        Map<String, String> map = new HashMap<>();
        LocalDate desde = mes.atDay(1);
        LocalDate hasta = mes.atEndOfMonth();

        try {
            List<Categoria> lista = em.createQuery(
                            "SELECT c FROM Categoria c WHERE c.usuario.id = :id AND c.fecha BETWEEN :desde AND :hasta ORDER BY c.nombre", Categoria.class)
                    .setParameter("id", usuario.getId())
                    .setParameter("desde", desde)
                    .setParameter("hasta", hasta)
                    .getResultList();

            for (Categoria c : lista) {
                map.put(c.getNombre(), c.getFecha().toString());  // Puedes cambiar por c.getInicial() si quieres mostrar montos
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public void crearCategoria(Categoria categoria) {
        try {
            em.getTransaction().begin();
            em.persist(categoria);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
    }


    public Map<String, Integer> obtenerGastosPorMes(Usuario usuario, YearMonth mes) {
        Map<String, Integer> resultados = new HashMap<>();
        LocalDate desde = mes.atDay(1);
        LocalDate hasta = mes.atEndOfMonth();

        try {
            List<Object[]> lista = em.createQuery(
                            "SELECT c.nombre, c.inicial FROM Categoria c WHERE c.usuario.id = :id AND c.fecha BETWEEN :desde AND :hasta ", Object[].class)
                    .setParameter("id", usuario.getId())
                    .setParameter("desde", desde)
                    .setParameter("hasta", hasta)
                    .getResultList();

            for (Object[] fila : lista) {
                String nombre = (String) fila[0];
                Integer gasto = (Integer) fila[1];
                if (gasto != null) {
                    resultados.merge(nombre, gasto, Integer::sum);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultados;
    }
}



