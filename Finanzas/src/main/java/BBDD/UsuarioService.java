package BBDD;

//@author AlejandroGpublic

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class UsuarioService {

private EntityManager em= Conector.getEntityManager();

    public Usuario validarCredenciales(String nombre, String contrasena) {
        try {
            TypedQuery<Usuario> query = em.createQuery(
                    "SELECT u FROM Usuario u WHERE u.email = :correo AND u.password = :contraseña", Usuario.class);
            query.setParameter("correo", nombre);
            query.setParameter("contraseña", contrasena);

            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }


public void crearUsuario( Usuario usuario){

            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();
}


}
