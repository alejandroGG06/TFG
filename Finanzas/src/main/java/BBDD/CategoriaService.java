package BBDD;

//@author AlejandroGpublic

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class CategoriaService {

    EntityManager em = Conector.getEntityManager();

    public int obtenerGastoTotalDelMes(Usuario usuario, String mes) {
        int total = 0;

        try {
            List<Integer> lista = em.createQuery(
                            "SELECT c.inicial FROM Categoria c WHERE c.usuario.id = :id AND c.mes = :mes", Integer.class)
                    .setParameter("id", usuario.getId())
                    .setParameter("mes", mes)
                    .getResultList();

            for (Integer gasto : lista) {
                if (gasto != null) total += gasto;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return total;
    }

    public List<String> obtenerMesesUnicos(Usuario usuario) {
        List<String> meses = new ArrayList<>();
        try {
            meses = em.createQuery(
                            "SELECT DISTINCT c.mes FROM Categoria c WHERE c.usuario.id = :id ORDER BY c.mes ASC", String.class)
                    .setParameter("id", usuario.getId())
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return meses;
    }


    public Map<String, String> categoriassPorMes(Usuario usuario, String mes) {
        Map<String, String> map = new HashMap<>();

        try {
            List<Categoria> lista = em.createQuery(
                            "SELECT c FROM Categoria c WHERE c.usuario.id = :id AND c.mes = :mes ORDER BY c.nombre", Categoria.class)
                    .setParameter("id", usuario.getId())
                    .setParameter("mes", mes)
                    .getResultList();

            for (Categoria c : lista) {
                map.put(c.getNombre(), c.getFecha());  // O puedes poner c.getInicial().toString()
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

    public Map<String, Integer> obtenerGastosPorMes(Usuario usuario, String mes) {
        Map<String, Integer> resultados = new HashMap<>();

        try {
            List<Object[]> lista = em.createQuery(
                            "SELECT c.nombre, c.inicial FROM Categoria c " +
                                    "WHERE c.usuario.id = :id AND c.mes = :mes", Object[].class)
                    .setParameter("id", usuario.getId())
                    .setParameter("mes", mes)
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




